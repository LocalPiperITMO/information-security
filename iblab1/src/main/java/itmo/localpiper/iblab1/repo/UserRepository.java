package itmo.localpiper.iblab1.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import itmo.localpiper.iblab1.dao.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginAndPassword(String login, String password);
}
