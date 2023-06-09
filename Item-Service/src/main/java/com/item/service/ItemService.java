package com.item.service;

import com.item.payloads.CommonApiResponse;
import com.item.payloads.ItemDto;

public interface ItemService {

	CommonApiResponse addItem(ItemDto itemDto);
	
	CommonApiResponse getAllItem();
	
	CommonApiResponse getItemById(Integer itemId);
	
	CommonApiResponse updateItem(ItemDto itemDto);
	
	CommonApiResponse deleteItem(Integer id);
}
