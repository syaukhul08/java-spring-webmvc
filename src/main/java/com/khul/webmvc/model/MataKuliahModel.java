package com.khul.webmvc.model;

import com.khul.webmvc.entity.MataKuliahEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class MataKuliahModel {
    private String id;
    private String code;
    private String name;
    private Integer sks;

    public MataKuliahModel() {
    }

    public MataKuliahModel(MataKuliahEntity entity) {
        BeanUtils.copyProperties(entity,this);
    }


}
