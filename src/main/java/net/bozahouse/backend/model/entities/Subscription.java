package net.bozahouse.backend.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date createdAt;
    private Date updatedAt;
    private Date endSubscription;
    private double amount = 0.0;
    private boolean isLocked;
    private boolean isActivated;
    private long duration=0;
    private String period;
    private String type;
    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH)
    private AppUser initiator;
    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH)
    private AppUser beneficiary;
    private double initAmount = 0.0;
    private long initDuration=0;
    private double dailyAmount = 0.0;
    private int numberOfUpdate = 0;


}
