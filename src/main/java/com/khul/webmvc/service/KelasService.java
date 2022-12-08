package com.khul.webmvc.service;

import com.khul.webmvc.entity.KelasEntity;
import com.khul.webmvc.model.KelasModel;

import java.util.List;
import java.util.Optional;

public interface KelasService {
    public List<KelasModel> getAll();
    public KelasModel getById(String id);
    public Optional<KelasModel> save(KelasModel request);
    public Optional<KelasModel> update(String id, KelasModel request);
    public Optional<KelasModel> delete(String id);
}
