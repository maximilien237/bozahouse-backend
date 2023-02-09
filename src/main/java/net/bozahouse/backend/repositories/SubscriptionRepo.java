package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    @Query("select s from Subscription s where s.period like :sub order by s.createdAt desc ")
    List<Subscription> listSubscriptionByPeriod(@Param(value = "sub")String subscriptionType, Pageable pageable);

    @Query("select s from Subscription s where s.period like :sub order by s.createdAt desc ")
    List<Subscription> listSubscriptionByPeriodNotPageable(@Param(value = "sub")String subscriptionType);
    @Query("select s from Subscription s where s.isActivated = true and s.beneficiary.isActivated = true order by s.createdAt desc ")
    List<Subscription> listSubscription(Pageable pageable);

    @Query("select s from Subscription s where s.isActivated = true and s.beneficiary.isActivated = true  order by s.createdAt desc")
    List<Subscription> listSubscriptionNotPageable();

    @Query("select s from Subscription s where s.beneficiary.isActivated = true order by s.createdAt desc ")
    List<Subscription> findAllByBeneficiaryActivatedTrue();
    @Query("select s from Subscription s where s.isActivated = false order by s.createdAt desc ")
    List<Subscription> listSubscriptionByActivatedFalse(Pageable pageable);

    @Query("select s from Subscription s where s.isActivated = false order by s.createdAt desc ")
    List<Subscription> listSubscriptionActivatedFalseNotPageable();
    @Query("select s from Subscription s where s.isActivated = true and s.beneficiary = :user order by s.createdAt desc ")
    List<Subscription> listSubscriptionByBeneficiary(@Param("user")AppUser appUser, Pageable pageable);

    @Query("select s from Subscription s where s.beneficiary = :user order by s.createdAt desc ")
    List<Subscription> listSubscriptionByBeneficiaryNotPageable(@Param("user")AppUser appUser);
    @Query("select s.type, s.beneficiary from Subscription s where s.type =:type and s.beneficiary = :user and s.isActivated = true order by s.createdAt desc ")
    boolean existsByTypeAndBeneficiaryAndActivatedTrue(@Param("type")String type,@Param("user") AppUser appUser);

}
