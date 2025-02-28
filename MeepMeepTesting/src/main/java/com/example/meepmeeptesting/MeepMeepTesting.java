package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 30, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(16, 16)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 58, 0))
                .strafeToLinearHeading(new Vector2d(0, 26), 3*Math.PI/2)

                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(0, 40), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-38, 40), 3*Math.PI/2)
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-38, 16), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-48, 16), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-48, 52), Math.PI/2)
                .waitSeconds(0.1)
                .splineToConstantHeading(new Vector2d(-48, 16), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-58, 16), Math.PI/2)
                .splineToConstantHeading(new Vector2d(-58, 52), Math.PI/2)

                .splineToConstantHeading(new Vector2d(-38, 60), Math.PI/2)

                // block 2

                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-2, 26), 3*Math.PI/2)

                //block 3
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-2, 30), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(-50, 60), Math.PI/2)

                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-4, 26), 3*Math.PI/2)

                // block 4
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-4, 30), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(-50, 60), Math.PI)

                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-6, 26), 3*Math.PI/2)

                // block 5
                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-6, 30), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(-50, 60), Math.PI)

                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-8, 26), Math.PI/2)

                //park
                        .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(-37, 60), Math.PI)



                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}