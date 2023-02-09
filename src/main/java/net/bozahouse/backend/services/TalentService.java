package net.bozahouse.backend.services;


import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.TalentNotFoundException;
import net.bozahouse.backend.model.entities.Talent;
import net.bozahouse.backend.model.forms.TalentForm;
import net.bozahouse.backend.model.views.TalentView;

import java.util.List;

public interface TalentService {


    List<TalentView> filterTalentView(String title, String contract, String workMode, String address, String experience, String type, String domain, int page, int size);

    List<TalentView> filterTalentNotValidView(String title, String contract, String workMode, String address, String experience, String type, String domain, int page, int size);

    List<TalentView> listTalentByAppUserView(String appUserId, int page, int size) throws AppUserNotFoundException;

    Talent getTalent(String talentId) throws TalentNotFoundException;

    Talent createTalent(Talent talent);

    Talent updateTalent(TalentForm form) throws TalentNotFoundException;

    TalentView getTalentView(String talentId) throws TalentNotFoundException;

    TalentView createTalentView(Talent talent);

    TalentView updateTalentView(TalentForm form) throws TalentNotFoundException;

    List<TalentView> listTalentView(int page, int size);

    List<TalentView> listByTalentsTitleView(String title, int page, int size);

    List<TalentView> listFormalTalentView(int page, int size);

    List<TalentView> listFreeTalentView(int page, int size);

    List<TalentView> lastThreeTalentView();

    void deleteTalent(String talentId) throws TalentNotFoundException;

    void disableTalent(String talentId) throws TalentNotFoundException;

    void enableTalent(String talentId) throws TalentNotFoundException;
}
