package com.rayane.minifast.surface;

import java.time.LocalDate;

public class VolPoint {
    
        private double strike;
        private LocalDate expireDate; 
        private double vol;

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
