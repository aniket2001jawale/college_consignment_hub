package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.Donate_ItemDTO;

public interface Donate_ItemRepository extends JpaRepository<Donate_ItemDTO, Long> {

}
