package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autos.CloseAuto;
import org.firstinspires.ftc.teamcode.utils.MyRobot;

@Autonomous(name = "Close Auto - Red", group = "CloseAuto")
public class CloseAutoRed extends CloseAuto {
    public CloseAutoRed() {
        super(MyRobot.Alliance.Red);
    }
}
