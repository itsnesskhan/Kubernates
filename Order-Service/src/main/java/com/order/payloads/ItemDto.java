package com.order.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {

	private Integer itemId;
	
	private String itemName;
	
	private Integer quantity;
	
	private Double cost;
	
	private String enviorment;
}
