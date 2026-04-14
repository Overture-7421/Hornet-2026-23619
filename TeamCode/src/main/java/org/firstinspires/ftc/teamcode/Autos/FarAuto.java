package org.firstinspires.ftc.teamcode.Autos;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelDeadlineGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.Paths.FarSidePaths;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.MyRobot;

public class FarAuto extends CommandOpMode {
    private MyRobot robot;
    private final MyRobot.Alliance alliance;
    private Intake intake;
    private Shooter shooter;
    private Chassis chassis;
    private GamepadEx driver;
    private FarSidePaths paths;

    public FarAuto(MyRobot.Alliance allianceColor){
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
        paths = new FarSidePaths(robot.follower(), alliance);
        robot.initAuto(paths.startPose);
        shooter.offset = 5;
        SequentialCommandGroup autoCommand = new SequentialCommandGroup(
                    new ParallelDeadlineGroup(
                            new FollowPathCommand(robot.follower(), paths.Path1, false, 0.7),
                            shooter.setShooter()
                    ),
                    robot.shootAutonomous(),
                    new ParallelDeadlineGroup(
                            new FollowPathCommand(robot.follower(), paths.Path2, false, 0.8),
                            intake.intakeAutoOn(),
                            shooter.stopShooter()
                    ),
                    new WaitCommand(1000),
                    intake.intakeAutoOff(),
                    intake.stopCommand(),
                    new ParallelDeadlineGroup(
                            new FollowPathCommand(robot.follower(), paths.Path3, false, 0.8),
                            shooter.setShooter()
                    ),
                    robot.shootAutonomous(),
                    new ParallelDeadlineGroup(
                            new FollowPathCommand(robot.follower(), paths.Path4, false, 0.7),
                            shooter.stopShooter()
                    ),
                    new WaitCommand(800),
                    new ParallelDeadlineGroup(
                            new FollowPathCommand(robot.follower(), paths.Path5, false, 0.7),
                            intake.intakeAutoOn(),
                            shooter.stopShooter()
                    ),
                    intake.intakeAutoOff(),
                    intake.stopCommand(),
                    new ParallelDeadlineGroup(
                            new FollowPathCommand(robot.follower(), paths.Path6, false, 0.8),
                            shooter.setShooter()
                    ),
                    robot.shootAutonomous(),
                    new ParallelDeadlineGroup(
                            new FollowPathCommand(robot.follower(), paths.Path7, false, 0.8),
                            intake.intakeAutoOn(),
                            shooter.stopShooter()
                    ),
                    intake.intakeAutoOff(),
                    intake.stopCommand(),
                    new ParallelDeadlineGroup(
                            new FollowPathCommand(robot.follower(), paths.Path8, false, 0.8),
                            shooter.setShooter()
                    ),
                    robot.shootAutonomous(),
                    new ParallelDeadlineGroup(
                            new FollowPathCommand(robot.follower(), paths.Path9, false, 0.8),
                            intake.intakeAutoOn(),
                            shooter.stopShooter()
                    )
            );
     schedule(autoCommand);
    }

    @Override
    public void run() {
        robot.run();

    }

    @Override
    public void end(){
        chassis.setLastPose();
        shooter.offset = 15;
    }

    }

