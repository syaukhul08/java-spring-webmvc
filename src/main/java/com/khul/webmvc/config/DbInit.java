package com.khul.webmvc.config;

import com.khul.webmvc.entity.FakultasEntity;
import com.khul.webmvc.entity.JurusanEntity;
import com.khul.webmvc.entity.RoleEntity;
import com.khul.webmvc.entity.UserEntity;
import com.khul.webmvc.repository.FakultasRepository;
import com.khul.webmvc.service.UserService;
import com.khul.webmvc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DbInit implements CommandLineRunner {
    private FakultasRepository repository;
    private PasswordEncoder encoder;

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public DbInit(FakultasRepository repository, PasswordEncoder encoder, UserService userService, RoleService roleService) {
        this.repository = repository;
        this.encoder = encoder;
        this.userService = userService;
        this.roleService = roleService;
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

    private void initUserAndRole(){
        if (roleService.getCount() == 0){
            this.roleService.save(Arrays.asList(
                    new RoleEntity("ROLE_ADMIN"),
                    new RoleEntity("ROLE_USER"),
                    new RoleEntity("ROLE_DOSEN"),
                    new RoleEntity("ROLE_MAHASISWA"),
                    new RoleEntity("ROLE_KEUANGAN")
            ));
        }

        if (userService.getCount() == 0){
            RoleEntity adminRole = roleService.getByName("ROLE_ADMIN");
            UserEntity admin = new UserEntity("admin", encoder.encode("admin32!"),"admin@gmail.com", Arrays.asList(adminRole));
            this.userService.save(admin);

            RoleEntity mhsRole = roleService.getByName("ROLE_MAHASISWA");
            UserEntity mhs = new UserEntity("mahasiswa", encoder.encode("mahasiswa32!"),"mahasiswa@gmail.com", Arrays.asList(mhsRole));
            this.userService.save(mhs);

            RoleEntity dosenRole = roleService.getByName("ROLE_DOSEN");
            UserEntity dosen = new UserEntity("dosen", encoder.encode("dosen32!"),"dosen@gmail.com", Arrays.asList(dosenRole));
            this.userService.save(dosen);
        }
    }
    @Override
    public void run(String... args) throws Exception {
        initFakultas();
        initUserAndRole();
    }
}
