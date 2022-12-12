package com.khul.webmvc.repository;

import com.khul.webmvc.entity.FakultasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FakultasRepository extends JpaRepository<FakultasEntity, String> {
    List<FakultasEntity> findByCode(String code);
    List<FakultasEntity> findByName(String name);
    List<FakultasEntity> findByCodeAndName(String code, String name);

}
