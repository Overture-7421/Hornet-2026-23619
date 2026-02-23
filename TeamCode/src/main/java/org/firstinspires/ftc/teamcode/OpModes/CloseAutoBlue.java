package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autos.CloseAuto;
import org.firstinspires.ftc.teamcode.utils.MyRobot;

@Autonomous(name = "Close Auto - Blue", group = "CloseAuto")
public class CloseAutoBlue extends CloseAuto {
    public CloseAutoBlue() {
        super(MyRobot.Alliance.Blue);
    }
}
