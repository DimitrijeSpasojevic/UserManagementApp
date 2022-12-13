package rs.raf.usermanagmentapp.responses;

import lombok.Data;
import rs.raf.usermanagmentapp.dtos.UserDtoWithRoles;

import java.util.List;

@Data
public class GetAllUsersResponse {
    private List<UserDtoWithRoles> users;

    public GetAllUsersResponse(List<UserDtoWithRoles> users) {
        this.users = users;
    }
}
