package com.project.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private int categoryId;
	@NotBlank
	@Size(min = 2, message = "Category title should have atleast 2 characters.")
	private String categoryTitle;
	@NotBlank
	private String categoryDescription;

}
