package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.FakultasEntity;
import com.khul.webmvc.model.FakultasModel;
import com.khul.webmvc.repository.FakultasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FakultasServiceImplTest {

    @Autowired
    @InjectMocks
    private FakultasServiceImpl service;

    @Mock
    private FakultasRepository repository;

    private static List<FakultasEntity> fakultasEntityList;

    @BeforeEach
    void setUp() {
        fakultasEntityList = Arrays.asList(
                new FakultasEntity("FK","Fakultas Kedokteran","Yogyakarta"),
                new FakultasEntity("FT","Fakultas Teknik","Yogyakarta"),
                new FakultasEntity("FE","Fakultas Ekonomi","Yogyakarta")
        );
    }

    @Test
    void get() {
        //jika methode repository.findAll dipanggil, kembalikan fakultas List
        when(repository.findAll()).thenReturn(fakultasEntityList);

        //test method get
        List<FakultasModel> result = service.getAll();

        //check 1
        assertNotNull(result);

        //check 2
        assertEquals(3,result.size());

        //check data ke 1
        assertEquals("FT", result.get(1).getCode());
        assertEquals("Fakultas Teknik", result.get(1).getName());

        //check data salah
        assertNotEquals("FK", result.get(2).getCode());
    }

    @Test
    void getById() {
        //sekenario 1
        FakultasModel result = service.getById("");

        assertNotNull(result);
        assertNull(result.getCode());

        //sekenario 2
        Optional<FakultasEntity> entity = Optional.of(fakultasEntityList.get(0));
        when(repository.findById(any(String.class))).thenReturn(entity);

        result = service.getById("123");
        assertNotNull(result);
        assertEquals("FK",result.getCode());
    }

    @Test
    void save() {
        FakultasModel request = new FakultasModel("FH", "Fakultas Hukum", "Yogyakarta");

        //stubing atau mocking
        when(repository.save(any(FakultasEntity.class))).thenReturn(fakultasEntityList.get(2));

        Optional<FakultasModel> result = service.save(request);
        assertNotNull(result);
        assertEquals("FH", result.get().getCode());
        assertEquals("Fakultas Hukum", result.get().getName());
        assertEquals("Yogyakarta", result.get().getAlamat());
    }

    @Test
    void update() {
        FakultasModel request = new FakultasModel("FH", "Fakultas Hukum", "Yogyakarta");

        //sekenario 1
        Optional<FakultasEntity> entity = Optional.of(fakultasEntityList.get(0));

        Optional<FakultasModel> result = service.update("",request);

        assertNotNull(result);

        //stubing and mocking
        when(repository.findById(any(String.class))).thenReturn(entity);
        when(repository.save(any(FakultasEntity.class))).thenReturn(fakultasEntityList.get(0));

        //sekenario 2
        Optional<FakultasModel> result1 = service.update("111",request);

        assertNotNull(result1);
        assertEquals("FH", result1.get().getCode());
        assertEquals("Fakultas Hukum", result1.get().getName());
        assertEquals("Yogyakarta", result1.get().getAlamat());
    }

    @Test
    void delete() {
        //sekenario 1
        Optional<FakultasModel> result = service.delete("");

        assertNotNull(result);
        assertEquals(Optional.empty(), result);

        //sekenario 2
        Optional<FakultasEntity> entity = Optional.of(fakultasEntityList.get(2));
        when(repository.findById(any(String.class))).thenReturn(entity);

        Optional<FakultasModel> result1 = service.delete("111");

        assertNotNull(result1);
        assertNotEquals(Optional.empty(), result1);
    }
}