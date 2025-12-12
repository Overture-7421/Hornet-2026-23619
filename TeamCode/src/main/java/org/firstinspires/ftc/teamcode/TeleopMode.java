package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.Robot;
import org.firstinspires.ftc.teamcode.utils.SubsystemComponent;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

public class TeleopMode extends NextFTCOpMode {

    public Robot robot;

    public TeleopMode(Robot.Alliance allianceColor){
        robot = new Robot(allianceColor);

        addComponents(
                new SubsystemComponent(Intake.INSTANCE, Shooter.INSTANCE, Chassis.INSTANCE, Camera.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }

    @Override
    public void onInit() {
        robot.initRobotTeleop();
    }

    @Override
    public void onStartButtonPressed() {
        robot.setBindings();
        Intake.INSTANCE.intakeAutoOff().schedule();
    }

    @Override
    public void onUpdate() {
        BindingManager.update();
    }

    @Override
    public void onStop() {
        BindingManager.reset();

        Chassis.INSTANCE.setLastPose();
    }

}
