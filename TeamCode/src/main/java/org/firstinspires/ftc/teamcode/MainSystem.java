package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.SubsystemComponent;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp
public class MainSystem extends NextFTCOpMode {
    public  MainSystem(){
        addComponents(
                new SubsystemComponent(Climber.INSTANCE, Shooter.INSTANCE, Intake.INSTANCE),
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }

    public void onInit() {

    }

    public void onStartButtonPressed() {

//        Gamepads.gamepad1().y().whenBecomesTrue(Climber.INSTANCE.climb());
//        Gamepads.gamepad1().x().whenBecomesTrue(Climber.INSTANCE.StopClimb());
       
    }

    public  void onUpdate(){
        BindingManager.update( );
    }

    public void  onStop(){
        BindingManager.reset();
    }
}

