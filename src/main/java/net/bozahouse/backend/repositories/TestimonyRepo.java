package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Testimony;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestimonyRepo extends JpaRepository<Testimony, Long> {
    Page<Testimony> findAllByOrderByCreatedAtDesc(Pageable pageable);

   // Page<Testimony> findAllByAuthorOrderByCreatedAtDesc(AppUser appUser, Pageable pageable);
}
