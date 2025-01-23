package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

@Config
public class SpecMec {

    private Servo swing1, swing2, turn, claw;

    public static double scorePos = 0.9, intakePos = 0.0, turn0 = 0.88, turn1 = 0.2, idlePos = 0.5, close = 0.88, open = 0.43;

    public static HashMap<String, Double> swingPos = new HashMap<String, Double>();
    public static HashMap<String, Double> turnPos = new HashMap<String, Double>();

    private double swPos, tPos;

    public SpecMec(HardwareMap hwMap, HashMap<String, String> config) {
        swing1 = hwMap.servo.get(config.get("swing1"));
        swing2 = hwMap.servo.get(config.get("swing2"));
        turn = hwMap.servo.get(config.get("turn"));
        claw = hwMap.servo.get(config.get("specClaw"));

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

    public void closeClaw() {
        claw.setPosition(close);
    }

    public void openClaw() {
        claw.setPosition(open);
    }
}
