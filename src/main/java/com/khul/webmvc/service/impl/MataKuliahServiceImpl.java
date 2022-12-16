package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.MataKuliahEntity;
import com.khul.webmvc.model.MataKuliahModel;
import com.khul.webmvc.repository.MataKuliahRepository;
import com.khul.webmvc.service.MataKuliahService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MataKuliahServiceImpl implements MataKuliahService {

    private MataKuliahRepository repository;

    @Autowired
    public MataKuliahServiceImpl(MataKuliahRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MataKuliahModel> getAll() {
        return this.repository.findAll().stream().map(MataKuliahModel::new).collect(Collectors.toList());
    }

    @Override
    public MataKuliahModel getById(String id) {
        if (id == null || id.isBlank() || id.isEmpty()){
            return new MataKuliahModel();
        }

        return this.repository.findById(id).map(MataKuliahModel::new).orElse(new MataKuliahModel());
    }

    @Override
    public Boolean validCode(MataKuliahModel model) {
        List<MataKuliahEntity> checkCode = this.repository.findByCode(model.getCode());
        return checkCode.isEmpty();
    }

    @Override
    public Boolean validName(MataKuliahModel model) {
        List<MataKuliahEntity> checkName = this.repository.findByName(model.getName());
        return checkName.isEmpty();
    }

    @Override
    public Optional<MataKuliahModel> save(MataKuliahModel request) {
        if (request == null){
            return Optional.empty();
        }

        List<MataKuliahEntity> checkCode = this.repository.findByCode(request.getCode());
        if (!checkCode.isEmpty()){
            return Optional.empty();
        }

        List<MataKuliahEntity> checkName = this.repository.findByName(request.getName());
        if (!checkName.isEmpty()){
            return Optional.empty();
        }

        MataKuliahEntity result = new MataKuliahEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new MataKuliahModel(result));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<MataKuliahModel> update(String id, MataKuliahModel request) {
        if (id == null || id.isBlank() || id.isEmpty()){
            return Optional.empty();
        }

        Optional<MataKuliahEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        MataKuliahEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        data.setId(id);
        data.setUpdatedAt(LocalDateTime.now());

        try {
            this.repository.save(data);
            return Optional.of(new MataKuliahModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<MataKuliahModel> delete(String id) {
        Optional<MataKuliahEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }

        try {
            MataKuliahEntity data = result.get();
            this.repository.delete(data);
            return Optional.of(new MataKuliahModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
