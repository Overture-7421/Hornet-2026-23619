package org.firstinspires.ftc.teamcode.subsystems;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

@Configurable
public class Camera implements Subsystem {
    public static Camera INSTANCE = new Camera();
    private Limelight3A limelight;
    private static final double CLOSE_POSITION_BLEND = 0.6;
    private static final double CLOSE_HEADING_BLEND = 0.6;

    private static final double FAR_POSITION_BLEND = 0.4;
    private static final double FAR_HEADING_BLEND = 0.9;
    private static final double CLOSE_DISTANCE_THRESHOLD = 120.0;  // inches

    private Camera() {}

    @Override
    public void initialize() {
        limelight = ActiveOpMode.hardwareMap().get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    @Override
    public void periodic() {
        updatePoseWithAprilTag();
    }

    public Pose convertToPedro(Pose pose) {
        Pose rotatedPose = pose.rotate(-Math.PI / 2, true);
        return rotatedPose.plus(new Pose(72, 72));
    }

    private double normalizeAngle(double angle) {
        return Math.atan2(Math.sin(angle), Math.cos(angle));
    }

    private void updatePoseWithAprilTag() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return;

        Pose3D robotPose = result.getBotpose();
        Position posePos = robotPose.getPosition().toUnit(DistanceUnit.INCH);

        Pose aprilTag = convertToPedro(new Pose(posePos.x, posePos.y, robotPose.getOrientation().getYaw(AngleUnit.RADIANS)));
        Pose current = follower().getPose();

        boolean isClose = Chassis.INSTANCE.getDistanceToTarget() < CLOSE_DISTANCE_THRESHOLD;
        double posBlend = isClose ? CLOSE_POSITION_BLEND : FAR_POSITION_BLEND;
        double headBlend = isClose ? CLOSE_HEADING_BLEND : FAR_HEADING_BLEND;

        follower().setPose(new Pose(
                current.getX() + posBlend * (aprilTag.getX() - current.getX()),
                current.getY() + posBlend * (aprilTag.getY() - current.getY()),
                current.getHeading() + headBlend * normalizeAngle(aprilTag.getHeading() - current.getHeading())
        ));
    }
}