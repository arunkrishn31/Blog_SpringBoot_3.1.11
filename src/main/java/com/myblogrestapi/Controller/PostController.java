package com.myblogrestapi.Controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myblogrestapi.Entity.PostDTO;
import com.myblogrestapi.Exception.ResourceNotFoundException;
import com.myblogrestapi.Service.PostService;

@RestController
@RequestMapping(path="/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/hello")
	public String sayhi() {
		return "Hey Kid..!";
	}
	
	@PostMapping("/newpost")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PostDTO>createPost(@Valid @RequestBody PostDTO postDto){
		PostDTO result=postService.createNewpost(postDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@GetMapping("/allposts")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<PostDTO>>getAllPosts(){
		List<PostDTO>res=postService.getAllPosts();
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO>getPostById(@PathVariable("id") Long id){
		PostDTO result=postService.getPostById(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO>updatePostById(@PathVariable("id")Long id, @Valid @RequestBody PostDTO postDto){
		PostDTO result=postService.updatePostById(id,postDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable("id") Long id) throws ResourceNotFoundException{
		postService.deletePostById(id);
		return  new ResponseEntity<>("Post deleted Successfully",HttpStatus.NO_CONTENT);
	}
	
	
	//http://localhost:8081/api/post/pagenation?pageNo=2&pageSize=10&sortBy=title&sortDir=desc
	
	@GetMapping("/pagenation")
	public List<PostDTO> getAllPost(@RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
							@RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
							@RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
							@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir) {
		return postService.getAllPosts1(pageNo, pageSize, sortBy, sortDir);
	}
}
