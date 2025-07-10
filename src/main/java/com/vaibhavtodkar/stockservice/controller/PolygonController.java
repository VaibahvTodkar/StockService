package com.vaibhavtodkar.stockservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaibhavtodkar.stockservice.entity.StockQuoteInfo;
import com.vaibhavtodkar.stockservice.service.PolygonService;

@RestController
@RequestMapping("/api/stocks")
public class PolygonController {

    @Autowired
    private PolygonService polygonService;

    @GetMapping("/{symbol}")
    public StockQuoteInfo getStockData(@PathVariable String symbol) {
    	System.out.println("get stock data method controller" + symbol);
        return polygonService.getStockInfo(symbol.toUpperCase());
    }
        
    @PostMapping("/batch")
    public List<StockQuoteInfo> getStocksBatch(@RequestBody List<String> symbols) {
    	System.out.println(symbols);
        return polygonService.getStockInfoList(symbols);
    }
}

