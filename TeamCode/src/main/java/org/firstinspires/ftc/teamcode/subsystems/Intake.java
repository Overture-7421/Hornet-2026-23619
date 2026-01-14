package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;

public class Intake implements Subsystem {

    public static Intake INSTANCE = new Intake();
    private MotorEx intakeMotor;
    private MotorEx topMotor;
    private ColorRangeSensor topSensor;
    private boolean autoIntake = false;
    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();

    private Intake(){}

    @Override
    public void initialize(){
        intakeMotor = new MotorEx("intake").brakeMode();
        topMotor = new MotorEx("upMotor").brakeMode().reversed();

        topSensor = ActiveOpMode.hardwareMap().get(ColorRangeSensor.class, "upSensor");

        autoIntake = false;
    }


    @Override
    public void periodic(){
        telemetry.addData("TopSensor", checkTopSensor());

        if (autoIntake){
            if (checkTopSensor()) {
                topMotor.setPower(0);
                intakeMotor.setPower(1);
            } else {
                topMotor.setPower(0.7);
                intakeMotor.setPower(1);
            }
        }
    }

    private boolean checkTopSensor(){
        return topSensor.getDistance(DistanceUnit.CM) < 5.9;
    }

    public Command shootCommand(){
        return new InstantCommand(() -> {
                        topMotor.setPower(0.5);
                        intakeMotor.setPower(1);
                })
                .requires(this);
    }

    public Command stopCommand(){
        return new InstantCommand(()-> {
                    topMotor.setPower(0);
                    intakeMotor.setPower(0);
                        }
                ).requires(this);
    }

    public Command reverseIntake(){
        return new InstantCommand(()-> {
                            topMotor.setPower(-1);
                            intakeMotor.setPower(-1);
                        }
                ).requires(this);
    }

    
    public Command intakeCommand(){
        return new LambdaCommand()
                .setUpdate(() -> {
                    if (checkTopSensor()) {
                        topMotor.setPower(0);
                        intakeMotor.setPower(1);
                    } else {
                        topMotor.setPower(0.3);
                        intakeMotor.setPower(1);
                    }
                }
                ).requires(this);
    }

    public Command intakeAutoOn(){
        return new InstantCommand(()->{
            autoIntake = true;
        });
    }

    public Command intakeAutoOff(){
        return new InstantCommand(()->{
            autoIntake = false;
        });
    }

}
