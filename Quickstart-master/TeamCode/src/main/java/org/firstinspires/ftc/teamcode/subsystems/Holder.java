package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Holder {
    private CRServo holder;
    private Gamepad driver1, driver2;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;


    public Holder(OpMode mode) {
        driver1 = mode.gamepad1;
        driver2 = mode.gamepad2;

        HardwareMap hardwareMap = mode.hardwareMap;
        holder = hardwareMap.get(CRServo.class, "littlewheel"); //c0

    }
    public void teleOp() {
        handleToggle();
/*
        if (driver2.y){
            holder.setPower(1);
        }
        else if(driver2.b){
            holder.setPower(-1);
        } else {
            holder.setPower(0);
        }

 */
    }
    private void handleToggle() {
        // Toggle power for little wheel when y is pressed
        if (driver2.y) {
            if (debounceCounter > DEBOUNCE_THRESHOLD) {
                toggleOn();
                debounceCounter = 0;
            }
        } else {
            debounceCounter++;
        }
    }

    private void toggleOn() {
        if (holder.getPower() != 0) {
            holder.setPower(0);
        } else {
            holder.setPower(1);
        }
    }

    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Right Stick Y Power: ", driver2.right_stick_y);
        opMode.telemetry.addData("Holder Power: ", holder.getPower());
        opMode.telemetry.update();
    }
}
