package org.firstinspires.ftc.teamcode.auton_subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Pivot;

import java.util.HashMap;

public class RR_Extension {

    private Extension extension;

    String targetPos;

    public RR_Extension(HardwareMap hwMap, HashMap<String, String> config) {
        extension = new Extension(hwMap, config);
    }

    public class SetPos implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            extension.setPos(targetPos);
            double tar = extension.getError();
            packet.put("tar", tar);
            return false;
        }
    }

    public Action setPos(String pos) {
        targetPos = pos;
        return new SetPos();
    }

    public class Update implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            extension.update();
            double pow = extension.getPower();
            packet.put("pow", pow);
            return true;
        }
    }

    public Action update() {
        return new Update();
    }
}
