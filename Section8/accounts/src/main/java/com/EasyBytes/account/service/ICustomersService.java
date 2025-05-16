package com.EasyBytes.account.service;

import com.EasyBytes.account.dto.CustomerDetailsDto;

public interface ICustomersService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
