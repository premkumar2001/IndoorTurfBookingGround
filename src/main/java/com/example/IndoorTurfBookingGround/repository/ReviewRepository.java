package com.example.IndoorTurfBookingGround.repository;

import com.example.IndoorTurfBookingGround.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {

    @Query("SELECT r from Review r where r.groundId=?1")
    List<Review> findByGroundId(Long groundId);

}
