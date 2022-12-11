package rs.raf.usermanagmentapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.usermanagmentapp.model.Role;
import rs.raf.usermanagmentapp.model.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String roleName);
}
