package org.firstinspires.ftc.teamcode.Autos;

import org.firstinspires.ftc.teamcode.Paths.CloseSidePaths;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.MyRobot;


import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelDeadlineGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;



public class CloseAuto extends CommandOpMode {
    private MyRobot robot;
    public MyRobot.Alliance alliance;

    private CloseSidePaths paths;

    public CloseAuto(MyRobot.Alliance allianceColor){
        alliance = allianceColor;
    }

    @Override
    public void initialize() {
        robot = new MyRobot(alliance, hardwareMap);
        paths = new CloseSidePaths(robot.follower(), alliance);
        robot.initAuto(paths.startPose);
        super.reset();

    }


    @Override
    public void run() {
        CommandScheduler.getInstance().schedule(autoCommand());
    }

    public SequentialCommandGroup autoCommand(){
        return new SequentialCommandGroup(
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path1, false, 0.7),
                        Shooter.setShooter()
                ),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path2,false, 1.0),
                        Intake.intakeAutoOn(),
                        Shooter.stopShooter(),
                        new SequentialCommandGroup(
                                new WaitCommand(500),
                                Shooter.setShooter()
                        )
                ),
                Intake.intakeAutoOff(),
                Intake.stopCommand(),
                robot.shootAutonomous(),
                new FollowPathCommand(robot.follower(), paths.Path3,false, 1.0),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path4,false, 1.0),
                        Intake.intakeAutoOn(),
                        Shooter.stopShooter()
                ),
                new WaitCommand(1000),
                
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path5, false, 1.0),
                        Shooter.setShooter()
                ),
                Intake.intakeAutoOff(),
                Intake.stopCommand(),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path6,false, 1.0),
                        Intake.intakeAutoOn(),
                        Shooter.stopShooter(),
                        new SequentialCommandGroup(
                                new WaitCommand(200),
                                Shooter.setShooter()
                        )
                ),
                Intake.intakeAutoOff(),
                Intake.stopCommand(),
                robot.shootAutonomous(),
                new ParallelDeadlineGroup(
                        new FollowPathCommand(robot.follower(), paths.Path7,false, 1.0),
                        Intake.intakeAutoOn(),
                        Shooter.stopShooter(),
                        new SequentialCommandGroup(
                                new WaitCommand(500),
                                Shooter.setShooter()
                        )
                ),
                Intake.intakeAutoOff(),
                Intake.stopCommand(),
                robot.shootAutonomous()



        );
    }
}


