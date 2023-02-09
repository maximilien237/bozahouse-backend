package net.bozahouse.backend.repositories;



import net.bozahouse.backend.model.entities.stats.TalentStatistic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TalentStatisticRepo extends JpaRepository<TalentStatistic, Long> {

    @Query("select t from TalentStatistic t order by t.createdAt desc")
    List<TalentStatistic> listTalentStatisticNotPageable();

    @Query("select t from TalentStatistic t order by t.createdAt desc")
    List<TalentStatistic> listTalentStatistic(Pageable pageable);
}
