package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforward;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.impl.VoltageCompensatingMotor;

public class Shooter implements Subsystem {

    private int countsPerRev = 2200;
    private  int temp;

    private final TelemetryManager telemetry = PanelsTelemetry.INSTANCE.getTelemetry();

    public static Shooter INSTANCE = new Shooter();
    private MotorEx shooterLeft;
    private MotorEx shooterRight;
    private ServoEx hood;
    private VoltageCompensatingMotor shooterMotors;
    public PIDCoefficients coefficients = new PIDCoefficients(0.005, 0.0,0.0);
    public BasicFeedforwardParameters feedforward = new BasicFeedforwardParameters(0.1,0.01,0);
    private ControlSystem controlSystem = ControlSystem.builder().velPid(coefficients).basicFF(feedforward).build();
  
    private Shooter(){}

    public void initialize(){
        shooterLeft =  new MotorEx("shooterLeft");
        shooterRight  =  new MotorEx("shooterRight");
        hood = new ServoEx("hood");
        shooterLeft.brakeMode();
        shooterRight.brakeMode();
        shooterMotors = new VoltageCompensatingMotor(new MotorGroup(shooterLeft,shooterRight));

        controlSystem.setGoal(shooterMotors.getState());
    }

    @Override
    public void periodic(){
        telemetry.addData("Current Speed:",(shooterMotors.getState().getVelocity() / 28) * 60);
        telemetry.addData("Ticks Speed:",shooterMotors.getState().getVelocity());
        telemetry.addData("Goal:",controlSystem.getGoal());

        shooterMotors.setPower(controlSystem.calculate(shooterMotors.getState()));
        telemetry.update();

    }
   // public Command maxVel = new RunToVelocity(controlSystem,countsPerRev,10);
    public Command Stop = new RunToVelocity(controlSystem,0,10);
    public  Command Shoot()
    {
        return new LambdaCommand()
                .setUpdate(()->controlSystem.setGoal(new KineticState(0,temp,0)))
                .setIsDone(()->controlSystem.isWithinTolerance(new KineticState(0,40,0)));
    }
    public  Command ShootManual()
    {
        return new LambdaCommand()
                .setUpdate(()->controlSystem.setGoal(new KineticState(0,800,0)))
                .setIsDone(()->controlSystem.isWithinTolerance(new KineticState(0,40,0)));
    }

}
