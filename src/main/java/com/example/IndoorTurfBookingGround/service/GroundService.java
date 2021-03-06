package com.example.IndoorTurfBookingGround.service;

import com.example.IndoorTurfBookingGround.exception.BookingException;
import com.example.IndoorTurfBookingGround.exception.GroundException;
import com.example.IndoorTurfBookingGround.exception.ResourceNotFoundException;
import com.example.IndoorTurfBookingGround.model.Bookings;
import com.example.IndoorTurfBookingGround.model.Ground;
import com.example.IndoorTurfBookingGround.repository.BookingsRepository;
import com.example.IndoorTurfBookingGround.repository.GroundRepository;
import com.example.IndoorTurfBookingGround.response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class GroundService {

    @Autowired
    private GroundRepository repo;

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private UserService userService;

    public List<Ground> availableGround() throws BookingException, GroundException {

        List<Ground> grounds=repo.findAll();
        for(Ground ground:grounds){
            ground.setGroundId(ground.getGroundId());

            LocalDate localDate = LocalDate.now();
            Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Bookings booking=userService.getBookingByGroundId(ground.getGroundId());

            if(booking!=null && booking.getToDate().before(currentDate))
                ground.setStatus(true);

            if(booking==null)
                    ground.setStatus(true);

            repo.save(ground);
        }
        return grounds;
    }

    public Ground saveGround(Ground ground) throws GroundException,Exception {

        Ground existingGround = repo.findByGroundName(ground.getGroundName());

        System.out.println(ground.getGroundName());

        if(existingGround!=null)
            throw new GroundException(("Ground already exists"));

        if(ground.getCapacity() < 5||ground.getCapacity()>20)
             throw new GroundException("Ground capacity cannot be less than 5 or greater than 20. :(");

        if(ground.getPrice() < 0)
            throw new GroundException("Ground price cannot be less than 0. :(");

        ground.setStatus(true);

        return repo.save(ground);

        //return new ResponseModel("Ground added successfully. :)");
    }

    public Ground editGround(Ground ground,Long id )throws ResourceNotFoundException,Exception {

        Ground ground1=finByGroundId(id);

        if(ground1==null){
           throw new  ResourceNotFoundException("Ground",id.toString());
        }

        ground.setGroundId(id);
        return repo.save(ground);

    }

    @Transactional
    public ResponseModel deleteGround(Long groundId) throws ResourceNotFoundException,Exception{
        repo.findById(groundId).orElseThrow(() -> new GroundException("Ground doesn't exists. :("));

        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println("Current Date : " + currentDate);

        List<Bookings> bookings = bookingsRepository
        .findByGroundIdEqualsAndToDateGreaterThanEqualOrGroundIdEqualsAndFromDateGreaterThanEqual
        (groundId, currentDate, groundId, currentDate);

        if(!bookings.isEmpty())
            throw new  GroundException("Ground already have bookings. Cannot delete ground. :(");


        repo.deleteById(groundId);

        return new ResponseModel("Ground deleted successfully! :)");
    }

    public Ground finByGroundId(Long id) throws ResourceNotFoundException,Exception {
        Ground ground=repo.findById(id).get();
        if(ground==null){
            throw new ResourceNotFoundException("Ground",id.toString());
        }
        return ground;
    }

    public List<Ground> availableAdminGround() {
        return repo.findAll();
    }
}
