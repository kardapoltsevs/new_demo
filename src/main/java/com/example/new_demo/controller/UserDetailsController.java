package com.example.new_demo.controller;

import com.example.new_demo.dto.UserDetailsDto;
import com.example.new_demo.service.UserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-details")
@RequiredArgsConstructor
public class UserDetailsController {
    private final UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<UserDetailsDto> createUserDetails(@Valid @RequestBody UserDetailsDto userDetailsDto) {
        UserDetailsDto createdDetails = userDetailsService.createUserDetails(userDetailsDto);
        return new ResponseEntity<>(createdDetails, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDetailsDto> getUserDetailsByUserId(@PathVariable Long userId) {
        UserDetailsDto userDetails = userDetailsService.getUserDetailsByUserId(userId);
        return ResponseEntity.ok(userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDto> updateUserDetails(
            @PathVariable Long id, @Valid @RequestBody UserDetailsDto userDetailsDto) {
        UserDetailsDto updatedDetails = userDetailsService.updateUserDetails(id, userDetailsDto);
        return ResponseEntity.ok(updatedDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable Long id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.noContent().build();
    }
}
