package rs.raf.usermanagmentapp.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.usermanagmentapp.model.User;
import rs.raf.usermanagmentapp.repositories.UserRepository;


@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Data...");


        User user1 = new User();
        user1.setUsername("pera@gmail.com");
        user1.setFirstName("Pera");
        user1.setLastName("Peric");
        user1.setPermissions("can_create_users, can_read_users, can_update_users, can_delete_users");
        user1.setPassword(this.passwordEncoder.encode("123123123"));
        this.userRepository.save(user1);

        System.out.println("Data loaded!");
    }
}
