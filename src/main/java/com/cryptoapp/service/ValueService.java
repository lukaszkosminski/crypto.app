package com.cryptoapp.service;

import com.cryptoapp.dto.ValueDTO;
import com.cryptoapp.dto.mapper.ValueMapper;
import com.cryptoapp.model.Value;
import com.cryptoapp.repository.ValueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValueService {


    private final ValueRepo valueRepo;
    @Autowired
    public ValueService(ValueRepo valueRepo) {
        this.valueRepo = valueRepo;
    }

    public ValueDTO save (ValueDTO valueDTO){
        valueRepo.save(ValueMapper.mapToValue(valueDTO));
        return valueDTO;
    }

    public Boolean checkWallet(Long idWallet, String symbol){
       List<Value> valueList = valueRepo.findByWallet_IdWallet(idWallet);
            for (Value value:valueList){
               if(value.getCurrency().getSymbol().equals(symbol)){
                   return true;
                }
            }
            return false;
    }


}
