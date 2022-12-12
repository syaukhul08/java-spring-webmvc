package com.khul.webmvc.service;

import com.khul.webmvc.entity.LookupEntity;

import java.util.List;
import java.util.Optional;

public interface LookupService {
    List<LookupEntity> getByGroup(String group);
    Optional<LookupEntity> getByCode(String code);
    Optional<LookupEntity> getById(String id);
    Optional<LookupEntity> save(LookupEntity entity);
    List<LookupEntity> saveAll(List<LookupEntity> entities);
}
