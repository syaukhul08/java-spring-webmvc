package com.khul.webmvc.model;

import com.khul.webmvc.entity.RuangEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RuangModel {
    private String id;

    @NotBlank
    @NotEmpty
    private String code;

    @NotBlank
    @NotEmpty
    private String name;

    @Min(value = 1, message = "The value must be not zero")
    @NotNull
    private Integer lantaiKe;

    @NotBlank
    @NotEmpty
    private String gedungId;
    private GedungModel gedung;

    private String gedungName;

    public RuangModel() {
    }

    public RuangModel(RuangEntity entity){
        BeanUtils.copyProperties(entity, this);
        if (entity.getGedung() != null){
            gedungId = entity.getGedung().getId();
            gedungName = entity.getGedung().getName();
        }
    }
}