package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Holder {
    private Servo holder;
    private Gamepad driver1, driver2;
    public static boolean isUp = false;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;


    public Holder(OpMode mode) {
        driver1 = mode.gamepad1;
        driver2 = mode.gamepad2;

        HardwareMap hardwareMap = mode.hardwareMap;
        holder = hardwareMap.get(Servo.class, "SpinArm"); //e 2

    }

    public void teleOp() {
        handleToggle();
    }
    private void handleToggle() {
        // Toggle claw when trigger is pressed
        if (driver2.y) {
            if (debounceCounter > DEBOUNCE_THRESHOLD) {
                toggleLeft();
                debounceCounter = 0;
            }
        } else {
            debounceCounter++;
        }
    }
    private void toggleLeft() {
        if (isUp) {
            holder.setPosition(0);
        } else {
            holder.setPosition(0.5);
        }
        isUp = !isUp;

    }

    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Right Stick Y Power: ", driver2.right_stick_y);
        opMode.telemetry.addData("Holder Power: ", holder.getPosition());
        opMode.telemetry.update();
    }
}
