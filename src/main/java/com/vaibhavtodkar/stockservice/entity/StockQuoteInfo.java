package com.vaibhavtodkar.stockservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockQuoteInfo {
	private String symbol;
    private String assetType;
    private String name;
    private String description;
    private double price;
    private double change;
    private double percentageChange;
    private String timestamp;
}
