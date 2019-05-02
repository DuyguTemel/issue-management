package com.temelt.issuemanagement.service.impl;

import com.temelt.issuemanagement.dto.ProjectDto;
import com.temelt.issuemanagement.dto.UserDto;
import com.temelt.issuemanagement.entity.Project;
import com.temelt.issuemanagement.entity.User;
import com.temelt.issuemanagement.repository.UserRepository;
import com.temelt.issuemanagement.service.UserService;
import com.temelt.issuemanagement.util.TPage;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto save(UserDto user) {
        User u = modelMapper.map(user, User.class);
        u = userRepository.save(u);
        user.setId(u.getId());
        return user;
    }

    @Override
    public UserDto getById(Long id) {
        User u = userRepository.getOne(id);
        return modelMapper.map(u, UserDto.class);
    }

    @Override
    public TPage<UserDto> getAllPageable(Pageable pageable) {
        Page<User> data = userRepository.findAll(pageable);
        TPage<UserDto> respnose = new TPage<UserDto>();
        respnose.setStat(data, Arrays.asList(modelMapper.map(data.getContent(), UserDto[].class)));
        return respnose;
    }

    public List<UserDto> getAll() {
        List<User> data = userRepository.findAll();
        return Arrays.asList(modelMapper.map(data, UserDto[].class));
    }


    @Override
    public UserDto getByUsername(String username) {
        User u = userRepository.findByUsername(username);
        return modelMapper.map(u, UserDto.class);
    }

//    @Transactional
//    public Boolean register(RegistrationRequest registrationRequest) {
//        try {
//            User user = new User();
//            user.setEmail(registrationRequest.getEmail());
//            user.setNameSurname(registrationRequest.getNameSurname());
//            user.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPassword()));
//            user.setUsername(registrationRequest.getUsername());
//            userRepository.save(user);
//            return Boolean.TRUE;
//        } catch (Exception e) {
//            log.error("REGISTRATION=>", e);
//            return Boolean.FALSE;
//        }
//    }
}