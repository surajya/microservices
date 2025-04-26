package com.EasyBytes.account.service;

import com.EasyBytes.account.dto.CustomerDTO;
import com.EasyBytes.account.entities.Customer;
import org.springframework.stereotype.Service;


public interface IAccountService {
    public void CreateAccount(CustomerDTO customerDTO);


    public CustomerDTO fetchAccount(String mobileNumber);

    public boolean updateAccount(CustomerDTO customerDTO);

    public boolean deleteAccount(String mobileNumber);


}