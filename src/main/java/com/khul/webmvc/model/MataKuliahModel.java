package com.khul.webmvc.model;

import com.khul.webmvc.entity.MataKuliahEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MataKuliahModel {
    private String id;

    @NotBlank
    @NotEmpty
    private String code;

    @NotBlank
    @NotEmpty
    private String name;

    @Min(value = 1, message = "The value must be not zero")
    @NotNull
    private Integer sks;

    public MataKuliahModel() {
    }

    public MataKuliahModel(MataKuliahEntity entity) {
        BeanUtils.copyProperties(entity,this);
    }


}
