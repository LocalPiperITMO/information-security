package itmo.localpiper.iblab1.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import itmo.localpiper.iblab1.repo.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/data")
    public List<?> getData(Authentication auth) {
        // jwt validation already done by filter
        return userRepository.findAll().stream()
                .map(u -> Map.of("id", u.getId(), "username", u.getLogin()))
                .collect(Collectors.toList());
    }
}

