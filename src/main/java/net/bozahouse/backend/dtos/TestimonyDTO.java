package net.bozahouse.backend.dtos;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.model.entities.Talent;
import net.bozahouse.backend.model.entities.Testimony;
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
public class TestimonyDTO {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String message;
    private String authorLastname;
    private String authorFirstname;
    private String authorUsername;

    public static TestimonyDTO mapToDTO(Testimony testimony) {
        if(testimony==null){
            return null;
        }

        TestimonyDTO testimonyDTO = new TestimonyDTO();
        BeanUtils.copyProperties(testimony,testimonyDTO);

        testimonyDTO.setAuthorUsername(testimony.getAuthor().getUsername());
        testimonyDTO.setAuthorLastname(testimony.getAuthor().getLastname());
        testimonyDTO.setAuthorFirstname(testimony.getAuthor().getFirstname());
        return testimonyDTO;

    }

    public static Testimony mapToEntity(TestimonyDTO testimonyDTO) {
        if(testimonyDTO==null){
            return null;
        }
        Testimony testimony = new Testimony();
        BeanUtils.copyProperties(testimonyDTO,testimony);

        return  testimony;
    }
}
