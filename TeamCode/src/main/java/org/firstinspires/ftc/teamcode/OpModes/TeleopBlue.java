package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleopMode;
import org.firstinspires.ftc.teamcode.utils.Robot;

@TeleOp(name = "Teleop - Blue", group = "Teleop")
public class TeleopBlue extends TeleopMode {
    public TeleopBlue() {
        super(Robot.Alliance.Blue);
    }
}
