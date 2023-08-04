package com.example.jpa.repository;

import com.example.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(
            value = "select * from new_users where user_id = ?1",
            nativeQuery = true
    )
    Optional<User> getUserById(Integer userId);

    List<User> findAllByDeleteAtIsNull();

    @Query(
            value = "select * from new_users order by user_id",
            countQuery = "select count(*) from new_users",
            nativeQuery = true
    )
    Page<User> findAllByDeleteAtIsNull(Pageable pageable);

    @Query(name = "searchByName")
    List<User> searchUserByName(@Param(value = "val") String value);

    @Query(
            value = "select * from new_users where name like concat(:val, '%')" +
                    "and deleted_at is null order by user_id",
            countQuery = "select count (*) from new_users",
            nativeQuery = true
    )
    Page<User> searchAllUserByValue(Pageable pageable, @Param(value = "val") String value);

    @Query(value = "select case when count(u.userId) > 0 then false else true end " +
                    "from User as u where u.email = :email")
    boolean existsByEmail(@Param(value = "email") String email);

    @Query(value = "select u from User as u  " +
            "where coalesce(:id, u.userId) = u.userId " +
            "and coalesce(:n, u.name) = u.name " +
            "and coalesce(:s, u.surname) = u.surname  " +
            "and coalesce(:e, u.email) = u.email " +
            "and coalesce(:p, u.password) = u.password " +
            "and coalesce(:a, u.age) = u.age ")
    Page<User> findAllUserByParams(
            @Param(value = "id") Integer id,
            @Param(value = "n") String name,
            @Param(value = "s") String surname,
            @Param(value = "e") String email,
            @Param(value = "p") String password,
            @Param(value = "a") Integer age,
            Pageable pageable);
}
