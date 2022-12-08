package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.FakultasEntity;
import com.khul.webmvc.entity.JurusanEntity;
import com.khul.webmvc.model.FakultasModel;
import com.khul.webmvc.model.JurusanModel;
import com.khul.webmvc.repository.JurusanRepository;
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
class JurusanServiceImplTest {

    @Autowired
    @InjectMocks
    private JurusanServiceImpl service;

    @Mock
    private JurusanRepository repository;

    private static List<JurusanEntity> jurusanEntityList;
    private static String fakultasId;

    @BeforeEach
    void setUp() {
        jurusanEntityList = Arrays.asList(
                new JurusanEntity("TM", "Teknik Mesin", fakultasId),
                new JurusanEntity("MJ", "Manajemen",fakultasId),
                new JurusanEntity("IF", "Informatika",fakultasId)
        );
    }

    @Test
    void get() {
        //jika methode repository.findAll dipanggil, kembalikan fakultas List
        when(repository.findAll()).thenReturn(jurusanEntityList);

        //test method get
        List<JurusanModel> result = service.get();

        //check 1
        assertNotNull(result);

        //check 2
        assertEquals(3,result.size());

        //check data ke 1
        assertEquals("MJ", result.get(1).getCode());
        assertEquals("Informatika", result.get(2).getName());

    }

    @Test
    void getById() {
        //sekenario 1
        JurusanModel result = service.getById("");

        assertNotNull(result);
        assertNull(result.getCode());

        //sekenario 2
        Optional<JurusanEntity> entity = Optional.of(jurusanEntityList.get(0));
        when(repository.findById(any(String.class))).thenReturn(entity);

        result = service.getById("123");
        assertNotNull(result);
        assertEquals("TM", result.getCode());
    }

    @Test
    void save_Check_Code(){
        Optional<JurusanModel> result = service.save(null);
        assertNotNull(result);
        assertEquals(Optional.empty(), result);

        when(repository.findByCode("IF")).thenReturn(Arrays.asList(jurusanEntityList.get(2)));

        JurusanModel request = new JurusanModel("IF", "Informatika", fakultasId);

        result = service.save(request);
        assertNotNull(result);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void update() {

    }

    @Test
    void delete() {

    }
}