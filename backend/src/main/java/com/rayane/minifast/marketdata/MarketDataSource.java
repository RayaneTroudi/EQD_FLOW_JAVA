package com.rayane.minifast.marketdata;

import java.time.LocalDate;

public interface MarketDataSource {
    MarketData load(LocalDate date);
}