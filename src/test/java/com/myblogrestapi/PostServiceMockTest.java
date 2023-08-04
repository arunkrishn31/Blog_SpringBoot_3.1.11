package com.myblogrestapi;

import com.myblogrestapi.Entity.Post;
import com.myblogrestapi.Entity.PostDTO;
import com.myblogrestapi.Exception.ResourceNotFoundException;
import com.myblogrestapi.Repository.PostRepository;
import com.myblogrestapi.Service.PostService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PostServiceMockTest.class)
public class PostServiceMockTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostService postService;
    @Test
    @Order(1)
    public void test_getAllPosts(){
        Post post1 = new Post(1L, "Title 1", "Description 1", "Content 1", new ArrayList<>());
        Post post2 = new Post(2L, "Title 2", "Description 2", "Content 2", new ArrayList<>());
        List<Post> posts = Arrays.asList(post1, post2);

        PostDTO postDTO1 = new PostDTO(1L, "Title 1", "Description 1", "Content 1",new ArrayList<>());
        PostDTO postDTO2 = new PostDTO(2L, "Title 2", "Description 2", "Content 2",new ArrayList<>());
        List<PostDTO> expectedPostDTOs = Arrays.asList(postDTO1, postDTO2);

        when(postRepository.findAll()).thenReturn(posts);//Mocking
        assertEquals(2,postService.getAllPosts().size());

        //or

        when(modelMapper.map(post1, PostDTO.class)).thenReturn(postDTO1);
        when(modelMapper.map(post2, PostDTO.class)).thenReturn(postDTO2);

        // when
        List<PostDTO> actualPostDTOs = postService.getAllPosts();

        // then
        assertEquals(expectedPostDTOs, actualPostDTOs);
    }
    @Test
    @Order(2)
    void getPostByIdTest() {
        // given
        Long id = 1L;
        Post post = new Post(id, "Title", "Description", "Content", new ArrayList<>());
        PostDTO expectedPostDTO = new PostDTO(id, "Title", "Description", "Content", new ArrayList<>());

        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        when(modelMapper.map(post, PostDTO.class)).thenReturn(expectedPostDTO);

        // when
        PostDTO actualPostDTO = postService.getPostById(id);

        // then
        assertEquals(expectedPostDTO, actualPostDTO);
    }

    @Test
    void getPostByIdNotFoundTest() {
        // given
        Long id = 1L;
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(id));

        // then
        assertEquals("post not found with id : '1'", exception.getMessage());
    }


    @Test
    void createNewpostTest() {
        // given
        PostDTO postDTO = new PostDTO(null, "Title", "Description", "Content", new ArrayList<>());
        Post post = new Post(null, "Title", "Description", "Content", new ArrayList<>());
        Post savedPost = new Post(1L, "Title", "Description", "Content", new ArrayList<>());
        PostDTO expectedPostDTO = new PostDTO(1L, "Title", "Description", "Content", new ArrayList<>());

        when(modelMapper.map(postDTO, Post.class)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(savedPost);
        when(modelMapper.map(savedPost, PostDTO.class)).thenReturn(expectedPostDTO);

        // when
        PostDTO actualPostDTO = postService.createNewpost(postDTO);

        // then
        assertEquals(expectedPostDTO, actualPostDTO);
    }

    @Test
    void updatePostByIdTest() {
        // given
        Long id = 1L;
        PostDTO postDTO = new PostDTO(id, "Updated Title", "Updated Description", "Updated Content", new ArrayList<>());
        Post post = new Post(id, "Title", "Description", "Content", new ArrayList<>());
        Post updatedPost = new Post(id, "Updated Title", "Updated Description", "Updated Content", new ArrayList<>());
        PostDTO expectedPostDTO = new PostDTO(id, "Updated Title", "Updated Description", "Updated Content", new ArrayList<>());

        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        when(postRepository.save(post)).thenReturn(updatedPost);
        when(modelMapper.map(updatedPost, PostDTO.class)).thenReturn(expectedPostDTO);

        // when
        PostDTO actualPostDTO = postService.updatePostById(id, postDTO);

        // then
        assertEquals(expectedPostDTO, actualPostDTO);
    }

    @Test
    void updatePostByIdNotFoundTest() {
        // given
        Long id = 1L;
        PostDTO postDTO = new PostDTO(id, "Updated Title", "Updated Description", "Updated Content", new ArrayList<>());
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.updatePostById(id, postDTO));

        // then
        assertEquals("post not found with id : '1'", exception.getMessage());
    }

    @Test
    void deletePostByIdTest() {
        // given
        Long id = 1L;
        Post post = new Post(id, "Title", "Description", "Content", new ArrayList<>());

        when(postRepository.findById(id)).thenReturn(Optional.of(post));

        // when
        postService.deletePostById(id);

        // then
        verify(postRepository).deleteById(id);
    }

    @Test
    void deletePostByIdNotFoundTest() {
        // given
        Long id = 1L;
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> postService.deletePostById(id));

        // then
        assertEquals("post not found with id : '1'", exception.getMessage());
    }
}

