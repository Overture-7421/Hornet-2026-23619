package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.utils.MyRobot;



public class TeleopMode extends CommandOpMode {

    public MyRobot robot;

    public TeleopMode(MyRobot.Alliance allianceColor){
        robot = new MyRobot(allianceColor, hardwareMap);

    }

    @Override
    public void initialize(){
        robot.initTeleop();
        super.reset();
    }

    @Override
    public void run() {

    }

    @Override
    public void end() {
        robot.onEnd();
    }

}
