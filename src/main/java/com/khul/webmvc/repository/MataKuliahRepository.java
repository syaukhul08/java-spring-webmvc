package com.khul.webmvc.repository;

import com.khul.webmvc.entity.MataKuliahEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MataKuliahRepository extends JpaRepository<MataKuliahEntity, String> {
    List<MataKuliahEntity> findByCode(String code);
    List<MataKuliahEntity> findByName(String name);
}
