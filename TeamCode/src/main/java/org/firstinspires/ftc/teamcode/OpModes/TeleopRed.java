package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleopMode;
import org.firstinspires.ftc.teamcode.utils.Robot;

@TeleOp(name = "Teleop - Red", group = "Teleop")
public class TeleopRed extends TeleopMode {
    public TeleopRed() {
        super(Robot.Alliance.Red);
    }
}
