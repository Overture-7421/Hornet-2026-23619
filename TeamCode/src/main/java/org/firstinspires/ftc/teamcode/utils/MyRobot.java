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

    private final Chassis chassis;
    private final Intake intake;
    private final Shooter shooter;
    private final Camera camera;
    private final Alliance alliance;
    private final GamepadEx driver;

    private final Follower follower;

    public MyRobot(Alliance alliance, HardwareMap hardwareMap, GamepadEx driver){
        this.driver = driver;
        this.alliance = alliance;
        follower = Constants.createFollower(hardwareMap);

        chassis = new Chassis(follower, driver);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap, chassis);
        camera = new Camera(hardwareMap, follower, chassis);

        setBulkReading(hardwareMap, LynxModule.BulkCachingMode.MANUAL);
    }

    public Chassis getChassis(){
        return this.chassis;
    }

    public Intake getIntake(){
        return this.intake;
    }

    public Shooter getShooter(){
        return this.shooter;
    }

    public Follower follower(){
        return follower;
    }

    public void initTeleop(){
        chassis.setAllianceColor(this.alliance, false);
        chassis.initPedro(false, new Pose(8, 8));
        setBindings();
    }

    public void initAuto(Pose starting){
        chassis.setAllianceColor(this.alliance, true);
        chassis.initPedro(true, starting);
    }

    public void setBindings(){
        chassis.setDefaultCommand(chassis.drive());

        new Trigger(()->driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.3)
                .whileActiveOnce(automaticShoot())
                .whenInactive(stopShooting());
        driver.getGamepadButton(GamepadKeys.Button.A)
                .whenHeld(manualShootFar())
                .whenReleased(stopShooting());
        driver.getGamepadButton(GamepadKeys.Button.B)
                .whenHeld(manualShootNear())
                .whenReleased(stopShooting());
        new Trigger(()->driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.3)
                .whileActiveOnce(intake.intakeCommand())
                .whenInactive(intake.stopCommand());
        driver.getGamepadButton(GamepadKeys.Button.X)
                .whenHeld(intake.shootCommand())
                .whenReleased(intake.stopCommand());
        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenHeld(intake.reverseIntake())
                .whenReleased(intake.stopCommand());
        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenActive(chassis.slowMode())
                .whenInactive(chassis.normalMode());
    }

    public void onEnd(){
        chassis.setLastPose();
    }



    public SequentialCommandGroup shootAutonomous(){
        return new SequentialCommandGroup(
                new InstantCommand(chassis::resetFrames),
                chassis.autoAlign(),
                shooter.setShooter(),
                intake.shootCommand(),
                new WaitCommand(1000)
                );
    }
    public SequentialCommandGroup shootAutonomousFar(){
        return new SequentialCommandGroup(
                chassis.autoAlign(),
                shooter.setShooterManualFar(),
                new InstantCommand(intake::shootCommand, intake),
                new WaitCommand(1400)
        );
    }

    public ParallelCommandGroup stopShooting(){
        return new ParallelCommandGroup(
                new InstantCommand(() -> chassis.isAlignOn = false),
                shooter.stopShooter(),
                intake.stopCommand()
        );
    }

    public SequentialCommandGroup automaticShoot(){
        return new SequentialCommandGroup(
                new InstantCommand(() -> chassis.isAlignOn = true),
                new InstantCommand(chassis::resetFrames),
                new ParallelDeadlineGroup(
                        new WaitUntilCommand(chassis::isAtTargetHeading),
                        shooter.setShooter()
                ),
                shooter.setShooter(),
                intake.shootCommand(),
            new InstantCommand(() -> chassis.isAlignOn = false)
        );
    }

    public SequentialCommandGroup manualShootNear(){
        return new SequentialCommandGroup(
                shooter.setShooterManualNear(),
                intake.shootCommand()
        );
    }
    public SequentialCommandGroup manualShootFar(){
        return new SequentialCommandGroup(
                shooter.setShooterManualFar(),
                intake.shootCommand()
        );
    }
}
