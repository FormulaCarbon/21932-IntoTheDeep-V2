package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointDrive;
import org.firstinspires.ftc.teamcode.subsystems.AutonPivot;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.Util;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

import java.util.HashMap;
@Config
@Autonomous(name = "Spec Cycle (4)", group = "Sensor")
public class Specimen_RR extends LinearOpMode {

    public static double intakeX= -56, intakeY = 60, hangY = 32;
    @Override
    public void runOpMode() throws InterruptedException {
        // Hardware Map HashMap
        Util util = new Util();

        Claw claw = new Claw(hardwareMap, util.deviceConf);

        Pivot pivot = new Pivot(hardwareMap, util.deviceConf);

        Extension extension = new Extension(hardwareMap, util.deviceConf);

        Wrist wrist = new Wrist(hardwareMap, util.deviceConf);

        Pose2d startPos = new Pose2d(-4, 62, 3*Math.PI/2);
        PinpointDrive drive = new PinpointDrive(hardwareMap, startPos);



        TrajectoryActionBuilder hang0 = drive.actionBuilder(startPos)
                .strafeTo(new Vector2d(-4, hangY));

        TrajectoryActionBuilder pushBlocks = hang0.endTrajectory().fresh()
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(-4, 40), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-34, 40), 3*Math.PI/2)
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-34, 12), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-46, 12), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-46, 56), Math.PI/2)
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-46, 12), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-56, 12), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-56, 60), Math.PI/2);

        TrajectoryActionBuilder hang1 = pushBlocks.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-6, 40), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-6, hangY), 3*Math.PI/2);


        TrajectoryActionBuilder block2 = hang1.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-6, 40), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI/2);

        TrajectoryActionBuilder hang2 = block2.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-8, 40), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-8, hangY), 3*Math.PI/2);

        TrajectoryActionBuilder block3 = hang2.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-8, 40), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI/2);

        TrajectoryActionBuilder hang3 = block3.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-10, 40), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-10, hangY), 3*Math.PI/2);

        TrajectoryActionBuilder park = hang3.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-10, 40), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI/2);

        Thread update = new Thread(()->updateAll(pivot, extension, wrist));


        // go to start pos
        /*claw.directSet(Claw.closed);
        wrist.setBicepPos("Start");
        wrist.setForearmPos("Start");
        wrist.setRotationPos(0);
        pivot.setPos("Start");
        pivot.setkP("Normal");
        update.start();*/

        telemetry.addData("pos", pivot.getCurrent());
        telemetry.addData("target", pivot.getTarget());
        telemetry.addData("error", pivot.getError());
        telemetry.update();

        // Wait for the start button to be pressed
        waitForStart();

        Actions.runBlocking(hang0.build());
        sleep(2000);
        Actions.runBlocking(pushBlocks.build());
        sleep(2000);
        Actions.runBlocking(hang1.build());
        sleep(2000);

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


            pivot.update();

            extension.update();
            wrist.update();
            telemetry.addData("pos", pivot.getCurrent());
            telemetry.addData("target", pivot.getTarget());
            telemetry.addData("error", pivot.getError());
            telemetry.update();
        }
    }

    public void setBucket(Action trajectory , Pivot pivot, Extension extension, Wrist wrist, Claw claw) {
        pivot.setPos("Basket");
        wrist.setPos("Auton Idle");
        //pivot.setkP("Extended");
        sleep(500);
        extension.setPos("Basket");

        Actions.runBlocking(trajectory);
        wrist.setPos("Basket");
        sleep(500);
        claw.directSet(Claw.open);
        sleep(500);
        wrist.setPos("Auton Idle");
        sleep(500);

    }

    public void getBlock(Action trajectory , Pivot pivot, Extension extension, Wrist wrist, Claw claw) {
        extension.setPos("Idle");


        Actions.runBlocking(trajectory);
        pivot.setkP("Normal");
        pivot.setPos("Down");

        wrist.setPos("Intake");
        sleep(1500);
        claw.directSet(Claw.closed);
        sleep(500);
        wrist.setPos("Auton Idle");
        pivot.setPos("Basket");

    }

}
