package com.cdrGenerator.services.cdrs;

import com.cdrGenerator.models.cdrs.Cdr;

import java.io.IOException;

/**
 * Service interface for processing Cdrs.
 */
public interface ICdrProcessor {
    /**
     * Saves a CDR to the database.
     * @param cdr The CDR to save
     */
    void saveToDb(Cdr cdr);
    /**
     * Saves a CDR string to a file.
     * @param cdrString The string representation of the CDR
     * @param filePath The path of the file to save the CDR string to
     * @throws IOException If an I/O error occurs while writing to the file
     */
    void saveToFile(String cdrString, String filePath) throws IOException;
}
