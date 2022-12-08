package com.khul.webmvc.model;

import com.khul.webmvc.entity.KelasEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class KelasModel {
    private String id;
    private String code;
    private String namaHari;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date jamMulai;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date jamSelesai;
    private String ruangId;
    private RuangModel ruang;
    private String matkulId;
    private MataKuliahModel mataKuliah;
    private String dosenId;
    private DosenModel dosen;
    private String status;
    private Integer tahunAjaran;
    private String semester;
    private Integer quota;
    private Boolean bisaOnline;

    public KelasModel() {
    }

    public KelasModel(String namaHari, Date jamMulai, Date jamSelesai, String ruangId, String matkulId, String dosenId) {
        this.namaHari = namaHari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.ruangId = ruangId;
        this.matkulId = matkulId;
        this.dosenId = dosenId;
    }

    public KelasModel(KelasEntity entity){
        BeanUtils.copyProperties(entity, this);
        if (entity.getRuang() != null){
            ruangId = entity.getRuang().getId();
            ruang = new RuangModel(entity.getRuang());
        }
        if (entity.getMataKuliah() != null){
            matkulId = entity.getMataKuliah().getId();
            mataKuliah = new MataKuliahModel(entity.getMataKuliah());

        }
        if (entity.getDosen() != null){
            dosenId = entity.getDosen().getId();
            dosen = new DosenModel(entity.getDosen());
        }
    }

}
