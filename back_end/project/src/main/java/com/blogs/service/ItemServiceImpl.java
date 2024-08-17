package com.blogs.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blogs.custom_exceptions.ItemNotFoundException;
import com.blogs.custom_exceptions.StudentNotFoundException;
import com.blogs.custom_exceptions.WatchlistNotFoundException;
import com.blogs.dto.ItemDTO;
import com.blogs.dto.RatingDTO;
import com.blogs.dto.StudentDTO;
import com.blogs.dto.WatchlistDTO;
import com.blogs.entities.Cart;
import com.blogs.entities.Category;
import com.blogs.entities.Donate_Item;
import com.blogs.entities.Item;
import com.blogs.entities.Rating;
import com.blogs.entities.Status;
import com.blogs.entities.Student;
import com.blogs.entities.Watchlist;
import com.blogs.repository.CartRepository;
import com.blogs.repository.CategoryRepository;
import com.blogs.repository.Donate_ItemRepository;
import com.blogs.repository.ItemRepository;
import com.blogs.repository.RatingRepository;
import com.blogs.repository.StudentRepository;
import com.blogs.repository.WatchlistRepository;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private WatchlistRepository watchlistRepository;
	@Autowired
	private Donate_ItemRepository donate_ItemRepository;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void addItem(ItemDTO itemDTO) {
		Item item = modelMapper.map(itemDTO, Item.class);

		// Set the student, cart, category, and watchlist relationships
		Student student = studentRepository.findById(itemDTO.getSeller_id())
				.orElseThrow(() -> new IllegalArgumentException("Invalid seller ID"));
		Cart cart = cartRepository.findById(itemDTO.getCart_id())
				.orElseThrow(() -> new IllegalArgumentException("Invalid cart ID"));
		Category category = categoryRepository.findById(itemDTO.getCategory_id())
				.orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
		Watchlist watchlist = watchlistRepository.findById(itemDTO.getWatchlist_id())
				.orElseThrow(() -> new IllegalArgumentException("Invalid watchlist ID"));

		item.setStudent(student);
		item.setCart(cart);
		item.setStatus(Status.AVAILABLE);
		item.setCategory(category);
		item.setWatchlist(watchlist);

		itemRepository.save(item);
	}

	@Override
	public ItemDTO getItemById(Long id) {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Item id not found: " + id));

		ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);

		// Check if related entities are not null before accessing their properties
		if (item.getCart() != null) {
			itemDTO.setCart_id(item.getCart().getCart_id());
		}

		if (item.getCategory() != null) {
			itemDTO.setCategory_id(item.getCategory().getCategory_id());
		}

		if (item.getStudent() != null) {
			itemDTO.setSeller_id(item.getStudent().getStudent_id());
		}

		if (item.getWatchlist() != null) {
			itemDTO.setWatchlist_id(item.getWatchlist().getWatchlist_id());
		}

		return itemDTO;
	}

	@Override
	public List<ItemDTO> getAllItems() {
		List<Item> items = itemRepository.findAll();

		return items.stream().map(item -> {
			ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);

			// Safe null checks before accessing properties
			if (item.getCart() != null) {
				itemDTO.setCart_id(item.getCart().getCart_id());
			} else {
				itemDTO.setCart_id(null); // or a default value
			}

			if (item.getCategory() != null) {
				itemDTO.setCategory_id(item.getCategory().getCategory_id());
			} else {
				itemDTO.setCategory_id(null); // or a default value
			}

			if (item.getStudent() != null) {
				itemDTO.setSeller_id(item.getStudent().getStudent_id());
			} else {
				itemDTO.setSeller_id(null); // or a default value
			}

			if (item.getWatchlist() != null) {
				itemDTO.setWatchlist_id(item.getWatchlist().getWatchlist_id());
			} else {
				itemDTO.setWatchlist_id(null); // or a default value
			}

			return itemDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public void updateItem(Long id, ItemDTO itemDTO) {
		Item existingItem = itemRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Item with ID " + id + " not found"));

		// Update fields
		existingItem.setTitle(itemDTO.getTitle());
		existingItem.setDescription(itemDTO.getDescription());
		existingItem.setPrice(itemDTO.getPrice());
		existingItem.setImage(itemDTO.getImage());
		existingItem.setItem_duration(itemDTO.getItem_duration());
		existingItem.setStatus(itemDTO.getStatus());

		// Update relationships if necessary
		Student student = studentRepository.findById(itemDTO.getSeller_id())
				.orElseThrow(() -> new IllegalArgumentException("Invalid seller ID"));
		Cart cart = cartRepository.findById(itemDTO.getCart_id())
				.orElseThrow(() -> new IllegalArgumentException("Invalid cart ID"));
		Category category = categoryRepository.findById(itemDTO.getCategory_id())
				.orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
		Watchlist watchlist = watchlistRepository.findById(itemDTO.getWatchlist_id())
				.orElseThrow(() -> new IllegalArgumentException("Invalid watchlist ID"));

		existingItem.setStudent(student);
		existingItem.setCart(cart);
		existingItem.setCategory(category);
		existingItem.setWatchlist(watchlist);

		itemRepository.save(existingItem);
	}

	@Override
	public void deleteItem(Long id) {

		Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));

		// Set the watchlist_id to null for the associated watchlist
		if (item.getWatchlist() != null) {
			Watchlist watchlist = item.getWatchlist();
			watchlist.getItem().remove(item); // Remove the item from the watchlist
			watchlistRepository.save(watchlist); // Save the watchlist to persist changes
		}
		// Now delete the item
		itemRepository.delete(item);
	}

	public List<Item> getItemsByCartId(Long id) {
		List<Item> items = itemRepository.findAllByCartId(id);
		if (items.isEmpty()) {
			throw new ItemNotFoundException("No items found for cart id: " + id);
		}
		return items;
	}

	@Override
	public List<Item> getItemsByWatchlistId(Long id) {
		List<Item> items = itemRepository.findAllByWatchlistId(id);
		if (items.isEmpty()) {
			throw new ItemNotFoundException("No items found for watchlist id: " + id);
		}
		return items;
	}

}
