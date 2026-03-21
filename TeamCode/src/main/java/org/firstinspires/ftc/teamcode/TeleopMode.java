package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.utils.MyRobot;



public class TeleopMode extends CommandOpMode {

    public MyRobot robot;
    public GamepadEx driver;
    public MyRobot.Alliance color;


    public TeleopMode(MyRobot.Alliance allianceColor){
        color = allianceColor;

    }

    @Override
    public void initialize(){
        super.reset();
        driver =  new GamepadEx(gamepad1);
        robot = new MyRobot(color, hardwareMap, driver);
        robot.initTeleop();
    }

    @Override
    public void run() {
        robot.run();
    }

    @Override
    public void end() {
        robot.onEnd();
    }

}
