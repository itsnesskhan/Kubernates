package com.order.feinClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.order.payloads.CommonApiResponse;
import com.order.payloads.ItemDto;

@FeignClient(name = "ITEM-SERVICE",url = "http://localhost:8081/api/v1/item")
public interface ItemClient {

	@GetMapping("/get-all")
	public ResponseEntity<com.order.payloads.CommonApiResponse> getAllItems();
	
	@PostMapping("/save")
	public ResponseEntity<CommonApiResponse> addItem(@RequestBody ItemDto itemDto);
	
	@GetMapping("/get/{id}")
	public ResponseEntity<CommonApiResponse> getItemById(@PathVariable Integer id);
}
