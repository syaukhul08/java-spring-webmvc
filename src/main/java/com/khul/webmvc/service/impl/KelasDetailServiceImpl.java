package com.khul.webmvc.service.impl;

import com.khul.webmvc.model.KelasDetailModel;
import com.khul.webmvc.service.KelasDetailService;

import java.util.List;
import java.util.Optional;

public class KelasDetailServiceImpl implements KelasDetailService {

    @Override
    public List<KelasDetailModel> get() {
        return null;
    }

    @Override
    public KelasDetailModel getById(String id) {
        return null;
    }

    @Override
    public Optional<KelasDetailModel> save(KelasDetailModel request) {
        return Optional.empty();
    }

    @Override
    public Optional<KelasDetailModel> update(String id, KelasDetailModel request) {
        return Optional.empty();
    }

    @Override
    public Optional<KelasDetailModel> delete(String id) {
        return Optional.empty();
    }
}
