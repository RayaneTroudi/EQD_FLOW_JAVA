package com.rayane.minifast.surface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ImpliedVolSurface {

    private final LocalDate asOf;
    private final List<VolPoint> points;
    private final VolInterpolator interpolator;

    public ImpliedVolSurface(LocalDate asOf, List<VolPoint> points, VolInterpolator interpolator) {
        this.asOf = asOf;
        this.points = points;
        this.interpolator = interpolator;
    }

    public double getVol(double strike, LocalDate maturity) {
        return interpolator.interpolate(points, strike, maturity);
    }

    public double getTimeToMaturity(LocalDate expireDate) {
        return ChronoUnit.DAYS.between(asOf, expireDate) / 365.0;
    }

    public int size() {
        return points.size();
    }

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("strike,maturity,vol\n");
        for (VolPoint p : points) {
            sb.append(p.getStrike()).append(",")
              .append(p.getMaturity()).append(",")
              .append(p.getVol()).append("\n");
        }
        return sb.toString();
    }

    public static void writeToFile(String content, String path) throws IOException {
        Files.writeString(Path.of(path), content);
    }

    public SurfaceGrid toGrid(double minStrike, double maxStrike, double strikeStep,
                               long[] maturitiesDays) {
        List<Double> strikeList = new ArrayList<>();
        for (double k = minStrike; k <= maxStrike; k += strikeStep) {
            strikeList.add(k);
        }

        double[][] vols = new double[maturitiesDays.length][strikeList.size()];

        for (int i = 0; i < maturitiesDays.length; i++) {
            LocalDate maturity = asOf.plusDays(maturitiesDays[i]);
            for (int j = 0; j < strikeList.size(); j++) {
                vols[i][j] = getVol(strikeList.get(j), maturity);
            }
        }

        SurfaceGrid grid = new SurfaceGrid();
        grid.asOf = asOf;
        grid.strikes = strikeList.stream().mapToDouble(Double::doubleValue).toArray();
        grid.maturitiesDays = maturitiesDays;
        grid.vols = vols;
        return grid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (VolPoint point : points) {
            sb.append(point.toString()).append("\n");
        }
        return sb.toString();
    }

    public static class SurfaceGrid {
        public LocalDate asOf;
        public double[] strikes;
        public long[] maturitiesDays;
        public double[][] vols;
    }
}
