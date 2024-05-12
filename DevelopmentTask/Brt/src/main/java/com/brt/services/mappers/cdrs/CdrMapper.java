package com.brt.services.mappers.cdrs;

import com.brt.models.cdrs.Cdr;
import com.brt.models.cdrs.dto.CdrDto;
import org.springframework.stereotype.Service;

/**
 * Service class for mapping between Cdr and CdrDto objects.
 */
@Service
public class CdrMapper {
    /**
     * Maps a Cdr object to a CdrDto object.
     * @param cdr The Cdr object to map
     * @return The mapped CdrDto object
     */
    public CdrDto cdrToDto(Cdr cdr) {
        CdrDto cdrDto = new CdrDto();

        cdrDto.setId(cdr.getId());
        cdrDto.setType(cdr.getType());
        cdrDto.setInternal(cdr.getInternal());
        cdrDto.setStartTime(cdr.getStartTime());
        cdrDto.setEndTime(cdr.getEndTime());
        cdrDto.setTariffId(cdr.getTariffId());
        cdrDto.setClientId(cdr.getClientId().getId());
        cdrDto.setCallerId(cdr.getCallerId());

        return cdrDto;
    }
}
