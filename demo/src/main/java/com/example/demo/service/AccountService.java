package com.example.demo.service;

import com.example.demo.model.MongoUser;
import com.example.demo.repository.MongoUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final MongoUserRepository accountRepository;

    public AccountService(MongoUserRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferMoney(String fromId, String toId) {

        MongoUser from = accountRepository.findById(fromId)
                .orElseThrow(() ->
                        new RuntimeException("Source account not found"));

        MongoUser to = accountRepository.findById(toId)
                .orElseThrow(() ->
                        new RuntimeException("Destination account not found"));

        if (from.getBalance() < 500) {
            throw new RuntimeException("Insufficient balance");
        }

        from.setBalance(from.getBalance() - 500);
        to.setBalance(to.getBalance() + 500);

        accountRepository.save(from);
        accountRepository.save(to);
    }
}