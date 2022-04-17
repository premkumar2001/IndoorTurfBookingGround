package com.example.IndoorTurfBookingGround.repository;

import com.example.IndoorTurfBookingGround.model.Ground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroundRepository extends JpaRepository<Ground,Long> {

    //Optional<Ground> findById(Long id);

     @Query("select g from Ground g where g.groundName=?1")
     Ground findByGroundName(String groundName);
}
