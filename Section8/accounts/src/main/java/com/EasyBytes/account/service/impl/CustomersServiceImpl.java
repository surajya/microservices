package com.EasyBytes.account.service.impl;

import com.EasyBytes.account.dto.AccountsDto;
import com.EasyBytes.account.dto.CardsDto;
import com.EasyBytes.account.dto.CustomerDetailsDto;
import com.EasyBytes.account.dto.LoansDto;
import com.EasyBytes.account.entities.Accounts;
import com.EasyBytes.account.entities.Customer;
import com.EasyBytes.account.exception.ResourceNotFoundException;
import com.EasyBytes.account.mapper.AccountsMapper;
import com.EasyBytes.account.mapper.CustomerMapper;
import com.EasyBytes.account.repository.AccountsRepository;
import com.EasyBytes.account.repository.CustomerRepository;
import com.EasyBytes.account.service.ICustomersService;
import com.EasyBytes.account.service.client.CardsFeignClient;
import com.EasyBytes.account.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerIdf(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", ""+customer.getCustomerId())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}
