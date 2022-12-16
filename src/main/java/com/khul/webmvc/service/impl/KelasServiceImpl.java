package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.DosenEntity;
import com.khul.webmvc.entity.KelasEntity;
import com.khul.webmvc.entity.MataKuliahEntity;
import com.khul.webmvc.entity.RuangEntity;
import com.khul.webmvc.model.KelasModel;
import com.khul.webmvc.repository.KelasRepository;
import com.khul.webmvc.service.KelasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KelasServiceImpl implements KelasService {

    private KelasRepository repository;

    @Autowired
    public KelasServiceImpl(KelasRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<KelasModel> getAll() {
        List<KelasEntity> result = this.repository.findAll();
        if (result.isEmpty()){
            Collections.emptyList();
        }
        return result.stream().map(KelasModel::new).collect(Collectors.toList());
    }

    @Override
    public KelasModel getById(String id) {
        if (id == null || id.isBlank() || id.isEmpty()){
            return new KelasModel();
        }
        Optional<KelasEntity> result = repository.findById(id);
        return result.map(KelasModel::new).orElseGet(KelasModel::new);
    }

    @Override
    public Boolean validCode(KelasModel model) {
        List<KelasEntity> checkCode = this.repository.findByCode(model.getCode());
        return checkCode.isEmpty();
    }

    @Override
    public Optional<KelasModel> save(KelasModel request) {
        if (request == null){
            return Optional.empty();
        }

        //check 01
        List<KelasEntity> check01 = this.repository.checkCase01(
                request.getRuangId(),
                request.getDosenId(),
                request.getNamaHari(),
                request.getJamMulai(),
                request.getJamSelesai()
        );

        if(check01 != null || !check01.isEmpty()){
            return Optional.empty();
        }

        //check 02
        List<KelasEntity> check02 = this.repository.checkCase02(
                request.getRuangId(),
                request.getNamaHari(),
                request.getJamMulai(),
                request.getJamSelesai()
        );

        if(check02 != null || !check02.isEmpty()){
            return Optional.empty();
        }

        //check 03
        List<KelasEntity> check03 = this.repository.checkCase03(
                request.getDosenId(),
                request.getNamaHari(),
                request.getJamMulai(),
                request.getJamSelesai()
        );

        if(check03 != null || !check03.isEmpty()){
            return Optional.empty();
        }

        KelasEntity result = new KelasEntity(request);
        try {
            this.repository.save(result);
            return Optional.of(new KelasModel(result));
        }catch (Exception e){
            log.error("save kelas entity failed, error : {}", e.getMessage());
            return Optional.empty();
        }


    }

    @Override
    public Optional<KelasModel> update(String id, KelasModel request) {
        Optional<KelasEntity> result = this.repository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }

        KelasEntity data = result.get();
        BeanUtils.copyProperties(request,data);
        RuangEntity ruang = new RuangEntity(request.getRuangId());
        MataKuliahEntity mataKuliah = new MataKuliahEntity(request.getMatkulId());
        DosenEntity dosen = new DosenEntity(request.getDosenId());
        data.setId(id);
        data.setRuang(ruang);
        data.setMataKuliah(mataKuliah);
        data.setDosen(dosen);
        data.setUpdatedAt(LocalDateTime.now());

        try {
            this.repository.save(data);
            return Optional.of(new KelasModel(data));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<KelasModel> delete(String id) {
        Optional<KelasEntity> result = this.repository.findById(id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        try {
            KelasEntity data = result.get();

            RuangEntity ruang = data.getRuang();
            ruang.removeRuang(data);
            data.setRuang(null);

            DosenEntity dosen = data.getDosen();
            dosen.removeDosen(data);
            data.setDosen(null);

            MataKuliahEntity mataKuliah = data.getMataKuliah();
            mataKuliah.removeMataKuliah(data);
            data.setMataKuliah(null);

            this.repository.delete(data);
            return Optional.of(new KelasModel(data));
        } catch (Exception e){
            return Optional.empty();
        }
    }
}
