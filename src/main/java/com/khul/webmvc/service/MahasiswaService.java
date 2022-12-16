package com.khul.webmvc.service;


import com.khul.webmvc.model.MahasiswaModel;

import java.util.List;
import java.util.Optional;

public interface MahasiswaService {
    public List<MahasiswaModel> getAll();
    public MahasiswaModel getById(String id);
    public Boolean validNim(MahasiswaModel data);
    public Boolean validName(MahasiswaModel data);
    public Optional<MahasiswaModel> save(MahasiswaModel data);
    public Optional<MahasiswaModel> update(String id, MahasiswaModel data);
    public Optional<MahasiswaModel> delete(String id);
}
