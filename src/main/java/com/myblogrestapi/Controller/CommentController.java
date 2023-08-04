package com.myblogrestapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.myblogrestapi.Entity.CommentDTO;
import com.myblogrestapi.Service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;


	@GetMapping("/welcome")
	public String wecome(){
		return "Welcome to application";
	}

	@PostMapping("/posts/{postId}/comments")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<CommentDTO>createComment(@PathVariable("postId")Long postId,
												   @RequestBody CommentDTO commentDto){
		CommentDTO comment = commentService.createComment(postId, commentDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(comment);
	}

	@GetMapping("/posts/{postId}/comments")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<CommentDTO>>getAllComments(@PathVariable("postId")Long postId){
		List<CommentDTO> comments = commentService.getALlCommentsByPostId(postId);
		return ResponseEntity.status(HttpStatus.CREATED).body(comments);
	}

	@GetMapping("/posts/{postId}/comments/{commentId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<CommentDTO>getCommentById(@PathVariable ("postId")Long postId, @PathVariable ("commentId")Long commentId){
		CommentDTO commentById = commentService.getCommentById(postId, commentId);
		return  ResponseEntity.status(HttpStatus.OK).body(commentById);
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO>updateCommentgetCommentById(@PathVariable ("postId")Long postId,
																 @PathVariable ("commentId")Long commentId,
																 @RequestBody CommentDTO commentDTO){
		CommentDTO updated_commentById = commentService.updateComment_getCommentById(postId, commentId, commentDTO);
		return  ResponseEntity.status(HttpStatus.OK).body(updated_commentById);
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String>deleteCommentbyusingId(@PathVariable ("postId") Long postId,
													  @PathVariable("commentId") Long commentId){
		commentService.deleteCommentById(postId,commentId);
		return  new ResponseEntity<>("Comment deleted Successfully",HttpStatus.NO_CONTENT);
	}
}
