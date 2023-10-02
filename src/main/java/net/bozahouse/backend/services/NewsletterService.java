package net.bozahouse.backend.services;


import net.bozahouse.backend.dtos.NewsletterDTO;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.model.entities.Newsletter;

public interface NewsletterService {

    Newsletter getNews(Long id);

    NewsletterDTO getNewsDTO(Long id);

    NewsletterDTO createNews(NewsletterDTO newsletterDTO, Long userId);

    NewsletterDTO updateNews(NewsletterDTO newsletterDTO);

    void deleteNews(Long id);

    PageDTO<NewsletterDTO> listNews(int page, int size);
}
