package com.cryptoapp.dto.mapper;

import com.cryptoapp.dto.WalletDTO;
import com.cryptoapp.model.Wallet;

public class WalletMapper {

    public static Wallet mapToWallet(WalletDTO walletDTO) {
        Wallet wallet = new Wallet();
        wallet.setName(walletDTO.getName());
        return wallet;
    }

    public static WalletDTO mapToDTO(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setName(wallet.getName());
        return walletDTO;
    }

}
