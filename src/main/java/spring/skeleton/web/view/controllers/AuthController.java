package spring.skeleton.web.view.controllers;

import spring.skeleton.service.models.auth.RegisterUserServiceModel;
import spring.skeleton.service.services.AuthService;
import spring.skeleton.service.services.SecurityService;
import spring.skeleton.service.services.validation.AuthValidationService;
import spring.skeleton.web.view.controllers.base.BaseController;
import spring.skeleton.web.view.models.RegisterUserViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AuthController extends BaseController {
    private final AuthService authService;
    private final SecurityService securityService;
    private final AuthValidationService authValidationService;
    private final ModelMapper mapper;

    public AuthController(AuthService authService, SecurityService securityService, AuthValidationService authValidationService, ModelMapper mapper) {
        this.authService = authService;
        this.securityService = securityService;
        this.authValidationService = authValidationService;
        this.mapper = mapper;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {

        if (!isUserAnonymous()) {
            return "redirect:/";
        }

        if (error != null && isUserAnonymous()) {
            model.addAttribute("loginError", "Invalid username or password");
        }

        if (logout != null && isUserAnonymous()) {
            model.addAttribute("logoutMessage", "You have been logged out successfully");
        }

        return "auth/login.html";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterForm(ModelAndView modelAndView) {
        if (!isUserAnonymous()) {
            return new ModelAndView("redirect:/");
        }
        modelAndView.addObject("model", new RegisterUserViewModel());
        modelAndView.setViewName("auth/register.html");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute RegisterUserViewModel model, ModelAndView modelAndView) {
        RegisterUserServiceModel serviceModel = mapper.map(model, RegisterUserServiceModel.class);

        if (!authValidationService.isValid(serviceModel)) {
            modelAndView.addObject("errorMessage", "Username is taken");
            modelAndView.addObject("model", new RegisterUserViewModel());
            modelAndView.setViewName("auth/register.html");
        } else {
            authService.register(serviceModel);
            securityService.autoLogin(model.getUsername(), model.getPassword());
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }
}
