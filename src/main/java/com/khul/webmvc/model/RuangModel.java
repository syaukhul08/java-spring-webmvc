package com.khul.webmvc.model;

import com.khul.webmvc.entity.RuangEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class RuangModel {
    private String id;
    private String code;
    private String name;
    private Integer lantaiKe;
    private String gedungId;
    private GedungModel gedung;

    public RuangModel() {
    }

    public RuangModel(RuangEntity entity){
        BeanUtils.copyProperties(entity, this);
        if (entity.getGedung() != null){
            gedungId = entity.getGedung().getId();
            gedung = new GedungModel(entity.getGedung());
        }
    }
}
