package org.firstinspires.ftc.teamcode.subsystems;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.util.InterpLUT;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;


@Configurable
public class Shooter extends SubsystemBase {
    private final MotorGroup shooterMotors;
    private final Chassis chassis;
    public static PIDFController controlSystem = new PIDFController(0.004,0.0,0.0,0.00049);
    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    public double manualNear = 1000;
    public double manualFar = 1250;
    public InterpLUT shooterVelocities = new InterpLUT();
    private long stableSince = 0;
    private static final long STABLE_NS = 120_000_000; // 120ms
    private static final double TOL = 30;
    public double offset = 12;


    public Shooter(HardwareMap hardwareMap, Chassis chassis){
        this.chassis = chassis;
        shooterVelocities.add(35.0, 880.0);
        shooterVelocities.add(45.0, 860.0);
        shooterVelocities.add(55.0, 900.0);
        shooterVelocities.add(65.0, 920.0);
        shooterVelocities.add(75.0, 970.0);
        shooterVelocities.add(85.0, 1000.0);
        shooterVelocities.add(95.0, 1040.0);
        shooterVelocities.add(105.0, 1080.0);
        shooterVelocities.add(115.0, 1130.0);
        shooterVelocities.add(125.0, 1170.0);
        shooterVelocities.add(135.0, 1210.0);
        shooterVelocities.add(145.0, 1240.0);
        shooterVelocities.add(155.0, 1260.0);
        shooterVelocities.add(160.0, 1280.0);
        shooterVelocities.createLUT();

        shooterMotors = new MotorGroup(
                new Motor(hardwareMap, "shooterLeft", Motor.GoBILDA.BARE),
                new Motor(hardwareMap, "shooterRight", Motor.GoBILDA.BARE)
        );
        controlSystem.setSetPoint(shooterMotors.getVelocity());

    }


    @Override
    public void periodic(){
        telemetry.addData("Ticks Speed", shooterMotors.getVelocity());
        telemetry.addData("Goal Speed", controlSystem.getSetPoint() + offset);

        shooterMotors.set(controlSystem.calculate(shooterMotors.getVelocity()));

        telemetry.update();
    }


    public InstantCommand stopShooter(){
        return new InstantCommand(()->controlSystem.setSetPoint(0));
    }

    public InstantCommand slowShooter(){
     return new InstantCommand(()-> {
         controlSystem.setSetPoint(600);
     });
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
        return new RunCommand(()-> controlSystem.setSetPoint(shooterVelocities.get(chassis.getDistanceToTarget()) + offset)).interruptOn(()-> isAtSpeed(
                shooterVelocities.get(
                        chassis.getDistanceToTarget()
                ) + offset
        ));
    }

    public Command setShooterManualNear(){
        return new RunCommand(()->controlSystem.setSetPoint(manualNear)).interruptOn(()-> isAtSpeed(manualNear));
    }



    public Command setShooterManualFar(){
        return new RunCommand(()->controlSystem.setSetPoint(manualFar)).interruptOn(()->isAtSpeed(manualFar));
    }


}
