package com.khul.webmvc.entity;

import com.khul.webmvc.model.KelasDetailModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "kelas_detail_tab")
public class KelasDetailEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "kelas_id", length = 36, insertable = false, updatable = false)
    private String kelasId;

    @Column(name = "mahasiswa_id", length = 36, insertable = false, updatable = false)
    private String mahasiswaId;

    @Column(name = "status", length = 225)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kelas_id", nullable = false)
    private KelasEntity kelas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private MahasiswaEntity mahasiswa;

    public KelasDetailEntity() {
    }

    public KelasDetailEntity(KelasDetailModel model) {
        BeanUtils.copyProperties(model, this);
        this.id = UUID.randomUUID().toString();
        if (model.getKelas() != null){
            this.kelas = new KelasEntity(model.getKelas().getId());
        }

        if (model.getMahasiswa() != null){
            this.kelas = new KelasEntity(model.getMahasiswa().getId());
        }
    }
}
