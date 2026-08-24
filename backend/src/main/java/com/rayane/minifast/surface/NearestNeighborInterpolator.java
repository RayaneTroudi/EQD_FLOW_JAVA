package com.rayane.minifast.surface;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NearestNeighborInterpolator implements VolInterpolator {

    @Override
    public double interpolate(List<VolPoint> points, double strike, LocalDate maturity) {
        VolPoint closest = null;
        double bestDistance = Double.MAX_VALUE;

        for (VolPoint p : points) {
            double strikeDiff = p.getStrike() - strike;
            long maturityDiffDays = Math.abs(ChronoUnit.DAYS.between(p.getMaturity(), maturity));
            double distance = strikeDiff * strikeDiff + maturityDiffDays * maturityDiffDays;

            if (distance < bestDistance) {
                bestDistance = distance;
                closest = p;
            }
        }

        if (closest == null) {
            throw new IllegalStateException("Implied vol surface has no points");
        }
        return closest.getVol();
    }
}
