package com.order.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
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
