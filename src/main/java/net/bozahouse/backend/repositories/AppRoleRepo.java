package net.bozahouse.backend.repositories;

import net.bozahouse.backend.model.entities.AppRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AppRoleRepo extends JpaRepository<AppRole, Long> {
  Optional<AppRole> findByName(String name);

  Page<AppRole> findAllByOrderByNameAsc(Pageable pageable);
}
