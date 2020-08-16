package spring.skeleton.service.services.implementations;

import spring.skeleton.service.services.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashingServiceImpl implements HashingService {

    private final PasswordEncoder passwordEncoder;

    public HashingServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String text) {
        return passwordEncoder.encode(text);
    }
}
