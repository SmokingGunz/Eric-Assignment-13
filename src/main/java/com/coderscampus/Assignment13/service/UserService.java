package com.coderscampus.Assignment13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.Assignment13.domain.Account;
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
			Account checking = new Account();
			checking.setAccountName("Checking Account");
			checking.getUsers().add(user);
			Account savings = new Account();
			savings.setAccountName("Savings Account");
			savings.getUsers().add(user);
			Address address = new Address();
			address.setUser(user);

			user.setAddress(address);
			user.getAccounts().add(checking);
			user.getAccounts().add(savings);
			accountRepo.save(checking);
			accountRepo.save(savings);
			addressRepo.save(address);
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

	public void updateUserProperties(User oldUser, User newUser) {
		if (newUser.getName() != null) {
			oldUser.setName(newUser.getName());
		}else {
			oldUser.setName(oldUser.getName());
		}
		if (newUser.getUsername() != null) {
			oldUser.setUsername(newUser.getUsername());
		} else {
			oldUser.setUsername(oldUser.getUsername());
		}
		if (newUser.getPassword() != null) {
			oldUser.setPassword(newUser.getPassword());
		}else {
			oldUser.setPassword(oldUser.getPassword());
		}
		if (newUser.getAccounts() != null) {
			oldUser.setAccounts(newUser.getAccounts());
		}else {
			oldUser.setAccounts(oldUser.getAccounts());
		}
	}

	public void updateAddressProperties(Address address, Address updatedAddress) {
		if (updatedAddress.getCity() != null) {
			address.setCity(updatedAddress.getCity());
		}else {
			address.setCity(address.getCity());
		}
		if (updatedAddress.getRegion() != null) {
			address.setRegion(updatedAddress.getRegion());
		}else {
			address.setRegion(address.getRegion());
		}
		if (updatedAddress.getCountry() != null) {
			address.setCountry(updatedAddress.getCountry());
		}else {
			address.setCountry(address.getCountry());
		}
		if (updatedAddress.getZipCode() != null) {
			address.setZipCode(updatedAddress.getZipCode());
		}else {
			address.setZipCode(address.getZipCode());
		}
		if (updatedAddress.getAddressLine1() != null) {
			address.setAddressLine1(updatedAddress.getAddressLine1());
		}else {
			address.setAddressLine1(address.getAddressLine1());
		}
		if (updatedAddress.getAddressLine2() != null) {
			address.setAddressLine2(updatedAddress.getAddressLine2());
		}else {
			address.setAddressLine2(address.getAddressLine2());
		}
	}

}
