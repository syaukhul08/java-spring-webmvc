package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.DosenEntity;
import com.khul.webmvc.entity.FakultasEntity;
import com.khul.webmvc.model.DosenModel;
import com.khul.webmvc.repository.DosenRepository;
import com.khul.webmvc.service.DosenService;
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
public class DosenServiceImpl implements DosenService {

    private DosenRepository repository;

    @Autowired
    public DosenServiceImpl(DosenRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DosenModel> getAll() {
        return this.repository.findAll().stream().map(DosenModel::new).collect(Collectors.toList());
    }

    @Override
    public DosenModel getById(String id) {
        if (id == null || id.isBlank() || id.isEmpty()){
            return new DosenModel();
        }
        return this.repository.findById(id).map(DosenModel::new).orElse(new DosenModel());
    }

    @Override
    public Boolean validNip(DosenModel model) {
        List<DosenEntity> checkNip = this.repository.findByNip(model.getNip());
        return checkNip.isEmpty();
    }

    @Override
    public Boolean validName(DosenModel model) {
        List<DosenEntity> checkName = this.repository.findByName(model.getName());
        return checkName.isEmpty();
    }

    @Override
    public Optional<DosenModel> save(DosenModel request) {
        if (request == null){
            return Optional.empty();
        }

        List<DosenEntity> checkNip = this.repository.findByNip(request.getNip());
        if (!checkNip.isEmpty()){
            return Optional.empty();
        }

        List<DosenEntity> checkName = this.repository.findByName(request.getName());
        if (!checkName.isEmpty()){
            return Optional.empty();
        }

        DosenEntity result = new DosenEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new DosenModel(result));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<DosenModel> update(String id, DosenModel request) {
        if (id == null || id.isBlank() || id.isEmpty()){
            return Optional.empty();
        }

        Optional<DosenEntity> result = repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        DosenEntity data = result.get();
        BeanUtils.copyProperties(request,data);
        data.setId(id);
        data.setUpdatedAt(LocalDateTime.now());
        try {
            this.repository.save(data);
            return Optional.of(new DosenModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<DosenModel> delete(String id) {
        DosenEntity result = this.repository.findById(id).orElse(null);
        if (result == null){
            return Optional.empty();
        }

//        if (result.isEmpty()){
//            return Optional.empty();
//        }

        try {
            this.repository.delete(result);
            return Optional.of(new DosenModel(result));
        }catch (Exception e){
            log.info("Delete is failed, error {}", e.getMessage());
        }
        return Optional.of(new DosenModel(result));
    }
}
