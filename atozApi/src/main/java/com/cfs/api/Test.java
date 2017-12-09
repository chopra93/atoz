package com.cfs.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author chopra
 * 12/11/17
 */
@RestController
public class Test {
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity test(){
        return ResponseEntity.ok("ok");
    }
}
