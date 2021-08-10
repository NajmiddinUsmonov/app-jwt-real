package uz.usmonov.appjwtrealemailauditing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.usmonov.appjwtrealemailauditing.entity.Role;
import uz.usmonov.appjwtrealemailauditing.entity.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByRoleName(RoleName roleName);

}
