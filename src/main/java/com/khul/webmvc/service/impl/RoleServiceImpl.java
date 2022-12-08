package com.khul.webmvc.service.impl;

import com.khul.webmvc.entity.RoleEntity;
import com.khul.webmvc.repository.RoleRepository;
import com.khul.webmvc.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long getCount() {
        return this.repository.count();
    }

    @Override
    public List<RoleEntity> get() {
        List<RoleEntity> result = this.repository.findAll();
        if(result == null)
            result = Collections.emptyList();
        log.info("Role get all data, result: {}", result);
        return result;
    }

    @Override
    public List<RoleEntity> getByNames(List<String> names) {
        List<RoleEntity> result = this.repository.findByNamesFetchMenus(names);
        if(result == null)
            result = Collections.emptyList();
//        log.info("Role get data by name: {}, result: {}", names, result);
        return result;
    }

    @Override
    public RoleEntity getByName(String name) {
        RoleEntity result = this.repository.findByName(name);
//        log.info("Role get data by name: {}, result: {}", name, result);
        return result;
    }

    @Override
    public RoleEntity getById(String id) {
        RoleEntity result = this.repository.findById(id).orElse(null);
        log.info("Role get data by id: {}, result: {}", id, result);
        return result;
    }

    @Override
    public RoleEntity save(RoleEntity data) {
        try{
            this.repository.save(data);
            log.info("Save Role data is success, data: {}", data);
            return data;
        }catch (Exception e){
            log.error("Save Role error: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RoleEntity> save(List<RoleEntity> data) {
        try{
            this.repository.saveAll(data);
            log.info("Save Role data is success, data: {}", data);
            return data;
        }catch (Exception e){
            log.error("Save Role error: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RoleEntity update(RoleEntity data, String id) {
        RoleEntity result = this.repository.findById(id).orElse(null);
        if(result != null) {
            try{
                BeanUtils.copyProperties(data, result);
                this.repository.save(result);
                log.info("Update Role data is success, data: {}", result);
                return result;
            }catch (Exception e){
                log.error("Save Role error: {}", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }else {
            log.info("Can not find Role with id: {}", id);
            return null;
        }
    }

    @Override
    public RoleEntity delete(String id) {
        RoleEntity result = this.repository.findByIdFetchMenuAndPrivilege(id);
        if(result != null) {
            try{
                this.repository.delete(result);
                log.info("Update Role data is success, data: {}", result);
                return result;
            }catch (Exception e){
                log.error("Save Role error: {}", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }else {
            log.info("Can not find Role with id: {}", id);
            return null;
        }
    }
}
