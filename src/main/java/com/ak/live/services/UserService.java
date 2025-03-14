package com.ak.live.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ak.live.convertor.UserConvertor;
import com.ak.live.entities.User;
import com.ak.live.exceptions.UserExist;
import com.ak.live.repositories.UserRepository;
import com.ak.live.request.UserRequest;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String addUser(UserRequest userRequest) {
		Optional<User> users = userRepository.findByEmailId(userRequest.getEmailId());
		
		if (users.isPresent()) {
			throw new UserExist();
		}

		User user = UserConvertor.userDtoToUser(userRequest,  passwordEncoder.encode("1234"));

		userRepository.save(user);
		return "User Saved Successfully";
	}

}
