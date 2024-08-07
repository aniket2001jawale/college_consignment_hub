package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.ItemDTO;


public interface ItemRepository extends JpaRepository<ItemDTO, Long> {

}
