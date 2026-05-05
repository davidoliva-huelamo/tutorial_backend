package com.ccsw.tutorial.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author David Oliva Huelamo
 *
 */
@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper getModelMapper() {

    return new ModelMapper();
  }

}
