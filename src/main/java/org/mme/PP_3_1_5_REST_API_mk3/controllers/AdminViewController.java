package org.mme.PP_3_1_5_REST_API_mk3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {

    @GetMapping("/admin")
    public String showAdminPage(){
        return "user-list";
    }
}