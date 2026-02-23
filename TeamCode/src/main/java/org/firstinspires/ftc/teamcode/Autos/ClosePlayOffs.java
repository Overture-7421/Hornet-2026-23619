package org.firstinspires.ftc.teamcode.Autos;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.Paths.CloseSidePaths;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.MyRobot;

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

public class ClosePlayOffs extends NextFTCOpMode {
    private final MyRobot robot;

    private CloseSidePaths paths;

    public ClosePlayOffs(MyRobot.Alliance allianceColor){
        robot = new MyRobot(allianceColor);

        addComponents(
                new SubsystemComponent(Intake.INSTANCE, Shooter.INSTANCE, Chassis.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }

    public void onInit() {
        paths = new CloseSidePaths(follower(), robot.alliance);

        robot.initRobotAuto(paths.startPose);

    }

    @Override
    public void onStartButtonPressed() {
        autoCommand().schedule();
    }

    @Override
    public void onUpdate() {
        BindingManager.update();
    }

    @Override
    public void onStop() {
        BindingManager.reset();

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
                        new FollowPath(paths.Path2,false, 1.0),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter(),
                        new SequentialGroup(
                                new Delay(0.5),
                                Shooter.INSTANCE.setShooter()
                        )
                ),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                robot.shootAutonomous(),
                new FollowPath(paths.Path3,false, 1.0),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path4,false, 1.0),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter()
                ),
                new Delay(1.0),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path5, false, 1.0),
                        Shooter.INSTANCE.setShooter()
                ),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                robot.shootAutonomous(),
                new FollowPath(paths.Path3,false, 1.0),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path4,false, 1.0),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter()
                ),
                new Delay(1.0),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path5, false, 1.0),
                        Shooter.INSTANCE.setShooter()
                ),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path6V2,false, 1.0),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter(),
                        new SequentialGroup(
                                new Delay(0.2),
                                Shooter.INSTANCE.setShooter()
                        )
                ),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                robot.shootAutonomous(),
                Shooter.INSTANCE.stopShooter(),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                new FollowPath(paths.End, false, 1.0)

        );
    }
}


