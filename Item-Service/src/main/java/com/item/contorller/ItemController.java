package com.item.contorller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.item.payloads.CommonApiResponse;
import com.item.payloads.ItemDto;
import com.item.service.ItemService;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	@GetMapping("/get-all")
	public ResponseEntity<CommonApiResponse> getAllItems(){
		CommonApiResponse response = itemService.getAllItem();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/save")
	public ResponseEntity<CommonApiResponse> addItem(@RequestBody ItemDto itemDto){
		CommonApiResponse response = itemService.addItem(itemDto);
		
		itemDto = (ItemDto) response.getData();
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/get/{id}")
				.buildAndExpand(itemDto.getItemId()).toUriString());
	
		return ResponseEntity.created(uri).body(response);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<CommonApiResponse> getItemById(@PathVariable Integer id){
		CommonApiResponse response = itemService.getItemById(id);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CommonApiResponse> updateItem(@RequestBody ItemDto itemDto){
		CommonApiResponse response = itemService.updateItem(itemDto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CommonApiResponse> deleteItemById(@PathVariable Integer id){
		CommonApiResponse response = itemService.deleteItem(id);
		return ResponseEntity.ok(response);
	}
}
