package com.EasyBytes.account.service.impl;

import com.EasyBytes.account.constant.AccountsConstants;
import com.EasyBytes.account.dto.AccountDTO;
import com.EasyBytes.account.dto.CustomerDTO;
import com.EasyBytes.account.entities.Account;
import com.EasyBytes.account.entities.Customer;
import com.EasyBytes.account.exception.CustomerAlreadyExistException;
import com.EasyBytes.account.exception.ResourceNotFoundException;
import com.EasyBytes.account.mapper.AccountMapper;
import com.EasyBytes.account.mapper.CustomerMapper;
import com.EasyBytes.account.repository.AccountRepository;
import com.EasyBytes.account.repository.CustomerRepository;
import com.EasyBytes.account.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void CreateAccount(CustomerDTO customerDTO) {
        Customer customer=CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (customerOptional.isPresent()) {
            throw new CustomerAlreadyExistException("Customer is already exist with mobile number " + customerDTO.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("ByAdmin34");
        Customer customerSave = customerRepository.save(customer);
        accountRepository.save(createNewAccount(customerSave));

    }



    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerIdf(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
         Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                 () -> new ResourceNotFoundException("Customer not found with ", "mobile number " , mobileNumber));

        Account account=accountRepository.findByCustomerIdf(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account not found with ", "customer id " , customer.getCustomerId())
        );
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
        customerDTO.setAccountDto(AccountMapper.mapToAccountsDto(account, new AccountDTO()));
        return customerDTO;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountDTO accountDTO = customerDTO.getAccountDto();
        if(accountDTO!=null) {
            Account account = accountRepository.findById(accountDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account not found with ", "account number " , accountDTO.getAccountNumber()));
            AccountMapper.mapToAccounts(accountDTO, account);
            account= accountRepository.save(account);

            Long customerIdf = account.getCustomerIdf();
            Customer customer = customerRepository.findById(customerIdf).orElseThrow(
                    () -> new ResourceNotFoundException("Customer not found with ", "customer id " , customerIdf));
            CustomerMapper.mapToCustomer(customerDTO, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with ", "mobile number " , mobileNumber)
        );

        accountRepository.deleteByCustomerIdf(customer.getCustomerId());
        customerRepository.delete(customer);

        return true;
    }


}