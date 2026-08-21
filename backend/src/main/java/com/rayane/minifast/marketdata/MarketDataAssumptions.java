package com.rayane.minifast.marketdata;

public class MarketDataAssumptions {

    private final double riskFreeRate;
    private final double dividendYield;

    public MarketDataAssumptions(double riskFreeRate, double dividendYield) {
        this.riskFreeRate = riskFreeRate;
        this.dividendYield = dividendYield;
    }

    public double getRiskFreeRate() {
        return riskFreeRate;
    }

    public double getDividendYield() {
        return dividendYield;
    }
}