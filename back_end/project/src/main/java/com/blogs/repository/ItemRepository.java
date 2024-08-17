package com.blogs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogs.entities.Item;



public interface ItemRepository extends JpaRepository<Item, Long> {


	@Query("SELECT i FROM Item i WHERE i.cart.id = :cartId")
    List<Item> findAllByCartId(@Param("cartId") Long cartId);

    @Query("SELECT i FROM Item i WHERE i.watchlist.id = :watchlistId")
    List<Item> findAllByWatchlistId(@Param("watchlistId") Long watchlistId);
    
}
