package net.bozahouse.backend.dtos;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.exception.EntityNotFoundException;
import net.bozahouse.backend.model.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;

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
public class PageDTO<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static PageDTO<AppRoleDTO> mapToAppRolePageDTO(Page<AppRole> appRolePage) {
        log.info("paginate AppRole DTO...");
        if (appRolePage.getContent().isEmpty()){
            throw new EntityNotFoundException( "nothing AppRole content in database");
        }
        PageDTO<AppRoleDTO> pageDTO = new PageDTO<>();
        BeanUtils.copyProperties(appRolePage,pageDTO );
        pageDTO.setPage(appRolePage.getNumber());
        // get content for page object
        pageDTO.setContent(appRolePage.getContent().stream().map(AppRoleDTO::mapToDTO).toList());
        return  pageDTO;
    }


    public static PageDTO<AppUserDTO> mapToAppUserPageDTO(Page<AppUser> appUserPage) {
        log.info("paginate AppUser DTO...");
        if (appUserPage.getContent().isEmpty()){
            throw new EntityNotFoundException( "nothing AppUser content in database");
        }
        PageDTO<AppUserDTO> pageDTO = new PageDTO<>();
        BeanUtils.copyProperties(appUserPage,pageDTO );
        pageDTO.setPage(appUserPage.getNumber());
        // get content for page object
        pageDTO.setContent(appUserPage.getContent().stream().map(AppUserDTO::mapToDTO).toList());
        return  pageDTO;
    }

    public static PageDTO<NewsletterDTO> mapToNewsletterPageDTO(Page<Newsletter> newsletterPage) {
        log.info("paginate Newsletter DTO...");
        if (newsletterPage.getContent().isEmpty()){
            throw new EntityNotFoundException( "nothing Newsletter content in database");
        }
        PageDTO<NewsletterDTO> pageDTO = new PageDTO<>();
        BeanUtils.copyProperties(newsletterPage,pageDTO );
        pageDTO.setPage(newsletterPage.getNumber());
        // get content for page object
        pageDTO.setContent(newsletterPage.getContent().stream().map(NewsletterDTO::mapToDTO).toList());
        return  pageDTO;
    }

    public static PageDTO<OfferDTO> mapToOfferPageDTO(Page<Offer> offerPage) {
        log.info("paginate Offer DTO...");
        if (offerPage.getContent().isEmpty()){
            throw new EntityNotFoundException( "nothing Offer content in database");
        }
        PageDTO<OfferDTO> pageDTO = new PageDTO<>();
        BeanUtils.copyProperties(offerPage,pageDTO );
        pageDTO.setPage(offerPage.getNumber());
        // get content for page object
        pageDTO.setContent(offerPage.getContent().stream().map(OfferDTO::mapToDTO).toList());
        return  pageDTO;
    }

    public static PageDTO<TalentDTO> mapToTalentPageDTO(Page<Talent> talentPage) {
        log.info("paginate Talent DTO...");
        if (talentPage.getContent().isEmpty()){
            throw new EntityNotFoundException( "nothing Talent content in database");
        }
        PageDTO<TalentDTO> pageDTO = new PageDTO<>();
        BeanUtils.copyProperties(talentPage,pageDTO );
        pageDTO.setPage(talentPage.getNumber());
        // get content for page object
        pageDTO.setContent(talentPage.getContent().stream().map(TalentDTO::mapToDTO).toList());
        return  pageDTO;
    }

    public static PageDTO<TestimonyDTO> mapToTestimonyPageDTO(Page<Testimony> testimonyPage) {
        log.info("paginate Testimony DTO...");
        if (testimonyPage.getContent().isEmpty()){
            throw new EntityNotFoundException( "nothing Testimony content in database");
        }
        PageDTO<TestimonyDTO> pageDTO = new PageDTO<>();
        BeanUtils.copyProperties(testimonyPage,pageDTO );
        pageDTO.setPage(testimonyPage.getNumber());
        // get content for page object
        pageDTO.setContent(testimonyPage.getContent().stream().map(TestimonyDTO::mapToDTO).toList());
        return  pageDTO;
    }


}
