package net.bozahouse.backend.services;

import net.bozahouse.backend.exception.entitie.NewsletterNotFoundException;
import net.bozahouse.backend.model.entities.Newsletter;
import net.bozahouse.backend.model.forms.NewsletterForm;
import net.bozahouse.backend.model.views.NewsletterView;

import java.text.ParseException;
import java.util.List;

public interface NewsletterService {
    Newsletter getNews(Long id) throws NewsletterNotFoundException;

    NewsletterView getNewsView(Long id) throws NewsletterNotFoundException;

    Newsletter createNews(Newsletter newsletter);

    NewsletterView createNewsView(Newsletter newsletter);

    Newsletter updateNews(NewsletterForm form) throws NewsletterNotFoundException, ParseException;

    NewsletterView updateNewsView(NewsletterForm form) throws NewsletterNotFoundException, ParseException;

    void deleteNews(Long id) throws NewsletterNotFoundException;

    List<NewsletterView> listNewsViewPageable(int page, int size);
}
