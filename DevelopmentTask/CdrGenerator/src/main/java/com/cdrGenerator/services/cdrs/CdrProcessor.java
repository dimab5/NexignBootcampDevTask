package com.cdrGenerator.services.cdrs;

import com.cdrGenerator.models.cdrs.Cdr;
import com.cdrGenerator.repositories.cdrs.CdrRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
@AllArgsConstructor
public class CdrProcessor implements ICdrProcessor {
    private final CdrRepository cdrRepository;

    @Override
    public void saveToDb(Cdr cdr) {
        cdrRepository.save(cdr);
    }

    @Override
    public void saveToFile(String cdrString, String filePath) {
        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(cdrString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
