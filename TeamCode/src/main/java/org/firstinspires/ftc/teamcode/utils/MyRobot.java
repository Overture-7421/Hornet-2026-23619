package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.ParallelDeadlineGroup;
import com.seattlesolvers.solverslib.command.Robot;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;


public class MyRobot extends Robot {

    public enum Alliance {
        Red,
        Blue
    }

    private Chassis chassis;
    private Intake intake;
    private Shooter shooter;
    private Camera camera;

    private final Alliance alliance;
    private GamepadEx driver;

    private final Follower follower;

    public MyRobot(Alliance alliance, HardwareMap hardwareMap){
        this.alliance = alliance;
        follower = Constants.createFollower(hardwareMap);

        chassis = new Chassis(hardwareMap, follower);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        camera = new Camera(hardwareMap);

        setBulkReading(hardwareMap, LynxModule.BulkCachingMode.MANUAL);
    }

    public Follower follower(){
        return follower;
    }

    public void initTeleop(){
        Chassis.setAllianceColor(this.alliance, false);
        Chassis.initPedro(false, new Pose(8, 8));
        setBindings();
    }

    public void initAuto(Pose starting){
        Chassis.setAllianceColor(this.alliance, true);
        Chassis.initPedro(true, starting);
    }

    public void setBindings(){
        Chassis.setDefaultCommand(Chassis.startDriving());

        new Trigger(()->driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.3)
                .whileActiveOnce(automaticShoot())
                .whenInactive(stopShooting());
        driver.getGamepadButton(GamepadKeys.Button.A)
                .whenHeld(manualShootFar())
                .whenReleased(stopShooting());
        driver.getGamepadButton(GamepadKeys.Button.B)
                .whenHeld(manualShootFar())
                .whenReleased(stopShooting());
        new Trigger(()->driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.3)
                .whileActiveOnce(Intake.intakeCommand())
                .whenInactive(Intake.stopCommand());
        driver.getGamepadButton(GamepadKeys.Button.X)
                .whenHeld(Intake.shootCommand())
                .whenReleased(Intake.stopCommand());
        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenHeld(Intake.reverseIntake())
                .whenReleased(Intake.stopCommand());
    }

    public void onEnd(){
        Chassis.setLastPose();
    }



    public SequentialCommandGroup shootAutonomous(){
        return new SequentialCommandGroup(
                new InstantCommand(()->Chassis.resetFrames()),
                Chassis.autoAlign(),
                Shooter.setShooter(),
                Intake.shootCommand(),
                new WaitCommand(1000)
                );
    }
    public SequentialCommandGroup shootAutonomousFar(){
        return new SequentialCommandGroup(
                Chassis.autoAlign(),
                Shooter.setShooterManualFar(),
                Intake.shootCommand(),
                new WaitCommand(1400)
        );
    }

    public ParallelCommandGroup stopShooting(){
        return new ParallelCommandGroup(
                new InstantCommand(() -> Chassis.isAlignOn = false),
                Shooter.stopShooter(),
                Intake.stopCommand()
        );
    }

    public SequentialCommandGroup automaticShoot(){
        return new SequentialCommandGroup(
                new InstantCommand(() -> Chassis.isAlignOn = true),
                new InstantCommand(()->Chassis.resetFrames()),
                new ParallelDeadlineGroup(
                        new WaitUntilCommand(()->Chassis.isAtTargetHeading()),
                        Shooter.setShooter()
                ),
                Shooter.setShooter(),
                Intake.shootCommand(),
            new InstantCommand(() -> Chassis.isAlignOn = false)
        );
    }

    public SequentialCommandGroup manualShootNear(){
        return new SequentialCommandGroup(
                Shooter.setShooterManualNear(),
                Intake.shootCommand()
        );
    }
    public SequentialCommandGroup manualShootFar(){
        return new SequentialCommandGroup(
                Shooter.setShooterManualFar(),
                Intake.shootCommand()
        );
    }
}
