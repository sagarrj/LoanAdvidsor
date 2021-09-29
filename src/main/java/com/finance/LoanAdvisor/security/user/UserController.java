package com.finance.LoanAdvisor.security.user;

import com.finance.LoanAdvisor.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController
{

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService= userService;

    }
    @GetMapping("/all")
    public void getUsers(){

    }

    @GetMapping("/{id}")
    public void getUserById(@PathVariable String id){

    }


    @PostMapping(value = "/add",produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user){
        User u = userService.createUser(user);
        return u;
    }

    @PostMapping("/update/{id}")
    public void updateUser(@PathVariable String id){

    }

    @GetMapping("/de-activate/{id}")
    public void deActivateUser(@PathVariable String id){

    }



}
