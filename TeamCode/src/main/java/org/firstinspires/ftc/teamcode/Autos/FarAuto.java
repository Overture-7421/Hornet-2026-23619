package org.firstinspires.ftc.teamcode.Autos;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.Paths.FarSidePaths;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.MyRobot;

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

public class FarAuto extends NextFTCOpMode {
    private final MyRobot robot;
    private FarSidePaths paths;

    public FarAuto(MyRobot.Alliance allianceColor){
        robot = new MyRobot(allianceColor);

        addComponents(
                new SubsystemComponent(Intake, Shooter, Chassis),
                new PedroComponent(Constants::createFollower),
                BindingsComponent,
                BulkReadComponent
        );

    }

    public void onInit() {
        paths = new FarSidePaths(follower(), robot.alliance);

        robot.initRobotAuto(paths.startPose);
    }

    @Override
    public void onStartButtonPressed() {

        autoCommand().schedule();
        shooter.offset = 5;
    }

    @Override
    public void onUpdate() {
        BindingManager.update();
    }

    @Override
    public void onStop() {
        BindingManager.reset();

        Chassis.setLastPose();
        shooter.offset = 15;
    }

    public CommandGroup autoCommand(){
        return new SequentialGroup(
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path1, false, 0.7),
                        shooter.setShooterManualFar()
                ),
                robot.shootAutonomousFar(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path2, false, 0.8),
                        Intake.intakeAutoOn(),
                        shooter.stopShooter()
                ),
                new Delay(1),
                Intake.intakeAutoOff(),
                Intake.stopCommand(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path3, false, 0.8),
                        shooter.setShooterManualFar()
                ),
                robot.shootAutonomousFar(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path4, false, 0.7),
                        shooter.stopShooter()
                ),
                new Delay(0.8),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path5, false, 0.7),
                        Intake.intakeAutoOn(),
                        shooter.stopShooter()
                ),
                Intake.intakeAutoOff(),
                Intake.stopCommand(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path6, false, 0.8),
                        shooter.setShooterManualFar()
                ),
                robot.shootAutonomousFar(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path7, false, 0.8),
                        Intake.intakeAutoOn(),
                        shooter.stopShooter()
                ),
                Intake.intakeAutoOff(),
                Intake.stopCommand(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path8, false, 0.8),
                        shooter.setShooterManualFar()
                ),
                robot.shootAutonomousFar(),
                new ParallelDeadlineGroup(
                        new FollowPath(paths.Path9, false, 0.8),
                        Intake.intakeAutoOn(),
                        shooter.stopShooter()
                )
        );
    }
}
