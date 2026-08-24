package com.rayane.minifast.surface;

import java.time.LocalDate;
import java.util.List;

public interface VolInterpolator {
    double interpolate(List<VolPoint> points, double strike, LocalDate maturity);
}
