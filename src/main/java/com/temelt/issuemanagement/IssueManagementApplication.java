package com.temelt.issuemanagement;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class IssueManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(IssueManagementApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().
                setMatchingStrategy(MatchingStrategies.STRICT);

        return new ModelMapper();
    }
/*
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("project.json")});
        return factory;
    }*/
}
