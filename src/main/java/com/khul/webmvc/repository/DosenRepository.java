package com.khul.webmvc.repository;

import com.khul.webmvc.entity.DosenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DosenRepository extends JpaRepository<DosenEntity, String> {
}
