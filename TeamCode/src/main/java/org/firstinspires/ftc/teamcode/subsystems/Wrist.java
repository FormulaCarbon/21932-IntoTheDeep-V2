package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;
@Config
public class Wrist {

    Servo wrist;
    Servo smallWrist, turn;

    public static double intake = 0.3, basket = 0.3, idle = 0.3, lowSpec = 0.5, highSpec = 0.45, realIntake = 0.75;
    private double pos, smallPos;
    private double turnPos = 0.5;

    public static double smallIntake = 0.3, smallBasket = 0.3, smallIdle = 0.3, smallLowSpec = 0.5, smallHighSpec = 0.45, smallRealIntake = 0.75;

    public static double turnRes;
    public String posStr = "";

    public Wrist(HardwareMap hwMap, HashMap<String, String> config)
    {
        wrist = hwMap.servo.get(config.get("wrist"));
        smallWrist = hwMap.servo.get(config.get("smallWrist"));
        turn = hwMap.servo.get(config.get("turn"));

        wrist.setDirection(Servo.Direction.REVERSE);
    }

    public void update()
    {
        wrist.setPosition(pos);
        smallWrist.setPosition(smallPos);
        turn.setPosition(turnPos);
    }

    public void setPos(String pos)
    {
        switch (pos)
        {
            case "Specimen Intake":
                this.pos = intake;
                this.smallPos = smallIntake;
                break;

            case "Sample Intake":
                this.pos = intake;
                this.smallPos = smallIntake;
                break;

            case "Low Basket":
                this.pos = basket;
                this.smallPos = smallBasket;
                break;

            case "High Basket":
                this.pos = basket;
                this.smallPos = smallBasket;
                break;

            case "Low Specimen":
                this.pos = lowSpec;
                this.smallPos = smallLowSpec;
                break;

            case "High Specimen":
                this.pos = highSpec;
                this.smallPos = smallHighSpec;
                break;

            case "Intake":
                this.pos = realIntake;
                this.smallPos = smallRealIntake;
                break;

            default:
                this.pos = idle;
                this.smallPos = idle;
                break;
        }
        posStr = pos;
    }

    public void turnClaw(boolean leftFlag, boolean rightFlag)
    {
        if (leftFlag)
        {
            turnPos -= turnRes;
        }
        else if (rightFlag)
        {
            turnPos += turnRes;
        }
    }

    public String getTarget()
    {
        return posStr;
    }
}
