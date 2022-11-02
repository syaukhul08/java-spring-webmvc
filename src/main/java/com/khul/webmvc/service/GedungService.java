package com.khul.webmvc.service;

import com.khul.webmvc.model.GedungModel;

import java.util.List;
import java.util.Optional;

public interface GedungService {
    public List<GedungModel> get();
    public GedungModel getById(String id);
    public Optional<GedungModel> save(GedungModel request);
    public Optional<GedungModel> update(String id, GedungModel request);
    public Optional<GedungModel> delete(String id);

}
