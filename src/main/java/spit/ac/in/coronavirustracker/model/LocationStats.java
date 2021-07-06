package spit.ac.in.coronavirustracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LocationStats {

    private String state;
    private String country;
    private int totalCases;


}
