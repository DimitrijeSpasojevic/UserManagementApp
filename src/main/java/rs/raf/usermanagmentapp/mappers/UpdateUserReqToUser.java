package rs.raf.usermanagmentapp.mappers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.usermanagmentapp.model.Role;
import rs.raf.usermanagmentapp.model.User;
import rs.raf.usermanagmentapp.repositories.RoleRepository;
import rs.raf.usermanagmentapp.requests.CreateUserRequest;
import rs.raf.usermanagmentapp.requests.UpdateUserRequest;

@Component
public class UpdateUserReqToUser {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    UpdateUserReqToUser(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User mapReqToUser(UpdateUserRequest request){
        User user = new User();
        user.setUserId(request.getUserId());
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        for (int i = 0; i < request.getRoles().size(); i++) {
            Role role = roleRepository.findByRole(request.getRoles().get(i));
            user.addRole(role);
        }
        return user;
    }
}
