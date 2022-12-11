package rs.raf.usermanagmentapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.raf.usermanagmentapp.model.User;
import rs.raf.usermanagmentapp.requests.LoginRequest;
import rs.raf.usermanagmentapp.responses.LoginResponse;
import rs.raf.usermanagmentapp.services.UserService;
import rs.raf.usermanagmentapp.utils.JwtUtil;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception   e){
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
        User user = userService.findByUserName(loginRequest.getUsername());
        return ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(loginRequest.getUsername(), user.getRoles())));
    }

}
