package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.ftc.Gamepads;

public class Robot {

    public enum Alliance {
        Red,
        Blue
    }

    public Alliance alliance;

    public Robot(Alliance alliance){
        this.alliance = alliance;
    }

    public void initRobotTeleop(){
        Chassis.INSTANCE.setAllianceColor(this.alliance, false);
        Chassis.INSTANCE.initPedro(false, new Pose(8, 8));
    }

    public void initRobotAuto(Pose starting){
        Chassis.INSTANCE.setAllianceColor(this.alliance, true);
        Chassis.INSTANCE.initPedro(true, starting);
    }

    public void setBindings(){
        Chassis.INSTANCE.startDriving();

        Gamepads.gamepad1().leftTrigger().greaterThan(0.3)
                .whenBecomesTrue(automaticShoot())
                .whenBecomesFalse(stopShooting());
        Gamepads.gamepad1().a()
                .whenBecomesTrue(manualShootNear())
                .whenBecomesFalse(stopShooting());
        Gamepads.gamepad1().b()
                .whenBecomesTrue(manualShootFar())
                .whenBecomesFalse(stopShooting());
        Gamepads.gamepad1().rightTrigger().greaterThan(0.3)
                .whenTrue(startIntake())
                .whenBecomesFalse(stopIntake());
        Gamepads.gamepad1().x()
                .whenTrue(spitArtefacts())
                .whenBecomesFalse(Intake.INSTANCE.stopCommand());
        Gamepads.gamepad1().rightBumper()
                .whenTrue(Intake.INSTANCE.reverseIntake())
                .whenBecomesFalse(Intake.INSTANCE.stopCommand());
    }



    public Command shootAutonomous(){
        return new SequentialGroup(
                Chassis.INSTANCE.selectTarget(),
                Chassis.INSTANCE.autoAlign(),
                Shooter.INSTANCE.setShooter(),
                Intake.INSTANCE.shootCommand(),
                new Delay(1.5)
                ).setRequirements(Intake.INSTANCE, Shooter.INSTANCE, Chassis.INSTANCE);
    }
    public Command shootAutonomousFar(){
        return new SequentialGroup(
                Chassis.INSTANCE.autoAlign(),
                Shooter.INSTANCE.setShooterManualFar(),
                Intake.INSTANCE.shootCommand(),
                new Delay(1.4)
        ).setRequirements(Intake.INSTANCE, Shooter.INSTANCE, Chassis.INSTANCE);
    }

    public Command stopShooting(){
        return new ParallelGroup(
                new InstantCommand(() -> Chassis.INSTANCE.isAlignOn = false),
                Shooter.INSTANCE.stopShooter(),
                Intake.INSTANCE.stopCommand()
        ).setRequirements(Intake.INSTANCE, Shooter.INSTANCE);
    }

    public Command automaticShoot(){
        return new SequentialGroup(
                new InstantCommand(() -> Chassis.INSTANCE.isAlignOn = true),
                new ParallelGroup(
                        Chassis.INSTANCE.selectTarget(),
                        new InstantCommand(()->Chassis.INSTANCE.resetPID())
                ),
                new ParallelGroup(
                        new WaitUntil(()->Chassis.INSTANCE.isAtTargetHeading()),
                        Shooter.INSTANCE.setShooter()
                ),
                Intake.INSTANCE.shootCommand(),
                new InstantCommand(() -> Chassis.INSTANCE.isAlignOn = false)
        ).setRequirements(Intake.INSTANCE, Shooter.INSTANCE);
    }

    public Command manualShootNear(){
        return new SequentialGroup(
                Chassis.INSTANCE.selectTarget(),
                Shooter.INSTANCE.setShooterManualNear(),
                Intake.INSTANCE.shootCommand()
        ).setRequirements(Intake.INSTANCE, Shooter.INSTANCE);
    }
    public Command manualShootFar(){
        return new SequentialGroup(
                Chassis.INSTANCE.selectTarget(),
                Shooter.INSTANCE.setShooterManualFar(),
                Intake.INSTANCE.shootCommand()
        ).setRequirements(Intake.INSTANCE, Shooter.INSTANCE);
    }

    public Command startIntake(){
        return new SequentialGroup(
                Intake.INSTANCE.intakeCommand(),
                Intake.INSTANCE.openServo()
        ).setRequirements(Intake.INSTANCE);
    }
    public Command stopIntake(){
        return new ParallelGroup(
                Intake.INSTANCE.stopCommand(),
                Intake.INSTANCE.openServo()
        ).setRequirements(Intake.INSTANCE);}
    public Command spitArtefacts(){
        return new SequentialGroup(
                Intake.INSTANCE.openServo(),
              Intake.INSTANCE.shootCommand()

        ).setRequirements(Intake.INSTANCE);
    }
}
