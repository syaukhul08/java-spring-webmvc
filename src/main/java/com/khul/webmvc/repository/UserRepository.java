package com.khul.webmvc.repository;

import com.khul.webmvc.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query("select t from UserEntity t join fetch t.roles where t.username= :username")
    UserEntity findByUsername(@Param("username") String username);

    @Query("select t from UserEntity t join fetch t.roles where t.id= :id")
    UserEntity findByIdFetchRoles(@Param("id") String id);

    List<UserEntity> findByUsernameContaining(String keyword);
}
