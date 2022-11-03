package com.khul.webmvc.model;

import com.khul.webmvc.entity.DosenEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class DosenModel {
    private String id;
    private String nip;
    private String name;
    private String jk;
    private String alamat;
    private String gelar;

    public DosenModel() {
    }

    public DosenModel(DosenEntity entity) {
        BeanUtils.copyProperties(entity,this);
    }
}
