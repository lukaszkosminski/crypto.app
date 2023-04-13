package com.cryptoapp.service;

import com.cryptoapp.dto.CurrencyDTO;
import com.cryptoapp.dto.mapper.CurrencyMapper;
import com.cryptoapp.model.Currency;
import com.cryptoapp.repository.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class CurrencyService {

    private final CurrencyRepo currencyRepo;

    @Autowired
    public CurrencyService(CurrencyRepo currencyRepo) {
        this.currencyRepo = currencyRepo;
    }


    public CurrencyDTO save(CurrencyDTO currencyDTO) {
        Currency currency = CurrencyMapper.mapToCurrency(currencyDTO);
        if(currencyRepo.existsBySymbol(currency.getSymbol())) {
            throw new IllegalArgumentException("Currency with symbol " + currency.getSymbol() + " already exists.");
        }
        currencyRepo.save(CurrencyMapper.mapToCurrency(currencyDTO));
        return currencyDTO;
    }


    public List<CurrencyDTO> getAllCurrencies() {
        List<Currency> listCurrency = currencyRepo.findAll();
        List<CurrencyDTO> currencyDTOList = new ArrayList<>();
        for (Currency currency : listCurrency) {
            CurrencyDTO currencyDTO = CurrencyMapper.mapToDTO(currency);
            currencyDTOList.add(currencyDTO);
        }
        return currencyDTOList;
    }

    public CurrencyDTO getCurrencyById(Long idCurrency) {
        Currency currency = currencyRepo.findById(idCurrency).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return CurrencyMapper.mapToDTO(currency);
    }

    public void deleteCurrency(Long idCurrency) {
        currencyRepo.deleteById(idCurrency);
    }

    public Currency save(Currency currency) {
        return currencyRepo.save(currency);
    }

}
