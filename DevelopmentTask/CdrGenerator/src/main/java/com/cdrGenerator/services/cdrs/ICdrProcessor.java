package com.cdrGenerator.services.cdrs;

import com.cdrGenerator.models.cdrs.Cdr;

import java.io.IOException;

public interface ICdrProcessor {
    void saveToDb(Cdr cdr);
    void saveToFile(String cdrString, String filePath) throws IOException;
}
