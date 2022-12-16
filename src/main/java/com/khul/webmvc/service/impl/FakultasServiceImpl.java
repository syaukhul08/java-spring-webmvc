package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.FakultasEntity;
import com.khul.webmvc.model.FakultasModel;
import com.khul.webmvc.repository.FakultasRepository;
import com.khul.webmvc.service.FakultasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FakultasServiceImpl implements FakultasService {

    private FakultasRepository repository;

    @Autowired
    public FakultasServiceImpl(FakultasRepository repository){
        this.repository = repository;
    }

    @Override
    public List<FakultasModel> getAll() {
        return this.repository.findAll().stream().map(FakultasModel::new).collect(Collectors.toList());
    }

    @Override
    public Boolean validCode(FakultasModel model) {
        List<FakultasEntity> checkCode = this.repository.findByCode(model.getCode());
        return checkCode.isEmpty();
    }

    @Override
    public Boolean validName(FakultasModel model) {
        List<FakultasEntity> checkName = this.repository.findByName(model.getName());
        return checkName.isEmpty();
    }

    @Override
    public FakultasModel getById(String id) {
        if (id == null || id.isEmpty() || id.isBlank()){
            return new FakultasModel();
        }

        return this.repository.findById(id).map(FakultasModel::new).orElse(new FakultasModel());
    }

    @Override
    public Optional<FakultasModel> save(FakultasModel request) {
        if(request == null) {
            return Optional.empty();
        }

        //check code
        List<FakultasEntity> checkCode = this.repository.findByCode(request.getCode());
        if (!checkCode.isEmpty()){
            return Optional.empty();
        }

        //check name
        List<FakultasEntity> checkName = this.repository.findByName(request.getName());
        if (!checkName.isEmpty()){
            return Optional.empty();
        }

        FakultasEntity result= new FakultasEntity(request);
        try{
            this.repository.save(result);
            return Optional.of(new FakultasModel(result));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<FakultasModel> update(String id, FakultasModel request) {
        if (id == null || id.isBlank() || id.isEmpty()){
            return Optional.empty();
        }

        Optional<FakultasEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }

        FakultasEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        request.setId(id);
        try {
            this.repository.save(data);
            return  Optional.of(new FakultasModel(data));
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<FakultasModel> delete(String id) {
        FakultasEntity result = this.repository.findById(id).orElse(null);
        if (result == null){
            return Optional.empty();
        }

        if (!result.getJurusans().isEmpty()){
            result.getJurusans().clear();
        }

        try {
            this.repository.delete(result);
        } catch (Exception e){
            log.info("Delete is failed, error {}", e.getMessage());
        }
        return Optional.of(new FakultasModel(result));
    }
}
