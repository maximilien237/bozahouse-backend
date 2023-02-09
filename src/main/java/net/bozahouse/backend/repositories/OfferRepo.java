package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Offer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface OfferRepo extends JpaRepository<Offer, String> {

    @Query(value = "select o from Offer o where o.valid = true order by o.publishedAt desc ")
    List<Offer> listOffer(Pageable pageable);

    @Query("select o from Offer o where o.valid = true order by o.publishedAt desc ")
    List<Offer> listOfferNotPageable();

    @Query(value = "select o from Offer o where o.valid = false order by o.publishedAt desc ")
    List<Offer> listOfferValidFalse(Pageable pageable);

    @Query(value = "select o from Offer o where o.valid = false order by o.publishedAt desc ")
    List<Offer> listOfferValidFalseNotPageable();

    @Query(value = "select o from Offer o where o.valid = true and o.user = :user1 order by o.publishedAt desc ")
    List<Offer> listOfferByUser(@Param(value = "user1") AppUser appUser, Pageable pageable);

    @Query(value = "select o from Offer o where o.valid = true and o.user = :user1 order by o.publishedAt desc ")
    List<Offer> listOfferByUserNotPageable(@Param(value = "user1") AppUser appUser);

    @Query("select o from Offer o where o.valid = true and o.title like :title order by o.publishedAt desc ")
    List<Offer> listOfferByTitle(@Param(value = "title") String title, Pageable pageable);

    @Query("select o from Offer o where o.valid = true and o.type like :type order by o.publishedAt desc ")
    List<Offer> listOfferByType(@Param(value = "type") String key, Pageable pageable);

    @Query("select o from Offer o where o.title = :title or o.contract = :contract or o.workMode =:workMode or o.address = :address or o.experience =:experience or o.type =:type or o.domain =:domain and o.valid = true order by o.publishedAt desc ")
    List<Offer> listOfferByFiltering(@Param(value = "title") String title, @Param(value = "contract") String contract,
                            @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                            @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain, Pageable pageable);


    @Query("select o from Offer o where o.title = :title or o.contract = :contract or o.workMode =:workMode or o.address = :address or o.experience =:experience or o.type =:type or o.domain =:domain and o.valid = true order by o.publishedAt desc ")
    List<Offer> listOfferByFilteringNotPageable(@Param(value = "title") String title, @Param(value = "contract") String contract,
                            @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                            @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain);


    @Query("select o from Offer o where o.title = :title or o.contract = :contract or o.workMode =:workMode or o.address = :address or o.experience =:experience or o.type =:type or o.domain =:domain and o.valid = false order by o.publishedAt desc ")
    List<Offer> listOfferNotValidByFiltering(@Param(value = "title") String title, @Param(value = "contract") String contract,
                                     @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                                     @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain, Pageable pageable);


    @Query("select o from Offer o where o.title = :title or o.contract = :contract or o.workMode =:workMode or o.address = :address or o.experience =:experience or o.type =:type or o.domain =:domain and o.valid = false order by o.publishedAt desc ")
    List<Offer> listOfferNotValidByFilteringNotPageable(@Param(value = "title") String title, @Param(value = "contract") String contract,
                                             @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                                             @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain);


    @Query(value = "select o from Offer o where o.title LIKE :title or o.contract LIKE :contract or o.workMode LIKE :workMode or o.address LIKE :address or o.experience LIKE :experience or o.type LIKE :type or o.domain LIKE :domain or o.publishedAt >= :startDate AND o.publishedAt <= :endDate and o.valid = true order by o.publishedAt desc ")
    List<Offer> listOfferByFilteringDate(@Param(value = "title") String title, @Param(value = "contract") String contract,
                                @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                                @Param(value = "experience")String experience, @Param(value = "type") String type,
                                @Param(value = "domain")String domain, @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate, Pageable pageable);


    /*
    @Query(value = "select * from offers  where title = :title or contract = :contract or work_mode =:workMode or address = :address or experience =:experience or type =:type or domain =:domain and valid = true order by published_at desc ",nativeQuery = true)
    List<Offer> filterOffer(@Param(value = "title") String title, @Param(value = "contract") String contract,
                            @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                            @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain, Pageable pageable);

*/

}
