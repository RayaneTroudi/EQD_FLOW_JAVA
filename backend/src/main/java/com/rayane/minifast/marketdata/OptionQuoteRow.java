package com.rayane.minifast.marketdata;

import java.time.LocalDate;

public class OptionQuoteRow {

    private final double underlyingLast;
    private final double strike;
    private final LocalDate expireDate;
    private final double callIV;
    private final double putIV;
    private final double callBid;
    private final double callAsk;
    private final double putBid;
    private final double putAsk;
    private final double callVolume;
    private final double putVolume;

    public OptionQuoteRow(
        double underlyingLast,
        double strike,
        LocalDate expireDate,
        double callIV,
        double putIV,
        double callBid,
        double callAsk,
        double putBid,
        double putAsk,
        double callVolume,
        double putVolume
    ) {
        this.underlyingLast = underlyingLast;
        this.strike = strike;
        this.expireDate = expireDate;
        this.callIV = callIV;
        this.putIV = putIV;
        this.callBid = callBid;
        this.callAsk = callAsk;
        this.putBid = putBid;
        this.putAsk = putAsk;
        this.callVolume = callVolume;
        this.putVolume = putVolume;
    }

    public double getUnderlyingLast() { return underlyingLast; }
    public double getStrike() { return strike; }
    public LocalDate getExpireDate() { return expireDate; }
    public double getCallIV() { return callIV; }
    public double getPutIV() { return putIV; }
    public double getCallBid() { return callBid; }
    public double getCallAsk() { return callAsk; }
    public double getPutBid() { return putBid; }
    public double getPutAsk() { return putAsk; }
    public double getCallVolume() { return callVolume; }
    public double getPutVolume() { return putVolume; }
}