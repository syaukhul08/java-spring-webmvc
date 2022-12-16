package com.khul.webmvc.repository;

import com.khul.webmvc.entity.DosenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DosenRepository extends JpaRepository<DosenEntity, String> {
    List<DosenEntity> findByNip(String nip);
    List<DosenEntity> findByName(String name);
}
