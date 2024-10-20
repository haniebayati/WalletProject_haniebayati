package com.example.demo.web;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


@RestController
@RequestMapping("/walletAuthentication")
public class AuthenticationController {
	
	@Autowired
	UserRepository userRepository;
	
    // get mobile number and authentication
	@PostMapping(value = "/users_authentication")
	public int userAuthentication(@RequestBody User user) {
	    int otp = generateOtp(); 
	    System.out.println(otp);
	    user.setOtpCode(otp);
	    userRepository.save(user);
	    return user.getOtpCode();
	}

	public int generateOtp() {
	    return new Random().nextInt(900000) + 100000; // generate random OTP
	}

    
    // verify otpCode
    @PostMapping(value = "/verify_authentication")
    public ResponseEntity<?> verifyAuthentication(@RequestBody User requestUser) {
    	User user = userRepository.findbyMobile(requestUser.getMobile())
    							.orElseThrow(() -> new RuntimeException("User not found"));
        if (isExpired(user.getOtpTime())) {
            throw new RuntimeException("Time Expired.");
        }
        
        if (user.getOtpCode() != requestUser.getOtpCode()) {
        	throw new RuntimeException("Invalid Code.");
        }
        
        // generate JWT token 
        String jwtToken = generateToken(user.getMobile());
        
        // Returning the JWT with a message like "authentication successful"
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Authentication successful");
        responseBody.put("token", jwtToken);
        
        return ResponseEntity.ok(responseBody);
        
     }
             
    // check expire time
    public static boolean isExpired(LocalDateTime storedTime) {
        // duration between current time and otp time
        Duration duration = Duration.between(storedTime, LocalDateTime.now());
        return duration.getSeconds() > 60 ;
    }

    // generate JWT token
    public String generateToken(String mobile) {
    	
    	// using base64 key
   // 	String secret_Key = Base64.getEncoder().encodeToString("YourSuperSecretKeyHere".getBytes());
    	
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plusHours(1); // The token expires in 1 hour
        Date issuedAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date expirationDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
    	
        // generate JWT token and return it 
        String token = JWT.create().withSubject(mobile).withIssuedAt(issuedAt)
        		.withExpiresAt(expirationDate).sign(Algorithm.HMAC256("secret_key"));
        
        return token;
    }
    
    
    public static DecodedJWT verifyToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256("secret_key"))
                .build()
                .verify(token);
        return jwt;
    }
    
    

}  