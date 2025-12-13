package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Delivery {
    private DcMotor delivery;
    private Gamepad driver1, driver2;


    public Delivery(OpMode mode) {
        driver1 = mode.gamepad1;
        driver2 = mode.gamepad2;

        HardwareMap hardwareMap = mode.hardwareMap;
        delivery = hardwareMap.get(DcMotor.class, "Delivery"); //e 2

    }

    public void teleOp() {
        if (driver2.right_trigger > 0.1) {
            delivery.setPower(-1*driver2.right_trigger);
        }
        else {
            delivery.setPower(0);
        }
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Right Stick Y Power: ", driver2.right_stick_y);
        opMode.telemetry.update();
    }
}
