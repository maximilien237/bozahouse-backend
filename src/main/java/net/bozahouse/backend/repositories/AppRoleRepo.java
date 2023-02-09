package net.bozahouse.backend.repositories;

import net.bozahouse.backend.model.entities.AppRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppRoleRepo extends JpaRepository<AppRole, Integer> {
  Optional<AppRole> findByName(String name);
  @Query("select a from AppRole a order by a.name asc ")
  List<AppRole> listRole(Pageable pageable);
}
