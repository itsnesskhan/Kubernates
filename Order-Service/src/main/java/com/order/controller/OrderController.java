package com.order.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.order.payloads.CommonApiResponse;
import com.order.payloads.OrderDto;
import com.order.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/save")
	public ResponseEntity<CommonApiResponse> addOrder(@RequestBody OrderDto orderDto){
		CommonApiResponse apiResponse = orderService.addOrder(orderDto);
		
		orderDto = (OrderDto) apiResponse.getData();
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/get/{id}")
				.buildAndExpand(orderDto.getOid()).toUriString());
	
		return ResponseEntity.created(uri).body(apiResponse);
		
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<CommonApiResponse> getAllOrders(){
		CommonApiResponse allOrders = orderService.getAllOrders();
		return ResponseEntity.ok(allOrders);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<CommonApiResponse> getOrderById(@PathVariable Integer id){
		CommonApiResponse order = orderService.getOrderById(id);
		if (order.getStatus()==HttpStatus.NOT_FOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(order);
		}
		return ResponseEntity.ok(order);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CommonApiResponse> updateOrder(@RequestBody OrderDto orderDto){
		CommonApiResponse apiResponse = orderService.updateOrder(orderDto);
		return ResponseEntity.ok(apiResponse);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CommonApiResponse> deleteOrderById(@PathVariable Integer id){
		CommonApiResponse apiResponse = orderService.deleteOrder(id);
		return ResponseEntity.ok(apiResponse);
	}
}
