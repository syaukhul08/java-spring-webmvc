package com.khul.webmvc.entity;

import com.khul.webmvc.model.RuangModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ruang_tab")
public class RuangEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "kode_ruang", length = 20, unique = true)
    private String code;

    @Column(name = "nama_ruang", length = 225)
    private String name;

    @Column(name = "gedung_id", length = 36, insertable = false, updatable = false)
    private String gedungId;

    @Column(name = "lantai_ke")
    private Integer lantaiKe;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @OneToMany(mappedBy = "ruang", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<KelasEntity> kelas = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gedung_id", nullable = false)
    private GedungEntity gedung;

    public RuangEntity() {
    }

    public RuangEntity(String id) {
        this.id = id;
    }

    public RuangEntity(String code, String name, Integer lantaiKe) {
        this.code = code;
        this.name = name;
        this.lantaiKe = lantaiKe;
    }

    public RuangEntity(RuangModel data) {
        BeanUtils.copyProperties(data, this);
        this.id = UUID.randomUUID().toString();
        this.code = data.getCode();
        this.name = data.getName();
        this.lantaiKe = data.getLantaiKe();

        if (data.getGedung() != null) {
            GedungEntity gedungEntity = new GedungEntity();
            gedungEntity.setId(data.getGedung().getId());
            this.gedung = gedungEntity;
        }
        this.createdAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "SYSTEM";
    }

    public void addKelas(KelasEntity kelas){
        this.kelas.add(kelas);
        kelas.setRuang(this);
    }

    public void removeKelas(KelasEntity kelas){
        this.kelas.remove(kelas);
        kelas.setRuang(null);
    }

    @PrePersist
    public void onCreated(){
        this.id = UUID.randomUUID().toString();
    }
}


