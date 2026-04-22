package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;



public class Intake extends SubsystemBase {
    private final MotorEx intakeMotor;
    private final MotorEx topMotor;

    private final ColorRangeSensor topSensor;
    private boolean autoIntake;
    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();

    public Intake(HardwareMap hardwareMap){
        intakeMotor = new MotorEx(hardwareMap, "intake");
        intakeMotor.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        topMotor =  new MotorEx(hardwareMap, "upMotor");;
        topMotor.setInverted(true);
        topSensor = hardwareMap.get(ColorRangeSensor.class, "upSensor");

        autoIntake = false;
    }


    @Override
    public void periodic(){
        telemetry.addData("TopSensor", checkTopSensor());

        if (autoIntake){
            if (checkTopSensor()) {
                topMotor.set(-0.1);
                intakeMotor.set(0.8);
            } else {
                topMotor.set(0.3);
                intakeMotor.set(0.8);
            }
        }
    }

    private boolean checkTopSensor(){
        return topSensor.getDistance(DistanceUnit.CM) < 5;
    }

    public InstantCommand shootCommand(){
        return new InstantCommand(() -> {
                        topMotor.set(0.7);
                        intakeMotor.set(1);
                });
    }

    public InstantCommand stopCommand(){
        return new InstantCommand(()-> {
                    topMotor.set(0);
                    intakeMotor.set(0);
                        });
    }

    public InstantCommand reverseIntake(){
        return new InstantCommand(()-> {
                            topMotor.set(-1);
                            intakeMotor.set(-1);
                        }
        );
    }

    
    public RunCommand intakeCommand(){
        return new RunCommand(
                () -> {
                    if (checkTopSensor()) {
                        topMotor.set(-0.2);
                        intakeMotor.set(1);
                    } else {
                        topMotor.set(0.25);
                        intakeMotor.set(1);
                    }
                }

         );
    }

    public InstantCommand intakeAutoOn(){
        return new InstantCommand(()-> autoIntake = true);
    }

    public InstantCommand intakeAutoOff(){
        return new InstantCommand(()-> autoIntake = false);
    }
}
