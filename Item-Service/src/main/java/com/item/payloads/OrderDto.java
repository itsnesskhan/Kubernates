package com.item.payloads;

import java.time.LocalDateTime;
import java.util.List;

import com.item.model.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {

	private Integer oid;
	
	private LocalDateTime orderDate;
	
	private String orderStatus;
	
	private Item item;
}
