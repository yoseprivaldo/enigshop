package com.enigmacamp.enigshop.repository;

import com.enigmacamp.enigshop.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
