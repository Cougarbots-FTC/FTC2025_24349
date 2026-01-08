package org.firstinspires.ftc.teamcode.subsystems;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class MecanumDrive {
    private final DcMotorEx front_left, front_right, back_left, back_right;

    private final Gamepad driver1, driver2;
    private final double AUTO_SPEED = -0.75;

    private double speed = 1;

    public MecanumDrive(OpMode mode) {

        driver1 = mode.gamepad1;
        driver2 = mode.gamepad2;

        HardwareMap hardwareMap = mode.hardwareMap;
        front_left = hardwareMap.get(DcMotorEx.class, "FrontLeft"); //2
        front_right = hardwareMap.get(DcMotorEx.class, "FrontRight"); //0
        back_left = hardwareMap.get(DcMotorEx.class, "BackLeft"); //1
        back_right = hardwareMap.get(DcMotorEx.class, "BackRight"); //3

        //this will make drivetrain used correctly
        back_left.setDirection(DcMotorEx.Direction.REVERSE);
        //front_right.setDirection(DcMotorEx.Direction.REVERSE);
        //back_right.setDirection(DcMotorEx.Direction.REVERSE);

        back_left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        back_right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        front_left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        front_right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

    }
    /*
    public void MecanumDriveStraff(OpMode mode){
        driver1 = mode.gamepad1;
        driver2 = mode.gamepad2;

        HardwareMap hardwareMap = mode.hardwareMap;
        front_left = hardwareMap.get(DcMotorEx.class, "FrontLeft"); //2
        front_right = hardwareMap.get(DcMotorEx.class, "FrontRight"); //0

        //this should fix our straffing problem, bc it would work for usual driving but not for straffing:
        front_right.setDirection(DcMotorEx.Direction.REVERSE);
        front_left.setDirection(DcMotorEx.Direction.REVERSE);
    }
*/
    public void mecanumDrive() {

        if (driver1.right_bumper) {
            speed = 0.5;
        } else {
            speed = 1;
        }
        // You may need to multiply some of these by -1 to invert direction of
        // the motor.  This is not an issue with the calculations themselves.
        double vertical = driver1.left_stick_y;
        double horizontal = driver1.left_stick_x * 1.1; //  Multiply by 1.1 to negate imperfect strafing
        double pivot = driver1.right_stick_x;

        //Obtains values for each motor through the positions through values
        //from  left joystick which has up/down(vertical) and left/right values(horizontal), and right joystick which has left/right values(pivot)
        double denominator = Math.max(Math.abs(vertical) + Math.abs(horizontal) + Math.abs(pivot), 1);
        double rightFrontPower = (vertical + horizontal + pivot) / denominator * speed;
        double rightBackPower = (vertical - horizontal + pivot) / denominator * speed;
        double LeftFrontPower = (vertical - horizontal - pivot) / denominator * speed;
        double LeftBackPower = (vertical + horizontal - pivot) / denominator * speed;

        front_left.setPower(LeftFrontPower);
        front_right.setPower(rightFrontPower);
        back_left.setPower(LeftBackPower);
        back_right.setPower(rightBackPower);
    }

    /*private void driveFieldRelative(double forward, double right, double rotate) {
        // First, convert direction being asked to drive to polar coordinates
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(right, forward);

        // Second, rotate angle by the angle the robot is pointing
        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        // Third, convert back to cartesian
        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);

        // Finally, call the drive method with robot relative forward and right amounts
        drive(newForward, newRight, rotate);
    }
*/

    // Thanks to FTC16072 for sharing this code!!
    public void Auto(int time) {

        front_left.setPower(AUTO_SPEED);
        front_right.setPower(AUTO_SPEED);
        back_left.setPower(AUTO_SPEED);
        back_right.setPower(AUTO_SPEED);

        try {
            sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        front_left.setPower(0);
        //.setVelocity() or .setSpeed()
        front_right.setPower(0);
        back_left.setPower(0);
        back_right.setPower(0);


    }

    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Left Back: ", back_left.getCurrent(CurrentUnit.AMPS));
        opMode.telemetry.addData("Right Back: ", back_right.getCurrent(CurrentUnit.AMPS));
        opMode.telemetry.addData("Left Front: ", front_left.getCurrent(CurrentUnit.AMPS));
        opMode.telemetry.addData("Right Front: ", front_left.getCurrent(CurrentUnit.AMPS));

        opMode.telemetry.update();
    }
}