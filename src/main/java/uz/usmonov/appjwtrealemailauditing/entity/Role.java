package uz.usmonov.appjwtrealemailauditing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.usmonov.appjwtrealemailauditing.entity.enums.RoleName;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    /**
     * This method return roleNames as String!
     * to make enums String, we use name()!!
     * @return
     */
    @Override
    public String getAuthority() {
        return roleName.name ();
    }
}
