package org.firstinspires.ftc.teamcode.subsystems;

import static java.lang.Thread.sleep;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class MecanumDrive {
    private final DcMotorEx front_left, front_right, back_left, back_right;
    private GoBildaPinpointDriver pinpoint;
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

        back_left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        back_right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        front_left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        front_right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        //Configure the pinpoint computer
        pinpoint.setOffsets(2.125, 4.125, DistanceUnit.INCH);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.REVERSED);
        //Set the pinpoint starting position
        pinpoint.resetPosAndIMU();
        //Pose2D startingPos = new Pose2D(DistanceUnit.INCH, 2, 3, AngleUnit.RADIANS, 0);
        pinpoint.setHeading(0, AngleUnit.RADIANS);
    }

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
        //from  left joystick which has up/down(vertical) and left/right values(horizontal),
        // and right joystick which has left/right values(pivot)
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

    public void driveFieldRelative() {
        pinpoint.update();
        double y = -driver1.left_stick_y; // Remember to invert
        double x = driver1.left_stick_x;
        double rx = driver1.right_stick_x;

        // Get current heading from Pinpoint in Radians
        double botHeading = pinpoint.getHeading(AngleUnit.RADIANS);
        Pose2D pos = pinpoint.getPosition();

        // Field-centric kinematic transformation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        // Denominator is the largest motor power
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        front_left.setPower(frontLeftPower);
        back_left.setPower(backLeftPower);
        front_right.setPower(frontRightPower);
        back_right.setPower(backRightPower);
    }


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

        //pinpoint.getPosition();
        //opMode.telemetry.addData("Robot Position X: ", pinpoint.getPosX());
        //opMode.telemetry.addData("Robot Position Y: ", pinpoint.getPosY());


        opMode.telemetry.update();
    }
}