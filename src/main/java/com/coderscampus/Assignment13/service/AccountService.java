package com.coderscampus.Assignment13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.Assignment13.domain.Account;
import com.coderscampus.Assignment13.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepo;

	// method to save account to repository
	public Account saveAccount(Account account) {

		return accountRepo.save(account);

	}

	public Account findById(Long accountId) {

		Optional<Account> accountOpt = accountRepo.findById(accountId);

		return accountOpt.orElse(new Account());
	}
	
	// method to find all accounts
	public List<Account> findAllAccounts() {

        return accountRepo.findAll();

    }

}
