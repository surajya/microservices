package com.EasyBytes.account.service;

import com.EasyBytes.account.dto.CustomerDTO;



public interface IAccountService {
    void CreateAccount(CustomerDTO customerDTO);


    CustomerDTO fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDTO customerDTO);

    boolean deleteAccount(String mobileNumber);


}