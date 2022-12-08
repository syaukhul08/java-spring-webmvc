package com.khul.webmvc.service.impl;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.khul.webmvc.entity.KelasEntity;
import com.khul.webmvc.model.KelasModel;
import com.khul.webmvc.repository.KelasRepository;
import com.khul.webmvc.util.DateUtil;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KelasServiceImplTest {

    @Autowired
    @InjectMocks
    private KelasServiceImpl service;

    @Mock
    private KelasRepository repository;

    private static List<KelasEntity> kelasList;
    private static String ruangId;
    private static String dosenId;
    private static String matkulId;

    @BeforeEach
    void setUp() {
        ruangId = UUID.randomUUID().toString();
        dosenId = UUID.randomUUID().toString();
        matkulId = UUID.randomUUID().toString();
        kelasList = Arrays.asList(
                new KelasEntity("K001", "Senin", "08:00:00", "10:00:00", ruangId, matkulId, dosenId),
                new KelasEntity("K002", "Senin", "10:00:00", "12:00:00", ruangId, matkulId, dosenId),
                new KelasEntity("K003", "Senin", "13:00:00", "15:00:00", ruangId, matkulId, dosenId),
                new KelasEntity("K004", "Senin", "15:00:00", "17:00:00", ruangId, matkulId, dosenId)
        );
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(kelasList);

        List<KelasModel> result = service.getAll();
        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals("K002", kelasList.get(1).getCode());
        assertEquals("Senin", kelasList.get(2).getNamaHari());


    }


    @Test
    void getById() {
    }

    @Test
    void save_Check_cose01() {
//        Optional<KelasModel> result = service.save(null);
//        assertNotNull(result);
//        assertEquals(Optional.empty(), result);
//
//        when(repository.checkCase01("Ruang A","Senin", DateUtil.getTime("10:00:00"),DateUtil.getTime("12:00:00"))).thenReturn(Arrays.asList(kelasList.get(0)));
//        KelasModel request = new KelasModel("Senin",DateUtil.getTime("10:00:00"),DateUtil.getTime("12:00:00"), "Ruang A","MK001","Shahal");
//        //check code
//        result = service.save(request);
//        assertNotNull(result);
//        assertEquals(Optional.empty(), result);
    }


    @Test
    void testGetAll() {
    }

    @Test
    void testGetById() {
    }

    @Test
    void testSave() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}