package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Testimony;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestimonyRepo extends JpaRepository<Testimony, Long> {

    @Query("select t from Testimony t order by t.createdAt desc")
    List<Testimony> listTestimony(Pageable pageable);
    @Query("select t from Testimony t order by t.createdAt desc")
    List<Testimony> listTestimonyNotPageable();

    @Query(value = "select t from Testimony t where  t.author = :user1 order by t.createdAt desc ")
    List<Testimony> listTestimonyByUser(@Param(value = "user1") AppUser appUser, Pageable pageable);

    @Query(value = "select t from Testimony t where t.author = :user1 order by t.createdAt desc ")
    List<Testimony> listTestimonyByUserNotPageable(@Param(value = "user1") AppUser appUser);
}
