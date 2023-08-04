package com.myblogrestapi.Entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Post model information")
public class PostDTO {
	private Long id;

	// title should not be null or empty
	// title should have at least 2 characters
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;

	// post description should be not null or empty
	// post description should have at least 10 characters
	@NotEmpty
	@Size(min = 10, message = "Post description should have at least 10 characters")
	private String description;

	// post content should not be null or empty
	@NotEmpty
	private String content;

	private List<Comment> comments;


}
