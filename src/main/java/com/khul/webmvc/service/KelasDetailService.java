package com.khul.webmvc.service;

import com.khul.webmvc.model.KelasDetailModel;

import java.util.List;
import java.util.Optional;

public interface KelasDetailService {

    public List<KelasDetailModel> get();
    public KelasDetailModel getById(String id);
    public Optional<KelasDetailModel> save(KelasDetailModel request);
    public Optional<KelasDetailModel> update(String id, KelasDetailModel request);
    public Optional<KelasDetailModel> delete(String id);
}
