package com.rayane.minifast;

import com.rayane.minifast.marketdata.MarketDataAssumptions;
import com.rayane.minifast.marketdata.MarketDataParquet;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        MarketDataAssumptions assumptions = new MarketDataAssumptions(0.003, 0.0);

        MarketDataParquet source = new MarketDataParquet(
            "backend/data/spy_eod_2010.parquet",
            assumptions
        );

        source.load(LocalDate.of(2010, 1, 4));
    }
}