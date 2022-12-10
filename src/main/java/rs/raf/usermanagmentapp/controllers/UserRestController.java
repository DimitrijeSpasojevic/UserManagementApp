package rs.raf.usermanagmentapp.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rs.raf.usermanagmentapp.model.User;
import rs.raf.usermanagmentapp.services.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllStudents(){
        return userService.findAll();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User createStudent(@RequestBody User user){
        return userService.save(user);
    }

}
