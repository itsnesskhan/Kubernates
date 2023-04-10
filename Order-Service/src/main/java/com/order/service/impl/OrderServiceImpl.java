package com.order.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.feinClient.ItemClient;
import com.order.model.Order;
import com.order.payloads.CommonApiResponse;
import com.order.payloads.ItemDto;
import com.order.payloads.OrderDto;
import com.order.repository.OrderRepository;
import com.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ItemClient itemClient;
	
	@Override
	public CommonApiResponse addOrder(OrderDto orderDto) {
		
		Order order = modelMapper.map(orderDto, Order.class);
		
		order = orderRepository.save(order);
		
		orderDto = modelMapper.map(order, OrderDto.class);
		
		CommonApiResponse apiResponse = itemClient.getItemById(orderDto.getItem_id()).getBody();
		
		ItemDto itemDto = objectMapper.convertValue(apiResponse, ItemDto.class);
		
		orderDto.setItem(itemDto);
		
		return CommonApiResponse.builder().data(orderDto).time(LocalDateTime.now()).status(HttpStatus.CREATED).build();
	}

	@Override
	public CommonApiResponse getAllOrders() {
		List<Order> allOrders = orderRepository.findAll();
		
		OrderDto[] ordersDtos = modelMapper.map(allOrders, OrderDto[].class);
		return CommonApiResponse.builder().data(ordersDtos).time(LocalDateTime.now()).status(HttpStatus.OK).build();
	}

	@Override
	public CommonApiResponse getOrderById(Integer oid) {
		
		Optional<Order> orderOptional = orderRepository.findById(oid);
		if (!orderOptional.isPresent()) {
			return CommonApiResponse.builder().status(HttpStatus.NOT_FOUND).time(LocalDateTime.now()).build()
;		}
		
		OrderDto orderDto = modelMapper.map(orderOptional, OrderDto.class);
		return CommonApiResponse.builder().data(orderDto).status(HttpStatus.OK).time(LocalDateTime.now()).build();
	}

}
