package com.rayane.minifast.marketdata;

import java.time.LocalDate;

public class MarketDataParquet implements MarketDataSource {
    
    private final String filePath;
    private final MarketDataAssumptions assumptions;

    // Constructor
    public MarketDataParquet(String filePath, MarketDataAssumptions assumptions ){
            this.filePath = filePath;
            this.assumptions = assumptions;
        }
        
    public MarketData load(LocalDate date){
        return null;
    }
}

