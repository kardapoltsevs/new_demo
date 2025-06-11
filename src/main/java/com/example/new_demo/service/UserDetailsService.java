package com.example.new_demo.service;

import com.example.new_demo.dto.UserDetailsDto;
import com.example.new_demo.model.User;
import com.example.new_demo.model.UserDetails;
import com.example.new_demo.repository.UserDetailsRepository;
import com.example.new_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserDetailsDto createUserDetails(UserDetailsDto userDetailsDto) {
        User user = userRepository.findById(userDetailsDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = UserDetails.builder()
                .firstName(userDetailsDto.getFirstName())
                .lastName(userDetailsDto.getLastName())
                .phone(userDetailsDto.getPhone())
                .user(user)
                .build();

        UserDetails savedDetails = userDetailsRepository.save(userDetails);
        user.setUserDetails(savedDetails);
        userRepository.save(user);

        return mapToDto(savedDetails);
    }
    @Transactional(readOnly = true)
    public UserDetailsDto getUserDetailsByUserId(Long userId) {
        UserDetails userDetails = userDetailsRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User details not found"));
        return mapToDto(userDetails);
    }

    @Transactional
    public UserDetailsDto updateUserDetails(Long id, UserDetailsDto userDetailsDto) {
        UserDetails userDetails = userDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User details not found"));

        userDetails.setFirstName(userDetailsDto.getFirstName());
        userDetails.setLastName(userDetailsDto.getLastName());
        userDetails.setPhone(userDetailsDto.getPhone());

        UserDetails updatedDetails = userDetailsRepository.save(userDetails);
        return mapToDto(updatedDetails);
    }
    @Transactional
    public void deleteUserDetails(Long id) {
        userDetailsRepository.deleteById(id);
    }

    private UserDetailsDto mapToDto(UserDetails userDetails) {
        return UserDetailsDto.builder()
                .id(userDetails.getId())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .phone(userDetails.getPhone())
                .userId(userDetails.getUser().getId())
                .build();
    }
}
