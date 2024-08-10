package com.Anikets.major.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Anikets.major.model.Role;
import com.Anikets.major.model.User;
import com.Anikets.major.repository.RoleRepository;
import com.Anikets.major.repository.UserRepository;

@Controller
public class LoginController {
   @Autowired
   UserRepository userRepository;
   
   @Autowired
   RoleRepository roleRepository;
   
   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;
   
   @GetMapping("/login")
   public String login() {
	   return "login";
   }
   
   @GetMapping("/register")
   public String registerGet() {
	   return "register";
   }
   @PostMapping("/register")
   public String registerPost(@ModelAttribute("user") User user,HttpServletRequest httpServletRequest)throws ServletException {
	   String password=user.getPassword();
	   user.setPassword(bCryptPasswordEncoder.encode(password));
	   List<Role> roles=new ArrayList<>();
	   roles.add(roleRepository.findById(1).get());
	   user.setRoles(roles);
	   userRepository.save(user);
	   httpServletRequest.login(user.getEmail(), password);
	   return "redirect:/";
	   
   }
}
