package org.firstinspires.ftc.teamcode.archive.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;
@Config
public class Wrist {

    Servo wrist;
    Servo smallWrist, turn;

    public static double intake = 0.4, basket = 0.4, idle = 0.6, lowSpec = 0.5, highSpec = 0.45, realIntake = 0.37, start = 1;
    private double pos, smallPos, turnPos;

    public static double smallIntake = 0.0, smallBasket = 0.25, smallIdle = 0.95, smallLowSpec = 0.5, smallHighSpec = 0.45, smallRealIntake = 0.88, smallStart = 0;
    public static double turn0 = 0.07, turn1 = 0.287, turn2 = 0.6117, turn3 = 0.9;
    public String posStr = "";

    public Wrist(HardwareMap hwMap, HashMap<String, String> config)
    {
        wrist = hwMap.servo.get(config.get("wrist"));
        smallWrist = hwMap.servo.get(config.get("smallWrist"));
        turn = hwMap.servo.get(config.get("turn"));

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

            case "Sample Extend":
                this.pos = intake;
                this.smallPos = smallIntake;
                break;

            case "Flip Down":
                this.pos = realIntake;
                this.smallPos = smallRealIntake;
                break;

            case "Flip Up":
                this.pos = intake;
                this.smallPos = smallIntake;
                break;

            case "Pullout":
                this.pos = intake;
                this.smallPos = smallIntake;
                break;

            case "Flip Out":
                this.pos = realIntake;
                this.smallPos = smallRealIntake;
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

            case "Start":
                this.pos = start;
                this.smallPos = smallStart;
                break;

            /*default:
                this.pos = idle;
                this.smallPos = idle;
                break;*/
        }
        posStr = pos;
    }

    public void turnClaw(int pos)
    {
        switch (pos)
        {
            case 0:
                turnPos = turn0;
                break;
            case 1:
                turnPos = turn1;
                break;
            case 2:
                turnPos = turn2;
                break;
            case 3:
                turnPos = turn3;
                break;
        }
    }

    public String getTarget()
    {
        return posStr;
    }
    public double getPos()
    {
        return pos;
    }
    public double getSmallPos()
    {
        return smallPos;
    }
}
