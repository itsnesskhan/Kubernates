package com.order.payloads;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderDto {

	private Integer oid;
	
	private LocalDateTime orderDate;
	
	private String orderStatus;
	
	private Integer item_id;
	
	private ItemDto item;
}
