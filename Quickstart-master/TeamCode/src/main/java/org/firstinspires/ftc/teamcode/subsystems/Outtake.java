package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    private DcMotorEx outtake, outtake2;
    private Gamepad driver1, driver2;

    public Outtake(OpMode mode) {
        driver1 = mode.gamepad1;
        driver2 = mode.gamepad2;

        HardwareMap hardwareMap = mode.hardwareMap;
        outtake = hardwareMap.get(DcMotorEx.class, "Outtake");   //e 3
        //outtake2 = hardwareMap.get(DcMotor.class, "Outtake2"); //e 1
    }
    public void teleOp() {

        if (driver2.left_trigger > 0.1) {
            outtake.setPower(-driver2.left_trigger);
            //outtake.setVelocity(-1500);
        } else {
            //outtake.setVelocity(0);
            outtake.setPower(0);
        }
        //outtake.setPower(driver2.left_stick_y);
    }

    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Left Stick Y Power: ", outtake.getVelocity());
        opMode.telemetry.update();
    }
}
