package com.khul.webmvc.service;

import com.khul.webmvc.entity.GedungEntity;
import com.khul.webmvc.entity.RuangEntity;
import com.khul.webmvc.model.RuangModel;
import com.khul.webmvc.repository.RuangRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RuangServiceImpl implements RuangService{

    private RuangRepository repository;

    @Autowired
    public RuangServiceImpl(RuangRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RuangModel> get() {
        return this.repository.findAll().stream().map(RuangModel::new).collect(Collectors.toList());
    }

    @Override
    public RuangModel getById(String id) {
        return this.repository.findById(id).map(RuangModel::new).orElse(new RuangModel());
    }

    @Override
    public Optional<RuangModel> save(RuangModel request) {
        if (request == null){
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
        GedungEntity gedung = new GedungEntity(request.getGedung().getId());
        data.setId(id);
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
