package com.example.workouttracker.service;

import com.example.workouttracker.user.User;

public interface UserService {
    User save(User user);
    User findByUsername(String username);
}
