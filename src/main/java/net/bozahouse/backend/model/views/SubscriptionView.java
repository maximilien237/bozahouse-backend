package net.bozahouse.backend.model.views;


import lombok.Data;

import java.util.Date;


@Data
public class SubscriptionView {
    private long id;
    private Date createdAt;
    private Date updatedAt;
    private Date endSubscription;
    private double amount;
    private boolean isLocked;
    private boolean isActivated;
    private long duration;
    private String period;
    private String type;
    private String initiatorLastname;
    private String beneficiaryUsername;
    private String beneficiaryEmail;
    private String beneficiaryAccount;
    private double initAmount;
    private long initDuration;
    private double dailyAmount;
    private int numberOfUpdate;
    private int currentPage;
    private int totalPages;
    private int pageSize;

}
