package gi2.spring.devoir1.repositories;

import gi2.spring.devoir1.models.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Page<Group> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Group> findByNameIgnoreCase(String name);

}
