package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.dto.Donate_ItemDTO;
import com.blogs.entities.Donate_Item;

public interface Donate_ItemRepository extends JpaRepository<Donate_Item, Long> {

}
