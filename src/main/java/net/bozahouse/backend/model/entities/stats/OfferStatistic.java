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
@Table(name = "offerStatistics")
public class OfferStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;
    private long totalOffer = 0;
    private long totalOfferPerDay = 0;
    private long totalOfferValid = 0;
    private long totalOfferNotValid  = 0;

    private int currentPage;
    private int totalPages;
    private int pageSize;
}
