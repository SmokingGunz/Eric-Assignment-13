package com.coderscampus.Assignment13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.Assignment13.domain.Address;
import com.coderscampus.Assignment13.domain.User;
import com.coderscampus.Assignment13.repository.AccountRepository;
import com.coderscampus.Assignment13.repository.AddressRepository;
import com.coderscampus.Assignment13.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private AddressRepository addressRepo;

	public List<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public List<User> findByNameAndUsername(String name, String username) {
		return userRepo.findByNameAndUsername(name, username);
	}

	public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
		return userRepo.findByCreatedDateBetween(date1, date2);
	}

	public User findExactlyOneUserByUsername(String username) {
		List<User> users = userRepo.findExactlyOneUserByUsername(username);
		if (users.size() > 0)
			return users.get(0);
		else
			return new User();
	}

	public Set<User> findAll() {
		return userRepo.findAllUsersWithAccountsAndAddresses();
	}

	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}

	public User saveUser(User user) {
		if (user.getUserId() == null) {
//			Account checking = new Account();
//			checking.setAccountName("Checking Account");
//			checking.getUsers().add(user);
//			Account savings = new Account();
//			savings.setAccountName("Savings Account");
//			savings.getUsers().add(user);
			Address address = new Address();
			address.setUser(user);

			user.setAddress(address);
//			user.getAccounts().add(checking);
//			user.getAccounts().add(savings);
//			accountRepo.save(checking);
//			accountRepo.save(savings);
			addressRepo.save(address);
		}

		if (user.getAddress() == null) {
			Address address = new Address();
			address.setUser(user);
			user.setAddress(address);
			addressRepo.save(address);
		}

		if (user.getAddress().getUser() == null) {
			user.getAddress().setUser(user);
			user.getAddress().setUserId(user.getUserId());
		}

		// Save the address separately before updating the user (to avoid cascading
		// issues)
		addressRepo.save(user.getAddress());

		// Check if the user has a password (not empty) and update it if necessary
		// Retrieve the existing user from the database
		User existingUser = findById(user.getUserId());

		// Check if the user has a password (not empty) and update it only if it was
		// changed on the form
		if (user.getPassword() != null && !user.getPassword().isEmpty()
				&& !user.getPassword().equals(existingUser.getPassword())) {
			existingUser.setPassword(user.getPassword());
			userRepo.save(existingUser);
		}else {
			user.setPassword(existingUser.getPassword());
		}

		return userRepo.save(user);
	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}

	public User findByIdWithAccounts(Long userId) {

		Optional<User> userOpt = userRepo.findUserByIdWithAccountsAndAddress(userId);
		return userOpt.orElse(new User());
	}

}
