package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.Newsletter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NewsletterRepo extends JpaRepository<Newsletter, Long> {
    List<Newsletter> findAllBySendingDate(Date date);
    Page<Newsletter> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
