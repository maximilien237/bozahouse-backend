package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.stats.AppUserStatistic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserStatisticRepo extends JpaRepository<AppUserStatistic, Long> {

    @Query("select a from AppUserStatistic a order by a.createdAt desc")
    List<AppUserStatistic> listAppUserStatisticNotPageable();

    @Query("select a from AppUserStatistic a order by a.createdAt desc")
    List<AppUserStatistic> listAppUserStatistic(Pageable pageable);
}
