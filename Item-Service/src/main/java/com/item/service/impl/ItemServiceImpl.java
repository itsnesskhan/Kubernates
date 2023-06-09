package com.item.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
	
	@Autowired
	private Environment environment;

	@Override
	public CommonApiResponse addItem(ItemDto itemDto) {
		Item item = modelMapper.map(itemDto, Item.class);

		item = itemRepository.save(item);

		itemDto = modelMapper.map(item, ItemDto.class);
		
		itemDto = addEnviorment(itemDto);
		
		return CommonApiResponse.builder().data(itemDto).time(LocalDateTime.now()).status(HttpStatus.CREATED).build();
	}

	@Override
	public CommonApiResponse getAllItem() {

		List<Item> allItems = itemRepository.findAll();

		ItemDto[] itemDtos = modelMapper.map(allItems, ItemDto[].class);
		
		itemDtos=  Arrays.stream(itemDtos).map(item-> addEnviorment(item)).toArray(ItemDto[]::new);
		
		return CommonApiResponse.builder().data(itemDtos).time(LocalDateTime.now()).status(HttpStatus.OK).build();
	}

	@Override
	public CommonApiResponse getItemById(Integer itemId) {

		Optional<Item> itemOptional = itemRepository.findById(itemId);
		if (!itemOptional.isPresent()) {
			return CommonApiResponse.builder().status(HttpStatus.NOT_FOUND).time(LocalDateTime.now()).build();
		}

		ItemDto itemDto = modelMapper.map(itemOptional.get(), ItemDto.class);
		
		itemDto = addEnviorment(itemDto);
		
		return CommonApiResponse.builder().data(itemDto).status(HttpStatus.OK).time(LocalDateTime.now()).build();
	}

	@Override
	public CommonApiResponse updateItem(ItemDto itemDto) {

		Optional<Item> itemOptional = itemRepository.findById(itemDto.getItemId());

		if (!itemOptional.isPresent()) {

			return CommonApiResponse.builder().status(HttpStatus.NOT_FOUND).time(LocalDateTime.now()).build();
		}

		itemOptional.get().setCost(itemDto.getCost());
		itemOptional.get().setQuantity(itemDto.getQuantity());
		itemOptional.get().setItemName(itemDto.getItemName());

		Item updatedItem = itemRepository.save(itemOptional.get());
		itemDto = modelMapper.map(updatedItem, itemDto.getClass());
		
		itemDto = addEnviorment(itemDto);
		return CommonApiResponse.builder().data(itemDto)
				.time(LocalDateTime.now()).status(HttpStatus.OK).build();
	}

	@Override
	public CommonApiResponse deleteItem(Integer id) {
		
		Optional<Item> itemOptional = itemRepository.findById(id);

		if (!itemOptional.isPresent()) {

			return CommonApiResponse.builder().status(HttpStatus.NOT_FOUND).time(LocalDateTime.now()).build();
		}
		
		itemRepository.deleteById(id);
		return CommonApiResponse.builder().status(HttpStatus.OK).time(LocalDateTime.now()).data("Item deleted Successfully!").build();			
		
	}
	
	private ItemDto addEnviorment(ItemDto itemDto) {
		String port = environment.getProperty("local.server.port");
		String host="";
		try {
			host = environment.getProperty("HOSTNAME") != null?environment.getProperty("HOSTNAME"):InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String version = "V1";
		
		System.out.println(port+" "+host+" "+version);
		itemDto.setEnviorment(host+" "+port+" "+version);
		return itemDto;
	}

}
