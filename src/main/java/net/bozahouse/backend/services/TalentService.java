package net.bozahouse.backend.services;


import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.dtos.TalentDTO;
import net.bozahouse.backend.model.entities.Talent;

import java.util.Date;
import java.util.List;

public interface TalentService {


    PageDTO<TalentDTO> filterTalent(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, int page, int size);

    PageDTO<TalentDTO>  filterTalentNotValid(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, int page, int size);

    PageDTO<TalentDTO> listTalentByAppUser(Long appUserId, int page, int size);

    Talent getTalent(Long talentId);

    TalentDTO createTalent(TalentDTO talentDTO, Long userId);

    TalentDTO updateTalent(TalentDTO talentDTO);

    TalentDTO getTalentDTO(Long talentId);

    List<TalentDTO> lastThreeTalent();

    void deleteTalent(Long talentId);

    void enableOrDisableTalent(Long talentId);
}
