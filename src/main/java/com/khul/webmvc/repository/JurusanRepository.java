package com.khul.webmvc.repository;

import com.khul.webmvc.entity.JurusanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JurusanRepository extends JpaRepository<JurusanEntity, String> {
}
