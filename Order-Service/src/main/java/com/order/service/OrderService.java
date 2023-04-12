package com.order.service;

import com.order.payloads.CommonApiResponse;
import com.order.payloads.OrderDto;

public interface OrderService {

	CommonApiResponse addOrder(OrderDto orderDto);
	
	CommonApiResponse getAllOrders();
	
	CommonApiResponse getOrderById(Integer oid);
	
	CommonApiResponse updateOrder(OrderDto orderDto);
	
	CommonApiResponse deleteOrder(Integer id);
	
}
