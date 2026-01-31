package org.firstinspires.ftc.teamcode.subsystems;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class Camera implements Subsystem {
    public static Camera INSTANCE = new Camera();

    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    private Limelight3A limelight;
    public boolean isMt2 = true;
    private Camera(){}

    @Override
    public void initialize() {
        limelight = ActiveOpMode.hardwareMap().get(Limelight3A.class, "limelight");

        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    @Override
    public void periodic() {
        telemetryAprilTag();
    }

    public Pose convertToPedro(Pose pose) {
        Pose rotatedPose = pose.rotate(-Math.PI / 2, true);
        return rotatedPose.plus(new Pose(72, 72));
    }

    private void telemetryAprilTag() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return;

        Pose3D robotPose = result.getBotpose(); // MT1 only
        Position posePos = robotPose.getPosition().toUnit(DistanceUnit.INCH);
        double poseHeading = robotPose.getOrientation().getYaw(AngleUnit.RADIANS);

        follower().setPose(convertToPedro(new Pose(posePos.x, posePos.y, poseHeading)));
    }
    }