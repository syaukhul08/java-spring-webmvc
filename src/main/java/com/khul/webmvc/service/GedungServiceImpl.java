package com.khul.webmvc.service;

import com.khul.webmvc.entity.GedungEntity;
import com.khul.webmvc.model.GedungModel;
import com.khul.webmvc.repository.GedungRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GedungServiceImpl implements GedungService{

    private GedungRepository repository;

    @Autowired
    public GedungServiceImpl(GedungRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GedungModel> get() {
        return this.repository.findAll().stream().map(GedungModel::new).collect(Collectors.toList());
    }

    @Override
    public GedungModel getById(String id) {
        return this.repository.findById(id).map(GedungModel::new).orElse(new GedungModel());
    }

    @Override
    public Optional<GedungModel> save(GedungModel request) {
        if (request == null){
            return Optional.empty();
        }

        GedungEntity result = new GedungEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new GedungModel(result));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<GedungModel> update(String id, GedungModel request) {
        Optional<GedungEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }

        GedungEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        data.setId(id);
        data.setUpdatedAt(LocalDateTime.now());
        try {
            this.repository.save(data);
            return Optional.of(new GedungModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<GedungModel> delete(String id) {
        GedungEntity result = this.repository.findById(id).orElse(null);
        if (result == null){
            return Optional.empty();
        }

        if (!result.getRuangs().isEmpty()){
            result.getRuangs().clear();
        }
        try {
            this.repository.delete(result);
            return Optional.of(new GedungModel(result));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
