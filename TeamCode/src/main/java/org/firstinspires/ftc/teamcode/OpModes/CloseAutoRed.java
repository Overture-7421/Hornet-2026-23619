package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autos.CloseAuto;
import org.firstinspires.ftc.teamcode.utils.Robot;

@Autonomous(name = "Close Auto - Red", group = "CloseAuto")
public class CloseAutoRed extends CloseAuto {
    public CloseAutoRed() {
        super(Robot.Alliance.Red);
    }
}
