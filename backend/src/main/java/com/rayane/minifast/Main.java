package com.rayane.minifast;

import com.rayane.minifast.marketdata.MarketData;
import com.rayane.minifast.marketdata.MarketDataAssumptions;
import com.rayane.minifast.marketdata.MarketDataParquet;
import com.rayane.minifast.surface.ImpliedVolSurface;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IOException { 
        MarketDataAssumptions assumptions = new MarketDataAssumptions(0.003, 0.0);

        MarketDataParquet source = new MarketDataParquet(
            "data/spy_eod_2010.parquet",
            assumptions
        );

        MarketData md = source.load(LocalDate.of(2010, 1, 4));

        System.out.println("Spot: " + md.getSpot());
        ImpliedVolSurface surface = md.getImpliedVolSurface();
        ImpliedVolSurface.writeToFile(surface.toCsv(), "data/surface_2010-01-04.csv");
    }
}