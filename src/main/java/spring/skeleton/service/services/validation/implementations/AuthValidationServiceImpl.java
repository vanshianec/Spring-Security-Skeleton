package spring.skeleton.service.services.validation.implementations;

import spring.skeleton.data.repositories.UserRepository;
import spring.skeleton.service.models.auth.RegisterUserServiceModel;
import spring.skeleton.service.services.validation.AuthValidationService;
import org.springframework.stereotype.Service;

@Service
public class AuthValidationServiceImpl implements AuthValidationService {

    private final UserRepository userRepository;

    public AuthValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(RegisterUserServiceModel model) {
        return isUsernameFree(model.getUsername());
    }

    private boolean isUsernameFree(String username) {
        return !userRepository.existsByUsername(username);
    }
}
