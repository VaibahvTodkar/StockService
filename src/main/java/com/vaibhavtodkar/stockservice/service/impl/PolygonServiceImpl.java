package com.vaibhavtodkar.stockservice.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vaibhavtodkar.stockservice.entity.StockQuoteInfo;
import com.vaibhavtodkar.stockservice.service.PolygonService;

@Service
public class PolygonServiceImpl implements PolygonService {
	
	
	@Value("${API_KEY}")
    private  String API_KEY ;
	@Value("${BASE_URL}")
    private  String BASE_URL;
//	 = "https://api.polygon.io"
//			 = "n8zj5NpH8U1KLQGYmJt4b3SMDkJRv9py"
    private final RestTemplate restTemplate = new RestTemplate();
    
    
    @Override
    public List<StockQuoteInfo> getStockInfoList(List<String> symbols) {
    	System.out.println("Method data get"+ symbols);
        List<StockQuoteInfo> stockInfoList = new ArrayList<>();

        for (String symbol : symbols) {
            try {
            	StockQuoteInfo info = getStockInfo(symbol);
                if (info != null) {
                    stockInfoList.add(info);
                }
            } catch (Exception e) {
                // Optionally log or handle per-symbol errors
                System.err.println("Failed to fetch for symbol: " + symbol + " Error: " + e.getMessage());
            }
        }
        return stockInfoList;
    }

    @Override
    public StockQuoteInfo getStockInfo(String symbol) {
//    	System.out.println("get stock Info method");
//         1. Get Ticker Details
        String metaUrl = UriComponentsBuilder
                .fromHttpUrl(BASE_URL + "/v3/reference/tickers/" + symbol)
                .queryParam("apiKey", API_KEY)
                .toUriString();

        JSONObject metaResponse = new JSONObject(restTemplate.getForObject(metaUrl, String.class));
        if (!metaResponse.has("results")) return null;

        JSONObject meta = metaResponse.getJSONObject("results");

        String name = meta.optString("name", "");
        String desc = meta.optString("description", "");
        String type = meta.optString("type", "Stock");

        // 2. Get Previous Close Info
        String priceUrl = UriComponentsBuilder
                .fromHttpUrl(BASE_URL + "/v2/aggs/ticker/" + symbol + "/prev")
                .queryParam("adjusted", "true")
                .queryParam("apiKey", API_KEY)
                .toUriString();

        JSONObject priceResponse = new JSONObject(restTemplate.getForObject(priceUrl, String.class));
        if (!priceResponse.has("results")) return null;

        JSONArray results = priceResponse.getJSONArray("results");
        if (results.isEmpty()) return null;

        JSONObject data = results.getJSONObject(0);

        double close = data.getDouble("c");
        double open = data.getDouble("o");
        double change = close - open;
        double percentChange = (change / open) * 100;
        long timestamp = data.getLong("t");

        return new StockQuoteInfo(
                symbol,
                type,	
                name,
                desc,
                close,
                change,
                percentChange,
                Instant.ofEpochMilli(timestamp).toString()
        );

    }
}
