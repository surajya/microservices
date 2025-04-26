package com.EasyBytes.account.repository;

import com.EasyBytes.account.entities.Account;
import com.EasyBytes.account.entities.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    Optional<Account> findByCustomerId(Long customerIdf);

    @Modifying
    @Transactional
    void deleteByCustomerId(Long customerIdf);


}
