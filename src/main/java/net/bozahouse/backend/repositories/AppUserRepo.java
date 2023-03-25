package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.AppUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, String> {
    @Query("" + "select case when count(a) > 0 then " +
    "true else false end " +
    "from AppUser a " +
    "where a.email=?1")
    Boolean selectExistsEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByPromoCode(String code);
    Boolean existsByReferralCode(String referral);
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByPromoCode(String promoCode);
    Optional<AppUser> findByEmail(String email);

    @Query("select a from AppUser a where a.account =?1")
    AppUser findByAccount(String account);

    @Query("select a from AppUser a where a.isActivated = true order by a.createdAt desc ")
    List<AppUser> listAppUser(Pageable pageable);
    @Query("select a from AppUser a where a.isActivated = true order by a.createdAt desc ")
    List<AppUser> listAppUserNotPageable();

    @Query("select a from AppUser a where a.activatedHostSubscription = true order by a.createdAt desc ")
    List<AppUser> listAppUserHost();

    @Query("select a from AppUser a where a.activatedNormalSubscription = true order by a.createdAt desc ")
    List<AppUser> listAppUserNormal();

    @Query("select a from AppUser a where a.isActivated = true and a.account like :acc order by a.createdAt desc ")
    List<AppUser> listAppUserTalent(@Param(value = "acc") String key);


    @Query("select a from AppUser a where a.isActivated = false order by a.createdAt desc ")
    List<AppUser> listAppUserByActivatedFalse(Pageable pageable);

    @Query("select a from AppUser a where a.isActivated = false order by a.createdAt desc ")
    List<AppUser> listAppUserByActivatedFalseNotPageable();

    @Query("select a from AppUser a where a.isActivated = true and a.username like :kw order by a.createdAt desc ")
    List<AppUser> findAllByUsername(@Param(value = "kw") String key);

    @Query("select a from AppUser a where a.isActivated = true and a.username like :kw order by a.createdAt desc ")
    List<AppUser> findAllByUsername(@Param(value = "kw") String key, Pageable pageable);

    @Query("select a from AppUser a where a.isActivated = true and a.username like :kw order by a.createdAt desc ")
    List<AppUser> findAllByUsernameOrderByCreatedAtDesc(@Param(value = "kw") String key);


    @Query("select a from AppUser a where a.isActivated = false and a.username like :kw order by a.createdAt desc ")
    List<AppUser> listAppUserDisabledByUsername(@Param(value = "kw") String key, Pageable pageable);

    @Query("select a from AppUser a where a.isActivated = false and a.username like :kw order by a.createdAt desc ")
    List<AppUser> listAppUserDisabledByUsernameNotPageable(@Param(value = "kw") String key);

    @Query("select count (a) from AppUser a where a.referralCode = :referral order by a.createdAt DESC" )
    Double countAllByReferralCode(@Param("referral") String referralCode);



}
