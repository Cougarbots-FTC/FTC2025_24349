package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake24349 {
    private DcMotor intake;
    private Gamepad driver1, driver2;
    public Intake24349(OpMode mode) {
        driver1 = mode.gamepad1;
        driver2 = mode.gamepad2;

        HardwareMap hardwareMap = mode.hardwareMap;
        intake = hardwareMap.get(DcMotor.class, "Intake");   //e 1
        //intake.setDirection(DcMotor.Direction.REVERSE);


    }

    public void teleOp() {

        intake.setPower(driver2.right_stick_y);

    }
}
