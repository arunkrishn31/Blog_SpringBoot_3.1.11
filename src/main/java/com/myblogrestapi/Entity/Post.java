package com.myblogrestapi.Entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Post model information")
@Entity
@Table ( name="posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "title",nullable = false)
	private String title;


	@Column(name ="description",nullable =false)
	private String description;


	@Column(name = "content", nullable = false)
	private String content;

	@Column(name="comments", nullable = false)
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comments= new ArrayList<>();


}
