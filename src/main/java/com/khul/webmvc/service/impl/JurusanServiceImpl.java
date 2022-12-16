package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.FakultasEntity;
import com.khul.webmvc.entity.JurusanEntity;
import com.khul.webmvc.model.JurusanModel;
import com.khul.webmvc.repository.JurusanRepository;
import com.khul.webmvc.service.JurusanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        List<JurusanEntity> checkCode = this.repository.findByCode(data.getCode());
        return checkCode.isEmpty();
    }

    @Override
    public Boolean validName(JurusanModel data) {
        List<JurusanEntity> checkName = this.repository.findByName(data.getName());
        return checkName.isEmpty();
    }

    @Override
    public Optional<JurusanModel> save(JurusanModel request) {

        if (request == null){
            return Optional.empty();
        }

        List<JurusanEntity> checkCode = this.repository.findByCode(request.getCode());
        if (!checkCode.isEmpty()){
            return Optional.empty();
        }

        List<JurusanEntity> checkName = this.repository.findByName(request.getName());
        if (!checkName.isEmpty()){
            return Optional.empty();
        }

        JurusanEntity result = new JurusanEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new JurusanModel(result));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<JurusanModel> update(String id, JurusanModel request) {
        Optional<JurusanEntity> result = this.repository.findById(id);

        if (result.isEmpty()){
            return Optional.empty();
        }

        JurusanEntity data = result.get();
        data.setCode(request.getCode());
        data.setName(request.getName());
        FakultasEntity fakultas = new FakultasEntity(request.getFakultasId());
        data.setFakultas(fakultas);
        data.setUpdatedAt(LocalDateTime.now());

        try {
            this.repository.save(data);
            return Optional.of(new JurusanModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<JurusanModel> delete(String id) {
        Optional<JurusanEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        try {
            JurusanEntity data = result.get();
            FakultasEntity fakultas = data.getFakultas();
            fakultas.removeJurusan(data);
            data.setFakultas(null);
            this.repository.delete(data);
            return Optional.of(new JurusanModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
