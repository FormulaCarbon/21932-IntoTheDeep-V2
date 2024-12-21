package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

import java.util.HashMap;

@Autonomous(name = "Sample Cycle", group = "Sensor")
public class Sample_RR extends LinearOpMode {
    public HashMap<String, String> deviceConf = new HashMap<String, String>();

    public static int tickChange = 100, pos = 150;

    @Override
    public void runOpMode() throws InterruptedException {
        // Hardware Map HashMap
        deviceConf.put("leftPivot",       "leftPivot");
        deviceConf.put("rightPivot",      "rightPivot");
        deviceConf.put("leftExtension",   "leftExtension");
        deviceConf.put("rightExtension",  "rightExtension");
        deviceConf.put("wrist",           "pivot");
        deviceConf.put("smallWrist",      "smallPivot");
        deviceConf.put("turn",            "turn");
        deviceConf.put("reset",           "reset");

        Claw claw = new Claw(hardwareMap);

        Pivot pivot = new Pivot(hardwareMap, deviceConf);

        Extension extension = new Extension(hardwareMap, deviceConf);

        Wrist wrist = new Wrist(hardwareMap, deviceConf);

        Pose2d startPos = new Pose2d(40.5, 66, Math.PI);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPos);

        TrajectoryActionBuilder bucket0 = drive.actionBuilder(startPos)
                .setTangent(3*Math.PI/2)
                .splineToLinearHeading(new Pose2d(56,56, 5*Math.PI/4), 0);

        TrajectoryActionBuilder wait1 = drive.actionBuilder(startPos)
                .waitSeconds(1);


        TrajectoryActionBuilder block1 = bucket0.endTrajectory().fresh()
                .setTangent(5*Math.PI/4)
                .splineToLinearHeading(new Pose2d(49.5, 41, 3*Math.PI/2), 3*Math.PI/2);

        TrajectoryActionBuilder bucket1 = block1.endTrajectory().fresh()
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(56,56, 5*Math.PI/4), Math.PI/4);


        TrajectoryActionBuilder block2 = bucket1.endTrajectory().fresh()
                .setTangent(3*Math.PI/2)
                .splineToLinearHeading(new Pose2d(61, 41, 3*Math.PI/2), 3*Math.PI/2);

        TrajectoryActionBuilder bucket2 = block2.endTrajectory().fresh()
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(56,56, 5*Math.PI/4), Math.PI/2);
        // TODO: Add third block
        /*TrajectoryActionBuilder block3 = bucket2.endTrajectory().fresh()
                .setTangent(3*Math.PI/2)
                .splineToLinearHeading(new Pose2d(60, 38, 5*Math.PI/3), 5*Math.PI/3);

        TrajectoryActionBuilder bucket3 = block3.endTrajectory().fresh()
                .setTangent(2*Math.PI/3)
                .splineToLinearHeading(new Pose2d(56,56, 5*Math.PI/4), Math.PI/2);*/

        TrajectoryActionBuilder park = bucket2.endTrajectory().fresh()
                .strafeTo(new Vector2d(50, 50));

        Thread update = new Thread(()->updateAll(pivot, extension, wrist));
        claw.directSet(Claw.closed);

        // Wait for the start button to be pressed
        wrist.setPos("Start");
        wrist.turnClaw(0);
        wrist.update();
        update.start();
        pivot.checkReset();
        pivot.setPos("Start");
        telemetry.addData("pos", pivot.getPos());
        telemetry.addData("target", pivot.getPos() - tickChange);
        telemetry.addData("error", pivot.getError());
        telemetry.update();

        waitForStart();



        /*while (!pivot.checkReset() && opModeIsActive())
        {
            if (pivot.checkReset())
            {
                pivot.applyPower(0);
            }
            pivot.setDirectPos(pivot.getPos() - tickChange);
            telemetry.addData("pos", pivot.getPos());
            telemetry.addData("target", pivot.getPos() - tickChange);
            telemetry.addData("error", pivot.getError());
            telemetry.update();
            pivot.update();
            if (pivot.checkReset())
            {
                pivot.applyPower(0);
            }
        }*/
        //pivot.checkReset();
        extension.setPos("Idle");


        setBucket(bucket0.build(), pivot, extension, wrist, claw);
        sleep(500);

        getBlock(block1.build(),pivot,extension,wrist,claw);
        setBucket(bucket1.build(), pivot, extension, wrist, claw);
        sleep(500);

        getBlock(block2.build(), pivot, extension, wrist, claw);
        setBucket(bucket2.build(), pivot, extension, wrist, claw);
        sleep(500);

        //getBlock(block3.build(), pivot, extension, wrist, claw);
        //setBucket(bucket3.build(), pivot, extension, wrist, claw);
        //sleep(500);

        extension.setPos("Idle");
        sleep(500);
        pivot.setPos("Intake");
        pivot.setKP("Idle");
        wrist.setPos("High Basket");
        Actions.runBlocking(park.build());

    }
    public void sleep(int t) {
        try {
            Thread.sleep(t); // Wait for 1 millisecond
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            // Optionally, log or handle the interruption
        }
    }
    public void updateAll(Pivot pivot, Extension extension, Wrist wrist) {
        while (opModeInInit() || opModeIsActive())
        {

            //pivot.setKP(extension.getTarget());
            pivot.update();

            //extension.update();
            //wrist.update();
            telemetry.addData("pos", pivot.getPos());
            telemetry.addData("target", pivot.getPos() - tickChange);
            telemetry.addData("error", pivot.getError());
            telemetry.update();
        }
    }

    public void setBucket(Action trajectory , Pivot pivot, Extension extension, Wrist wrist, Claw claw) {
        pivot.setPos("High Basket");
        pivot.setKP("High Basket");

        wrist.setPos("Intake");

        Actions.runBlocking(trajectory);

        extension.setPos("High Basket");
        sleep(1500);
        wrist.setPos("High Basket");
        sleep(1000);
        claw.directSet(Claw.open);
        sleep(750);
        wrist.setPos("Intake");

    }

    public void getBlock(Action trajectory , Pivot pivot, Extension extension, Wrist wrist, Claw claw) {
        extension.setPos("Idle");
        sleep(1500);
        pivot.setPos("Idle");
        pivot.setKP("Idle");
        Actions.runBlocking(trajectory);

        pivot.setPos("Intake");
        //pivot.setKP("Intake");
        sleep(1000);
        wrist.setPos("Intake");
        sleep(500);
        //extension.setPos("Intake");
        claw.directSet(Claw.closed);
        sleep(500);
        wrist.setPos("Sample Intake");
        //extension.setPos("Idle");
        pivot.setPos("Idle");
        //pivot.setKP("Idle");
    }

}
