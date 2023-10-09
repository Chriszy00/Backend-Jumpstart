package com.jumpstart.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entity.User;
import com.jumpstart.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public Optional<User> getUserByUsername(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}
}
