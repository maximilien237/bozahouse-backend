package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.entities.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    Long countAllByActivatedTrue();

    Long countAllByActivatedFalse();

    Long countAllByCreatedAt(Date createdAt);

    List<AppUser> findAllByActivatedTrue();
    List<AppUser> findAllByAccountContainingAndActivatedTrue(String account);

    Page<AppUser> findAllByActivatedTrueOrderByCreatedAtDesc(Pageable pageable);

    //Page<AppUser> findAllByActivatedTrueAndRolesOrderByCreatedAtDesc(List<AppRole> roles, Pageable pageable);

    Page<AppUser> findAllByAccountContainingAndActivatedTrueOrderByCreatedAtDesc(String account, Pageable pageable);

    Page<AppUser> findAllByAccountContainingAndActivatedFalseOrderByCreatedAtDesc(String account, Pageable pageable);

    Page<AppUser> findAllByEmailContainingAndActivatedTrueOrderByCreatedAtDesc(String username, Pageable pageable);

    Page<AppUser> findAllByEmailContainingAndActivatedFalseOrderByCreatedAtDesc(String username, Pageable pageable);




}
