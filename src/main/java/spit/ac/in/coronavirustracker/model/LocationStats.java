package spit.ac.in.coronavirustracker.model;

import lombok.Data;

@Data
public class LocationStats {

    private String country;
    private int totalCases;
    private int diff;

}
