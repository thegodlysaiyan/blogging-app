package com.project.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.blog.entities.Category;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.CategoryDto;
import com.project.blog.repositories.CategoryRepo;
import com.project.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category createdCategory = categoryRepo.save(dtoToCategory(categoryDto));
		
		return categoryToDto(createdCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		// TODO Auto-generated method stub
		Category categoryToUpdate = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found."));
		categoryToUpdate.setCategoryTitle(categoryDto.getCategoryTitle());
		categoryToUpdate.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = categoryRepo.save(categoryToUpdate);
		return categoryToDto(updatedCategory);
	}

	@Override
	public void deleteCategory(int categoryId) {
		// TODO Auto-generated method stub
		Category categoryToDelete = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found."));
		categoryRepo.delete(categoryToDelete);
	}

	@Override
	public CategoryDto getCategory(int categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found."));
		
		return categoryToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream()
				.map(category -> categoryToDto(category)).collect(Collectors.toList());
		
		return categoryDtos;
	}
	
	private Category dtoToCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	private CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
