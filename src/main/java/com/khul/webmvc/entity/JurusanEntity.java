package com.khul.webmvc.entity;


import com.khul.webmvc.model.JurusanModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "jurusan_tab")
public class JurusanEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "kode_jurusan", length = 20, unique = true)
    private String code;

    @Column(name = "nama_jurusan", length = 225)
    private String name;

    @Column(name = "fakultas_id", length = 36, insertable = false, updatable = false)
    private String fakultasId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fakultas_id", nullable = false)
    private FakultasEntity fakultas;

    public JurusanEntity() {
    }

    public JurusanEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public JurusanEntity(String id) {
        this.id = id;
    }

    public JurusanEntity(JurusanModel data) {
        this.code = data.getCode();
        this.name = data.getName();

        if (data.getFakultas() != null) {
            FakultasEntity fakultasEntity = new FakultasEntity();
            fakultasEntity.setId(data.getFakultas().getId());
            this.fakultas = fakultasEntity;
        }

        this.createdAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "SYSTEM";
        this.id = UUID.randomUUID().toString();
    }


    public JurusanEntity(String code, String name, String fakultasId) {
        this.code = code;
        this.name = name;

        FakultasEntity fakultasEntity = new FakultasEntity(fakultasId);
        this.fakultas = fakultasEntity;

        this.createdAt=LocalDateTime.now();
        this.createdBy="SYSTEM";
    }

    @PrePersist
    public void onCreated(){
        this.id = UUID.randomUUID().toString();
    }
}
