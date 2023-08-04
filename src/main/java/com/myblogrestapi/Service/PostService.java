package com.myblogrestapi.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myblogrestapi.Entity.Post;
import com.myblogrestapi.Entity.PostDTO;
import com.myblogrestapi.Exception.ResourceNotFoundException;
import com.myblogrestapi.Repository.PostRepository;


@Service
@Profile(value = {"local","dev","prod"})
public class PostService {
	@Autowired
	private PostRepository postRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	
	public PostDTO createNewpost(PostDTO postDTO) {
				Post post = modelMapper.map(postDTO,Post.class);
				Post savedpost = postRepo.save(post);
				return modelMapper.map(savedpost,PostDTO.class);
		
	}

	 public List<PostDTO> getAllPosts() {
	        return postRepo.findAll()
	                .stream()
	                .map(post -> modelMapper.map(post, PostDTO.class))
	                .collect(Collectors.toList());
	    }

	public PostDTO getPostById(Long id) {
			Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
			return modelMapper.map(post,PostDTO.class);
	}


	
	 public PostDTO updatePostById(Long id, PostDTO postDto) {
	        Post post = postRepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
	       // modelMapper.map(postDto, Post.class);
	        post.setTitle(postDto.getTitle());
	        post.setContent(postDto.getContent());
	        post.setDescription(postDto.getDescription());

	        Post updatedPost = postRepo.save(post);
	        return modelMapper.map(updatedPost, PostDTO.class);
	    }

	public void deletePostById(Long id) {
		postRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		postRepo.deleteById(id);
	}

	
	public List<PostDTO> getAllPosts1(int pageNo, int pageSize, String sortBy, String sortDir) {
	    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

	    // creating pageable instance
	    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

	    Page<Post> listOfPosts = postRepo.findAll(pageable);

	    // get content for page object
	    List<Post> posts = listOfPosts.getContent();
	    List<PostDTO> postDto = posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	    return postDto;
	}


	

}
