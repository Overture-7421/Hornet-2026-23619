package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autos.FarPlayOffs;
import org.firstinspires.ftc.teamcode.utils.MyRobot;

@Autonomous(name = "Far PlayOffs - Red", group = "FarPlayOffs")
public class FarPlayOffsRed extends FarPlayOffs {
    public FarPlayOffsRed() {
        super(MyRobot.Alliance.Red);
    }
}
