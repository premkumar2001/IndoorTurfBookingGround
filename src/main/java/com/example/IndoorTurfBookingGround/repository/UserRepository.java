package com.example.IndoorTurfBookingGround.repository;

import com.example.IndoorTurfBookingGround.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String email);
    public User findByMobile(String mobile);
    public List<User> findByRole(String role);
}
