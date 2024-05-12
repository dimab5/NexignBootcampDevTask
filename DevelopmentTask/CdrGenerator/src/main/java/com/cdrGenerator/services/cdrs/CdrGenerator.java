package com.cdrGenerator.services.cdrs;

import com.cdrGenerator.kafka.KafkaProducer;
import com.cdrGenerator.models.cdrs.Cdr;
import com.cdrGenerator.models.clients.Client;
import com.cdrGenerator.services.clients.IClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CdrGenerator implements ICdrGenerator {
    private static final int NUM_RECORDS_PER_FILE = 10;
    private static final long ONE_YEAR_IN_MILLISECONDS = 31622400000L;
    private static final long ONE_WEEK_IN_MILLISECONDS = 604800000;
    private static final String FILE_PATH = "CdrGenerator/generatedCdr/";

    private final ICdrProcessor cdrProcessor;
    private final IClientService clientService;
    private final KafkaProducer kafkaProducer;

    @Override
    public void generateCdr() {
        LocalDate startDate = LocalDate.of(2023, 4, 1);
        LocalDate endDate = startDate.plusYears(1);

        ExecutorService executor = Executors.newCachedThreadPool();

        int count = 0;

        while (startDate.isBefore(endDate)) {
            count++;

            List<Cdr> cdrs = generateRandomCdrRecords(NUM_RECORDS_PER_FILE / 2,
                    startDate.atStartOfDay().toInstant(ZoneOffset.UTC), endDate.atStartOfDay().toInstant(ZoneOffset.UTC));

            cdrs.sort(Comparator.comparing(Cdr::getEndTime));

            StringBuilder cdrFile = new StringBuilder();
            cdrs.forEach(cdr -> {
                cdrFile.append(cdrToString(cdr));
                cdrFile.append(cdrToString(generateReverseCdr(cdr)));
            });

            File directory = new File(FILE_PATH);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    throw new RuntimeException("Failed to create directory: " + FILE_PATH);
                }
            }

            String fileName = FILE_PATH + count + "_cdr.txt";

            Runnable saveToFileTask = () -> {
                try {
                    cdrProcessor.saveToFile(cdrFile.toString(), fileName);
                } catch (IOException e) {
                    throw new RuntimeException("Error saving CDR file: " + e.getMessage());
                }
            };

            Runnable saveToDbTask = () -> cdrs.stream()
                    .flatMap(cdr -> Stream.of(cdr, generateReverseCdr(cdr)))
                    .forEach(cdrProcessor::saveToDb);

            executor.submit(saveToFileTask);
            executor.submit(saveToDbTask);

            kafkaProducer.sendMessage(cdrFile.toString());

            startDate = LocalDate.ofInstant(Instant.ofEpochMilli(cdrs.get(cdrs.size() - 1)
                    .getEndTime()).plusMillis(1), ZoneOffset.UTC);
        }

        executor.shutdown();
    }

    private List<Cdr> generateRandomCdrRecords(int numRecords, Instant startDate, Instant endDate) {
        return Stream.generate(() -> generateRandomCdrRecord(null, startDate, endDate))
                .limit(numRecords)
                .collect(Collectors.toList());
    }

    private Cdr generateRandomCdrRecord(Instant previousEndTime, Instant startDate, Instant endDate) {
        Cdr cdrRecord = new Cdr();
        cdrRecord.setType(generateRandomCdrType());

        Client client = clientService.getRandomClient();
        cdrRecord.setClientId(client);

        cdrRecord.setCallerId(clientService.getRandomClientNonEqual(client.getId()));

        cdrRecord.setStartTime(previousEndTime != null ? generateStartTime(previousEndTime).toEpochMilli()
                : generateRandomDate(startDate, endDate).toEpochMilli());

        cdrRecord.setEndTime(generateEndTime(Instant.ofEpochMilli(cdrRecord.getStartTime())).toEpochMilli());
        return cdrRecord;
    }

    private Cdr generateReverseCdr(Cdr originalCdr) {
        Cdr reverseCdr = new Cdr();

        reverseCdr.setType(originalCdr.getType().equals("01") ? "02" : "01");

        reverseCdr.setClientId(originalCdr.getCallerId());
        reverseCdr.setCallerId(originalCdr.getClientId());

        reverseCdr.setStartTime(originalCdr.getStartTime());
        reverseCdr.setEndTime(originalCdr.getEndTime());

        return reverseCdr;
    }

    private Instant generateStartTime(Instant previousEndTime) {
        long startTime = previousEndTime.toEpochMilli() + (long) (Math.random() * 3600000);
        return Instant.ofEpochMilli(startTime);
    }

    private String cdrToString(Cdr cdr) {
        return String.format("%s,%s,%s,%s,%s%n",
                cdr.getType(),
                cdr.getClientId().getId(),
                cdr.getCallerId().getId(),
                cdr.getStartTime(),
                cdr.getEndTime());
    }

    private static Instant generateEndTime(Instant startTime) {
        long endTime = startTime.toEpochMilli() + (long) (Math.random() * 3600000);
        return Instant.ofEpochMilli(endTime);
    }

    private String generateRandomCdrType() {
        Random random = new Random();
        int randomNumber = random.nextInt(2) + 1;

        return String.format("%02d", randomNumber);
    }

    private Instant generateRandomDate(Instant startDate, Instant endDate) {
        long offset = (long) (Math.random() * (endDate.toEpochMilli() - startDate.toEpochMilli()));
        return Instant.ofEpochMilli(startDate.toEpochMilli() + offset);
    }
}

