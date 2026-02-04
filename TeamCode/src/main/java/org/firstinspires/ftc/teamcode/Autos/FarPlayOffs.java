package org.firstinspires.ftc.teamcode.Autos;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.Paths.FarSidePlayOffsPaths;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.SubsystemComponent;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.CommandGroup;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

public class FarPlayOffs extends NextFTCOpMode {
    private final Robot robot;
    private FarSidePlayOffsPaths paths;

    public FarPlayOffs(Robot.Alliance allianceColor){
        robot = new Robot(allianceColor);

        addComponents(
                new SubsystemComponent(Intake.INSTANCE, Shooter.INSTANCE, Chassis.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );

    }

    public void onInit() {
        paths = new FarSidePlayOffsPaths(follower(), robot.alliance);

        robot.initRobotAuto(paths.startPose);
    }

    @Override
    public void onStartButtonPressed() {
        Shooter.INSTANCE.offset = 5;
        autoCommand().schedule();
    }

    @Override
    public void onUpdate() {
        BindingManager.update();
    }

    @Override
    public void onStop() {
        BindingManager.reset();
        Shooter.INSTANCE.offset = 15;

        Chassis.INSTANCE.setLastPose();
    }

    public CommandGroup autoCommand(){
        return new SequentialGroup(
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path1, false, 0.7),
                        Shooter.INSTANCE.setShooter()
                ),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path2, false, 1.0),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter()
                ),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path3, false, 1.0),
                        Shooter.INSTANCE.setShooter()
                ),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path4, false, 1.0),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter()
                ),
                new Delay(1.5),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path5, false, 1.0),
                        Shooter.INSTANCE.setShooter()
                ),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path4, false, 1.0),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter()
                ),
                new Delay(1.5),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path5, false, 1.0),
                        Shooter.INSTANCE.setShooter()
                ),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path4, false, 1.0),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter()
                ),
                new Delay(1.5),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path5, false, 1.0),
                        Shooter.INSTANCE.setShooter()
                ),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path6, false, 1.0),
                        Intake.INSTANCE.intakeAutoOff(),
                        Intake.INSTANCE.stopCommand(),
                        Shooter.INSTANCE.stopShooter()
                )
        );
    }
}
