package com.khul.webmvc.repository;

import com.khul.webmvc.entity.KelasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KelasRepository extends JpaRepository<KelasEntity, String> {
    @Query("select t from KelasEntity t where t.ruangId= :ruangId and t.dosenId= :dosenId and t.namaHari= :namaHari " +
            "and t.jamMulai >= :jamMulai or t.jamSelesai <= :jamSelesai")
    List<KelasEntity> checkCase01(@Param("ruangId") String ruangId,
                                  @Param("dosenId") String dosenId,
                                  @Param("namaHari") String namaHari,
                                  @Param("jamMulai") Date jamMulai,
                                  @Param("jamSelesai") Date jamSelesai);

}
