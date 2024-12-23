package org.firstinspires.ftc.teamcode.archive.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.archive.subsystems.Extension;

import java.util.HashMap;

@TeleOp
@Config
public class extensionsTest extends LinearOpMode {
    public static int pos = 100;
    public HashMap<String, String> deviceConf = new HashMap<String, String>();
    @Override
    public void runOpMode() throws InterruptedException {
        deviceConf.put("leftExtension",   "leftExtension");
        deviceConf.put("rightExtension",  "rightExtension");
        Extension extension = new Extension(hardwareMap, deviceConf);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.a)
            {
                extension.setDirectPos(pos);
            }
            if (gamepad1.b)
            {
                extension.setDirectPos(10);
            }

            extension.update();


            telemetry.addData("Cur", extension.getCurPos());
            telemetry.addData("Tar", extension.getTarPos());

            telemetry.update();


        }
    }
}