package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.FakultasEntity;
import com.khul.webmvc.model.FakultasModel;
import com.khul.webmvc.repository.FakultasRepository;
import com.khul.webmvc.service.FakultasService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FakultasServiceImpl implements FakultasService {

    private FakultasRepository repository;

    @Autowired
    public FakultasServiceImpl(FakultasRepository repository){
        this.repository = repository;
    }

    @Override
    public List<FakultasModel> get(){
        return this.repository.findAll().stream().map(FakultasModel::new).collect(Collectors.toList());
    }

    @Override
    public FakultasModel getById(String id){
       return this.repository.findById(id).map(FakultasModel::new).orElse(new FakultasModel());
    }

    @Override
    public Optional<FakultasModel> save(FakultasModel request){
        if (request == null){
            return Optional.empty();
        }
        FakultasEntity result = new FakultasEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new FakultasModel(result));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<FakultasModel> update(String id, FakultasModel request){

        Optional<FakultasEntity> result = this.repository.findById(id);

        if(result.isEmpty()) {
            return Optional.empty();
        }

        FakultasEntity data = result.get();
        BeanUtils.copyProperties(request,data);
        data.setId(id);
            try {
                this.repository.save(data);
                return Optional.of(new FakultasModel(data));
            }catch (Exception e){
                return Optional.empty();
            }
    }

    @Override
    public Optional<FakultasModel> delete(String id){
        FakultasEntity fakultas = this.repository.findById(id).orElse(null);
        if (fakultas == null){
            return Optional.empty();
        }
        if (!fakultas.getJurusans().isEmpty()){
            fakultas.getJurusans().clear();
        }

        try {
            this.repository.delete(fakultas);
            return Optional.of(new FakultasModel(fakultas));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
