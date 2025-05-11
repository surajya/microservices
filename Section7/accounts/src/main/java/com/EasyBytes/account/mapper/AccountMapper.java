package com.EasyBytes.account.mapper;

import com.EasyBytes.account.dto.AccountDTO;
import com.EasyBytes.account.entities.Account;

public class AccountMapper {

    public static AccountDTO mapToAccountsDto(Account account, AccountDTO accountsDto) {
        accountsDto.setAccountNumber(account.getAccountNumber());
        accountsDto.setAccountType(account.getAccountType());
        accountsDto.setBranchAddress(account.getBranchAddress());
        return accountsDto;
    }

    public static Account mapToAccounts(AccountDTO accountsDto, Account account) {
        account.setAccountNumber(accountsDto.getAccountNumber());
        account.setAccountType(accountsDto.getAccountType());
        account.setBranchAddress(accountsDto.getBranchAddress());
        return account;
    }

}