package org.firstinspires.ftc.teamcode.subsystems;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;


import org.firstinspires.ftc.teamcode.utils.InterpolatableMap;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.VoltageCompensatingMotor;

@Configurable
public class Shooter implements Subsystem {
    public static Shooter INSTANCE = new Shooter();
    private final VoltageCompensatingMotor shooterMotors = new VoltageCompensatingMotor(new MotorGroup(new MotorEx("shooterRight"), new MotorEx("shooterLeft")));
    public PIDCoefficients coefficients = new PIDCoefficients(0.002, 0.0,0.0);
    public BasicFeedforwardParameters feedForward = new BasicFeedforwardParameters(0.00049,0.0,0.0);
    public ControlSystem controlSystem = ControlSystem.builder().velPid(coefficients).basicFF(feedForward).build();
    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    public double manualNear = 1000;
    public double manualFar = 1200;
    public InterpolatableMap shooterVelocities = new InterpolatableMap();
    private long stableSince = 0;
    private static final long STABLE_NS = 120_000_000; // 120ms
    private static final double TOL = 30;
    private final double offset = 0;


    private Shooter(){
        shooterVelocities.put(35.0, 880.0);
        shooterVelocities.put(45.0, 860.0);
        shooterVelocities.put(55.0, 900.0);
        shooterVelocities.put(65.0, 920.0);
        shooterVelocities.put(75.0, 970.0);
        shooterVelocities.put(85.0, 1000.0);
        shooterVelocities.put(95.0, 1040.0);
        shooterVelocities.put(105.0, 1080.0);
        shooterVelocities.put(115.0, 1130.0);
        shooterVelocities.put(125.0, 1170.0);
        shooterVelocities.put(135.0, 1210.0);
        shooterVelocities.put(145.0, 1240.0);
        shooterVelocities.put(155.0, 1260.0);
        shooterVelocities.put(160.0, 1280.0);

    }

    @Override
    public void initialize(){
        controlSystem.setGoal(shooterMotors.getState());
    }

    @Override
    public void periodic(){
        telemetry.addData("Ticks Speed", shooterMotors.getState().getVelocity());
        telemetry.addData("Goal Speed", controlSystem.getGoal().getVelocity());

        shooterMotors.setPower(controlSystem.calculate(shooterMotors.getState()));

        telemetry.update();
    }


    public Command stopShooter(){
        return new RunToVelocity(controlSystem, 0, 10).setRequirements(this);
    }

    public Command slowShooter(){
        return new RunToVelocity(controlSystem, 600, 10).setRequirements(this);
    }

    public boolean isAtSpeed(double target) {
        double error = Math.abs(shooterMotors.getVelocity() - target);

        if (error < TOL) {
            if (stableSince == 0) {
                stableSince = System.nanoTime();
            }
            return System.nanoTime() - stableSince > STABLE_NS;
        }

        stableSince = 0;
        return false;
    }

    public Command setShooter(){
        return new LambdaCommand()
                .setUpdate(()->controlSystem.setGoal(
                        new KineticState(
                                0,
                                shooterVelocities.interpolate(Chassis.INSTANCE.getDistanceToTarget()) + offset,
                                0)))
                .setIsDone(()-> isAtSpeed(
                        shooterVelocities.interpolate(
                                Chassis.INSTANCE.getDistanceToTarget()
                        ) + offset
                ))
                .requires(this);
    }

    public Command setShooterManualNear(){
        return new LambdaCommand()
                .setUpdate(()->controlSystem.setGoal(new KineticState(0, manualNear, 0)))
                .setIsDone(()-> isAtSpeed(manualNear))
                .requires(this);
    }

    public Command setShooterManualFar(){
        return new LambdaCommand()
                .setUpdate(()->controlSystem.setGoal(new KineticState(0, manualFar, 0)))
                .setIsDone(()-> isAtSpeed(manualFar))
                .requires(this);
    }


}
