package uz.pdp.jwtappauditingemail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.jwtappauditingemail.entity.Role;
import uz.pdp.jwtappauditingemail.entity.enums.RoleName;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role , Integer> {

    Role findByRoleName(RoleName roleName);
}
