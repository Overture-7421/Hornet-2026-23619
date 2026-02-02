package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autos.FarPlayOffs;
import org.firstinspires.ftc.teamcode.utils.Robot;

@Autonomous(name = "Far PlayOffs - Blue", group = "FarPlayOffs")
public class FarPlayOffsBlue extends FarPlayOffs {
    public FarPlayOffsBlue() {
        super(Robot.Alliance.Blue);
    }
}
