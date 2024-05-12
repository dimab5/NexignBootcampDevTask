package com.brt.services.cdrs;

import com.brt.models.cdrs.Cdr;

import java.util.List;

public interface ICdrService {
    void addCdrInHistory(Cdr cdr);
    List<Cdr> parseCdrs(String cdrs);
    String createJsonCdr(List<Cdr> cdrs);
}
