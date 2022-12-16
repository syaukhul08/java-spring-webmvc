package com.khul.webmvc.model;

import com.khul.webmvc.entity.KelasEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class KelasModel {
    private String id;

    @NotBlank
    @NotEmpty
    private String code;

    @NotBlank
    @NotEmpty
    private String namaHari;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date jamMulai;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date jamSelesai;

    @NotBlank
    @NotEmpty
    private String ruangId;

    @NotBlank
    @NotEmpty
    private String matkulId;

    @NotBlank
    @NotEmpty
    private String dosenId;

    @NotBlank
    @NotEmpty
    private String status;

    @Min(value = 1, message = "The value must be not zero")
    @NotNull
    private Integer tahunAjaran;

    @NotBlank
    @NotEmpty
    private String semester;

    @Min(value = 1, message = "The value must be not zero")
    @NotNull
    private Integer quota;

    @NotBlank
    @NotEmpty
    private String bisaOnline;

    private String ruangName;
    private String matkulName;
    private String dosenName;

    public KelasModel() {
    }


    public KelasModel(KelasEntity entity){
        BeanUtils.copyProperties(entity, this);
        if (entity.getRuang() != null){
            ruangId = entity.getRuang().getId();
            ruangName = entity.getRuang().getName();
        }

        if (entity.getMataKuliah() != null){
            matkulId = entity.getMataKuliah().getId();
            matkulName = entity.getMataKuliah().getName();
        }

        if (entity.getDosen() != null){
            dosenId = entity.getDosen().getId();
            dosenName = entity.getDosen().getName();
        }
    }

}
