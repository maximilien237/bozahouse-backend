package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.TalentNotFoundException;
import net.bozahouse.backend.mappers.EntityToViewConverter;
import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.mappers.ListEntityToListViewConverter;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Talent;
import net.bozahouse.backend.model.forms.TalentForm;
import net.bozahouse.backend.model.views.TalentView;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.repositories.TalentRepo;
import net.bozahouse.backend.utils.Constant;
import net.bozahouse.backend.utils.DateUtils;
import net.bozahouse.backend.utils.RandomUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class TalentServiceImpl implements TalentService{
    private TalentRepo talentRepo;
    private AppUserRepo userRepo;



    @Override
    public List<TalentView> filterTalentView(String title, String contract, String workMode, String address, String experience, String type, String domain, int page, int size)  {
        log.info("filer talent");
        log.info(" before " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        List<Talent> talents = talentRepo.listTalentByFiltering(title, contract, workMode,address, experience, type, domain, PageRequest.of(page, size));

        List<Talent> talentList = talentRepo.listTalentByFilteringNotPageable(title, contract, workMode,address, experience, type, domain);


        if (talents.isEmpty()){
            return listTalentView(page, size);
        }

        log.info(" after " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        int sizeActivated = talentRepo.listTalentNotPageable().size();
        int sizeDisabled = talentRepo.listTalentValidFalseNotPageable().size();

        List<TalentView> talentViews = ListEntityToListViewConverter.paginateTalentView(talents,page,size,sizeActivated, sizeActivated, sizeDisabled);
        return talentViews;
    }

    @Override
    public List<TalentView> filterTalentNotValidView(String title, String contract, String workMode, String address, String experience, String type, String domain, int page, int size)  {
        log.info("filer talent");
        log.info(" before " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        List<Talent> talents = talentRepo.listTalentValidFalseByFiltering(title, contract, workMode,address, experience, type, domain, PageRequest.of(page, size));

        List<Talent> talentList = talentRepo.listTalentValidFalseByFilteringNotPageable(title, contract, workMode,address, experience, type, domain);


        if (talents.isEmpty()){
            return listTalentNotValidView(page, size);
        }

        log.info(" after " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        int sizeActivated = talentRepo.listTalentNotPageable().size();
        int sizeDisabled = talentRepo.listTalentValidFalseNotPageable().size();

        List<TalentView> talentViews = ListEntityToListViewConverter.paginateTalentView(talents,page,size,sizeDisabled, sizeActivated, sizeDisabled);
        return talentViews;
    }

    @Override
    public List<TalentView> listTalentByAppUserView(String appUserId, int page, int size) throws AppUserNotFoundException {
        log.info("list Talent By AppUser View");
        AppUser appUser = userRepo.findById(appUserId).orElseThrow(()-> new AppUserNotFoundException("user not found"));
        List<Talent> talents = talentRepo.listTalentByUser(appUser, PageRequest.of(page, size));
        List<Talent> talentList = talentRepo.listTalentByUserNotPageable(appUser);

        int sizeActivated = talentRepo.listTalentNotPageable().size();
        int sizeDisabled = talentRepo.listTalentValidFalseNotPageable().size();

        List<TalentView> talentViews = ListEntityToListViewConverter.paginateTalentView(talents, page, size, talentList.size(),sizeActivated, sizeDisabled);
        return talentViews;
    }


    @Override
    public Talent getTalent(String talentId) throws TalentNotFoundException {
        log.info("getting talent by id :: "+talentId);
        Talent talent = talentRepo.findById(talentId).
                orElseThrow(()->new TalentNotFoundException("talent not found for this id ::"+talentId));

        return talent;
    }


    @Override
    public Talent createTalent(Talent talent)  {
        log.info("creating talent...");

        talent.setId(RandomUtils.id());
        talent.setCountryCode("+237");

        talent.setPublishedAt(DateUtils.currentDate());
        talent.setReference("talent" + RandomUtils.unique().substring(4,8));
        talent.setValid(true);
        Talent savedTalent = talentRepo.save(talent);

        return savedTalent;

    }

    @Override
    public TalentView createTalentView(Talent talent)  {
        log.info("creating talent view...");
        TalentView view = EntityToViewConverter.convertEntityToTalentView(createTalent(talent));
        return view;
    }


    @Override
    public Talent updateTalent(TalentForm form) throws TalentNotFoundException {
        log.info("updating talent...");

        Talent talent = FormToEntityConverter.convertFormToTalent(form);

        talent.setUpdatedAt(DateUtils.currentDate());

        Talent talentUpdated = getTalent(talent.getId());
        talent.setReference(talentUpdated.getReference());
        talent.setPublishedAt(talentUpdated.getPublishedAt());
        talent.setValid(true);
        talent.setUser(talentUpdated.getUser());
        return talentRepo.save(talent);
    }

    @Override
    public TalentView updateTalentView(TalentForm form) throws TalentNotFoundException {
        log.info("updating talent view...");
        return EntityToViewConverter.convertEntityToTalentView(updateTalent(form));
    }

    //views

    @Override
    public TalentView getTalentView(String talentId) throws TalentNotFoundException {
        log.info("getting talent view by id ::"+talentId);
        Talent talent = getTalent(talentId);
        return EntityToViewConverter.convertEntityToTalentView(talent);

    }


    @Override
    public List<TalentView> listTalentView(int page, int size) {
        log.info("list talent view");
        List<Talent> talents = talentRepo.listTalent(PageRequest.of(page, size));
        //List<Talent> talentList = talentRepo.listTalentNotPageable();

        int sizeActivated = talentRepo.listTalentNotPageable().size();
        int sizeDisabled = talentRepo.listTalentValidFalseNotPageable().size();

        List<TalentView> talentViews = ListEntityToListViewConverter.paginateTalentView(talents, page, size, sizeActivated, sizeActivated, sizeDisabled);

        return talentViews;
    }


    public List<TalentView> listTalentNotValidView(int page, int size) {
        log.info("list talent not valid view");
        List<Talent> talents = talentRepo.listTalentValidFalse(PageRequest.of(page, size));
        //List<Talent> talentList = talentRepo.listTalentValidFalseNotPageable();

        int sizeActivated = talentRepo.listTalentNotPageable().size();
        int sizeDisabled = talentRepo.listTalentValidFalseNotPageable().size();

        List<TalentView> talentViews = ListEntityToListViewConverter.paginateTalentView(talents, page, size, sizeDisabled, sizeActivated, sizeDisabled);

        return talentViews;
    }

    @Override
    public List<TalentView> listByTalentsTitleView(String title, int page, int size) {
        log.info("list talent view");
        List<Talent> talents = talentRepo.listTalentByType(title, PageRequest.of(page, size));
        List<Talent> talentList = talentRepo.listTalentByTitleNotPageable(title);

        int sizeActivated = talentRepo.listTalentNotPageable().size();
        int sizeDisabled = talentRepo.listTalentValidFalseNotPageable().size();

        List<TalentView> talentViews = ListEntityToListViewConverter.paginateTalentView(talents, page, size, talentList.size(), sizeActivated, sizeDisabled);

        return talentViews;
    }



    @Override
    public List<TalentView> listFormalTalentView(int page, int size) {
        log.info("list formal talent view");
        List<Talent> talents = talentRepo.listTalentByType(Constant.talent,PageRequest.of(page, size));
        List<TalentView> talentViews = talents.stream().map(talent -> EntityToViewConverter.convertEntityToTalentView(talent)).collect(Collectors.toList());
        return talentViews;
    }

    @Override
    public List<TalentView> listFreeTalentView(int page, int size) {
        log.info("list free talent view");
        List<Talent> talents = talentRepo.listTalentByType(Constant.talent,PageRequest.of(page, size));
        List<TalentView> talentViews = talents.stream().map(talent -> EntityToViewConverter.convertEntityToTalentView(talent)).collect(Collectors.toList());
        return talentViews;
    }

    @Override
    public List<TalentView> lastThreeTalentView() {
        log.info("list formal talent view");
        List<Talent> talents = talentRepo.listTalentNotPageable();
        List<TalentView> formalTalentViews = talents.stream().limit(3).map(talent -> EntityToViewConverter.convertEntityToTalentView(talent)).collect(Collectors.toList());
        return formalTalentViews;
    }


    @Override
    public void deleteTalent(String talentId) throws TalentNotFoundException {
        log.info("delete talent with id ::" +talentId);
        Talent talent = getTalent(talentId);
            talentRepo.delete(talent);
    }

    @Override
    public void disableTalent(String talentId) throws TalentNotFoundException {
        log.info("disable talent with id ::" +talentId);
        Talent talent = getTalent(talentId);
        talent.setValid(false);
        talentRepo.save(talent);
    }

    @Override
    public void enableTalent(String talentId) throws TalentNotFoundException {
        log.info("enable talent with id ::" +talentId);
        Talent talent = getTalent(talentId);
        talent.setValid(true);
        talentRepo.save(talent);
    }

}
