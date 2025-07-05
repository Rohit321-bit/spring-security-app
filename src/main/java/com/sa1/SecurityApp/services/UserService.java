package com.sa1.SecurityApp.services;

import com.sa1.SecurityApp.dto.SignUpDTO;
import com.sa1.SecurityApp.dto.UserDTO;
import com.sa1.SecurityApp.entities.User;
import com.sa1.SecurityApp.exceptions.ResourceNotFoundException;
import com.sa1.SecurityApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new BadCredentialsException("User with the following email "+username+" does not exist!"));
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        User user=modelMapper.map(signUpDTO,User.class);
        Optional<User> getUser=userRepository.findByEmail(user.getEmail());
        if(getUser.isPresent()){
            throw new BadCredentialsException("User with the given Email already exists!");
        }
        User toBeCreatedUser = modelMapper.map(signUpDTO, User.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        User createUser=userRepository.save(toBeCreatedUser);
        return modelMapper.map(createUser,UserDTO.class);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User with the given Id not present")
        );
    }
}
