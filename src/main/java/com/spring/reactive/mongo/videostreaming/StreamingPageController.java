package com.spring.reactive.mongo.videostreaming;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/video")
public class StreamingPageController {

    @GetMapping("/loading")
    public String getVideo() {
        return "marvel";
    }
}
