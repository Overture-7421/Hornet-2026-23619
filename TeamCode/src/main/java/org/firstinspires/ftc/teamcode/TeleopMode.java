package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.utils.MyRobot;



public class TeleopMode extends CommandOpMode {

    public MyRobot robot;

    public GamepadEx driver = new GamepadEx(gamepad1);

    public TeleopMode(MyRobot.Alliance allianceColor){
        robot = new MyRobot(allianceColor, hardwareMap, driver);

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
