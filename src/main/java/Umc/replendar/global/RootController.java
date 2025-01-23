package Umc.replendar.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    // 빈스톡 health check endpoint
    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }
}