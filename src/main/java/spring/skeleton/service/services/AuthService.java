package spring.skeleton.service.services;

import spring.skeleton.service.models.auth.RegisterUserServiceModel;

public interface AuthService {
    void register(RegisterUserServiceModel model);
}
