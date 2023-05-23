package dev.webservice_admin.controller;

import dev.webservice_admin.model.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public String dashboardPage(@AuthenticationPrincipal CustomUserDetails userDetails) {

//        String role = userDetails.getRole();
//
//        System.out.println(role);

        return "dashboard";
    }

}
