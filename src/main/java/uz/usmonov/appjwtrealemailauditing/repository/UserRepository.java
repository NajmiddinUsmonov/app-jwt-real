package uz.usmonov.appjwtrealemailauditing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.usmonov.appjwtrealemailauditing.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsUserByEmail(String email);

    Optional<User> findByEmailAndEmailCode(String email,String emailCode);

    Optional<User> findByEmail(String username);
}
