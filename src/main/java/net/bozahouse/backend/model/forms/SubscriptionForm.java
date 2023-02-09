package net.bozahouse.backend.model.forms;


import lombok.Data;


@Data
public class SubscriptionForm {
    private long id;
    private String period;
    private String type;
    private String username;


}
