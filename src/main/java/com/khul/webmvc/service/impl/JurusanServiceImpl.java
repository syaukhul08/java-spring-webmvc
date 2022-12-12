package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.FakultasEntity;
import com.khul.webmvc.entity.JurusanEntity;
import com.khul.webmvc.model.JurusanModel;
import com.khul.webmvc.repository.JurusanRepository;
import com.khul.webmvc.service.JurusanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JurusanServiceImpl implements JurusanService {

    private JurusanRepository repository;

    @Autowired
    public JurusanServiceImpl(JurusanRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<JurusanModel> getAll() {
        List<JurusanEntity> result = this.repository.findAll();
        if(result.isEmpty()){
            Collections.emptyList();
        }
        // conver dari List<JurusanEntity> => List<JurusanModel>
        return result.stream().map(JurusanModel::new).collect(Collectors.toList());
    }

    @Override
    public JurusanModel getById(String id) {
        if(id == null || id.isBlank() || id.isEmpty()) {
            return new JurusanModel();
        }
        Optional<JurusanEntity> result = repository.findById(id);
        // convert dari JurusanEntity => JurusanModel
        return result.map(JurusanModel::new).orElseGet(JurusanModel::new);
    }

    @Override
    public Boolean validCode(JurusanModel data) {
        return null;
    }

    @Override
    public Boolean validName(JurusanModel data) {
        return null;
    }

    @Override
    public Optional<JurusanModel> save(JurusanModel data) {
        return Optional.empty();
    }

    @Override
    public Optional<JurusanModel> update(String id, JurusanModel data) {
        return Optional.empty();
    }

    @Override
    public Optional<JurusanModel> delete(String id) {
        return Optional.empty();
    }
}
