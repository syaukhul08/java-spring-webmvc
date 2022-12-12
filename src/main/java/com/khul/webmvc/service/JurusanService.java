package com.khul.webmvc.service;


import com.khul.webmvc.model.JurusanModel;

import java.util.List;
import java.util.Optional;

public interface JurusanService {
    public List<JurusanModel> getAll();
    public JurusanModel getById(String id);
    public Boolean validCode(JurusanModel data);
    public Boolean validName(JurusanModel data);
    public Optional<JurusanModel> save(JurusanModel request);
    public Optional<JurusanModel> update(String id, JurusanModel request);
    public Optional<JurusanModel> delete(String id);

}
