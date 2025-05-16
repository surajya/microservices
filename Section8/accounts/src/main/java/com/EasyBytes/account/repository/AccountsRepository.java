package com.EasyBytes.account.repository;

import com.EasyBytes.account.entities.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {


    public Optional<Accounts> findByCustomerIdf(Long customerIdf);

    @Modifying
    @Transactional
    void deleteByCustomerIdf(Long customerIdf);


}
