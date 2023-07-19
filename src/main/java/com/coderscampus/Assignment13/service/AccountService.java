package com.coderscampus.Assignment13.service;

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

}
