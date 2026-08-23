package com.rayane.minifast.marketdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ImpliedVolSurface {

    private final LocalDate asOf;
    private List<VolPoint> points;

    private static final class VolPoint{
        final double strike;
        final LocalDate expireDate; 
        final double vol;

        VolPoint(double strike, LocalDate expireDate, double vol){
            this.strike = strike; 
            this.expireDate = expireDate;
            this.vol = vol;
        }

        @Override
        public String toString(){
            return expireDate + "," + strike + "," + vol;
        }

    }
    

    public ImpliedVolSurface(List<OptionQuoteRow> filtered, LocalDate asOf) {
        this.asOf = asOf;
        this.points = new ArrayList<>();
        for (OptionQuoteRow row : filtered) {
            boolean isOtmCall = row.getStrike() >= row.getUnderlyingLast();
            double vol = isOtmCall ? row.getCallIV() : row.getPutIV();
            points.add(new VolPoint(row.getStrike(), row.getExpireDate(), vol));
        }
    }

    // get the cloest value of vol for a given strike and maturity
    public double getVol(double strike, LocalDate maturity) {
        VolPoint closest = null;
        double bestDistance = Double.MAX_VALUE;

        for (VolPoint p : points) {
            double strikeDiff = p.strike - strike;
            long maturityDiffDays = Math.abs(ChronoUnit.DAYS.between(p.expireDate, maturity));
            double distance = strikeDiff * strikeDiff + maturityDiffDays * maturityDiffDays;

            if (distance < bestDistance) {
                bestDistance = distance;
                closest = p;
            }
        }

        return closest.vol;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (VolPoint point : points) {
            sb.append(point.toString()).append("\n");
        }
        return sb.toString();
    }

    public LocalDate getAsOf(){
        return this.asOf;
    }


    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("strike,maturity,vol\n");

        for (VolPoint p : points) {
            sb.append(p.strike).append(",")
            .append(p.expireDate).append(",")
            .append(p.vol).append("\n");
        }

        return sb.toString();
    }
    public static void writeToFile(String content, String path) throws IOException {
        Files.writeString(Path.of(path), content);
    }


}
