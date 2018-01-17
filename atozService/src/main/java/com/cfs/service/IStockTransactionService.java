package com.cfs.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author chopra
 * 10/01/18
 */
public interface IStockTransactionService {
    SseEmitter getStockTransactions();
}
