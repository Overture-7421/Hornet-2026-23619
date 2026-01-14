package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.utils.PoseStorage;
import org.firstinspires.ftc.teamcode.utils.Robot.Alliance;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

@Configurable
public class Chassis implements Subsystem {
    public static Chassis INSTANCE = new Chassis();
    public PIDCoefficients headingControl = new PIDCoefficients(0.95, 0, 0);
    public ControlSystem controlSystem = ControlSystem.builder().posPid(headingControl).build();
    private Follower follower;
    public double speedMultiplier;
    private final double turnMultiplier = -0.7;
    private double allianceMultiplier = -1;
    public  Pose target = new Pose(0,0);
    public Pose nearTarget2 = new Pose(0,0);
    public Pose nearTarget = new Pose(4, 140);
    public Pose farTarget2 = new Pose(0,0);
    public Pose farTarget = new Pose(7, 140);

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
    }

    public void setAllianceColor(Alliance allianceColor, boolean isAuto) {
        if (allianceColor == Alliance.Blue) {
            allianceMultiplier = -1;
            target = nearTarget;
            nearTarget2 = nearTarget;
            farTarget2 = farTarget;
        } else {
            allianceMultiplier = 1;
            target = nearTarget.mirror();
            nearTarget2 = nearTarget.mirror();
            farTarget2 = farTarget.mirror();
        }
    }

    public Command selectTarget(){ 
        return new InstantCommand(()->{
            if (getDistanceToTarget() > 122){
                target = farTarget2;
            } else {
                target = nearTarget2;
            }
        });
    }

    public Command drive() {
        return new LambdaCommand()
                .setStart(() -> follower.startTeleopDrive(true))
                .setUpdate(() -> {
                    double turn;
                    if (ActiveOpMode.gamepad1().left_trigger > 0.4) {
                        updateHeadingGoal();
                        turn = controlSystem.calculate(new KineticState(follower.getHeading()));
                    } else {
                        turn = ActiveOpMode.gamepad1().right_stick_x * turnMultiplier;
                    }

                    if(ActiveOpMode.gamepad1().left_bumper){
                        speedMultiplier = 0.01;
                    } else {
                        speedMultiplier = 1;
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
                .setStart(() -> follower.startTeleopDrive(true))
                .setUpdate(() -> {
                    updateHeadingGoal();
                    double turn = controlSystem.calculate(new KineticState(follower.getHeading()));

                    follower.setTeleOpDrive(
                            0,
                            0,
                            turn,
                            false);

                })
                .setStop((Boolean interrupted) -> {
                   follower.breakFollowing();
                })
                .setIsDone(this::isAtTargetHeading)
                .requires(this);
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
        return Math.atan2(tempTarget.getY() - robotPose.getY(), tempTarget.getX() - robotPose.getX()
        );
    }

    public boolean isAtTargetHeading() {
        double error = Math.atan2(
                Math.sin(calculateHeading(target) - follower.getHeading()),
                Math.cos(calculateHeading(target) - follower.getHeading())
        );
        return Math.abs(error) < Math.toRadians(3);
    }

    public double getDistanceToTarget() {
        return follower.getPose().distanceFrom(target);
    }

    public void setLastPose() {
        PoseStorage.currentPose = follower.getPose();
    }

    public void updateHeadingGoal() {
        double robotHeading = follower.getHeading();
        double targetHeading = calculateHeading(target);

        double error = Math.atan2(
                Math.sin(targetHeading - robotHeading),
                Math.cos(targetHeading - robotHeading)
        );

        double continuousTarget = robotHeading + error;

        controlSystem.setGoal(new KineticState(continuousTarget));
    }

}
