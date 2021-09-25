package com.robotarm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Arm {

    private double weight; // kg
    private double lenght; // m

}
