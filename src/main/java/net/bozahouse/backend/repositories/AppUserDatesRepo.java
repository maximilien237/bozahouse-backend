package net.bozahouse.backend.repositories;

import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.AppUserDates;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppUserDatesRepo extends JpaRepository<AppUserDates, Long> {

    @Query("select a from AppUserDates a where a.user = :user order by a.lastConnexion desc")
    List<AppUserDates> findByUserOrderByLastConnexionDesc(@Param(value = "user") AppUser appUser, Pageable pageable);

    @Query("select a from AppUserDates a where a.user = :user order by a.lastConnexion desc")
    List<AppUserDates> findByUser(@Param(value = "user") AppUser appUser);
}
