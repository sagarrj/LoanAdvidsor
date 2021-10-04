package com.finance.loanadvisor.security.user;

import com.finance.loanadvisor.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * This controller class contains REST api methods of {@link User} entity.
 *
 */
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
    
    
    /**
     * This method accepts and saves user details and return saved user object.
     * @param user : {@link User}
     * @return : {@link User}
     */
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
