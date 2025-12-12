package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autos.FarAuto;
import org.firstinspires.ftc.teamcode.utils.Robot;

@Autonomous(name = "Far Auto - Blue", group = "FarAuto")
public class FarAutoBlue extends FarAuto {
    public FarAutoBlue() {
        super(Robot.Alliance.Blue);
    }
}
