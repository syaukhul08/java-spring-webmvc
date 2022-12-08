package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.GedungEntity;
import com.khul.webmvc.model.GedungModel;
import com.khul.webmvc.repository.GedungRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GedungServiceImplTest {

    @Autowired
    @InjectMocks
    private GedungServiceImpl service;

    @Mock
    private GedungRepository repository;

    private static List<GedungEntity> gedungEntityList;

    @BeforeEach
    void setUp() {
        gedungEntityList = Arrays.asList(
                new GedungEntity("A","Gedung A",5),
                new GedungEntity("B","Gedung B",6),
                new GedungEntity("C","Gedung C",3),
                new GedungEntity("D","Gedung D",5)
        );
    }

    @Test
    void get() {
        //jika repository.findAll di panggil, return gedung List
        when(repository.findAll()).thenReturn(gedungEntityList);

        List<GedungModel> result = service.get();

        assertNotNull(result);
        assertEquals(4,result.size());
        assertEquals("A", result.get(0).getCode());
        assertNotEquals("B",result.get(2).getCode());
    }

    @Test
    void getById() {
        //sekenario 1
        GedungModel result = service.getById("");

        assertNotNull(result);
        assertNull(result.getCode());

        //sekenario 2
        Optional<GedungEntity> entity = Optional.of(gedungEntityList.get(1));
        when(repository.findById(any(String.class))).thenReturn(entity);

        result = service.getById("122");
        assertNotNull(result);
        assertEquals("B", result.getCode());
    }

    @Test
    void save() {
        GedungModel request = new GedungModel("E","Gedung E", 4);

        //stubing atau mocking
        when(repository.save(any(GedungEntity.class))).thenReturn(gedungEntityList.get(1));

        Optional<GedungModel> result = service.save(request);
        assertNotNull(result);
        assertEquals("E", result.get().getCode());
        assertEquals(4, result.get().getJmlLantai());
    }

    @Test
    void update() {
        GedungModel request = new GedungModel("E","Gedung E", 4);
        //sekenario 1
        Optional<GedungEntity> entity = Optional.of(gedungEntityList.get(0));

        Optional<GedungModel> result = service.update("",request);

        assertNotNull(result);

        //stubing and mocking
        when(repository.findById(any(String.class))).thenReturn(entity);
        when(repository.save(any(GedungEntity.class))).thenReturn(gedungEntityList.get(0));

        //sekenario 2
        Optional<GedungModel> result1 = service.update("111",request);

        assertNotNull(result1);
        assertEquals("E", result1.get().getCode());
        assertEquals("Gedung E", result1.get().getName());
        assertEquals(4, result1.get().getJmlLantai());
    }

    @Test
    void delete() {
        //sekenario 1
        Optional<GedungModel> result = service.delete("");

        assertNotNull(result);
        assertEquals(Optional.empty(), result);

        //sekenario 2
        Optional<GedungEntity> entity = Optional.of(gedungEntityList.get(2));
        when(repository.findById(any(String.class))).thenReturn(entity);

        Optional<GedungModel> result1 = service.delete("111");

        assertNotNull(result1);
        assertNotEquals(Optional.empty(), result1);
    }
}