package com.brt.services.cdrs;

import com.brt.models.cdrs.Cdr;

import java.util.List;

/**
 * Service interface for CDR operations.
 */
public interface ICdrService {
    /**
     * Adds a CDR to the history.
     * @param cdr The CDR to be added to the history
     */
    void addCdrInHistory(Cdr cdr);
    /**
     * Parses a string containing multiple CDRs.
     * @param cdrs The string containing CDR data
     * @return A list of parsed CDR objects
     */
    List<Cdr> parseCdrs(String cdrs);
    /**
     * Creates a JSON representation of a list of CDRs.
     * @param cdrs The list of CDRs to be converted to JSON
     * @return A JSON string representing the list of CDRs
     */
    String createJsonCdr(List<Cdr> cdrs);
}
