package com.khul.webmvc.model;

import com.khul.webmvc.entity.MahasiswaEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MahasiswaModel {
    private String id;
    private String name;
    private String jk;
    private String alamat;
    private String tmpLahir;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tglLahir;
    private String agama;
    private String jurusanId;
    private JurusanModel jurusan;

    public MahasiswaModel() {
    }

    public MahasiswaModel(MahasiswaEntity entity) {
        BeanUtils.copyProperties(entity,this);
        if (entity.getJurusan() != null) {
            jurusanId = entity.getJurusan().getId();
            jurusan = new JurusanModel(entity.getJurusan());
        }
    }
}
