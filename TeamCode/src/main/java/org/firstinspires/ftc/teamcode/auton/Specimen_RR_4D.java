package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PinpointDrive;
import org.firstinspires.ftc.teamcode.auton_subsystems.RR_Pivot;
import org.firstinspires.ftc.teamcode.auton_subsystems.RR_SpecMec;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Util;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Config
@Autonomous(name = "Spec Cycle (4D)", group = "Sensor")
@Disabled
public class Specimen_RR_4D extends LinearOpMode {

    public static double intakeX= -46, intakeY = 58, intakeX2 = -46, intakeY2 = 58, hangY = 26, startX = -8, startY = 64, t0 = 1.6, t1 = 1.7, pullOutTime0 = 3, inTime0 = 1, hangTime = 2.3, openTime = 2.4, idleTime = 1, pullOutTime = 0.7, inTime = 2.1, b1X = -44, b2X = -54, wallX = -60, hangX = 2, hangTime2 = 2.5, openTime2 = 2.6;
    @Override
    public void runOpMode() throws InterruptedException {
        // Hardware Map HashMap
        Util util = new Util();

        Claw claw = new Claw(hardwareMap, util.deviceConf);

        RR_Pivot pivot = new RR_Pivot(hardwareMap, util.deviceConf);

        Extension extension = new Extension(hardwareMap, util.deviceConf);

        Wrist wrist = new Wrist(hardwareMap, util.deviceConf);

        RR_SpecMec specMec = new RR_SpecMec(hardwareMap, util.deviceConf);

        Pose2d startPos = new Pose2d(startX, startY, Math.PI);
        PinpointDrive drive = new PinpointDrive(hardwareMap, startPos);



        TrajectoryActionBuilder hang0 = drive.actionBuilder(startPos)
                .afterTime(t0, specMec.setPos("Score", "Score"))
                .afterTime(t1, specMec.openClaw())
                .strafeToLinearHeading(new Vector2d(0, hangY), 3*Math.PI/2);

        TrajectoryActionBuilder pushBlocks = hang0.endTrajectory().fresh()
                .afterTime(pullOutTime0, specMec.setPos("Intake", "Intake"))
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(0, 40), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-38, 40), 3*Math.PI/2)
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-38, 16), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(b1X, 16), Math.PI/2)
                .splineToConstantHeading(new Vector2d(b1X, 52), Math.PI/2)
                .waitSeconds(0.1)
                .splineToConstantHeading(new Vector2d(b1X, 16), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(b2X, 16), Math.PI/2)
                .splineToConstantHeading(new Vector2d(b2X, 52), Math.PI/2)

                .waitSeconds(0.1)
                .afterTime(inTime0, specMec.closeClaw())
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI/2);


        TrajectoryActionBuilder hang1 = pushBlocks.endTrajectory().fresh()
                .afterTime(idleTime, specMec.setPos("Idle", "Score"))
                .afterTime(hangTime, specMec.setPos("Score", "Score"))
                .afterTime(openTime, specMec.openClaw())
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-2, hangY), 3*Math.PI/2);

        TrajectoryActionBuilder block2 = hang1.endTrajectory().fresh()
                .afterTime(pullOutTime, specMec.setPos("Intake", "Intake"))
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-2, 30), Math.PI/2)
                .afterTime(inTime, specMec.closeClaw())
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI);

        TrajectoryActionBuilder hang2 = block2.endTrajectory().fresh()
                .afterTime(idleTime, specMec.setPos("Idle", "Score"))
                .afterTime(hangTime, specMec.setPos("Score", "Score"))
                .afterTime(openTime, specMec.openClaw())
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-4, hangY), 3*Math.PI/2);

        TrajectoryActionBuilder block3 = hang2.endTrajectory().fresh()
                .afterTime(pullOutTime, specMec.setPos("Intake", "Intake"))
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-4, 30), Math.PI/2)
                .afterTime(inTime, specMec.closeClaw())
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI);

        TrajectoryActionBuilder hang3 = block3.endTrajectory().fresh()
                .afterTime(idleTime, specMec.setPos("Idle", "Score"))
                .afterTime(hangTime, specMec.setPos("Score", "Score"))
                .afterTime(openTime, specMec.openClaw())
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-6, hangY), 3*Math.PI/2);

        TrajectoryActionBuilder block4 = hang3.endTrajectory().fresh()
                .afterTime(pullOutTime, specMec.setPos("Intake", "Intake"))
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-6, 30), Math.PI/2)
                .afterTime(inTime, specMec.closeClaw())
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI);

        TrajectoryActionBuilder hang4 = block4.endTrajectory().fresh()
                .afterTime(idleTime, specMec.setPos("Idle", "Score"))
                .afterTime(hangTime, specMec.setPos("Score", "Score"))
                .afterTime(openTime, specMec.openClaw())
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-8, hangY), 3*Math.PI/2);


        TrajectoryActionBuilder park = hang3.endTrajectory().fresh()
                .afterTime(pullOutTime, specMec.setPos("Intake", "Intake"))
                .strafeTo(new Vector2d(-8, 40));

        TrajectoryActionBuilder wholeThing = drive.actionBuilder(startPos)
                .afterDisp(t0, specMec.setPos("Score", "Score"))

                .afterDisp(t1, specMec.openClaw())
                .strafeToLinearHeading(new Vector2d(hangX, hangY), 3*Math.PI/2)

                .afterTime(pullOutTime0, specMec.setPos("Intake", "Intake"))
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(0, 40), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-38, 40), 3*Math.PI/2)
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-38, 16), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(b1X, 16), Math.PI/2)
                .splineToConstantHeading(new Vector2d(b1X, 52), Math.PI/2)
                .waitSeconds(0.1)
                .splineToConstantHeading(new Vector2d(b1X, 16), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(b2X, 16), Math.PI/2)
                .splineToConstantHeading(new Vector2d(b2X, 52), Math.PI/2)
                .waitSeconds(0.1)
                .afterTime(inTime0, specMec.closeClaw())
                .splineToConstantHeading(new Vector2d(intakeX, intakeY), Math.PI/2)

                .afterTime(idleTime, specMec.setPos("Idle", "Score"))
                .afterTime(hangTime2, specMec.setPos("Score", "Score"))
                .afterTime(openTime2, specMec.openClaw())
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(hangX-2, hangY), 3*Math.PI/2)

                .afterTime(pullOutTime, specMec.setPos("Intake", "Intake"))
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(hangX-2, 30), Math.PI/2)
                .afterTime(inTime, specMec.closeClaw())
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX2, intakeY2), Math.PI)

                .afterTime(idleTime, specMec.setPos("Idle", "Score"))
                .afterTime(hangTime2, specMec.setPos("Score", "Score"))
                .afterTime(openTime2, specMec.openClaw())
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(hangX-4, hangY), 3*Math.PI/2)

                .afterTime(pullOutTime, specMec.setPos("Intake", "Intake"))
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(hangX-4, 30), Math.PI/2)
                .afterTime(inTime, specMec.closeClaw())
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(intakeX2, intakeY2), Math.PI)

                .afterTime(idleTime, specMec.setPos("Idle", "Score"))
                .afterTime(hangTime, specMec.setPos("Score", "Score"))
                .afterTime(openTime, specMec.openClaw())
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(hangX-6, hangY), 3*Math.PI/2)

                .afterTime(pullOutTime, specMec.setPos("Intake", "Intake"))
                .strafeTo(new Vector2d(hangX-6, 40));





        Thread update = new Thread(()->updateAll(pivot, extension, wrist, specMec));


        // go to start pos
        Actions.runBlocking(
                new SequentialAction(
                        pivot.setPos("Start"),
                        specMec.closeClaw(),
                        specMec.setPos("Start", "Start")
                )
        );
        telemetry.addLine("initpos");

        update.start();

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        pivot.setPos("Basket"),
                        specMec.setPos("Idle", "Score")
                )

        );

        // Pathing start
        /*Actions.runBlocking(
                new SequentialAction(
                        hang0.build(),
                        pushBlocks.build(),
                        hang1.build(),
                        block2.build(),
                        hang2.build(),
                        block3.build(),
                        hang3.build(),
                        park.build()
                )
        );*/

        Actions.runBlocking(wholeThing.build());

        Actions.runBlocking(
                new SequentialAction(
                        pivot.setPos("Down"),
                        specMec.setPos("Intake", "Intake"),
                        new SleepAction(5)
                )

        );





    }
    public void updateAll(RR_Pivot pivot, Extension extension, Wrist wrist, RR_SpecMec specMec) {
        while (opModeInInit() || opModeIsActive())
        {


            Actions.runBlocking(
                    new ParallelAction(
                            pivot.update(),
                            specMec.update()
                    )
            );
            telemetry.update();
        }
    }

}
