package org.firstinspires.ftc.teamcode.auton_subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.SpecMec;

import java.util.HashMap;

public class RR_SpecMec {

    private SpecMec specMec;



    public RR_SpecMec(HardwareMap hwMap, HashMap<String, String> config) {
        specMec = new SpecMec(hwMap, config);
        specMec.setPosition("Start", "Start");
        specMec.closeClaw();
    }

    public class SetPos implements Action {
        private boolean initialized = false;
        String swPos = "Start", tPos = "Start";

        public SetPos(String swPos, String tPos) {
            this.swPos = swPos;
            this.tPos = tPos;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            specMec.setPosition(swPos, tPos);
            double tar = specMec.getPos();
            packet.put("tar2", tar);
            return false;
        }
    }

    public Action setPos(String swPos, String tPos) {
        return new SetPos(swPos, tPos);
    }

    public class Update implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            specMec.update();
            specMec.updateClaw();
            return false;
        }
    }

    public Action update() {
        return new Update();
    }

    public class CloseClaw implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            specMec.closeClaw();
            return false;
        }
    }

    public Action closeClaw() {
        return new CloseClaw();
    }

    public class OpenClaw implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                initialized = true;
            }

            specMec.openClaw();
            return false;
        }
    }

    public Action openClaw() {
        return new OpenClaw();
    }
}
