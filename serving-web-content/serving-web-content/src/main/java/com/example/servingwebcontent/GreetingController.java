package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {
    //@GetMapping("/greeting?name=Parro")
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";// busca la pagina que tiene este nombre. Se encuentra en la ruta src/main/resources/templates/greeting.html
    }

    @RequestMapping("/hello")
    //@ResponseBody
    private String hello() {
        return "Hello World juan";
    }

    @RequestMapping("/user/{id}")
    //@ResponseBody
    private ModelAndView getUser(@PathVariable Integer id, Model model) {
        //String uri = "https://jsonplaceholder.typicode.com/users/1";
        String uri = "https://jsonplaceholder.typicode.com/users/"+ id;
        RestTemplate restTemplate = new RestTemplate();

        User user = restTemplate.getForObject(uri, User.class);

        //---
        Address address2 = user.getAddress();
        Geo geo2 = address2.getGeo();
        Company company2 = user.getCompany();

        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", user);
        modelAndView.addObject("address", address2);
        modelAndView.addObject("geo", geo2);
        modelAndView.addObject("company", company2);
        //---

        System.out.println("User: " + user);

        System.out.println("Userid: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());

        Address address = user.getAddress();
        System.out.println("Address: "
                + address.getStreet() + ", "
                + address.getCity() + ", "
                + address.getZipcode()
        );

        Geo geo = address.getGeo();
        System.out.println("Geo Lat: "
                + geo.getLat() + ", Geo Lng: "
                + geo.getLng()
        );

        Company company = user.getCompany();
        System.out.println("Company: "
                + company.getName() + ", "
                + company.getCatchPhrase() + ", "
                + company.getBs()
        );

        //return "User detail page.";
        return modelAndView;
    }

    @RequestMapping("/delectus")
    //@ResponseBody
    private ModelAndView getDelectus(Model model) {
        String uri = "https://jsonplaceholder.typicode.com/todos/1";
        RestTemplate restTemplate = new RestTemplate();

        delectus myDelectus  = restTemplate.getForObject(uri, delectus.class);

        //---
        ModelAndView modelAndView = new ModelAndView("delectus");
        modelAndView.addObject("myDelectus", myDelectus);
        //---

        System.out.println("User: " + myDelectus);

        System.out.println("Userid: " + myDelectus.getUserId());
        System.out.println("id: " + myDelectus.getId());
        System.out.println("title: " + myDelectus.getTitle());
        System.out.println("completed: " + myDelectus.getCompleted());

        //return "User detail page.";
        return modelAndView;
    }
}
