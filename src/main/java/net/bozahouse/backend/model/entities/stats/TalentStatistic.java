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
@Table(name = "talentStatistics")
public class TalentStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;
    private long totalTalent = 0;
    private int totalTalentPerDay = 0;
    private long totalTalentValid = 0;
    private long totalTalentNotValid = 0;

    private int currentPage;
    private int totalPages;
    private int pageSize;
}
