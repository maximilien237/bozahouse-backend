package net.bozahouse.backend.dtos;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.model.entities.Newsletter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author maximilien kengne kongne
 * email : maximiliendenver@gmail.com
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class NewsletterDTO {
    private Long id;
    private String subject;
    private String frenchContent;
    private String englishContent;
    private Date sendingDate;
    private Date createdAt;
    private Date updatedAt;
    private String username;

    public static NewsletterDTO mapToDTO(Newsletter newsletter) {
        if(newsletter==null){
            return null;
        }

        NewsletterDTO newsletterDTO = new NewsletterDTO();
        BeanUtils.copyProperties(newsletter,newsletterDTO);
        newsletterDTO.setUsername(newsletter.getUser().getUsername());
        return newsletterDTO;

    }

    public static Newsletter mapToEntity(NewsletterDTO newsletterDTO) {
        if(newsletterDTO==null){
            return null;
        }
        Newsletter newsletter = new Newsletter();
        BeanUtils.copyProperties(newsletterDTO,newsletter);

        return  newsletter;
    }

}
