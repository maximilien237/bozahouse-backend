package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.dtos.TalentDTO;
import net.bozahouse.backend.exception.EntityNotFoundException;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Talent;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.repositories.TalentRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class TalentServiceImpl implements TalentService{
    private TalentRepo talentRepo;
    private AppUserRepo userRepo;



    @Override
    public PageDTO<TalentDTO> filterTalent(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, int page, int size)  {
        log.info("filer talent");
        log.info(" before " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        Page<Talent> talentPage = talentRepo.findAllByValidTrueAndTitleOrContractOrWorkModeOrAddressOrExperienceOrTypeOrDomainOrCreatedAtBetweenOrderByCreatedAtDesc(title, contract, workMode,address, experience, type, domain,startDate, endDate, PageRequest.of(page, size));
        return PageDTO.mapToTalentPageDTO(talentPage);

    }

    @Override
    public PageDTO<TalentDTO>  filterTalentNotValid(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, int page, int size)  {
        log.info("filer talent not valid");
        log.info(" before " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        Page<Talent> talentPage = talentRepo.findAllByValidFalseAndTitleOrContractOrWorkModeOrAddressOrExperienceOrTypeOrDomainOrCreatedAtBetweenOrderByCreatedAtDesc(title, contract, workMode,address, experience, type, domain,startDate, endDate, PageRequest.of(page, size));
        return PageDTO.mapToTalentPageDTO(talentPage);

    }

    @Override
    public PageDTO<TalentDTO> listTalentByAppUser(Long appUserId, int page, int size)  {
        log.info("list Talent By AppUser");
        AppUser appUser = userRepo.findById(appUserId).orElseThrow(()-> new EntityNotFoundException("user not found"));
        Page<Talent> talentPage = talentRepo.findByValidTrueAndUserOrderByCreatedAtDesc(appUser, PageRequest.of(page, size));
        return PageDTO.mapToTalentPageDTO(talentPage);

    }


    @Override
    public Talent getTalent(Long talentId)   {
        log.info("getting talent by id :: "+talentId);
        Optional<Talent> optionalTalent = talentRepo.findById(talentId);
        if (optionalTalent.isPresent()){
            return optionalTalent.get();
        }
        throw new EntityNotFoundException("talent not found for this id :: "+talentId);
    }


    @Override
    public TalentDTO createTalent(TalentDTO talentDTO, Long userId)  {
        log.info("creating talent...");
        AppUser appUser = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found " +userId));
        Talent talent = TalentDTO.mapToEntity(talentDTO);
        talent.setUser(appUser);
        talent.setCountryCode("+237");
        talent.setReference("talent-n° " + talentRepo.count());
        talent.setValid(true);
        talentRepo.save(talent);

        return TalentDTO.mapToDTO(talent);

    }


    @Override
    public TalentDTO updateTalent(TalentDTO talentDTO)   {
        log.info("updating talent...");

        Talent talent = TalentDTO.mapToEntity(talentDTO);

        Talent talentUpdated = getTalent(talent.getId());
        talent.setReference(talentUpdated.getReference());
        talent.setCreatedAt(talentUpdated.getCreatedAt());
        talent.setValid(true);
        talent.setUser(talentUpdated.getUser());
        talentRepo.save(talent);
        return TalentDTO.mapToDTO(talent);
    }

    @Override
    public TalentDTO getTalentDTO(Long talentId)   {
        log.info("getting talent view by id :: "+talentId);
        return TalentDTO.mapToDTO(getTalent(talentId));

    }


    @Override
    public List<TalentDTO> lastThreeTalent() {
        log.info("list formal talent view");
        List<Talent> talents = talentRepo.findTop3ByValidTrueOrderByCreatedAtDesc();
        return talents.stream().map(TalentDTO::mapToDTO).toList();
    }


    @Override
    public void deleteTalent(Long talentId)   {
        log.info("delete talent with id :: " +talentId);
        Talent talent = getTalent(talentId);
        talentRepo.delete(talent);
    }


    @Override
    public void enableOrDisableTalent(Long talentId)   {
        log.info("enable or disable talent with id :: " +talentId);

        Talent talent = getTalent(talentId);
        if (talent.isValid()) {
            talent.setValid(false);
            talentRepo.save(talent);
        }
        talent.setValid(true);
        talentRepo.save(talent);
    }

}
