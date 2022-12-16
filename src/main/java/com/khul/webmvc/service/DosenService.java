package com.khul.webmvc.service;

import com.khul.webmvc.model.DosenModel;

import java.util.List;
import java.util.Optional;

public interface DosenService {
    public List<DosenModel> getAll();
    public DosenModel getById(String id);
    public Boolean validNip(DosenModel model);
    public Boolean validName(DosenModel model);
    public Optional<DosenModel> save(DosenModel request);
    public Optional<DosenModel> update(String id, DosenModel request);
    public Optional<DosenModel> delete(String id);
}
