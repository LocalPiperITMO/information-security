package itmo.localpiper.iblab1.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itmo.localpiper.iblab1.repo.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itmo.localpiper.iblab1.dao.User;


@RestController
@RequestMapping("/api")
public class DataController {
    @Autowired
    private UserRepository repo;

    @GetMapping("/data")
    public List<User> getUsers(@RequestParam String param) {
        return repo.findAll();
    }
    
}
