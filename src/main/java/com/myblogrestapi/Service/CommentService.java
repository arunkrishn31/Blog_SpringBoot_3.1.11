package com.myblogrestapi.Service;

import com.myblogrestapi.Exception.BlogAPIException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.myblogrestapi.Entity.Comment;
import com.myblogrestapi.Entity.CommentDTO;
import com.myblogrestapi.Entity.Post;
import com.myblogrestapi.Exception.ResourceNotFoundException;
import com.myblogrestapi.Repository.CommentRepository;
import com.myblogrestapi.Repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepo;
	@Autowired
	private PostRepository postRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	public CommentDTO createComment(Long postId, CommentDTO commentDto) {

		//retrieve post by entity id
		Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));

		Comment comment = modelMapper.map(commentDto, Comment.class);

		//set post to comment entity
		comment.setPost(post);
		//comment entity to DB
		Comment newComment = commentRepo.save(comment);
		return modelMapper.map(newComment, CommentDTO.class);
	}

	public List<CommentDTO> getALlCommentsByPostId(Long postId) {
//		Post post = postRepo.findById(postId).orElseThrow(
//				() -> new ResourceNotFoundException("post","id",postId));
		//retrieve comment by postId
		List<Comment> comments = commentRepo.findByPostId(postId);

		//convert List of comments into commentsDto
		return comments.stream().map((element) -> modelMapper.map(element, CommentDTO.class))
				.collect(Collectors.toList());
	}

	public CommentDTO getCommentById(Long postId, Long commentId){
		//retrieve post entity by id
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		//retrieve comment by id
		Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		if(!comment.getPost().getId().equals(post.getId())){
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment doesn't belongs to this Post");
		}
		return modelMapper.map(comment,CommentDTO.class);
	}

	public CommentDTO updateComment_getCommentById(Long postId, Long commentId, CommentDTO commentDTO) {
		//retrieve post entity by id
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		//retrieve comment by id
		Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		if(!comment.getPost().getId().equals(post.getId())){
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment doesn't belongs to this Post");
		}

		comment.setName(commentDTO.getName());
		comment.setBody(commentDTO.getBody());
		comment.setEmail(commentDTO.getEmail());

		Comment updatedComment = commentRepo.save(comment);
		return  modelMapper.map(updatedComment, CommentDTO.class);

	}

	public void deleteCommentById(Long postId, Long commentId) {
		//retrieve post entity by id
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

		//retrieve comment by id
		Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

		if(!comment.getPost().getId().equals(post.getId())){
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment doesn't belongs to this Post");
		}

		commentRepo.deleteById(commentId);
	}

}
