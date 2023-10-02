package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.dtos.NewsletterDTO;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.exception.EntityNotFoundException;
import net.bozahouse.backend.exception.InvalidEntityException;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Newsletter;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.repositories.NewsletterRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class NewsletterServiceImpl implements NewsletterService {
    private NewsletterRepo newsletterRepo;
    private AppUserRepo appUserRepo;

    @Override
    public Newsletter getNews(Long id) {
        log.info("getting News by id :: " +id + "...");
        Optional<Newsletter> optionalNews = newsletterRepo.findById(id);

        if (optionalNews.isPresent()){
            return optionalNews.get();
        }
            throw new EntityNotFoundException("News not found for id :: " +id);


    }


    @Override
    public NewsletterDTO getNewsDTO(Long id) {
        log.info("getting News view by id :: " + id + "...");
        return NewsletterDTO.mapToDTO(getNews(id));
    }


    @Override
    public NewsletterDTO createNews(NewsletterDTO newsletterDTO, Long userId){
        log.info("creating News...");
        if (newsletterDTO.getSendingDate() == null){
            throw new InvalidEntityException("sending date is null");
        }
        AppUser appUser = appUserRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        Newsletter newsletter = NewsletterDTO.mapToEntity(newsletterDTO);
        newsletter.setUser(appUser);
        newsletterRepo.save(newsletter);
        return NewsletterDTO.mapToDTO(newsletter);
    }


    @Override
    public NewsletterDTO updateNews(NewsletterDTO newsletterDTO)  {
        log.info("updating News...");
        Newsletter newsletter = NewsletterDTO.mapToEntity(newsletterDTO);
        Newsletter updatedNewsletter = getNews(newsletter.getId());
        newsletter.setSendingDate(newsletterDTO.getSendingDate());
        newsletter.setCreatedAt(updatedNewsletter.getCreatedAt());
        newsletter.setUser(updatedNewsletter.getUser());
        newsletterRepo.save(newsletter);
        return NewsletterDTO.mapToDTO(newsletter);
    }


    @Override
    public void deleteNews(Long id){
        log.info("deleting News by id :: " + id + "...");
        newsletterRepo.delete(getNews(id));

    }

    @Override
    public PageDTO<NewsletterDTO> listNews(int page, int size) {
        log.info("list News DTO...");
        Page<Newsletter> newsletterPage = newsletterRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
        return PageDTO.mapToNewsletterPageDTO(newsletterPage);
    }
}
