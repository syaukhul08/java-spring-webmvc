package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.GedungEntity;
import com.khul.webmvc.entity.RuangEntity;
import com.khul.webmvc.model.RuangModel;
import com.khul.webmvc.repository.RuangRepository;
import com.khul.webmvc.service.RuangService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RuangServiceImpl implements RuangService {

    private RuangRepository repository;

    @Autowired
    public RuangServiceImpl(RuangRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RuangModel> getAll() {
       List<RuangEntity> result = this.repository.findAll();
       if (result.isEmpty()){
           Collections.emptyList();
       }
        return result.stream().map(RuangModel::new).collect(Collectors.toList());
    }

    @Override
    public RuangModel getById(String id) {
        if (id == null || id.isBlank() || id.isEmpty()){
            return new RuangModel();
        }
        Optional<RuangEntity> result = repository.findById(id);

        return result.map(RuangModel::new).orElse(new RuangModel());
    }

    @Override
    public Boolean validCode(RuangModel data) {
        List<RuangEntity> checkCode = this.repository.findByCode(data.getCode());
        return checkCode.isEmpty();
    }

    @Override
    public Boolean validName(RuangModel data) {
        List<RuangEntity> checkName = this.repository.findByName(data.getName());
        return checkName.isEmpty();
    }

    @Override
    public Optional<RuangModel> save(RuangModel request) {
        if (request == null){
            return Optional.empty();
        }

        List<RuangEntity> checkCode = this.repository.findByCode(request.getCode());
        if (!checkCode.isEmpty()){
            return Optional.empty();
        }

        List<RuangEntity> checkName = this.repository.findByName(request.getName());
        if (!checkName.isEmpty()){
            return Optional.empty();
        }

        RuangEntity result = new RuangEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new RuangModel(result));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<RuangModel> update(String id, RuangModel request) {
        Optional<RuangEntity> result = this.repository.findById(id);

        if (result.isEmpty()){
            return Optional.empty();
        }

        RuangEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        data.setCode(request.getCode());
        data.setName(request.getName());
        GedungEntity gedung = new GedungEntity(request.getGedungId());
        //data.setId(id);
        data.setGedung(gedung);
        data.setUpdatedAt(LocalDateTime.now());

        try {
            this.repository.save(data);
            return Optional.of(new RuangModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<RuangModel> delete(String id) {
        Optional<RuangEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        try {
            RuangEntity data = result.get();
            GedungEntity gedung = data.getGedung();
            gedung.removeRuang(data);
            data.setGedung(null);
            this.repository.delete(data);
            return Optional.of(new RuangModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
