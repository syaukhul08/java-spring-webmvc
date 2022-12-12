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
import java.util.Collections;
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
    public List<MahasiswaModel> getAll() {
        List<MahasiswaEntity> result = this.repository.findAll();
        if (result.isEmpty()) {
            Collections.emptyList();
        }
        // conver dari List<SiswaEntity> => List<SiswaModel>
        return result.stream().map(MahasiswaModel::new).collect(Collectors.toList());
    }

    @Override
    public MahasiswaModel getById(String id) {
        if (id == null || id.isBlank() || id.isEmpty()) {
            return new MahasiswaModel();
        }
        Optional<MahasiswaEntity> result = repository.findById(id);
        // convert dari SiswaEntity => SiswaModel
        return result.map(MahasiswaModel::new).orElseGet(MahasiswaModel::new);
    }

    @Override
    public Boolean validNim(MahasiswaModel data) {
        List<MahasiswaEntity> checkNim = this.repository.findByNim(data.getNim());
        return checkNim.isEmpty();
    }

    @Override
    public Boolean validName(MahasiswaModel data) {
        List<MahasiswaEntity> checkName = this.repository.findByName(data.getName());
        return checkName.isEmpty();
    }

    @Override
    public Optional<MahasiswaModel> save(MahasiswaModel request) {
        if (request == null) {
            return Optional.empty();
        }
        MahasiswaEntity result = new MahasiswaEntity(request);
        try {
            // proses simpan data
            this.repository.save(result);
            return Optional.of(new MahasiswaModel(result));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MahasiswaModel> update(String id, MahasiswaModel request) {
        Optional<MahasiswaEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        // check data dari result
        MahasiswaEntity data = result.get();
        // replace data lama dengan dataBaru
        data.setName(request.getName());
        data.setJk(request.getJk());
        data.setAlamat(request.getAlamat());
        data.setTmpLahir(request.getTmpLahir());
        data.setTglLahir(request.getTglLahir());
        data.setAgama(request.getAgama());
        JurusanEntity jurusan = new JurusanEntity(request.getJurusanId());
        data.setJurusan(jurusan);

        data.setUpdatedAt(LocalDateTime.now());
        data.setUpdatedBy("SYSTEM");

        try {
            this.repository.save(data);
            return Optional.of(new MahasiswaModel(data));
        } catch (Exception e) {
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