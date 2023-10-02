package net.bozahouse.backend.dtos;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.model.entities.Offer;
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
public class OfferDTO {

    private Long id;
    private String type;
    private String title;
    private String mission;
    private String skills;
    private int needPeople;
    private String domain;
    private String address;
    private String tel;
    private String whatsAppNumber;
    private String experience;
    private String email;
    private String salary;
    private String salaryChoice;
    private Date endOffer;
    private String workMode;
    private String profile;
    private int duration;
    private String reference;
    private Date publishedAt;
    private Date updatedAt;
    private String username;
    private String firstname;
    private String lastname;
    private String sex;
    private String name;
    private String fcb;
    private String linkedin;
    private String web;
    private String contract;
    private String countryCode;
    private Long totalOffer;
    private Long totalOfferValid;
    private Long totalOfferNotValid;

    public static OfferDTO mapToDTO(Offer offer) {
        if(offer==null){
            return null;
        }

        OfferDTO offerDTO = new OfferDTO();
        BeanUtils.copyProperties(offer,offerDTO);

        offerDTO.setSex(offer.getUser().getSex());
        offerDTO.setUsername(offer.getUser().getUsername());
        offerDTO.setLastname(offer.getUser().getLastname());
        offerDTO.setFirstname(offer.getUser().getFirstname());
        return offerDTO;

    }

    public static Offer mapToEntity(OfferDTO offerDTO) {
        if(offerDTO==null){
            return null;
        }
        Offer offer = new Offer();
        BeanUtils.copyProperties(offerDTO,offer);

        return  offer;
    }
}
