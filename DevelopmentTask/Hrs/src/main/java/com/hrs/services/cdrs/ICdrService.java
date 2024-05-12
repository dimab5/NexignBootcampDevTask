package com.hrs.services.cdrs;

import com.hrs.models.cdrs.CdrDto;

import java.util.List;

public interface ICdrService {
    List<CdrDto> parseCdr(String cdr);
    void handleCdrs(List<CdrDto> cdrs);
}
