package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.ApiResponseForex;

public interface ICryptoService {
    ApiResponseForex getRealTimeCrypto(String symbol);

    ApiResponseForex getRealTimeCrypto(String symbol, String range, String datestart, String dateend);
}
