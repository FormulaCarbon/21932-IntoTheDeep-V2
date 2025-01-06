package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.Util;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@TeleOp
@Config
public class IntoTheDeep extends LinearOpMode {



    private int incr = 1;
    boolean incrUpdate = false;

    public static int maxSampleSteps = 8, maxSpecimenSteps = 8;

    boolean pivotReady, wristReady, extensionReady, swapReady, cycleReady, clawReady, turnReady;
    boolean wristManual = false, extensionManual = false, pivotManual = false;

    String sequence = "Sample";

    @Override
    public void runOpMode() throws InterruptedException {
        Util util = new Util();
        Drive drive = new Drive(hardwareMap, util.deviceConf);
        Pivot pivot = new Pivot(hardwareMap, util.deviceConf);
        Extension extension = new Extension(hardwareMap, util.deviceConf);
        Wrist wrist = new Wrist(hardwareMap, util.deviceConf);
        Claw claw = new Claw(hardwareMap, util.deviceConf);

        waitForStart();

        wrist.setRotationPos(0);

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            turnReady = pivotReady = wristReady = extensionReady = swapReady = cycleReady = clawReady = true;
            drive.getXYZ(gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);

            increment(gamepad1.right_bumper, gamepad1.left_bumper, sequence);
            setPositions(incr, sequence, pivot, extension, wrist);

            /*if (gamepad1.x && wristReady) {
                wristManual = true;
                wrist.setBicepPos("Intake");
                wrist.setForearmPos("Intake");
                wristReady = false;
            }
            else if (gamepad1.y && wristReady) {
                wristManual = true;
                wrist.setBicepPos("Basket");
                wrist.setForearmPos("Basket");
                wristReady = false;
            }*/

            if (gamepad2.dpad_up && turnReady) {
                wrist.setRotationPos(0);
                turnReady = false;
            }
            else if (gamepad2.dpad_left && turnReady) {
                wrist.setRotationPos(3);
                turnReady = false;
            }
            else if (gamepad2.dpad_down && turnReady) {
                wrist.setRotationPos(2);
                turnReady = false;
            }
            else if (gamepad2.dpad_right && turnReady) {
                wrist.setRotationPos(1);
                turnReady = false;
            }

            if (gamepad1.dpad_up && turnReady) {
                wrist.setRotationPos(0);
                turnReady = false;
            }
            else if (gamepad1.dpad_left && turnReady) {
                wrist.setRotationPos(3);
                turnReady = false;
            }
            else if (gamepad1.dpad_down && turnReady) {
                wrist.setRotationPos(2);
                turnReady = false;
            }
            else if (gamepad1.dpad_right && turnReady) {
                wrist.setRotationPos(1);
                turnReady = false;
            }

            if (gamepad1.b && pivotReady && extensionReady && wristReady) {
                incr = -1;
            }

            if (gamepad1.y && extensionReady) {
                incr = -2;
            }

            if (gamepad1.x && extensionReady) {
                incr = -3;
            }

            if (gamepad2.right_bumper || gamepad2.left_bumper || gamepad1.right_bumper || gamepad1.left_bumper)
            {
                wristManual = false;
                extensionManual = pivotManual =  false;

            }



            drive.update();
            pivot.update();
            extension.update();
            wrist.update();
            claw.update(gamepad1.a);

            telemetry.addData("incr", incr);
            telemetry.addData("tar", pivot.getTarget());
            telemetry.addData("cur", pivot.getCurrent());
            telemetry.addData("pow", pivot.getPower());
            telemetry.update();
        }
    }

    public void increment(boolean upFlag, boolean downFlag, String sequence) {
        if (sequence.equals("Sample")) {
            if (downFlag && !incrUpdate && incr > 0) {
                incr -= 1;
                incrUpdate = true;
            } else if (upFlag && !incrUpdate) {
                incr += 1;
                incrUpdate = true;
            } else if (!upFlag && !downFlag) {
                incrUpdate = false;
            }

            if (incr > maxSampleSteps) {
                incr = 0;
            }
        }
        else if (sequence.equals("Specimen")) {
            if (downFlag && !incrUpdate && incr > 0) {
                incr -= 1;
                incrUpdate = true;
            } else if (upFlag && !incrUpdate) {
                incr += 1;
                incrUpdate = true;
            } else if (!upFlag && !downFlag) {
                incrUpdate = false;
            }

            if (incr > maxSpecimenSteps) {
                incr = 0;
            }
        }
    }

    public void setPositions(int pos, String sequence, Pivot pivot, Extension extension, Wrist wrist)  {
        if (sequence.equals("Sample")) {
            switch (pos) {
                case 0: // Idle
                    pivot.setPos("Idle");
                    pivot.setkP("Normal");
                    extension.setPos("Idle");
                    wrist.setBicepPos("Idle");
                    wrist.setForearmPos("Idle");
                    break;
                case 1: // Sample Intake: Down, Unextended
                    pivot.setPos("Down");
                    pivot.setkP("Normal");
                    extension.setPos("Idle");
                    wrist.setBicepPos("Idle");
                    wrist.setForearmPos("Idle");

                    break;
                case 2: // Sample Extend
                    pivot.setPos("Down");
                    pivot.setkP("Extended");
                    extension.setPos("Intake");
                    wrist.setBicepPos("Idle");
                    wrist.setForearmPos("Idle");
                    break;
                case 3: // Flip Down
                    pivot.setPos("Down");
                    pivot.setkP("Extended");
                    extension.setPos("Intake");
                    wrist.setBicepPos("Intake");
                    wrist.setForearmPos("Intake");
                    break;
                case 4: // Flip Up
                    pivot.setPos("Down");
                    pivot.setkP("Extended");
                    extension.setPos("Intake");
                    wrist.setBicepPos("Idle");
                    wrist.setForearmPos("Idle");
                    break;
                case 5: // Pullout
                    pivot.setPos("Down");
                    pivot.setkP("Normal");
                    extension.setPos("Idle");
                    wrist.setBicepPos("Idle");
                    wrist.setForearmPos("Idle");
                    wrist.setRotationPos(0);
                    break;
                case 6: // Idle
                    pivot.setPos("Idle");
                    pivot.setkP("Normal");
                    extension.setPos("Idle");
                    wrist.setBicepPos("Idle");
                    wrist.setForearmPos("Idle");
                    break;
                case 7: // High Basket
                    pivot.setPos("Basket");
                    pivot.setkP("Extended");
                    extension.setPos("Basket");
                    wrist.setBicepPos("Basket");
                    wrist.setForearmPos("Basket");
                    break;
                case 8: // Flip Out
                    pivot.setPos("Basket");
                    pivot.setkP("Extended");
                    extension.setPos("Basket");
                    wrist.setBicepPos("Intake");
                    wrist.setForearmPos("Intake");
                    break;
                case -1: // Hang Pivot Position
                    wrist.setBicepPos("Intake");
                    wrist.setForearmPos("Intake");
                    pivot.setPos("Hang");
                    extension.setPos("Idle");
                    break;
                case -2: // Hang Extend
                    extension.setPos("Hang");
                    break;
                case -3: // Hang Retract
                    extension.setPos("Retract");
                    break;
            }
        }

    }
}
