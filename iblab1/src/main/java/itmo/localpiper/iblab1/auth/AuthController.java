package itmo.localpiper.iblab1.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itmo.localpiper.iblab1.dao.User;
import itmo.localpiper.iblab1.dto.AuthRequest;
import itmo.localpiper.iblab1.dto.AuthResponse;
import itmo.localpiper.iblab1.repo.UserRepository;
import itmo.localpiper.iblab1.service.JwtService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest request) {
        if (repo.findByLogin(request.getLogin()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "username_taken"));
        }
        String hashed = passwordEncoder.encode(request.getPassword());
        User user = new User(null, request.getLogin(), hashed);
        repo.save(user);
        return ResponseEntity.ok(Map.of("msg", "registered"));
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        return repo.findByLogin(request.getLogin())
        .map(user -> {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getLogin());
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                return ResponseEntity.status(401).body(Map.of("error", "invalid_credentials"));
            }
        })
        .orElse(ResponseEntity.status(401).body(Map.of("error", "invalid_credentials")));
    }
    
}
