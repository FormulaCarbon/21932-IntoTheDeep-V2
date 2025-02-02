package org.firstinspires.ftc.teamcode.auton_subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Pivot;

import java.util.HashMap;

public class RR_Pivot {

    private Pivot pivot;



    public RR_Pivot(HardwareMap hwMap, HashMap<String, String> config) {
        pivot = new Pivot(hwMap, config);
        pivot.setPos("Start");
    }

    public class SetPos implements Action {
        private boolean initialized = false;
        String targetPos = "Start";
        public SetPos(String targetPos) {
            this.targetPos = targetPos;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            pivot.setPos(targetPos);
            int tar = pivot.getTarget();
            packet.put("tar", tar);
            return false;
        }
    }

    public Action setPos(String pos) {
        return new SetPos(pos);
    }

    public class Update implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            pivot.update();
            double pow = pivot.getPower();
            packet.put("pow", pow);
            return false;
        }
    }

    public Action update() {
        return new Update();
    }
}
