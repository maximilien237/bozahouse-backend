package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.TestimonyNotFoundException;
import net.bozahouse.backend.mappers.EntityToViewConverter;
import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.mappers.ListEntityToListViewConverter;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Testimony;
import net.bozahouse.backend.model.forms.TestimonyForm;
import net.bozahouse.backend.model.views.TestimonyView;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.repositories.TestimonyRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TestimonyServiceImpl implements TestimonyService{

    private TestimonyRepo testimonyRepo;
    private AppUserRepo userRepo;

    @Override
    public Testimony getTestimony(Long id) throws TestimonyNotFoundException {
        log.info("getting Testimony by id :: " +id + "...");
        Optional<Testimony> optionalTestimony = testimonyRepo.findById(id);
        Testimony testimony;
        if (optionalTestimony.isPresent()){
            testimony = optionalTestimony.get();
        }else {
            throw new TestimonyNotFoundException("Testimony not found for id ::" +id);
        }
        return testimony;
    }

    @Override
    public TestimonyView getTestimonyView(Long id) throws TestimonyNotFoundException {
        log.info("getting Testimony view by id :: " + id + "...");
        Testimony testimony = getTestimony(id);
        TestimonyView testimonyView = EntityToViewConverter.convertEntityToTestimonyView(testimony);
        return testimonyView;
    }

    @Override
    public Testimony createTestimony(Testimony testimony) {
        log.info("creating Testimony...");
        testimony.setCreatedAt(new Date());
        Testimony savedTestimony = testimonyRepo.save(testimony);

        return savedTestimony;
    }

    @Override
    public TestimonyView createTestimonyView(Testimony testimony) {
        log.info("creating Testimony view...");
        TestimonyView view = EntityToViewConverter.convertEntityToTestimonyView(createTestimony(testimony));
        return view;
    }

    @Override
    public Testimony updateTestimony(TestimonyForm form) throws TestimonyNotFoundException {
        log.info("updating Testimony...");

        Testimony testimony = FormToEntityConverter.convertFormToTestimony(form);
        Testimony updatedTestimony = getTestimony(form.getId());
        testimony.setCreatedAt(updatedTestimony.getCreatedAt());
        testimony.setUpdatedAt(new Date());
        testimony.setAuthor(updatedTestimony.getAuthor());
        Testimony updateTestimony = testimonyRepo.save(testimony);
        return updateTestimony;
    }

    @Override
    public TestimonyView updateTestimonyView(TestimonyForm form) throws TestimonyNotFoundException, ParseException {
        log.info("updating Testimony view...");
        return EntityToViewConverter.convertEntityToTestimonyView(updateTestimony(form));
    }

    @Override
    public void deleteTestimony(Long id) throws TestimonyNotFoundException {
        log.info("deleting Testimony by id :: " + id + "...");
        getTestimony(id);
        testimonyRepo.deleteById(id);
    }


    @Override
    public List<TestimonyView> listTestimonyViewPageable(int page, int size) {
        log.info("list Testimony view...");
        List<Testimony> testimonies = testimonyRepo.listTestimony(PageRequest.of(page, size));
        List<Testimony> testimonyList = testimonyRepo.listTestimonyNotPageable();
        List<TestimonyView> testimonyViews = ListEntityToListViewConverter.paginateTestimonyViewList(testimonies, page, size, testimonyList.size());
        return testimonyViews;
    }

    @Override
    public List<TestimonyView> listTestimonyByUserView(String appUserId, int page, int size) throws AppUserNotFoundException {
        log.info("list Testimony By AppUser View");
        AppUser appUser = userRepo.findById(appUserId).orElseThrow(()-> new AppUserNotFoundException("user not found"));
        List<Testimony> testimonies = testimonyRepo.listTestimonyByUser(appUser, PageRequest.of(page, size));
        List<Testimony> testimonyList = testimonyRepo.listTestimonyByUserNotPageable(appUser);
        List<TestimonyView> testimonyViews = ListEntityToListViewConverter.paginateTestimonyViewList(testimonies, page, size, testimonyList.size());
        return testimonyViews;
    }


}
