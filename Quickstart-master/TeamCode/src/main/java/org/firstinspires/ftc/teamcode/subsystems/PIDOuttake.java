package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class PIDOuttake {
    private DcMotorEx outtake;
    private Gamepad driver1, driver2;
    private double outtakeVelocity;
    private double lowVelocity;
    private double currentVelocity;

    private double F = 15.592; //says how much power the motor should have, -1
    private double P = 43.239;//dampener 82.9
    double [] stepSizes = {10, 1, 0.1, 0.01, 0.001, 0.0001};
    int stepIndex = 2;

    boolean shootFromBack;

    public PIDOuttake(OpMode mode) {
        driver1 = mode.gamepad1;
        driver2 = mode.gamepad2;
        shootFromBack = true;

        outtakeVelocity = 1710;
        lowVelocity = 900; //shoot from large launch vertex
        currentVelocity = outtakeVelocity;

        HardwareMap hardwareMap = mode.hardwareMap;
        outtake = hardwareMap.get(DcMotorEx.class, "Outtake");   //e 3
        outtake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtake.setDirection(DcMotorSimple.Direction.REVERSE);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        outtake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
    }
    public void teleOp() {
        if (driver2.left_trigger > 0.1) {
            //outtake.setPower(-driver2.left_trigger);
            //outtake.setVelocity(outtakeVelocity);

            PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
            outtake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

            outtake.setVelocity(outtakeVelocity);
        } else {
            outtake.setVelocity(0);
            //outtake.setPower(0);
        }
        //outtake.setPower(driver2.left_stick_y);


        if (driver2.yWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if (driver2.bWasPressed()) {
            outtakeVelocity += 5;
        }

        if (driver2.aWasPressed()) {
            outtakeVelocity -= 5;
        }

        if (driver2.dpadLeftWasPressed()) {
            F -= stepSizes[stepIndex];
        }
        if (driver2.dpadRightWasPressed()) {
            F += stepSizes[stepIndex];
        }
        if (driver2.dpadUpWasPressed()) {
            P += stepSizes[stepIndex];
        }
        if (driver2.dpadDownWasPressed()) {
            P -= stepSizes[stepIndex];
        }

/*
// For tuning the PID Controller
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        outtake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        outtake.setVelocity(currentVelocity);
*/

    }

    public void turnOnOuttake(int velocity) {
        //outtake.setVelocity(velocity);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        outtake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        outtake.setVelocity(velocity);
    }
    public void turnOffOuttake() {
        outtake.setVelocity(0);
    }

    public void addTelemetry(OpMode opMode) {

        double currVelocity = outtake.getVelocity();
        double error = outtakeVelocity - currVelocity;

        opMode.telemetry.addData("Target Velocity: ", outtakeVelocity);
        opMode.telemetry.addData("Current Velocity: ", currVelocity);
        opMode.telemetry.addData("Error: ", error);
        opMode.telemetry.addLine("----------------------------------------------------");
        opMode.telemetry.addData("Tuning P: ", P);
        opMode.telemetry.addData("Tuning F: ", F);
        opMode.telemetry.addData("Step Size: ", stepSizes[stepIndex]);

        opMode.telemetry.update();
    }
}
