package com.vaibhavtodkar.stockservice.service;

import java.util.List;

import com.vaibhavtodkar.stockservice.entity.StockQuoteInfo;


public interface PolygonService {
	public List<StockQuoteInfo> getStockInfoList(List<String> symbols);
	public StockQuoteInfo getStockInfo(String symbol);
	
}
