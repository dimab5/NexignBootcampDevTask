package com.hrs.kafka;

import com.hrs.services.cdrs.ICdrService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumer {
    private final ICdrService cdrService;

    @KafkaListener(topics = "cdr_enriched_brt_to_hrs", groupId = "group")
    public void listen(String message) {
        cdrService.handleCdrs(cdrService.parseCdr(message));
    }
}
