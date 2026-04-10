package org.firstinspires.ftc.teamcode.Autos;


import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelDeadlineGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.Paths.FarSidePlayOffsPaths;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.MyRobot;

public class FarPlayOffs extends CommandOpMode {
    private MyRobot robot;
    private final MyRobot.Alliance alliance;
    private Intake intake;
    private Shooter shooter;
    private Chassis chassis;
    private GamepadEx driver;
    private FarSidePlayOffsPaths paths;

    public FarPlayOffs(MyRobot.Alliance allianceColor){
        alliance = allianceColor;
    }

    @Override
    public void initialize() {
        super.reset();
        driver = new GamepadEx(gamepad1);
        robot = new MyRobot(alliance, hardwareMap, driver);
        intake = robot.getIntake();
        shooter = robot.getShooter();
        chassis = robot.getChassis();
        paths = new FarSidePlayOffsPaths(robot.follower(), alliance);
        robot.initAuto(paths.startPose);

        shooter.offset = 0;
    }

    @Override
    public void run() {
        robot.run();
        autoCommand().schedule();
    }

    @Override
    public void end(){
        chassis.setLastPose();
        shooter.offset = 15;
    }

    public SequentialCommandGroup autoCommand(){
        return new SequentialCommandGroup(
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path1, false, 0.7),
                        shooter.setShooter()
                ),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path2, false, 0.827),
                        intake.intakeAutoOn(),
                        shooter.stopShooter(),
                        new SequentialCommandGroup(
                                new WaitCommand(500),
                                shooter.setShooter()
                        )
                ),
                intake.intakeAutoOff(),
                intake.stopCommand(),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path3, false, 1.0),
                        intake.intakeAutoOn(),
                        shooter.stopShooter()
                ),
                new WaitCommand(400),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path4, false, 1.0),
                        new SequentialCommandGroup(
                                new WaitCommand(700),
                                shooter.setShooter()
                        )
                ),
                intake.intakeAutoOff(),
                intake.stopCommand(),
                robot.shootAutonomous(),
                new WaitCommand(400),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path3, false, 1.0),
                        intake.intakeAutoOn(),
                        shooter.stopShooter()
                ),
                new WaitCommand(200),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path4, false, 1.0),
                        new SequentialCommandGroup(
                                new WaitCommand(700),
                                shooter.setShooter()
                        )
                ),
                intake.intakeAutoOff(),
                intake.stopCommand(),
                robot.shootAutonomous(),
                new WaitCommand(400),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path3, false, 1.0),
                        intake.intakeAutoOn(),
                        shooter.stopShooter()
                ),
                new WaitCommand(200),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path4, false, 1.0),
                        new SequentialCommandGroup(
                                new WaitCommand(700),
                                shooter.setShooter()
                        )
                ),
                intake.intakeAutoOff(),
                intake.stopCommand(),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path6, false, 1.0),
                        shooter.stopShooter()
                )
        );
    }
}
