package com.kea.shipsandsails.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    @GetMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/scenario12")
    public String scenario12()
    {
        return "scenario_12_12";
    }
}
