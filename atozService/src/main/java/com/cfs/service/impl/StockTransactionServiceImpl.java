package com.cfs.service.impl;

import com.cfs.core.objects.StockTransaction;
import com.cfs.service.IStockTransactionService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;

/**
 * @author chopra
 * 10/01/18
 */
@Service
public class StockTransactionServiceImpl  implements IStockTransactionService{

    private final SseEmitter emitter = new SseEmitter();

    @Override
    public SseEmitter getStockTransactions() {
        //timerHandler();
        return emitter;
    }

//    void timerHandler(){
//        StockTransaction stockTransaction = new StockTransaction();
//        stockTransaction.setName("test");
//        stockTransaction.setPrice((float) 1.2);
//        stockTransaction.setWhen(new Date());
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    emitter.send(stockTransaction, MediaType.APPLICATION_JSON);
//                } catch (IOException e) {
//                    emitter.completeWithError(e);
//                    return;
//                }
//                emitter.complete();
//            }
//        }).start();
//    }
}
