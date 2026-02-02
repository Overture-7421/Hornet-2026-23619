package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.utils.PoseStorage;
import org.firstinspires.ftc.teamcode.utils.Robot.Alliance;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

@Configurable
public class Chassis implements Subsystem {
    public static Chassis INSTANCE = new Chassis();
    private Follower follower;
    public double speedMultiplier;
    public double turnMultiplier = -0.7;
    private double allianceMultiplier = -1;
    public Pose target = new Pose(8,136);
    public Pose pastTarget = new Pose(8,136);
    private int stableFrames = 0;
    public double alignMinSpeed = 0.1;
    public double alignMaxSpeed = 1.0;
    public double deadband = 3.0;
    private static final int REQUIRED_STABLE_FRAMES = 20;
    public boolean isAlignOn = false;
    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();

    private Chassis() {
    }

    @Override
    public void periodic() {
        follower.update();

        telemetry.addData("DistanceToTarget", getDistanceToTarget());
        telemetry.addData("CurrentHeading", Math.toDegrees(follower.getHeading()));
        telemetry.addData("TargetHeading", Math.toDegrees(calculateHeading(target)));
        telemetry.addData("ChassisPos", follower.getPose());
//        telemetry.addData("Is at target", Chassis.INSTANCE.isAtTargetHeading());
    }

    public void setAllianceColor(Alliance allianceColor, boolean isAuto) {
        if (allianceColor == Alliance.Blue) {
            allianceMultiplier = -1;
            target = pastTarget;
        } else {
            allianceMultiplier = 1;
            pastTarget = target;
            target = target.mirror();
        }
    }

    private double calculateAlignmentTurn() {
        double errorRad = getSignedError();
        double errorDeg = Math.abs(Math.toDegrees(errorRad));

        if (errorDeg < deadband) return 0;

        double speed = Math.max(alignMinSpeed, Math.min(alignMaxSpeed, errorDeg / 90));

        return speed * Math.signum(errorRad);
    }

    public Command drive() {
        return new LambdaCommand()
                .setStart(() -> follower.startTeleopDrive(true))
                .setUpdate(() -> {
                    double turn;

                    if(ActiveOpMode.gamepad1().left_bumper){
                        turnMultiplier = -0.35;
                        speedMultiplier = 0.25;
                    } else {
                        turnMultiplier = -0.7;
                        speedMultiplier = 1;
                    }

                    if (isAlignOn) {
                        turn = calculateAlignmentTurn();
                    } else {
                        stableFrames = 0;
                        turn = ActiveOpMode.gamepad1().right_stick_x * turnMultiplier;
                    }

                    follower.setTeleOpDrive(
                            -ActiveOpMode.gamepad1().left_stick_y * allianceMultiplier * speedMultiplier,
                            -ActiveOpMode.gamepad1().left_stick_x * allianceMultiplier * speedMultiplier,
                            turn,
                            false);

                })
                .setStop((Boolean interrupted) -> {
                    if (interrupted) follower.breakFollowing();
                })
                .setIsDone(() -> false)
                .requires(this);
    }

    public Command autoAlign() {
        return new LambdaCommand()
                .setStart(() -> {
                    follower.startTeleopDrive(true);
                    resetFrames();
                })
                .setUpdate(() -> {
                    double turn = calculateAlignmentTurn();

                    follower.setTeleOpDrive(
                            0,
                            0,
                            turn,
                            false);

                })
                .setStop((Boolean interrupted) -> follower.breakFollowing())
                .setIsDone(this::isAtTargetHeading)
                .requires(this);
    }

    public void resetFrames(){
        stableFrames = 0;
    }

    public void initPedro(boolean isAuto, Pose starting) {
        follower = follower();

        if (PoseStorage.currentPose != null && !isAuto) {
            follower.setPose(PoseStorage.currentPose);
        } else if (!isAuto) {
            follower.setPose(starting);
        } else {
            follower.setPose(starting);
        }
    }

    public void startDriving() {
        drive().schedule();
    }

    public double calculateHeading(Pose tempTarget) {
        Pose robotPose = follower.getPose();
        return Math.atan2(tempTarget.getY() - robotPose.getY(), tempTarget.getX() - robotPose.getX());
    }

    public double getSignedError() {
        double targetHeading = calculateHeading(target);
        double robotHeading = follower.getHeading();

        return Math.atan2(
                Math.sin(targetHeading - robotHeading),
                Math.cos(targetHeading - robotHeading)
        );
    }

    public double getError() {
        return Math.abs(getSignedError());
    }

    public boolean isAtTargetHeading() {
        if (Math.toDegrees(getError()) < deadband) {
            stableFrames++;
            return stableFrames >= REQUIRED_STABLE_FRAMES;
        } else {
            stableFrames = 0;
            return false;
        }
    }
    public double getDistanceToTarget() {
        return follower.getPose().distanceFrom(target);
    }

    public void setLastPose() {
        PoseStorage.currentPose = follower.getPose();
    }
}
