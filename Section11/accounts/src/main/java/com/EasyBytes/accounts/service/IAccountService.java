package com.EasyBytes.accounts.service;

import com.EasyBytes.accounts.dto.CustomerDTO;



public interface IAccountService {
    void CreateAccount(CustomerDTO customerDTO);


    CustomerDTO fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDTO customerDTO);

    boolean deleteAccount(String mobileNumber);


}