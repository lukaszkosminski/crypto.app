package com.cryptoapp.controller;

import com.cryptoapp.dto.UserDTO;
import com.cryptoapp.dto.mapper.UserMapper;
import com.cryptoapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    private final UserRepo userRepo;
    @Autowired
    public IndexController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String getIndex() {
       return "index";
    }


    @GetMapping("/api/public/top20")
    public String getTop1000() {
        return "top20";
    }

    @GetMapping("/api/public/register")
    public String getRegister(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/api/public/register")
    public String registerUserAccount(@ModelAttribute("user")  UserDTO userDTO,
                                      BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        userRepo.save(UserMapper.mapToUser(userDTO));

        return "redirect:/login";
    }
    @GetMapping("/api/public/example")
    public String getExample() {
        return "example";
    }
}


