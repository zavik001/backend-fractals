package backend.academy.fractals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FractalController {
    @GetMapping("/")
    public String home() {
        return "Welcome to the Fractals Generator!";
    }
}
