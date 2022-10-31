package com.khul.webmvc.repository;

import com.khul.webmvc.entity.FakultasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FakultasRepository extends JpaRepository<FakultasEntity, String> {
}
