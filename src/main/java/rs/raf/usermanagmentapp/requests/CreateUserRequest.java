package rs.raf.usermanagmentapp.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import rs.raf.usermanagmentapp.model.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateUserRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> roles = new ArrayList<>();

}
