package com.hrs.services.cdrs;

import com.hrs.models.cdrs.CdrDto;

import java.util.List;

/**
 * Service interface for handling Cdrs.
 */
public interface ICdrService {
    /**
     * Parses the provided CDR data and returns a list of CDR DTOs.
     * @param cdr The CDR data to parse
     * @return A list of CdrDto objects parsed from the provided CDR data
     */
    List<CdrDto> parseCdr(String cdr);
    /**
     * Handles a list of CDR DTOs.
     * @param cdrs The list of CDR DTOs to handle
     */
    void handleCdrs(List<CdrDto> cdrs);
}
