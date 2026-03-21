package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;
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
    public boolean isAlignOn = false;
    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    private final PIDController pid = new PIDController(1.0, 0.0, 0.055);

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
        return pid.calculate(follower.getHeading(), updateHeadingGoal());
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
                })
                .whenFinished(follower::breakFollowing)
                .interruptOn(this::isAtTargetHeading);
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

    public double getError(){
        return Math.abs(Math.atan2(
                Math.sin(calculateHeading(target) - follower.getHeading()),
                Math.cos(calculateHeading(target) - follower.getHeading())
        ));
    }

    public boolean isAtTargetHeading() {
        return getError() < Math.toRadians(3);
    }
    public double getDistanceToTarget() {
        return follower.getPose().distanceFrom(target);
    }

    public void setLastPose() {
        PoseStorage.currentPose = follower.getPose();
    }

    public double updateHeadingGoal() {
        double robotHeading = follower.getHeading();
        double targetHeading = calculateHeading(target);

        double error = Math.atan2(
                Math.sin(targetHeading - robotHeading),
                Math.cos(targetHeading - robotHeading)
        );

        return robotHeading + error;
    }
}
