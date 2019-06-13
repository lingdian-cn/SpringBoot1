package com.dling.springboot.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api")
public class ApiController {
//    private static final Logger slf4j = LoggerFactory.getLogger(ApiController.class);
    private static final Log logger = LogFactory.getLog(ApiController.class);


    @RequestMapping("/index")
    ModelAndView index(Model model){
//        slf4j.debug("index()方法");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.trace("trace");
        logger.error("error");
        logger.fatal("fatal");
        return new ModelAndView("/views/index.html");
    }
}
