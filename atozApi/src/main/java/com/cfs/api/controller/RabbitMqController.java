package com.cfs.api.controller;

import com.cfs.core.objects.LoanRequest;
import com.cfs.rabbitservice.IRabbitMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author chopra
 * 18/01/18
 */
@RestController
public class RabbitMqController {

    @Autowired
    private IRabbitMqProducer producer;

    @RequestMapping(value = "/v1/rabbitmq/send", method = RequestMethod.GET)
    public ResponseEntity sendMessage(@RequestParam(value = "message", required = true) String message,
                                      @Context HttpServletRequest request){
        producer.createMessage(message);
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/v1/reabbitmq/receive", method = RequestMethod.GET)
    public ResponseEntity receiveMessafe(@RequestBody LoanRequest loanRequest,
                                         @Context HttpServletRequest request){
        return null;
    }
}
