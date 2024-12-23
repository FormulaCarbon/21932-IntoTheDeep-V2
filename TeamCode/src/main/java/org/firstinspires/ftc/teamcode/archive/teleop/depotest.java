package org.firstinspires.ftc.teamcode.archive.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Config
public class depotest extends LinearOpMode {
    public static String s1 = "turn";
    public static String s2 = "pivot";
    public static String s3 = "smallPivot";

    public static double joystickMult = 0.002;
    @Override
    public void runOpMode() throws InterruptedException {
        Servo turn = hardwareMap.get(Servo.class, s1);
        Servo pivot = hardwareMap.get(Servo.class, s2);
        Servo smallPivot = hardwareMap.get(Servo.class, s3);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.left_trigger != 0)
            {
                turn.setPosition(turn.getPosition() + (gamepad1.left_trigger * joystickMult));
            }
            else if (gamepad1.right_trigger != 0)
            {
                turn.setPosition(turn.getPosition() - (gamepad1.right_trigger * joystickMult));
            }

            pivot.setPosition(pivot.getPosition() + (gamepad1.left_stick_y * joystickMult));
            smallPivot.setPosition(smallPivot.getPosition() + (gamepad1.right_stick_y * joystickMult));


            telemetry.update();


        }
    }
}