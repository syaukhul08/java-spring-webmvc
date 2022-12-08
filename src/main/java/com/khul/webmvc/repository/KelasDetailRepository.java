package com.khul.webmvc.repository;

import com.khul.webmvc.entity.KelasDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KelasDetailRepository extends JpaRepository<KelasDetailEntity, String> {
}
