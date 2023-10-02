package net.bozahouse.backend.dtos;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.model.entities.Talent;
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
public class TalentDTO {
    private Long id;
    private String type;
    private String title;
    private String domain;
    private String experience;
    private String skills;
    private String sex;
    private String salary;
    private String salaryChoice;
    private String tel;
    private String whatsAppNumber;
    //private String countryName;
    //private String cityName;
    private String level;
    private String countryCode;
    private String address;
    private String workMode;
    private String reference;
    private Date createdAt;
    private Date updatedAt;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String contract;
    private String linkedin;
    private String github;
    private Long totalTalent;
    private Long totalTalentValid;
    private Long totalTalentNotValid;

    public static TalentDTO mapToDTO(Talent talent) {
        if(talent==null){
            return null;
        }

        TalentDTO talentDTO = new TalentDTO();
        BeanUtils.copyProperties(talent,talentDTO);

        talentDTO.setLastname(talent.getUser().getLastname());
        talentDTO.setFirstname(talent.getUser().getFirstname());
        talentDTO.setUsername(talent.getUser().getUsername());
        talentDTO.setSex(talent.getUser().getSex());
        return talentDTO;

    }

    public static Talent mapToEntity(TalentDTO talentDTO) {
        if(talentDTO==null){
            return null;
        }
        Talent talent = new Talent();
        BeanUtils.copyProperties(talentDTO,talent);

        return  talent;
    }
}
