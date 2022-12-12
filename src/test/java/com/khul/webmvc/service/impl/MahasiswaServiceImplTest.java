package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.JurusanEntity;
import com.khul.webmvc.entity.MahasiswaEntity;
import com.khul.webmvc.entity.MahasiswaEntity;
import com.khul.webmvc.model.JurusanModel;
import com.khul.webmvc.model.MahasiswaModel;
import com.khul.webmvc.repository.MahasiswaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MahasiswaServiceImplTest {
/**
    @Autowired
    @InjectMocks
    private MahasiswaServiceImpl service;

    @Mock
    private MahasiswaRepository repository;

    private static List<MahasiswaEntity> mahasiswaEntityList;

    @BeforeEach
    void setUp() {
        mahasiswaEntityList = Arrays.asList(
                new MahasiswaEntity("Budi","Pria","Bantul","", LocalDate.of(2000, 2, 4),"Islam", new JurusanEntity("TM","Teknik Mesin")),
                new MahasiswaEntity("Siti","Wanita","Sleman","", LocalDate.of(2000, 4, 16),"Islam",new JurusanEntity("KD","Kedokteran")),
                new MahasiswaEntity("Abdul","Pria","Kulon Progo","", LocalDate.of(2000, 10, 27),"Islam",new JurusanEntity("IF","Informatika"))
        );
    }

    @Test
    void get() {
        //jika methode repository.findAll dipanggil, kembalikan fakultas List
        when(repository.findAll()).thenReturn(mahasiswaEntityList);

        //test method get
        List<MahasiswaModel> result = service.get();

        //check 1
        assertNotNull(result);

        //check 2
        assertEquals(3,result.size());

        //check data ke 1
        assertEquals("Bantul", result.get(0).getAlamat());
        assertEquals("Informatika", result.get(2).getJurusan().getName());

    }

    @Test
    void getById() {
        //sekenario 1
        MahasiswaModel result = service.getById("");

        assertNotNull(result);
        assertNull(result.getJurusan());

        //sekenario 2
        Optional<MahasiswaEntity> entity = Optional.of(mahasiswaEntityList.get(0));
        when(repository.findById(any(String.class))).thenReturn(entity);

        result = service.getById("123");
        assertNotNull(result);
        assertEquals("Budi", result.getName());
        assertEquals("Teknik Mesin", result.getJurusan().getName());
    }

    @Test
    void save() {
        MahasiswaModel request = new MahasiswaModel("Toni","Pria","Bantul","", LocalDate.of(2000, 11, 18),"Islam", new JurusanModel("HI", "Hubungan Internasional", "Fakultas Ilmu Sosial dan Ilmu Politik"));

        //stubing atau mocking
        when(repository.save(any(MahasiswaEntity.class))).thenReturn(mahasiswaEntityList.get(2));

        Optional<MahasiswaModel> result = service.save(request);

        assertNotNull(result);
        assertEquals("HI", result.get().getJurusan().getCode());
        assertEquals("Bantul", result.get().getName());
    }


    @Test
    void update() {
        MahasiswaModel request = new MahasiswaModel("Toni","Pria","Bantul","", LocalDate.of(2000, 11, 18),"Islam", new JurusanModel("HI", "Hubungan Internasional", "Fakultas Ilmu Sosial dan Ilmu Politik"));

        //sekenario 1
        Optional<MahasiswaEntity> entity = Optional.of(mahasiswaEntityList.get(0));

        Optional<MahasiswaModel> result = service.update("",request);

        assertNotNull(result);

        //stubing and mocking
        when(repository.findById(any(String.class))).thenReturn(entity);
        when(repository.save(any(MahasiswaEntity.class))).thenReturn(mahasiswaEntityList.get(0));

        //sekenario 2
        Optional<MahasiswaModel> result1 = service.update("111",request);

        assertNotNull(result1);
        assertEquals("Islam", result1.get().getAgama());
        assertEquals("HI", result1.get().getJurusan().getCode());
    }

    @Test
    void delete() {
        //sekenario 1
        Optional<MahasiswaModel> result = service.delete("");

        assertNotNull(result);
        assertEquals(Optional.empty(), result);

        //sekenario 2
        Optional<MahasiswaEntity> entity = Optional.of(mahasiswaEntityList.get(2));
        when(repository.findById(any(String.class))).thenReturn(entity);

        Optional<MahasiswaModel> result1 = service.delete("111");

        assertNotNull(result1);
        assertNotEquals(Optional.empty(), result1);
    }
    **/
}