package org.firstinspires.ftc.teamcode.Autos;

import org.firstinspires.ftc.teamcode.Paths.CloseSidePaths;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.SubsystemComponent;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.CommandGroup;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

public class CloseAuto extends NextFTCOpMode {
    private final Robot robot;

    private CloseSidePaths paths;

    public CloseAuto(Robot.Alliance allianceColor){
        robot = new Robot(allianceColor);

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
                        new FollowPath(paths.path1, false, 0.7),
                        Shooter.INSTANCE.stopShooter()
                ),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path2,false, 0.7),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter()
                ),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                new FollowPath(paths.Path3,false, 0.65),
                new Delay(0.8),
                new FollowPath(paths.Path4,false, 0.75),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path5, false, 0.7),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter()
                ),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                new FollowPath(paths.Path6),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path7, false, 1.0),
                        Intake.INSTANCE.intakeAutoOn(),
                        Shooter.INSTANCE.stopShooter()
                ),
                Intake.INSTANCE.intakeAutoOff(),
                Intake.INSTANCE.stopCommand(),
                new FollowPath(paths.Path8, false),
                robot.shootAutonomous(),
                new ParallelGroup(
                        Shooter.INSTANCE.stopShooter(),
                        new FollowPath(paths.Path9, false, 0.8),
                        Intake.INSTANCE.stopCommand()
                )

        );
    }
}


