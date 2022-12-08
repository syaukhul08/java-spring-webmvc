package com.khul.webmvc.entity;

import com.khul.webmvc.model.DosenModel;
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
@Table(name = "dosen_tab")
public class DosenEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "nip", length = 20, unique = true)
    private String nip;

    @Column(name = "nama_dosen", length = 225)
    private String name;

    @Column(name = "jk", length = 10)
    private String jk;

    @Column(name = "alamat", length = 200)
    private String alamat;

    @Column(name = "gelar", length = 32)
    private String gelar;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @OneToMany(mappedBy = "dosen", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<KelasEntity> kelas = new HashSet<>();

    public DosenEntity() {
    }

    public DosenEntity(String id) {
        this.id = id;
    }

    public DosenEntity(String nip, String name, String jk, String alamat, String gelar) {
        this.nip = nip;
        this.name = name;
        this.jk = jk;
        this.alamat = alamat;
        this.gelar = gelar;
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
    }

    public DosenEntity(DosenModel model) {
        BeanUtils.copyProperties(model,this);
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = "SYSTEM";
    }

    public void addKelas(KelasEntity kelas){
        this.kelas.add(kelas);
        kelas.setDosen(this);
    }

    public void removeKelas(KelasEntity kelas){
        this.kelas.remove(kelas);
        kelas.setDosen(null);
    }
}
