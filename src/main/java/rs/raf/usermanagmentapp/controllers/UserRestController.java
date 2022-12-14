package rs.raf.usermanagmentapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.raf.usermanagmentapp.aspect.MyAuthorization;
import rs.raf.usermanagmentapp.mappers.CreateUserReqToUser;
import rs.raf.usermanagmentapp.mappers.UpdateUserReqToUser;
import rs.raf.usermanagmentapp.mappers.UserMapper;
import rs.raf.usermanagmentapp.model.Role;
import rs.raf.usermanagmentapp.model.User;
import rs.raf.usermanagmentapp.permissions.RoleEnum;
import rs.raf.usermanagmentapp.requests.CreateUserRequest;
import rs.raf.usermanagmentapp.requests.UpdateUserRequest;
import rs.raf.usermanagmentapp.responses.GetAllUsersResponse;
import rs.raf.usermanagmentapp.responses.GetUserResponse;
import rs.raf.usermanagmentapp.services.UserService;

import java.security.Permission;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final CreateUserReqToUser mapper;
    private final UpdateUserReqToUser mapperUpdate;
    private final UserMapper userMapper;

    public UserRestController(UserService userService, CreateUserReqToUser mapper, UpdateUserReqToUser mapperUpdate, UserMapper userMapper) {
        this.userService = userService;
        this.mapper = mapper;
        this.mapperUpdate = mapperUpdate;
        this.userMapper = userMapper;
    }

    @MyAuthorization(authorization = RoleEnum.can_read_users)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(new GetAllUsersResponse(userService.findAll()));
    }

    @MyAuthorization(authorization = RoleEnum.can_read_users)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userMapper.mapUserToUserDtoWithRoles(userService.findById(id)));
    }

    @MyAuthorization(authorization = RoleEnum.can_create_users)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request){
        return ResponseEntity.ok(new GetUserResponse(userService.save(mapper.mapReqToUser(request))));
    }

    @MyAuthorization(authorization = RoleEnum.can_delete_users)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable("id") Long id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @MyAuthorization(authorization = RoleEnum.can_update_users)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request){
        return ResponseEntity.ok(userService.save(mapperUpdate.mapReqToUser(request)));
    }


}
