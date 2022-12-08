package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.DosenEntity;
import com.khul.webmvc.model.DosenModel;
import com.khul.webmvc.repository.DosenRepository;
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
class DosenServiceImplTest {

    @Autowired
    @InjectMocks
    private DosenServiceImpl service;

    @Mock
    private DosenRepository repository;

    private static List<DosenEntity> dosenEntityList;

    @BeforeEach
    void setUp() {
        dosenEntityList = Arrays.asList(
                new DosenEntity("111","Agus","Pria","Yogyakarta","S.Kom"),
                new DosenEntity("112","Siti","Wanita","Sleman","S.Kom"),
                new DosenEntity("113","Rudy","Pria","Bantul","S.T"),
                new DosenEntity("114","Joko","Pria","Sleman","S.Kom")

        );
    }

    @Test
    void get() {
        //jika method repository.findAll dipanggil, kembalikan Dosen List
        when(repository.findAll()).thenReturn(dosenEntityList);

        //test method get()
        List<DosenModel> result = service.get();

        assertNotNull(result);
        assertEquals(4, result.size());

        assertEquals("113", result.get(2).getNip());

        //check data salah
        assertNotEquals("112", result.get(3).getNip());
    }

    @Test
    void getById() {
        //sekenario 1
        DosenModel result = service.getById("");

        assertNotNull(result);
        assertNull(result.getNip());

        //sekenario 2
        Optional<DosenEntity> entity = Optional.of(dosenEntityList.get(1));
        when(repository.findById(any(String.class))).thenReturn(entity);

        result = service.getById("123");
        assertNotNull(result);
        assertEquals("112",result.getNip());
    }

    @Test
    void save() {
        DosenModel request = new DosenModel("115","Tono","Pria","Solo","S.Kom");

        //stubing atau mocking
        when(repository.save(any(DosenEntity.class))).thenReturn(dosenEntityList.get(1));

        Optional<DosenModel> result = service.save(request);
        assertNotNull(result);
        assertEquals("115", result.get().getNip());
        assertEquals("Tono", result.get().getName());
        assertEquals("S.Kom", result.get().getGelar());
    }

    @Test
    void update() {
        DosenModel request = new DosenModel("115","Tono","Pria","Solo","S.Kom");

        //sekenario 1
        Optional<DosenEntity> entity = Optional.of(dosenEntityList.get(1));
        Optional<DosenModel> result = service.update("", request);

        assertNotNull(result);

        //stubing and mocking
        when(repository.findById(any(String.class))).thenReturn(entity);
        when(repository.save(any(DosenEntity.class))).thenReturn(dosenEntityList.get(1));

        //sekenario 2
        Optional<DosenModel> result1 = service.update("123", request);
        assertNotNull(result1);
        assertEquals("115", result1.get().getNip());
        assertEquals("Tono", result1.get().getName());
        assertEquals("S.Kom", result1.get().getGelar());
    }

    @Test
    void delete() {

        //sekenario 1
        Optional<DosenModel> result = service.delete("");
        assertNotNull(result);
        assertEquals(Optional.empty(), result);

        //sekenario 2
        Optional<DosenEntity> entity = Optional.of(dosenEntityList.get(2));
        when(repository.findById(any(String.class))).thenReturn(entity);

        Optional<DosenModel> result1 = service.delete("123");
        assertNotNull(result1);
        assertNotEquals(Optional.empty(), result1);
    }
}