package com.khul.webmvc.config;

import com.khul.webmvc.entity.FakultasEntity;
import com.khul.webmvc.entity.JurusanEntity;
import com.khul.webmvc.repository.FakultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DbInit implements CommandLineRunner {
    private FakultasRepository repository;

    @Autowired
    public DbInit(FakultasRepository repository) {
        this.repository = repository;
    }

    private void initFakultas(){
        if (repository.count() == 0){
            FakultasEntity fakultas = new FakultasEntity("FMIPA", "Fakultas Matematika dan IPA", "Yogyakarta");
            fakultas.addJurusan(new JurusanEntity("MTK", "Matematika"));
            fakultas.addJurusan(new JurusanEntity("FIS", "Fisika"));
            fakultas.addJurusan(new JurusanEntity("BIO", "Biologi"));
            fakultas.addJurusan(new JurusanEntity("KIM", "Kimia"));

            this.repository.save(fakultas);

            FakultasEntity fakultas2 = new FakultasEntity("FIK", "Fakultas Kedokteran", "Yogyakarta");
            fakultas2.addJurusan(new JurusanEntity("PDK", "Pendidikan Kedokteran"));
            fakultas2.addJurusan(new JurusanEntity("KG", "Kedokteran Gigi"));
            fakultas2.addJurusan(new JurusanEntity("FRM", "Farmasi"));

            this.repository.save(fakultas2);

        }
    }

    @Override
    public void run(String... args) throws Exception {
        initFakultas();
    }
}
