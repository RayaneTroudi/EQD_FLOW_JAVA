package com.rayane.minifast.marketdata;

import java.time.LocalDate;

public class MarketData {

    private final LocalDate asOf;
    private final double spot;
    private final MarketDataAssumptions assumptions;
    private final ImpliedVolSurface impliedVolSurface;

    public MarketData(LocalDate asOf, double spot, MarketDataAssumptions assumptions,
                       ImpliedVolSurface impliedVolSurface) {
        this.asOf = asOf;
        this.spot = spot;
        this.assumptions = assumptions;
        this.impliedVolSurface = impliedVolSurface;
    }

    public LocalDate getAsOf() {
        return asOf;
    }

    public double getSpot() {
        return spot;
    }

    public MarketDataAssumptions getAssumptions() {
        return assumptions;
    }

    public ImpliedVolSurface getImpliedVolSurface() {
        return impliedVolSurface;
    }
}