package com.rayane.minifast.surface;

import java.time.LocalDate;

public class VolPoint {

    private final double strike;
    private final LocalDate maturity;
    private final double vol;

    public VolPoint(double strike, LocalDate maturity, double vol) {
        this.strike = strike;
        this.maturity = maturity;
        this.vol = vol;
    }

    public double getStrike() {
        return strike;
    }

    public LocalDate getMaturity() {
        return maturity;
    }

    public double getVol() {
        return vol;
    }

    @Override
    public String toString() {
        return "VolPoint{strike=" + strike + ", maturity=" + maturity + ", vol=" + vol + "}";
    }
}
