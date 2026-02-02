package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autos.ClosePlayOffs;
import org.firstinspires.ftc.teamcode.utils.Robot;

@Autonomous(name = "Close PlayOffs - Blue", group = "ClosePlayOffsRed")
public class ClosePlayOffsBlue extends ClosePlayOffs {
    public ClosePlayOffsBlue() {
        super(Robot.Alliance.Blue);
    }
}
