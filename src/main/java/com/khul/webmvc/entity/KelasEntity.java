package com.khul.webmvc.entity;

import com.khul.webmvc.model.KelasModel;
import com.khul.webmvc.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "kelas_tab")
@AllArgsConstructor
public class KelasEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "kode", length = 20, unique = true)
    private String code;

    @Column(name = "hari", length = 36)
    private String namaHari;

    @Temporal(TemporalType.TIME)
    @Column(name = "jam_mulai")
    private Date jamMulai;

    @Temporal(TemporalType.TIME)
    @Column(name = "jam_selesai")
    private Date jamSelesai;

    @Column(name = "ruang_id", length = 36, insertable = false, updatable = false)
    private String ruangId;

    @Column(name = "matkul_id", length = 36, insertable = false, updatable = false)
    private String matkulId;

    @Column(name = "dosen_id", length = 36, insertable = false, updatable = false)
    private String dosenId;

    @Column(name = "status", length = 225)
    private String status;

    @Column(name = "tahun_ajaran", length = 36)
    private Integer tahunAjaran;

    @Column(name = "semester", length = 20)
    private String semester;

    @Column(name = "quota", length = 36)
    private Integer quota;

    @Column(name = "bisa_online")
    private String bisaOnline;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ruang_id", nullable = false)
    private RuangEntity ruang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matkul_id", nullable = false)
    private MataKuliahEntity mataKuliah;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dosen_id", nullable = false)
    private DosenEntity dosen;

    public KelasEntity() {
    }

    public KelasEntity(String id) {
        this.id = id;
    }

    public KelasEntity(String code, String namaHari, String jamMulai, String jamSelesai, String ruangId, String matkulId, String dosenId) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.namaHari = namaHari;
        this.jamMulai = DateUtil.getTime(jamMulai);
        this.jamSelesai = DateUtil.getTime(jamSelesai);
        this.ruangId = ruangId;
        this.matkulId = matkulId;
        this.dosenId = dosenId;
    }

    public KelasEntity(KelasModel model){
        this.id = UUID.randomUUID().toString();
        BeanUtils.copyProperties(model, this);

        if (model.getRuangId() != null) {
            RuangEntity ruangEntity = new RuangEntity();
            ruangEntity.setId(model.getRuangId());
            this.ruang = ruangEntity;
        }

        if (model.getMatkulId() != null) {
            MataKuliahEntity mataKuliahEntity = new MataKuliahEntity();
            mataKuliahEntity.setId(model.getMatkulId());
            this.mataKuliah = mataKuliahEntity;
        }

        if (model.getDosenId() != null) {
            DosenEntity dosenEntity = new DosenEntity();
            dosenEntity.setId(model.getDosenId());
            this.dosen = dosenEntity;
        }

        this.createdAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "SYSTEM";
        this.id = UUID.randomUUID().toString();
    }

    @PrePersist
    public void onCreated(){
        this.id = UUID.randomUUID().toString();
    }
}
