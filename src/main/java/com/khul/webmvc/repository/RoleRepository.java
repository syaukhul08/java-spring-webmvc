package com.khul.webmvc.repository;

import com.khul.webmvc.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    RoleEntity findByName(String name);

    //@Query("select t from RoleEntity t join fetch t.menus where t.name in :names")
    @Query("select t from RoleEntity t where t.name in :names")
    List<RoleEntity> findByNamesFetchMenus(@Param("names") List<String> names);

    //@Query("select t from RoleEntity t join fetch t.menus join fetch t.privileges where t.id=:id")
    @Query("select t from RoleEntity t where t.id=:id")
    RoleEntity findByIdFetchMenuAndPrivilege(@Param("id") String id);
}
