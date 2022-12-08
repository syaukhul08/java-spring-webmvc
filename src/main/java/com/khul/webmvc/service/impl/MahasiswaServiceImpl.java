package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.JurusanEntity;
import com.khul.webmvc.entity.MahasiswaEntity;
import com.khul.webmvc.model.MahasiswaModel;
import com.khul.webmvc.repository.MahasiswaRepository;
import com.khul.webmvc.service.MahasiswaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MahasiswaServiceImpl implements MahasiswaService {
    private MahasiswaRepository repository;

    @Autowired
    public MahasiswaServiceImpl(MahasiswaRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<MahasiswaModel> get() {
        return this.repository.findAll().stream().map(MahasiswaModel::new).collect(Collectors.toList());
    }

    @Override
    public MahasiswaModel getById(String id) {
        return this.repository.findById(id).map(MahasiswaModel::new).orElse(new MahasiswaModel());
    }

    @Override
    public Optional<MahasiswaModel> save(MahasiswaModel request) {
        if (request == null){
            return Optional.empty();
        }
        MahasiswaEntity result = new MahasiswaEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new MahasiswaModel(result));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<MahasiswaModel> update(String id, MahasiswaModel request) {
        Optional<MahasiswaEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        MahasiswaEntity data = result.get();
        BeanUtils.copyProperties(request, data);
        JurusanEntity jurusan = new JurusanEntity(request.getJurusan().getId());
        data.setId(id);
        data.setJurusan(jurusan);
        data.setUpdatedAt(LocalDateTime.now());

        try {
            this.repository.save(data);
            return Optional.of(new MahasiswaModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<MahasiswaModel> delete(String id) {
        Optional<MahasiswaEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }

        try {
            MahasiswaEntity data = result.get();
            this.repository.delete(data);
            return Optional.of(new MahasiswaModel(data));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}