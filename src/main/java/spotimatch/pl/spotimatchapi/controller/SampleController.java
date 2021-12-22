package spotimatch.pl.spotimatchapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/test")
    public String something() {
        return "something";
    }
}
