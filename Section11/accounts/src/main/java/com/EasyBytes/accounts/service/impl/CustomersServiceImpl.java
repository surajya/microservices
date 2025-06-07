package com.EasyBytes.accounts.service.impl;

import com.EasyBytes.accounts.dto.AccountsDto;
import com.EasyBytes.accounts.dto.CardsDto;
import com.EasyBytes.accounts.dto.CustomerDetailsDto;
import com.EasyBytes.accounts.dto.LoansDto;
import com.EasyBytes.accounts.entities.Accounts;
import com.EasyBytes.accounts.entities.Customer;
import com.EasyBytes.accounts.exception.ResourceNotFoundException;
import com.EasyBytes.accounts.mapper.AccountsMapper;
import com.EasyBytes.accounts.mapper.CustomerMapper;
import com.EasyBytes.accounts.repository.AccountsRepository;
import com.EasyBytes.accounts.repository.CustomerRepository;
import com.EasyBytes.accounts.service.ICustomersService;
import com.EasyBytes.accounts.service.client.CardsFeignClient;
import com.EasyBytes.accounts.service.client.LoansFeignClient;
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
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerIdf(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", ""+customer.getCustomerId())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if(null!=loansDtoResponseEntity){
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }



        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if(null!=cardsDtoResponseEntity){
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }


        return customerDetailsDto;

    }
}
