package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;

import java.util.HashMap;

@Config
public class Util {
    public static double MAX_PIVOT_VELOCITY = 3000;
    public static double MAX_PIVOT_ACCEL = 1500;

    public static boolean inThresh(double val, double val2, double tol) {
        return Math.abs(val - val2) < tol;
    }

    public HashMap<String, String> deviceConf = new HashMap<String, String>();

    public Util() {
        deviceConf.put("frontLeft", "frontLeftMotor");
        deviceConf.put("backLeft", "backLeftMotor");
        deviceConf.put("frontRight", "frontRightMotor");
        deviceConf.put("backRight", "backRightMotor");
        deviceConf.put("leftPivot", "leftPivot");
        deviceConf.put("rightPivot", "rightPivot");
        deviceConf.put("leftExtension", "leftExtension");
        deviceConf.put("rightExtension", "rightExtension");
        deviceConf.put("bicep", "pivot");
        deviceConf.put("forearm", "smallPivot");
        deviceConf.put("rotation", "turn");
        deviceConf.put("claw", "claw");
        deviceConf.put("reset", "reset");
    }
}