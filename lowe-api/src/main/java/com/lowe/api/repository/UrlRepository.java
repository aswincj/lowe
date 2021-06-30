package com.lowe.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lowe.api.entity.URL;

@Repository
public interface UrlRepository extends JpaRepository<URL, Long> {

    @Query("SELECT u FROM URL u WHERE u.code = ?1")
    URL getByCode(String code);

}
