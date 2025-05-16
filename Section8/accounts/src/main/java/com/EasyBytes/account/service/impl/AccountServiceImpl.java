package com.EasyBytes.account.service.impl;

import com.EasyBytes.account.constant.AccountsConstants;
import com.EasyBytes.account.dto.AccountsDto;
import com.EasyBytes.account.dto.CustomerDTO;
import com.EasyBytes.account.entities.Accounts;
import com.EasyBytes.account.entities.Customer;
import com.EasyBytes.account.exception.CustomerAlreadyExistException;
import com.EasyBytes.account.exception.ResourceNotFoundException;
import com.EasyBytes.account.mapper.AccountsMapper;
import com.EasyBytes.account.mapper.CustomerMapper;
import com.EasyBytes.account.repository.AccountsRepository;
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

    private AccountsRepository accountsRepository;
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
        accountsRepository.save(createNewAccount(customerSave));

    }



    private Accounts createNewAccount(Customer customer) {
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerIdf(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccounts.setAccountNumber(randomAccNumber);
        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccounts;
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
         Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                 () -> new ResourceNotFoundException("Customer not found with ", "mobile number " , mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerIdf(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts not found with ", "customer id " , customer.getCustomerId())
        );
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
        customerDTO.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDTO;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountsDto accountsDTO = customerDTO.getAccountsDto();
        if(accountsDTO !=null) {
            Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Accounts not found with ", "accounts number " , accountsDTO.getAccountNumber()));
            AccountsMapper.mapToAccounts(accountsDTO, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerIdf = accounts.getCustomerIdf();
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

        accountsRepository.deleteByCustomerIdf(customer.getCustomerId());
        customerRepository.delete(customer);

        return true;
    }


}