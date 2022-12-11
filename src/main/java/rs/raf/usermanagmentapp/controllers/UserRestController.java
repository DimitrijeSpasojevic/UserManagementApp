package rs.raf.usermanagmentapp.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.usermanagmentapp.mappers.CreateUserReqToUser;
import rs.raf.usermanagmentapp.model.User;
import rs.raf.usermanagmentapp.requests.CreateUserRequest;
import rs.raf.usermanagmentapp.services.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final CreateUserReqToUser mapper;

    public UserRestController(UserService userService, CreateUserReqToUser mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllStudents(){
        return userService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createStudent(@RequestBody CreateUserRequest request){
        return userService.save(mapper.mapReqToUser(request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable("id") Long id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

//    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public User updateUser(@RequestBody CreateUserRequest request){
//        return userService.save(teacher);
//    }


}
