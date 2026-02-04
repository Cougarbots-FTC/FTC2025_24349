package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    private DcMotorEx outtake, outtake2;
    private Gamepad driver1, driver2;
    private boolean shootFromBack;
    private int debounceCounterA = 0;
    private int debounceCounterB = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;
    private int outtakeVelocity;

    public Outtake(OpMode mode) {
        driver1 = mode.gamepad1;
        driver2 = mode.gamepad2;
        shootFromBack = true;

        outtakeVelocity = -1720;

        HardwareMap hardwareMap = mode.hardwareMap;
        outtake = hardwareMap.get(DcMotorEx.class, "Outtake");   //e 3
        //outtake2 = hardwareMap.get(DcMotor.class, "Outtake2"); //e 1
    }
    public void teleOp() {
        if (driver2.left_trigger > 0.1) {
            //outtake.setPower(-driver2.left_trigger);
            outtake.setVelocity(outtakeVelocity);
        } else {
            outtake.setVelocity(0);
            //outtake.setPower(0);
        }
        //outtake.setPower(driver2.left_stick_y);

        if (driver2.a) {
            if (debounceCounterA > DEBOUNCE_THRESHOLD) {
                outtakeVelocity += 5;
                debounceCounterA = 0;
            }
        }else {
            debounceCounterA++;
        }
        if (driver2.b) {
            if (debounceCounterB > DEBOUNCE_THRESHOLD) {
                outtakeVelocity -= 5;
                debounceCounterB = 0;
            }
        }else {
            debounceCounterB++;
        }
    }

    public void turnOnOuttake(int velocity) {
        outtake.setVelocity(velocity);
    }

    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Outtake Velocity Setting: ", outtakeVelocity);
        opMode.telemetry.addData("Outtake Velocity: ", outtake.getVelocity());
        opMode.telemetry.update();
    }
}
