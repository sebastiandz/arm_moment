package com.robotarm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Servo {

    private double weight; // kg
    private double torque; // Nm

}
