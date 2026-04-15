package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.InvertedFTCCoordinates;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;


@Configurable
public class Camera extends SubsystemBase {
    private final Limelight3A limelight;
    private final Chassis chassis;
    public static double CLOSE_POSITION_BLEND = 0.6;
    public static double CLOSE_HEADING_BLEND = 0.6;
    public static double FAR_POSITION_BLEND = 0.4;
    public static double FAR_HEADING_BLEND = 0.9;
    private static final double CLOSE_DISTANCE_THRESHOLD = 120.0;  // inches
    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();

    private boolean isActive = true;

    private final Follower follower;
    public Camera(HardwareMap hardwareMap, Follower follower, Chassis chassis) {
        this.chassis = chassis;
        this.follower = follower;
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();
    }



    @Override
    public void periodic() {

        updatePoseWithAprilTag();
    }

    public void setActive(boolean isActive){
        this.isActive =  isActive;
    }

    public Pose convertToPedro(Pose pose) {
        Pose rotatedPose = pose.rotate(-Math.PI / 2, true);
        return rotatedPose.plus(new Pose(72, 72));
    }

    private double normalizeAngle(double angle) {
        return Math.atan2(Math.sin(angle), Math.cos(angle));
    }

    private void updatePoseWithAprilTag() {
        if (isActive) {
            LLResult result = limelight.getLatestResult();
            if (result == null || !result.isValid()) return;

            Pose3D robotPose = result.getBotpose();
            Position posePos = robotPose.getPosition().toUnit(DistanceUnit.INCH);

            Pose aprilTag = convertToPedro(new Pose(posePos.x, posePos.y, robotPose.getOrientation().getYaw(AngleUnit.RADIANS)));
            Pose current = follower.getPose();

            boolean isClose = chassis.getDistanceToTarget() < CLOSE_DISTANCE_THRESHOLD;
            double posBlend = isClose ? CLOSE_POSITION_BLEND : FAR_POSITION_BLEND;
            double headBlend = isClose ? CLOSE_HEADING_BLEND : FAR_HEADING_BLEND;

            follower.setPose(new Pose(
                    current.getX() + posBlend * (aprilTag.getX() - current.getX()),
                    current.getY() + posBlend * (aprilTag.getY() - current.getY()),
                    current.getHeading() + headBlend * normalizeAngle(aprilTag.getHeading() - current.getHeading())
            ));
        }
    }
}