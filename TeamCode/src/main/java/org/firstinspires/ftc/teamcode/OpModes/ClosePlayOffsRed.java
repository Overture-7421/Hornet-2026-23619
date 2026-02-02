package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autos.ClosePlayOffs;
import org.firstinspires.ftc.teamcode.utils.Robot;

@Autonomous(name = "Close PlayOffs - Red", group = "ClosePlayOffsRed")
public class ClosePlayOffsRed extends ClosePlayOffs {
    public ClosePlayOffsRed() {
        super(Robot.Alliance.Red);
    }
}
