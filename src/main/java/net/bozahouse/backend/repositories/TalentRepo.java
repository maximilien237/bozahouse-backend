package net.bozahouse.backend.repositories;




import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Talent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface TalentRepo extends JpaRepository<Talent, String> {

    @Query("select t from Talent t where t.valid = true order by t.publishedAt DESC  ")
    List<Talent> listTalentNotPageable();

    @Query(value = "select t from Talent t where t.valid = true order by t.publishedAt desc ")
    List<Talent> listTalent(Pageable pageable);

    @Query("select t from Talent t where t.valid = false order by t.publishedAt desc ")
    List<Talent> listTalentValidFalse(Pageable pageable);

    @Query("select t from Talent t where t.valid = false order by t.publishedAt desc ")
    List<Talent> listTalentValidFalseNotPageable();

    @Query("select t from Talent t where t.valid = true and t.title like :title order by t.publishedAt desc ")
    List<Talent> listTalentByTitle(@Param(value = "title") String title, Pageable pageable);

    @Query("select t from Talent t where t.valid = true and t.title like :title order by t.publishedAt desc ")
    List<Talent> listTalentByTitleNotPageable(@Param(value = "title") String title);

    @Query("select t from Talent t where t.valid = true and t.type like :type order by t.publishedAt desc ")
    List<Talent> listTalentByType(@Param(value = "type") String key, Pageable pageable);

    @Query(value = "select t from Talent t where t.valid = true and t.user = :user1 order by t.publishedAt desc ")
    List<Talent> listTalentByUser(@Param(value = "user1") AppUser appUser, Pageable pageable);

    @Query(value = "select t from Talent t where t.valid = true and t.user = :user1 order by t.publishedAt desc ")
    List<Talent> listTalentByUserNotPageable(@Param(value = "user1") AppUser appUser);

    @Query("select t from Talent t where t.title = :title or t.contract = :contract or t.workMode =:workMode or t.address = :address or t.experience =:experience or t.type =:type or t.domain =:domain and t.valid = true order by t.publishedAt desc ")
    List<Talent> listTalentByFiltering(@Param(value = "title") String title, @Param(value = "contract") String contract,
                              @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                              @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain, Pageable pageable);


    @Query("select t from Talent t where t.title = :title or t.contract = :contract or t.workMode =:workMode or t.address = :address or t.experience =:experience or t.type =:type or t.domain =:domain and t.valid = true order by t.publishedAt desc ")
    List<Talent> listTalentByFilteringNotPageable(@Param(value = "title") String title, @Param(value = "contract") String contract,
                              @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                              @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain);



    @Query("select t from Talent t where t.title = :title or t.contract = :contract or t.workMode =:workMode or t.address = :address or t.experience =:experience or t.type =:type or t.domain =:domain and t.valid = false order by t.publishedAt desc ")
    List<Talent> listTalentValidFalseByFiltering(@Param(value = "title") String title, @Param(value = "contract") String contract,
                                       @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                                       @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain, Pageable pageable);



    @Query("select t from Talent t where t.title = :title or t.contract = :contract or t.workMode =:workMode or t.address = :address or t.experience =:experience or t.type =:type or t.domain =:domain and t.valid = false order by t.publishedAt desc ")
    List<Talent> listTalentValidFalseByFilteringNotPageable(@Param(value = "title") String title, @Param(value = "contract") String contract,
                                                 @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                                                 @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain);


    /*

    @Query(value = "select * from talents  where title = :title or contract = :contract or work_mode =:workMode or address = :address or experience =:experience or type =:type or domain =:domain and valid = true order by published_at desc ",nativeQuery = true)
    List<Talent> filterTalent(@Param(value = "title") String title, @Param(value = "contract") String contract,
                            @Param(value = "workMode") String workMode, @Param(value = "address") String address,
                            @Param(value = "experience")String experience, @Param(value = "type") String type, @Param(value = "domain")String domain, Pageable pageable);

*/

}