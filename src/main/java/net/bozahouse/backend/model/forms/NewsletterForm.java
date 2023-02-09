package net.bozahouse.backend.model.forms;

import lombok.Data;

@Data
public class NewsletterForm {

    private long id;
    private String subject;
    private String frenchContent;
    private String englishContent;
    private String sendingDate;


}
