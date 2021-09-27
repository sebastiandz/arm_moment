package com.robotarm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ArmCalculator {

    /*
    Siła:
    F = m * g
     gdzie:
      g - przyspieszenie grawitacyjne, ok 9.8 m/s2
      m - masa [kg]

    Moment:
    M = r * F
     gdzie:
      r - ramię [m]
      F - siła [Nm]
    */

    private final double CONST_G = 9.8; // [m/s2] przyspieszenie grawitacyjne
    private final double PAYLOAD = 0.8; // [kg]

    private static DecimalFormat df2 = new DecimalFormat("0.00");

    public void calculate() {
        System.out.println("### calculate ###");

        List<ArmElement> model = modelPrzykladowy();
        for (int i = 0; i < model.size(); i++) {
            obliczMoment(model, i);
        }
    }

    private double obliczMoment(List<ArmElement> model, int start) {
        double momentSum = 0.0;
        for (int i = start; i < model.size(); i++) {
            ArmElement armElement = model.get(i);
            double calkowitaDlugoscDzwigni = 0.0;

            for (int j = start; j <= i; j++) {
                double ramie = model.get(j).getArm().getLenght();
                calkowitaDlugoscDzwigni += ramie;
            }

            double masa = armElement.getArm().getWeight();
            int nextI = i + 1;
            if (nextI < model.size()) {
                double masaNastepnegoSerwa = model.get(nextI).getServo().getWeight();
                // moment wynikający z masy serwa umieszczonego na końcu naszego ramienia, jest to serwo sterujące następnym ramieniem
                momentSum += CONST_G * 0.5 * calkowitaDlugoscDzwigni * masaNastepnegoSerwa / armElement.getServo().getRatio();
            }

            // moment wynikający z masy ramienia, przyjmuje że cała masa ramienia jest skupiowna w  połowie długości (* 0.5)
            momentSum += CONST_G * 0.5 * calkowitaDlugoscDzwigni * masa / armElement.getServo().getRatio();
        }

        System.out.println(String.format("S%s : %s Nm; %s kg/cm", start, df2.format(momentSum), df2.format(10.197162129779 * momentSum)));
        return momentSum;
    }

    private List<ArmElement> modelPrzykladowy() {
        // momenty bez uwzgledniania przekladni!
        List<ArmElement> elements = new ArrayList<>();

        // arm 0
        elements.add(
                ArmElement.builder()
                        .arm(Arm.builder().weight(0.3).lenght(0.17).build())
                        .servo(Servo.builder().weight(0.28).torque(0.45).ratio(38.4).build())
                        .build()
        );

        // arm 1
        elements.add(
                ArmElement.builder()
                        .arm(Arm.builder().weight(0.23).lenght(0.23).build())
                        .servo(Servo.builder().weight(0.4).torque(0.59).ratio(38.4).build())
                        .build()
        );

        // arm 2
        elements.add(
                ArmElement.builder()
                        .arm(Arm.builder().weight(0.2).lenght(0.20).build())
                        .servo(Servo.builder().weight(0.22).torque(0.22).ratio(38.4).build())
                        .build()
        );

        // arm 3
        elements.add(
                ArmElement.builder()
                        .arm(Arm.builder().weight(0.05).lenght(0.04).build())
                        .servo(Servo.builder().weight(0.07).torque(0.046).ratio(38.4).build())
                        .build()
        );

        // arm 4
        elements.add(
                ArmElement.builder()
                        .arm(Arm.builder().weight(0.05).lenght(0.04).build())
                        .servo(Servo.builder().weight(0.07).torque(0.046).ratio(38.4).build())
                        .build()
        );

        // arm 5
        elements.add(
                ArmElement.builder()
                        .arm(Arm.builder().weight(0.05 + PAYLOAD).lenght(0.04).build())
                        .servo(Servo.builder().weight(0.07).torque(0.046).ratio(38.4).build())
                        .build()
        );

        return elements;
    }
}
