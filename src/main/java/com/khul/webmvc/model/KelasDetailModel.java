package com.khul.webmvc.model;

import com.khul.webmvc.entity.KelasDetailEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class KelasDetailModel {
    private String id;
    private String kelasId;
    private KelasModel kelas;
    private String mahasiswaId;
    private MahasiswaModel mahasiswa;
    private String status;


    public KelasDetailModel() {
    }

    public KelasDetailModel(KelasDetailEntity entity){
        if (entity.getKelas() != null){
            kelasId = entity.getKelasId();
            kelas = new KelasModel(entity.getKelas());
        }
        if (entity.getMahasiswa() != null){
            mahasiswaId = entity.getMahasiswaId();
            mahasiswa = new MahasiswaModel(entity.getMahasiswa());
        }
    }
}
