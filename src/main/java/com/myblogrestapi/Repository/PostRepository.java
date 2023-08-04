package com.myblogrestapi.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myblogrestapi.Entity.Post;
import com.myblogrestapi.Entity.PostDTO;

@Repository
public interface PostRepository extends JpaRepository<Post , Long>{

	Post save(PostDTO postDto);

}
