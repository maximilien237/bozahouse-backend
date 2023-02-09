package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.exception.entitie.NewsletterNotFoundException;
import net.bozahouse.backend.mappers.EntityToViewConverter;
import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.mappers.ListEntityToListViewConverter;
import net.bozahouse.backend.model.entities.Newsletter;
import net.bozahouse.backend.model.forms.NewsletterForm;
import net.bozahouse.backend.model.views.NewsletterView;
import net.bozahouse.backend.repositories.NewsletterRepo;
import net.bozahouse.backend.utils.DateUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class NewsletterServiceImpl implements NewsletterService {
    private NewsletterRepo newsletterRepo;
    @Override
    public Newsletter getNews(Long id) throws NewsletterNotFoundException {
        log.info("getting News by id :: " +id + "...");
        Optional<Newsletter> optionalNews = newsletterRepo.findById(id);
        Newsletter newsletter;
        if (optionalNews.isPresent()){
            newsletter = optionalNews.get();
        }else {
            throw new NewsletterNotFoundException("News not found for id ::" +id);
        }
        return newsletter;
    }


    @Override
    public NewsletterView getNewsView(Long id) throws NewsletterNotFoundException {
        log.info("getting News view by id :: " + id + "...");
        Newsletter newsletter = getNews(id);
        NewsletterView newsletterView = EntityToViewConverter.convertEntityToNewsView(newsletter);
        return newsletterView;
    }


    @Override
    public Newsletter createNews(Newsletter newsletter){
        log.info("creating News...");
        newsletter.setCreatedAt(new Date());
        Newsletter savedNewsletter = newsletterRepo.save(newsletter);

        return savedNewsletter;
    }

    @Override
    public NewsletterView createNewsView(Newsletter newsletter) {
        log.info("creating News view...");
        NewsletterView view = EntityToViewConverter.convertEntityToNewsView(createNews(newsletter));
        return view;
    }

    @Override
    public Newsletter updateNews(NewsletterForm form) throws NewsletterNotFoundException, ParseException {
        log.info("updating News...");
        Date sendingDate = DateUtils.convertStringToDate(form.getSendingDate());
        Newsletter newsletter = FormToEntityConverter.convertFormToNews(form);
        Newsletter updatedNewsletter = getNews(form.getId());
        newsletter.setSendingDate(sendingDate);
        newsletter.setCreatedAt(updatedNewsletter.getCreatedAt());
        newsletter.setUpdatedAt(new Date());
        newsletter.setUser(updatedNewsletter.getUser());
        Newsletter updateNewsletter = newsletterRepo.save(newsletter);
        return updateNewsletter;
    }

    @Override
    public NewsletterView updateNewsView(NewsletterForm form) throws NewsletterNotFoundException, ParseException {
        log.info("updating News view...");
        return EntityToViewConverter.convertEntityToNewsView(updateNews(form));
    }


    @Override
    public void deleteNews(Long id) throws NewsletterNotFoundException{
        log.info("deleting News by id :: " + id + "...");
        getNews(id);
        newsletterRepo.deleteById(id);

    }

    @Override
    public List<NewsletterView> listNewsViewPageable(int page, int size) {
        log.info("list News view...");
        List<Newsletter> newsletters = newsletterRepo.listNews(PageRequest.of(page, size));
        List<Newsletter> newsletterList = newsletterRepo.listNewsNotPageable();
        List<NewsletterView> newsletterViews = ListEntityToListViewConverter.paginateNewsletterViewList(newsletters, page, size, newsletterList.size());
        return newsletterViews;
    }
}
