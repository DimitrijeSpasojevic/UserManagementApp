package rs.raf.usermanagmentapp.responses;

import lombok.Data;
import rs.raf.usermanagmentapp.dtos.UserDtoWithRoles;
import rs.raf.usermanagmentapp.model.User;

import java.util.List;

@Data
public class GetUserResponse {
    private User user;

    public GetUserResponse(User user) {
        this.user = user;
    }
}
