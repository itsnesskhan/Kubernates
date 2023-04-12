package com.order.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.order.model.Order;
import com.order.payloads.CommonApiResponse;
import com.order.payloads.ItemDto;
import com.order.payloads.OrderDto;
import com.order.service.OrderService;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OrderService orderService;
	
	private ObjectMapper objectMapper;
	
	private OrderDto orderDto;
	
	private Order order;
	
	private ItemDto itemDto;

	@BeforeEach
	void setUp() throws Exception {
		
		objectMapper= new ObjectMapper().registerModule(new JavaTimeModule());
		
		orderDto = OrderDto.builder().oid(1).orderDate(LocalDateTime.now()).orderStatus("PENDING").item_id(1).build();

		order = Order.builder().oid(1).orderDate(LocalDateTime.now()).orderStatus("PENDING").item_id(1).build();

		itemDto = ItemDto.builder().itemId(1).itemName("test").cost(100d).quantity(10).build();
	}

	@Test
	void testAddOrder() throws Exception {
		
		CommonApiResponse apiResponse = CommonApiResponse.builder().data(orderDto).status(HttpStatus.CREATED).build();
		
		String jsonRequest = objectMapper.writeValueAsString(orderDto);
		
		when(orderService.addOrder(orderDto)).thenReturn(apiResponse);
		
		new ObjectMapper().registerModule(new JavaTimeModule());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post("/api/v1/order/save")
				.content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.orderStatus", Matchers.equalTo("PENDING")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.item_id", Matchers.equalTo(1)))
		.andExpect(status().isCreated())
		.andReturn();	

		
	}

	@Test
	void testGetAllOrders() throws Exception {
		
		OrderDto orderDto2 = OrderDto.builder().oid(2).orderDate(LocalDateTime.now()).orderStatus("PENDING").item_id(2).build();
		
		CommonApiResponse apiResponse = CommonApiResponse.builder().data(List.of(orderDto,orderDto2)).status(HttpStatus.CREATED).build();

		when(orderService.getAllOrders()).thenReturn(apiResponse);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/order/get-all")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.size()", Matchers.is(2)))
        .andExpect(status().isOk())
		.andReturn();

	}

	@Test
	void testDeleteOrderById() throws Exception {
		CommonApiResponse apiResponse = CommonApiResponse.builder().data("ORDER DELETED SUCCESSFULLY!").status(HttpStatus.OK).build();

		when(orderService.deleteOrder(1)).thenReturn(apiResponse);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/order/delete/1")
												.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is("ORDER DELETED SUCCESSFULLY!")))
				.andReturn();
		
	}

}
