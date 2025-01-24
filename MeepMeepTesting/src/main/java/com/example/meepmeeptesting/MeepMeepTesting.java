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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(16, 34)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, 62, 3*Math.PI/2))
                .strafeTo(new Vector2d(0, 32))

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
                        .setTangent(0)
                .splineToConstantHeading(new Vector2d(-54, 60), Math.PI/2)

                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-6, 40), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-6, 32), 3*Math.PI/2)

                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-6, 40), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(-56, 60), Math.PI/2)

                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-8, 40), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-8, 32), 3*Math.PI/2)

                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-8, 40), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(-56, 60), Math.PI/2)

                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-10, 40), 3*Math.PI/2)
                .splineToConstantHeading(new Vector2d(-10, 32), 3*Math.PI/2)

                .setTangent(3 * Math.PI/2)
                .splineToConstantHeading(new Vector2d(-10, 40), Math.PI/2)
                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(-56, 60), Math.PI/2)


                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}