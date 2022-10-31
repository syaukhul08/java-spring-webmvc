package com.khul.webmvc.repository;

import com.khul.webmvc.entity.MahasiswaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MahasiswaRepository extends JpaRepository<MahasiswaEntity, String> {

}
