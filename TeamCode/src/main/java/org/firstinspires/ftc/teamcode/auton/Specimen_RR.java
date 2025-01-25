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
import org.firstinspires.ftc.teamcode.subsystems.SpecMec;
import org.firstinspires.ftc.teamcode.subsystems.Util;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

import java.util.HashMap;
@Config
@Autonomous(name = "Spec Cycle (4)", group = "Sensor")
public class Specimen_RR extends LinearOpMode {

    public static double intakeX= -60, intakeY = 60, hangY = 26;
    @Override
    public void runOpMode() throws InterruptedException {
        // Hardware Map HashMap
        Util util = new Util();

        Claw claw = new Claw(hardwareMap, util.deviceConf);

        Pivot pivot = new Pivot(hardwareMap, util.deviceConf);

        Extension extension = new Extension(hardwareMap, util.deviceConf);

        Wrist wrist = new Wrist(hardwareMap, util.deviceConf);

        SpecMec specMec = new SpecMec(hardwareMap, util.deviceConf);

        Pose2d startPos = new Pose2d(0, 58, 3*Math.PI/2);
        PinpointDrive drive = new PinpointDrive(hardwareMap, startPos);



        TrajectoryActionBuilder hang0 = drive.actionBuilder(startPos)
                .strafeTo(new Vector2d(0, hangY));

        TrajectoryActionBuilder pushBlocks = hang0.endTrajectory().fresh()
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(0, 40), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-38, 40), 3*Math.PI/2)
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-38, 12), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-50, 12), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-50, 56), Math.PI/2)
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-50, 12), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-60, 12), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-60, 56), Math.PI/2)
                .strafeTo(new Vector2d(intakeX, intakeY-12));

        TrajectoryActionBuilder back1 = pushBlocks.endTrajectory().fresh()
                .strafeTo(new Vector2d(intakeX, intakeY-3));

        TrajectoryActionBuilder grab1 = pushBlocks.endTrajectory().fresh()
                .strafeTo(new Vector2d(intakeX, intakeY));

        TrajectoryActionBuilder hang1 = pushBlocks.endTrajectory().fresh()
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-2, hangY), 3*Math.PI/2);


        TrajectoryActionBuilder block2 = hang1.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-2, 40), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX, intakeY-6), Math.PI/2);

        TrajectoryActionBuilder hang2 = block2.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-4, 40), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-4, hangY), 3*Math.PI/2);

        TrajectoryActionBuilder block3 = hang2.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-4, 40), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI/2);

        TrajectoryActionBuilder hang3 = block3.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-8, 40), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-8, hangY), 3*Math.PI/2);

        TrajectoryActionBuilder park = hang3.endTrajectory().fresh()
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-8, 40), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI/2);

        Thread update = new Thread(()->updateAll(pivot, extension, wrist, specMec));


        // go to start pos
        /*claw.directSet(Claw.closed);
        wrist.setBicepPos("Start");
        wrist.setForearmPos("Start");
        wrist.setRotationPos(0);
        pivot.setPos("Start");
        pivot.setkP("Normal");*/
        specMec.setPosition("Start", "Start");
        update.start();

        telemetry.addData("pos", pivot.getCurrent());
        telemetry.addData("target", pivot.getTarget());
        telemetry.addData("error", pivot.getError());
        telemetry.update();

        // Wait for the start button to be pressed
        specMec.closeClaw();
        waitForStart();

        specMec.setPosition("Idle", "Score");
        sleep(500);
        Actions.runBlocking(hang0.build());
        specMec.setPosition("Score", "Score");
        sleep(500);
        specMec.openClaw();
        sleep(500);

        specMec.setPosition("Intake", "Intake");
        Actions.runBlocking(pushBlocks.build());
        sleep(2000);
        Actions.runBlocking(back1.build());
        sleep(2000);
        Actions.runBlocking(grab1.build());
        sleep(500);
        specMec.closeClaw();
        sleep(500);

        specMec.setPosition("Idle", "Score");
        sleep(500);
        Actions.runBlocking(hang1.build());
        specMec.setPosition("Score", "Score");
        sleep(500);
        specMec.openClaw();
        sleep(500);

        /*hang(hang0.build(), pivot, extension, wrist, claw, specMec);

        getSpec(pushBlocks.build(), pivot, extension, wrist, claw, specMec);

        hang(hang1.build(), pivot, extension, wrist, claw, specMec);

        getSpec(block2.build(), pivot, extension, wrist, claw, specMec);

        hang(hang2.build(), pivot, extension, wrist, claw, specMec);

        getSpec(block3.build(), pivot, extension, wrist, claw, specMec);

        hang(hang3.build(), pivot, extension, wrist, claw, specMec);

        getSpec(park.build(), pivot, extension, wrist, claw, specMec);*/


    }
    public void sleep(int t) {
        try {
            Thread.sleep(t); // Wait for 1 millisecond
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            // Optionally, log or handle the interruption
        }
    }
    public void updateAll(Pivot pivot, Extension extension, Wrist wrist, SpecMec specMec) {
        while (opModeInInit() || opModeIsActive())
        {


            /*pivot.update();

            extension.update();
            wrist.update();*/
            specMec.updateClaw();
            specMec.update();
            telemetry.addData("pos", specMec.getPos());
            telemetry.addData("target", specMec.getTarget());
            telemetry.addData("error", pivot.getError());
            telemetry.update();
        }
    }

    public void hang(Action trajectory , Pivot pivot, Extension extension, Wrist wrist, Claw claw, SpecMec specMec) {
        specMec.setPosition("Idle", "Score");
        sleep(500);
        Actions.runBlocking(trajectory);
        specMec.setPosition("Score", "Score");
        sleep(500);
        specMec.openClaw();
        sleep(500);

    }

    public void getSpec(Action trajectory , Pivot pivot, Extension extension, Wrist wrist, Claw claw, SpecMec specMec) {
        specMec.setPosition("Intake", "Intake");
        Actions.runBlocking(trajectory);
        sleep(500);
        specMec.closeClaw();

    }

}
