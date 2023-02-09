package net.bozahouse.backend.services;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.TestimonyNotFoundException;


import net.bozahouse.backend.model.entities.Testimony;

import net.bozahouse.backend.model.forms.TestimonyForm;

import net.bozahouse.backend.model.views.TestimonyView;

import java.text.ParseException;
import java.util.List;

public interface TestimonyService {
    Testimony getTestimony(Long id) throws TestimonyNotFoundException;

    TestimonyView getTestimonyView(Long id) throws TestimonyNotFoundException;

    Testimony createTestimony(Testimony testimony);

    TestimonyView createTestimonyView(Testimony testimony);

    Testimony updateTestimony(TestimonyForm form) throws TestimonyNotFoundException, ParseException;

    TestimonyView updateTestimonyView(TestimonyForm form) throws TestimonyNotFoundException, ParseException;

    void deleteTestimony(Long id) throws TestimonyNotFoundException;

    List<TestimonyView> listTestimonyViewPageable(int page, int size);

    List<TestimonyView> listTestimonyByUserView(String appUserId, int page, int size) throws AppUserNotFoundException;
}
