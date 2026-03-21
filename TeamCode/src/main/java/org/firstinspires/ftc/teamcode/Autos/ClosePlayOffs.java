package org.firstinspires.ftc.teamcode.Autos;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelDeadlineGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.Paths.CloseSidePaths;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.MyRobot;

public class ClosePlayOffs extends CommandOpMode {
    private MyRobot robot;
    private final MyRobot.Alliance alliance;
    private Intake intake;
    private Shooter shooter;
    private Chassis chassis;
    
    private GamepadEx driver;

    private CloseSidePaths paths;

    public ClosePlayOffs(MyRobot.Alliance allianceColor){
        alliance = allianceColor;
    }

    @Override
    public void initialize() {
        driver = new GamepadEx(gamepad1);
        robot = new MyRobot(alliance, hardwareMap, driver);
        intake = robot.getIntake();
        shooter = robot.getShooter();
        chassis = robot.getChassis();
        paths = new CloseSidePaths(robot.follower(), alliance);
        robot.initAuto(paths.startPose);
        super.reset();

    }

    @Override
    public void run() {
        CommandScheduler.getInstance().schedule(autoCommand());
    }

    @Override
    public void end(){
        chassis.setLastPose();
    }

    public SequentialCommandGroup autoCommand(){
        return new SequentialCommandGroup(
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(),paths.Path1, false, 0.7),
                        shooter.setShooter()
                ),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(),paths.Path2,false, 1.0),
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
                new FollowPathCommand(robot.follower(),paths.Path3,false, 1.0),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(),paths.Path4,false, 1.0),
                        intake.intakeAutoOn(),
                        shooter.stopShooter()
                ),
                new WaitCommand(1000),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(),paths.Path5, false, 1.0),
                        shooter.setShooter()
                ),
                intake.intakeAutoOff(),
                intake.stopCommand(),
                robot.shootAutonomous(),
                new FollowPathCommand(robot.follower(),paths.Path3,false, 1.0),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(),paths.Path4,false, 1.0),
                        intake.intakeAutoOn(),
                        shooter.stopShooter()
                ),
                new WaitCommand(1000),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(),paths.Path5, false, 1.0),
                        shooter.setShooter()
                ),
                intake.intakeAutoOff(),
                intake.stopCommand(),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(),paths.Path6V2,false, 1.0),
                        intake.intakeAutoOn(),
                        shooter.stopShooter(),
                        new SequentialCommandGroup(
                                new WaitCommand(200),
                                shooter.setShooter()
                        )
                ),
                intake.intakeAutoOff(),
                intake.stopCommand(),
                robot.shootAutonomous(),
                shooter.stopShooter(),
                intake.intakeAutoOff(),
                intake.stopCommand(),
                new FollowPathCommand(robot.follower(),paths.End, false, 1.0)

        );
    }
}


