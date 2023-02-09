package net.bozahouse.backend.model.entities.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userStatistics")
public class AppUserStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;
    private long totalAppUser = 0;
    private long totalAppUserPerDay = 0;
    private long totalAppUserActivated = 0;
    private long totalAppUserDisabled = 0;
    private long totalAppUserHostSubscription = 0;
    private long totalAppUserNormalSubscription = 0;

    private int currentPage;
    private int totalPages;
    private int pageSize;

}
