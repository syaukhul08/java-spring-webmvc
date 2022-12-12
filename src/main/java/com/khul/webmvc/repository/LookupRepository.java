package com.khul.webmvc.repository;

import com.khul.webmvc.entity.LookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LookupRepository extends JpaRepository<LookupEntity, String> {
    List<LookupEntity> findByGroups(String groups);
    Optional<LookupEntity> findByCode(String code);
}
