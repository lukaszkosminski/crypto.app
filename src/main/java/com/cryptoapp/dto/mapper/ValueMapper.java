package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.ValueDTO;
import com.cryptoapp.model.Value;

public class ValueMapper {
    public static Value mapToValue(ValueDTO valueDTO) {
        Value value= new Value();
        value.setQuantity(valueDTO.getQuantity());
        value.setCurrency(valueDTO.getCurrency());
        value.setWallet(valueDTO.getWallet());
        return value;
    }

    public static ValueDTO mapToDTO(Value value) {
        ValueDTO valueDTO = new ValueDTO();
        valueDTO.setQuantity(value.getQuantity());
        valueDTO.setCurrency(value.getCurrency());
        valueDTO.setWallet(value.getWallet());
        return valueDTO;
    }
}
