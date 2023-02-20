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

    public CurrencyDTO depositFunds(Long id, Integer depositFunds) {
        Currency currency = currencyRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        currency.setQuantity((currency.getQuantity() + depositFunds));
        currencyRepo.save(currency);
        return CurrencyMapper.mapToDTO(currency);
    }

    public CurrencyDTO subtractQuantity(Long id, Integer subtract) throws Exception {
        Currency currency = currencyRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        currency.setQuantity(currency.getQuantity() - subtract);
        if (currency.getQuantity() < 0) {
            throw new Exception("Quantity is less than 0");
        }
        return CurrencyMapper.mapToDTO(currency);
    }

    public CurrencyDTO setZeroQuantity(Long id) {
        Currency currency = currencyRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        currency.setQuantity(0);
        currencyRepo.save(currency);
        return CurrencyMapper.mapToDTO(currency);
    }

    public CurrencyDTO setSpecificValueQuantity(Long id, Integer specificValueQuantity) {
        Currency currency = currencyRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        currency.setQuantity(specificValueQuantity);
        currencyRepo.save(currency);
        return CurrencyMapper.mapToDTO(currency);
    }


    public CurrencyDTO save(CurrencyDTO currencyDTO) {
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

    public void save(Currency currency) {
        currencyRepo.save(currency);
    }

}
