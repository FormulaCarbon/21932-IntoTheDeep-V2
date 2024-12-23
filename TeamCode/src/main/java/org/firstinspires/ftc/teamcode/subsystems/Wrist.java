package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

@Config
public class Wrist {

    private Servo rotation, forearm, bicep;

    private double bicepPos, forearmPos, rotationPos;

    public static HashMap<String, Double> bicepPositions, forearmPositions;

    public static double[] rotationPositions = new double[4];

    public Wrist(HardwareMap hwMap, HashMap<String, String> config) {
        bicep = hwMap.servo.get(config.get("bicep"));
        forearm = hwMap.servo.get(config.get("forearm"));
        rotation = hwMap.servo.get(config.get("rotation"));

        bicepPositions.put("Intake", 0.37);
        bicepPositions.put("Basket", 0.4);
        bicepPositions.put("Idle",   0.4);
        bicepPositions.put("Start",  1.0);

        forearmPositions.put("Intake", 0.88);
        forearmPositions.put("Basket", 0.25);
        forearmPositions.put("Idle",   0.0);
        forearmPositions.put("Start",  0.0);

        rotationPositions[0] = 0.88;
        rotationPositions[1] = 0.25;
        rotationPositions[2] = 0.0;
        rotationPositions[3] = 0.0;

    }

    public void update()
    {
        bicep.setPosition(bicepPos);
        forearm.setPosition(forearmPos);
        rotation.setPosition(rotationPos);
    }

    public void setBicepPos(String pos)
    {
        bicepPos = bicepPositions.get(pos);
    }
    public void setForearmPos(String pos)
    {
        forearmPos = forearmPositions.get(pos);
    }
    public void setRotationPos(int pos)
    {
        rotationPos = rotationPositions[pos];
    }

}
