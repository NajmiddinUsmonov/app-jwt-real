package uz.usmonov.appjwtrealemailauditing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;//userning takrorlanmas raqami

    @Column(nullable = false,length = 50)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @Column(unique = true,nullable = false)
    private String email;//(username sifatida ko'ramiz)


    @Column(nullable = false)
    private String password;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp//save paytida ozi save qiladi automat ravishda
    private Timestamp createdAt;


    @UpdateTimestamp//edit qiganda yozadi automat ravishda
    private Timestamp updateAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    private boolean accountNonExpired=true;


    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled;

    private String emailCode;





    //------- All of the methods are overrided from USERDETAILS Interface.This is crucial ------//


    /**
     * This method include and work with Roles and Permissions
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    /**
     * To get Username,We can use this method!
     * @return
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * This indicate whether Expire Date of account has come or not
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /**
     * This indicate whether Account is locked or not
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /**
     * This indicate whether Date of persistance  of account finish or not!
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /**
     *  This indicate whether Accessing of account to system is active or not !
     * @return
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
