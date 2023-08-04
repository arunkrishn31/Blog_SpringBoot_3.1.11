package com.myblogrestapi.Cofig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlogConfig {

	
	@Bean
	public ModelMapper model() {
		return new ModelMapper();
	}
}
