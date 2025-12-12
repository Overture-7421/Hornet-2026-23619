package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autos.FarAuto;
import org.firstinspires.ftc.teamcode.utils.Robot;

@Autonomous(name = "Far Auto - Red", group = "FarAuto")
public class FarAutoRed extends FarAuto {
    public FarAutoRed() {
        super(Robot.Alliance.Red);
    }
}
