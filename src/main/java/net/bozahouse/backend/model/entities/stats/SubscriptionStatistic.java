package net.bozahouse.backend.model.entities.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@Table(name = "subscriptionStatistics")
public class SubscriptionStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int totalSubscription;
    private Date createdAt;
    private double totalDailyAmount;
    private double totalAmount;
    private int numberOfSubscriptionPerDay;
    private int totalNumberOfSubscription;

    private int currentPage;
    private int totalPages;
    private int pageSize;

    public SubscriptionStatistic() {
        super();
        this.totalDailyAmount = 0.0;
        this.totalAmount = 0.0;
        this.numberOfSubscriptionPerDay = 0;
        this.totalNumberOfSubscription = 0;

    }
}
