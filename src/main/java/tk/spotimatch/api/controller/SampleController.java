package tk.spotimatch.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spotimatch.pl.spotimatchapi.model.TestModel;

import java.security.Principal;

@RestController
public class SampleController {

    @GetMapping("/something")
    public TestModel something() {
        return new TestModel("123", "abc");
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/test")
    public TestModel test(Principal principal) {
        return new TestModel("123", "abc");
    }

    @GetMapping("/")
    public String index() {
        return "hello";
    }
}
