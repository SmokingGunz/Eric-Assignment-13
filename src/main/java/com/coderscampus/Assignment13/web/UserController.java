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
import com.coderscampus.Assignment13.domain.Address;
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

        if (user.getAddress() == null) {
            Address address = new Address();
            address.setUser(user);
            address.setUserId(userId);
            user.setAddress(address);
        }

        model.put("users", Arrays.asList(user));
        model.put("user", user);
        model.put("accounts", account);
        model.put("address", user.getAddress());
        return "users";
    }

    // PostMapping method to update a user and address in the database then redirect to the users page
    @PostMapping("/users/{userId}")
    public String postOneUser(@PathVariable Long userId, User newUser) {
        Address address = newUser.getAddress(); // Retrieve the existing Address from the
        User oldUser = userService.findByIdWithAccounts(userId); // Retrieve the existing User from the database
        newUser.setAccounts(oldUser.getAccounts());
        newUser.setAddress(address);
		newUser.setUserId(userId);
		address.setUser(newUser);
        address.setUserId(userId);
        userService.saveUser(newUser);
        return "redirect:/users/" + userId;
    }

//	@PostMapping("/users/{userId}")
//	public String postUser(@PathVariable Long userId, User user) {
//
//		User oldUser = userService.findByIdWithAccounts(userId); // Retrieve the existing User from the database
//		user.setAccounts(oldUser.getAccounts());
//		Address address = addressService.save(user.getAddress()); // Retrieve the existing Address from the database
//		user.setAddress(address);
//
//		address.setUser(oldUser);
//		user.setAddress(address);
//		address.setUserId(userId);
////
////		addressService.save(address); // Save the updated Address object
//
//		userService.saveUser(user);
//
//		return "redirect:/users/" + userId;
//	}

//	@PostMapping("/users/{userId}")
//	public String postOneUser(@PathVariable Long userId, User newUser) {
//		User oldUser = userService.findById(userId); // Retrieve the existing User from the database
//
//		// Get the updated Address from the form
//		Address updatedAddress = newUser.getAddress();
//
//		// Update properties of oldUser with properties from newUser if they are not
//		// null
//		userService.updateUserProperties(oldUser, newUser);
//
//		// Update properties of the associated Address with properties from
//		// updatedAddress if they are not null
//		userService.updateAddressProperties(oldUser.getAddress(), updatedAddress);
//
//		userService.saveUser(oldUser); // Save the updated User object
//
//		return "redirect:/users/" + userId;
//	}

//	@PostMapping("/users/{userId}")
//	public String postUser(@PathVariable Long userId, User user) {
//	    User oldUser = userService.findById(userId); // Retrieve the existing User from the database
//	    user.setAccounts(oldUser.getAccounts());
//
//	    // Get the updated Address from the form
//	    Address updatedAddress = user.getAddress();
//	    if (updatedAddress == null) {
//	        updatedAddress = new Address();
//	    } else {
//	        // Retrieve the existing Address associated with the user
//	        Address address = oldUser.getAddress();
//	    }
//
//	    user.setAddress(updatedAddress);
//	    updatedAddress.setUser(user);
//
//	    userService.saveUser(user); // Save the updated User object
//
//	    return "redirect:/users/" + userId;
//	}


    @PostMapping("/users/{userId}/delete")
    public String deleteOneUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }

}
