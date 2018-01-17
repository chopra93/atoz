package com.cfs.api.controller;

import com.cfs.service.IStockTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chopra
 * 10/01/18
 */
@RestController
public class StockController {

    @Autowired
    private IStockTransactionService stockTransactionService;

    private final SseEmitter emitter = new SseEmitter();

    @RequestMapping(value = "/v1/stock", method = RequestMethod.GET)
    private SseEmitter getInfiniteStock(@Context HttpServletRequest request){
        //return stockTransactionService.getStockTransactions();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    emitter.send(LocalTime.now().toString() , MediaType.TEXT_PLAIN);
                    Thread.sleep(200);
                } catch (Exception e) {
                    emitter.completeWithError(e);
                    return;
                }
            }
            emitter.complete();
        });
        return emitter;
    }

    @RequestMapping(value = "/v1/stock/{count}", method = RequestMethod.GET)
    private ResponseEntity getFiniteStock(@PathVariable("count") int count, @Context HttpServletRequest request){
        return null;
    }


}
