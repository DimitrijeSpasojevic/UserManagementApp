package rs.raf.usermanagmentapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(){
        Collection<Role> roles = (Collection<Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for(Role r : roles){
            if(r.getRole().equals(RoleEnum.can_read_users.name()))
                return ResponseEntity.ok(new GetAllUsersResponse(userService.findAll()));
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Collection<Role> roles = (Collection<Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for(Role r : roles){
            if(r.getRole().equals(RoleEnum.can_read_users.name()))
                return ResponseEntity.ok(userMapper.mapUserToUserDtoWithRoles(userService.findById(id)));
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request){
        Collection<Role> roles = (Collection<Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for(Role r : roles){
            if(r.getRole().equals(RoleEnum.can_create_users.name()))
                return ResponseEntity.ok(new GetUserResponse(userService.save(mapper.mapReqToUser(request))));
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable("id") Long id){
        Collection<Role> roles = (Collection<Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for(Role r : roles){
            if(r.getRole().equals(RoleEnum.can_delete_users.name())) {
                userService.deleteById(id);
                return ResponseEntity.ok().build();
            }
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request){
        Collection<Role> roles = (Collection<Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for(Role r : roles){
            if(r.getRole().equals(RoleEnum.can_update_users.name()))
                return ResponseEntity.ok(userService.save(mapperUpdate.mapReqToUser(request)));
        }
        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }


}
