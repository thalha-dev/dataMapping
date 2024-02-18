package com.learn.dataMapping;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataMappingApplication {

  public static void main(String[] args) {
    SpringApplication.run(DataMappingApplication.class, args);
  }

  @Bean
  public ModelMapper getModelMapper() {
    return new ModelMapper();
  }

}
