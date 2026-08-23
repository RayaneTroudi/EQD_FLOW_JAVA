package com.rayane.minifast.marketdata;

import java.time.LocalDate;

public class ImpliedVolSurface {

    private static final class VolPoint{
        final double strike;
        final LocalDate maturity; 
        final double vol;

        VolPoint(double strike, LocalDate maturity, double vol){
            this.strike = strike; 
            this.maturity = maturity;
            this.vol = vol;
        }
    }

    
}
