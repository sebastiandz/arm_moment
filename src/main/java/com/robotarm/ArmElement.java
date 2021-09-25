package com.robotarm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArmElement {

    private Servo servo;
    private Arm arm;

}
