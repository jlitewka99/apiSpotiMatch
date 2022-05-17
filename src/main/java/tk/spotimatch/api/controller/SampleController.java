package tk.spotimatch.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class SampleController {

    @GetMapping("/")
    public String index() {
        return "hello";
    }
}
