package com.learncodetogether.ipldashboard.controller;

import com.learncodetogether.ipldashboard.exceptions.UserNotFoundException;
import com.learncodetogether.ipldashboard.model.User;
import com.learncodetogether.ipldashboard.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserServiceInterface userServiceInterface;

    // get all users
    @GetMapping()
    public List<User> getAllUsers(){
        return userServiceInterface.findAll();
    }

    // get specific user
    @GetMapping("/{id}")
    public EntityModel<User> getUserDetail(@PathVariable Integer id){
        User user = userServiceInterface.findOne(id);
        if(user == null){
            throw new UserNotFoundException("User not found for id - " + id);
        }

//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "dateOfBirth");
//
//        FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
//        mappingJacksonValue.setFilters(filters);

        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).getAllUsers());
        EntityModel entityModel = EntityModel.of(user).add(webMvcLinkBuilder.withRel("users"));
        return entityModel;
    }

    // save new user
    @PostMapping
    public ResponseEntity<EntityModel<Object>> saveUser(@Valid @RequestBody User user){
        userServiceInterface.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).getAllUsers());
        EntityModel entityModel = EntityModel.of(user).add(webMvcLinkBuilder.withRel("users"));

        return ResponseEntity.created(location).body(entityModel);
    }

    // delete specific user
    @DeleteMapping("/{id}")
    public EntityModel<User> deleteUser(@PathVariable Integer id){
        User user = userServiceInterface.deleteById(id);
        if(user == null){
            throw new UserNotFoundException("User not found for id - " + id);
        }

        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).getAllUsers());
        EntityModel entityModel = EntityModel.of(user).add(webMvcLinkBuilder.withRel("users"));
        return entityModel;
    }

    @GetMapping("/show")
    public String showInternationalizedMessage(){

           return messageSource.
                    getMessage(
                            "welcome.message",
                            null,
                            "Default Subject",
                            LocaleContextHolder.getLocale()
    );
    }
}
