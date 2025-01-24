package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

@Config
public class SpecMec {

    private Servo swing1, swing2, turn, claw;

    public static double scorePos = 0.84, intakePos = 0.2, turn0 = 0.25, turn1 = 0.92, idlePos = 1.0, close = 0.59, open = 0.15;
    public static int ledBrightness = 100;

    public static HashMap<String, Double> swingPos = new HashMap<String, Double>();
    public static HashMap<String, Double> turnPos = new HashMap<String, Double>();

    public static double redThresh = 0.008, blueThresh = 0.008;
    ColorRangefinder sensor;
    NormalizedRGBA colors;



    private double swPos, tPos, clawPos = open;

    public SpecMec(HardwareMap hwMap, HashMap<String, String> config) {
        swing1 = hwMap.servo.get(config.get("swing1"));
        swing2 = hwMap.servo.get(config.get("swing2"));
        turn = hwMap.servo.get(config.get("turn"));
        claw = hwMap.servo.get(config.get("specClaw"));
        sensor = new ColorRangefinder(hwMap.get(RevColorSensorV3.class, config.get("colorSensor")));
        sensor.setLedBrightness(ledBrightness);
        colors = sensor.emulator.getNormalizedColors();

        swingPos.put("Intake",      intakePos);
        swingPos.put("Score",        scorePos);
        swingPos.put("Idle",        idlePos);
        swingPos.put("Start",       0.0);


        turnPos.put("Intake",      turn0);
        turnPos.put("Score",        turn1);
        turnPos.put("Start",       0.0);

    }

    public void setPosition(String swPos, String tPos) {
        this.swPos = swingPos.get(swPos);
        this.tPos = turnPos.get(tPos);
    }

    public void update() {
        swing1.setPosition(swPos);
        swing2.setPosition(swPos);
        turn.setPosition(tPos);
    }

    public void updateClaw() {
        claw.setPosition(clawPos);
    }

    public void closeClaw() {
        clawPos = close;
    }

    public void openClaw() {
        clawPos = open;
    }

    public void checkSensor() {
        colors = sensor.emulator.getNormalizedColors();
        if (colors.red > redThresh || colors.blue > blueThresh) {
            clawPos = close;
        }
    }

    public NormalizedRGBA getColors() {
        return colors;
    }
}
