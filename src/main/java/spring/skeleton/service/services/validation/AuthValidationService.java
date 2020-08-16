package spring.skeleton.service.services.validation;

import spring.skeleton.service.models.auth.RegisterUserServiceModel;

public interface AuthValidationService {
    boolean isValid(RegisterUserServiceModel model);
}
