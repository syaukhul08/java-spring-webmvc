package com.khul.webmvc.service;


import com.khul.webmvc.model.FakultasModel;

import java.util.List;
import java.util.Optional;

public interface FakultasService {
    public List<FakultasModel> getAll();
    public Boolean validCode(FakultasModel model);
    public Boolean validName(FakultasModel model);
    public FakultasModel getById(String id);
    public Optional<FakultasModel> save(FakultasModel request);
    public Optional<FakultasModel> update(String id, FakultasModel request);
    public Optional<FakultasModel> delete(String id);
}
