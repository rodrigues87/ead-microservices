package com.ead.authuser.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/logs")
public class LogController {

    // Logger logger = LoggerFactory.getLogger(this.getClass());

    //LOG4J2
    // Logger logger = LogManager.getLogger(this.getClass());

    @GetMapping()
    public String log(){

        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARNING");
        log.error("ERROR");

        return "log do sistema";
    }
}
