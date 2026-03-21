package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.FunctionalCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.utils.PoseStorage;
import org.firstinspires.ftc.teamcode.utils.MyRobot.Alliance;

@Configurable
public class Chassis extends SubsystemBase {
    private final Follower follower;
    private final GamepadEx driver;
    public double speedMultiplier = 1;
    public double turnMultiplier = -0.7;
    private double allianceMultiplier = -1;
    public Pose target = new Pose(8,136);
    public Pose blueTarget = new Pose(8,136);

    public Pose redTarget = blueTarget.mirror();

    private int stableFrames = 0;
    public double alignMinSpeed = 0.1;
    public double alignMaxSpeed = 1.0;
    public double deadband = 3.0;
    private static final int REQUIRED_STABLE_FRAMES = 20;
    public boolean isAlignOn = false;
    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();

    public Chassis(Follower follower, GamepadEx driver) {
        this.follower = follower;
        this.driver = driver;
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
            target = blueTarget;
        } else {
            allianceMultiplier = 1;
            target = redTarget;
        }
    }

    private double calculateAlignmentTurn() {
        double errorRad = getSignedError();
        double errorDeg = Math.abs(Math.toDegrees(errorRad));

        if (errorDeg < deadband) return 0;

        double speed = Math.max(alignMinSpeed, Math.min(alignMaxSpeed, errorDeg / 90));

        return speed * Math.signum(errorRad);
    }

    public InstantCommand slowMode(){
        return new InstantCommand(()->{
            turnMultiplier = -0.35;
            speedMultiplier = 0.25;
        });
    }

    public InstantCommand normalMode(){
        return new InstantCommand(()->{
            turnMultiplier = -0.7;
            speedMultiplier = 1;
        });
    }

    public Command drive() {
        return new RunCommand(()->{
            double turn;

            if (isAlignOn) {
                turn = calculateAlignmentTurn();
            } else {
                stableFrames = 0;
                turn = driver.getRightX() * turnMultiplier;
            }

            follower.setTeleOpDrive(
                    driver.getLeftY() * allianceMultiplier * speedMultiplier,
                    driver.getLeftX() * -allianceMultiplier * speedMultiplier,
                    turn,
                    false);
        })
                .addRequirements(this)
                .beforeStarting(() -> follower.startTeleopDrive(true))
                .whenFinished(follower::breakFollowing);
    }

    public Command autoAlign() {
        return new RunCommand(() -> {
            double turn = calculateAlignmentTurn();

            follower.setTeleOpDrive(
                    0,
                    0,
                    turn,
                    false);

        })
                .beforeStarting(() -> {
                    follower.startTeleopDrive(true);
                    resetFrames();
                })
                .whenFinished(follower::breakFollowing)
                .interruptOn(this::isAtTargetHeading);
    }

    public void resetFrames(){
        stableFrames = 0;
    }

    public void initPedro(boolean isAuto, Pose starting) {
        if (PoseStorage.currentPose != null && !isAuto) {
            follower.setPose(PoseStorage.currentPose);
        } else if (!isAuto) {
            follower.setPose(starting);
        } else {
            follower.setPose(starting);
        }
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
