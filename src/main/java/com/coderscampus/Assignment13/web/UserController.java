package com.coderscampus.Assignment13.web;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.Assignment13.domain.Account;
import com.coderscampus.Assignment13.domain.User;
import com.coderscampus.Assignment13.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String getCreateUser(ModelMap model) {

		model.put("user", new User());

		return "register";
	}

	@PostMapping("/register")
	public String postCreateUser(User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/register";
	}

	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		Set<User> users = userService.findAll();

		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}

		return "users";
	}

	@GetMapping("/users/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		List<Account> account = user.getAccounts();

//		if (user.getAddress() == null) {
//			Address address = new Address();
//			address.setUser(user);
//			address.setUserId(userId);
//			user.setAddress(address);
//		}

		model.put("users", Arrays.asList(user));
		model.put("user", user);
		model.put("accounts", account);
//		model.put("address", user.getAddress());
		return "users";
	}

	// PostMapping method to update a user and address in the database then redirect
	// to the users page
	@PostMapping("/users/{userId}")
	public String postOneUser(@PathVariable Long userId, User user) {

		userService.saveUser(user);
		return "redirect:/users/" + userId;
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser(@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}

}
