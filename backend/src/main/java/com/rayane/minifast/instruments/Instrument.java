package com.rayane.minifast.instruments;

import java.time.LocalDate;


public interface Instrument {

    /**
     * Returns the underlying asset of the instrument.
     *
     * @return the underlying asset as a String
     */
    String getUnderlying();

    LocalDate getMaturity();

    String getId();
}