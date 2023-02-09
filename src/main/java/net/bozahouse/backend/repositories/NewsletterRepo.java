package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.Newsletter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsletterRepo extends JpaRepository<Newsletter, Long> {
    @Query("select n from Newsletter n order by n.createdAt desc")
    List<Newsletter> listNews(Pageable pageable);
    @Query("select n from Newsletter n order by n.createdAt desc")
    List<Newsletter> listNewsNotPageable();
}
