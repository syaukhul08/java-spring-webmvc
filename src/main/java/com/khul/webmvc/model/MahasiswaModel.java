package com.khul.webmvc.model;

import com.khul.webmvc.entity.MahasiswaEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class MahasiswaModel {
    private String id;

    @NotBlank
    @NotEmpty
    private String nim;

    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    private String jk;

    @NotBlank
    @NotEmpty
    private String alamat;

    @NotBlank
    @NotEmpty
    private String tmpLahir;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tglLahir;

    @NotBlank
    @NotEmpty
    private String agama;

    @NotBlank
    @NotEmpty
    private String jurusanId;
    private String jurusanName;

    public MahasiswaModel() {
    }


    public MahasiswaModel(MahasiswaEntity entity) {
        BeanUtils.copyProperties(entity,this);
        if (entity.getJurusan() != null) {
            jurusanId = entity.getJurusanId();
            jurusanName = entity.getJurusan().getName();
        }
    }

    public MahasiswaModel(String id, String nim, String name, String jk, String alamat) {
        this.id = id;
        this.nim = nim;
        this.name = name;
        this.jk = jk;
        this.alamat = alamat;
    }
}
