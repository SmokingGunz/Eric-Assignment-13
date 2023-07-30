package com.coderscampus.Assignment13.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coderscampus.Assignment13.domain.Account;
import com.coderscampus.Assignment13.domain.User;
import com.coderscampus.Assignment13.service.AccountService;
import com.coderscampus.Assignment13.service.UserService;

@Controller
@RequestMapping("users/{userId}/accounts")
public class AccountController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@PostMapping("")
	public String createAccount(@PathVariable Long userId) {
		User user = userService.findById(userId);
		Account account = new Account();
		account.getUsers().add(user);
		user.getAccounts().add(account);
		account.setAccountName("Account #" + user.getAccounts().size());

		accountService.saveAccount(account);

		return "redirect:/users/" + userId + "/accounts/" + account.getAccountId();

	}

	// method to populate model with user account information by account id
	@GetMapping("/{accountId}")
	public String getAccount(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
		Account account = accountService.findById(accountId);
		User user = userService.findById(userId);
		model.put("account", account);
		model.put("user", user);
		return "account";
	}

	// post method to save user account information by account id
	@PostMapping("/{accountId}")
	public String saveAccount(@PathVariable Long userId, @PathVariable Long accountId, Account account) {

		accountService.saveAccount(account);

		return "redirect:/users/" + userId + "/accounts/" + accountId;
	}

}
