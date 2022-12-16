package com.khul.webmvc.service;

import com.khul.webmvc.model.GedungModel;
import com.khul.webmvc.model.MataKuliahModel;

import java.util.List;
import java.util.Optional;

public interface MataKuliahService {
    public List<MataKuliahModel> getAll();
    public MataKuliahModel getById(String id);
    public Boolean validCode(MataKuliahModel model);
    public Boolean validName(MataKuliahModel model);
    public Optional<MataKuliahModel> save(MataKuliahModel request);
    public Optional<MataKuliahModel> update(String id, MataKuliahModel request);
    public Optional<MataKuliahModel> delete(String id);
}
