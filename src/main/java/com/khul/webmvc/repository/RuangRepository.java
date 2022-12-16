package com.khul.webmvc.repository;


import com.khul.webmvc.entity.RuangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuangRepository extends JpaRepository<RuangEntity, String> {

    List<RuangEntity> findByCode(String code);
    List<RuangEntity> findByName(String name);
}
