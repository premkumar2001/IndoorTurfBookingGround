package com.example.IndoorTurfBookingGround.controller;
import com.example.IndoorTurfBookingGround.exception.*;
import com.example.IndoorTurfBookingGround.model.Bookings;
import com.example.IndoorTurfBookingGround.model.Ground;
import com.example.IndoorTurfBookingGround.model.Review;
import com.example.IndoorTurfBookingGround.model.User;
import com.example.IndoorTurfBookingGround.repository.UserRepository;
import com.example.IndoorTurfBookingGround.request.EditBooking;
import com.example.IndoorTurfBookingGround.response.GroundBookingDates;
import com.example.IndoorTurfBookingGround.response.ResponseModel;
import com.example.IndoorTurfBookingGround.service.UserService;
import com.example.IndoorTurfBookingGround.service.GroundService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class UserController {
    @Autowired
    private UserRepository repo;

    @Autowired
    private UserService service;

    @Autowired
    private GroundService serv;


    private String Default="USER";

    @PostMapping("/signup")
    private String loginByUser(@RequestBody User um){
        /*String pwd=um.getPassword();
        String encryptPwd=pwdEncoder.encode(pwd);
        um.setPassword(encryptPwd);*/
        um.setRole(Default);
        User u=service.saveUser(um);
        return "Registration Successful";
    }

    @GetMapping("/dashboard")
    private ResponseEntity<List<Ground>> availableGround() throws BookingException, GroundException {
        List<Ground> allGrounds= serv.availableGround();
        return new ResponseEntity<>(allGrounds, HttpStatus.OK);
    }

     // Add Booking.
    @PutMapping("/addBooking/{email}/{groundId}")
    public ResponseEntity<ResponseModel> addBooking(@RequestBody Bookings requestBooking, @PathVariable("email") String email,@PathVariable("groundId") long groundId)
    throws ResourceNotFoundException, UserNotAuthorizedException, BookingException, Exception
    {
        return new ResponseEntity<ResponseModel>(service.addBooking(requestBooking, email,groundId), HttpStatus.OK);
    }


    // Edit Booking.
    @PutMapping("/editbookedgrounds/{id}")
    public  ResponseEntity<Bookings> editBooking(@PathVariable("id") long bookingId, @RequestBody EditBooking editBooking) throws ResourceNotFoundException, Exception
    {
        return new ResponseEntity<Bookings>(service.editBooking(bookingId, editBooking), HttpStatus.OK);
    }


    // Cancle Booking.
    @DeleteMapping("/deletebookedgrounds/{id}")
    public ResponseEntity<ResponseModel> cancleBooking(@PathVariable("id") long bookingId) throws Exception
    {
        return new ResponseEntity<ResponseModel>(service.cancleBooking(bookingId), HttpStatus.OK);
    }

    //Get Booking By Id.
    @GetMapping("/getBookingById/{bookingId}")
    public ResponseEntity<Bookings> getBooking(@PathVariable("bookingId") long bookingId)throws Exception{
        return new ResponseEntity<Bookings>(service.getBookingById(bookingId),HttpStatus.OK);
    }

    // Display ground booking dates.
    @GetMapping("get/bookings/ground/{id}")
    public ResponseEntity<List<GroundBookingDates>> getBookingDatesOfGround(@PathVariable("id") long groundId) throws GroundException, Exception
    {
        return new ResponseEntity<List<GroundBookingDates>>(service.getBookingDatesOfGround(groundId), HttpStatus.OK);
    }


    // Display Customer Bookings.
    @GetMapping("/bookedgrounds/{email}")
    public ResponseEntity<List<Bookings>> getCustomerBookings(@PathVariable("email") String email) throws ResourceNotFoundException, Exception
    {
        return new ResponseEntity<List<Bookings>>(service.getUserBookings(email), HttpStatus.OK);
    }

    @PostMapping("/addreview/{user_email}/{ground_id}")
    private ResponseEntity<Review> addReview(@RequestBody Review r, @PathVariable(value="user_email") String user_email, @PathVariable(value="ground_id") Long ground_id) throws Exception {
        r.setGroundId(ground_id);
        r.setUserEmail(user_email);
        return new ResponseEntity<>(service.addReview(r),HttpStatus.CREATED);
    }

    @GetMapping("/viewreview/{ground_id}")
    private ResponseEntity<List<Review>> allReview(@PathVariable(value="ground_id") Long ground_id) throws GroundException,Exception {
        return new ResponseEntity<>(service.getAllReview(ground_id),HttpStatus.OK);
    }

    @PutMapping("/editreview/{review_id}/{ground_id}")
    private ResponseEntity<Review> editReview(@RequestBody Review r,@PathVariable(value="review_id") int review_id,@PathVariable(value="ground_id") Long ground_id) throws ReviewException {
        Review add=service.findReviewById(r,review_id,ground_id);
        return new ResponseEntity<>(add,HttpStatus.OK);
    }

    @DeleteMapping("/deletereview/{review_id}/{ground_id}")
    private ResponseEntity<?> deleteReview(@PathVariable(value="review_id")int review_id,@PathVariable(value="ground_id")Long ground_id) throws ReviewException {
        service.deleteReview(review_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getReviewById/{id}")
    private Review getReview(@PathVariable(value="id")int id) throws ReviewException {
        return service.getReview(id);
    }
}
