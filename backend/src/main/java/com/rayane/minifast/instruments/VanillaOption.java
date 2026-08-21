package com.rayane.minifast.instruments;

import java.time.LocalDate;

public class VanillaOption implements Instrument {

    private double strike; 
    private LocalDate maturity;
    private boolean isCall;
    private final String underlying;
    private final String id;

    public VanillaOption(
        double strike, 
        LocalDate maturity, 
        boolean isCall, 
        String underlying,
        String id)
        {
            this.strike = strike;
            this.maturity = maturity;
            this.isCall = isCall;
            this.underlying = underlying;
            this.id = id;
        }

    public LocalDate getMaturity(){
        return this.maturity;
    }

    public String getUnderlying(){
        return this.underlying;
    }

    public String getId(){
        return this.id;
    }

    public double getStrike(){
        return this.strike;
    }

    public boolean isCall(){
        return this.isCall;
    }
    

}
