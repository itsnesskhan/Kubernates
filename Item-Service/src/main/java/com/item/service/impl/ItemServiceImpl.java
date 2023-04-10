package com.item.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.item.model.Item;
import com.item.payloads.CommonApiResponse;
import com.item.payloads.ItemDto;
import com.item.repository.ItemRepository;
import com.item.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommonApiResponse addItem(ItemDto itemDto) {
		Item item = modelMapper.map(itemDto, Item.class);

		item = itemRepository.save(item);

		itemDto = modelMapper.map(item, itemDto.getClass());
		return CommonApiResponse.builder().data(itemDto).time(LocalDateTime.now()).status(HttpStatus.CREATED).build();
	}

	@Override
	public CommonApiResponse getAllItem() {

		List<Item> allItems = itemRepository.findAll();

		ItemDto[] itemDtos = modelMapper.map(allItems, ItemDto[].class);
		return CommonApiResponse.builder().data(itemDtos).time(LocalDateTime.now()).status(HttpStatus.OK).build();
	}

	@Override
	public CommonApiResponse getItemById(Integer itemId) {

		Optional<Item> itemOptional = itemRepository.findById(itemId);
		if (!itemOptional.isPresent()) {
			return CommonApiResponse.builder().status(HttpStatus.NOT_FOUND).time(LocalDateTime.now()).build();
		}

		ItemDto itemDto = modelMapper.map(itemOptional.get(), ItemDto.class);
		return CommonApiResponse.builder().data(itemDto).status(HttpStatus.OK).time(LocalDateTime.now()).build();
	}

}