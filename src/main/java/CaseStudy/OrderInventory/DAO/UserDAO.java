package CaseStudy.OrderInventory.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import CaseStudy.OrderInventory.model.*;

@Repository
public interface UserDAO extends JpaRepository<UserEntity,Long> {

	Optional<UserEntity> findByUsername(String username);
}