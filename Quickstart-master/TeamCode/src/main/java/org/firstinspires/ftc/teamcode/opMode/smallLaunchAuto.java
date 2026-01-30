package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

@Autonomous(name = "Small Launch Zone Auto (TIME)")
public class smallLaunchAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive mecanumDrive;

    public void runOpMode() {
        mecanumDrive = new MecanumDrive(this);
        waitForStart();
        runtime.reset();

        mecanumDrive.Auto(700);

        while (opModeIsActive()){// && (runtime.seconds() < 10.0)) {
            Outtake.Auto(600);

            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        @Override
        public void runOpMode(){
                robot.Map(hardwareMap);
                telemetry.addData("Time", time.time());
                waitForStart();

                turnRight(500);
            }

            public void moveForward(double p, int t){
                robot.driveLeftBack.setPower(-p);
                robot.driveRightFront.setPower(-p);
                robot.driveLeftFront.setPower(-p);
                robot.driveRightBack.setPower(-p);
                sleep(t);

                robot.driveLeftBack.setPower(0);
                robot.driveRightFront.setPower(0);
                robot.driveLeftFront.setPower(0);
                robot.driveRightBack.setPower(0);
            }

            public void moveBack(double p, int t){
                robot.driveLeftBack.setPower(p);
                robot.driveRightFront.setPower(p);
                robot.driveLeftFront.setPower(p);
                robot.driveRightBack.setPower(p);
                sleep(t);

                robot.driveLeftBack.setPower(0);
                robot.driveLeftFront.setPower(0);
                robot.driveRightBack.setPower(0);
                robot.driveRightFront.setPower(0);
            }

            public void moveLeft(double p, int t){
                robot.driveLeftBack.setPower(-p);
                robot.driveRightFront.setPower(-p);
                robot.driveLeftFront.setPower(p);
                robot.driveRightBack.setPower(p);
                sleep(t);

                robot.driveLeftBack.setPower(0);
                robot.driveRightFront.setPower(0);
                robot.driveLeftFront.setPower(0);
                robot.driveRightBack.setPower(0);
            }

            public void moveRight(double p, int t){
                robot.driveLeftBack.setPower(-p);
                robot.driveRightFront.setPower(-p);
                robot.driveLeftFront.setPower(p);
                robot.driveRightBack.setPower(p);
                sleep(t);

                robot.driveLeftBack.setPower(0);
                robot.driveRightFront.setPower(0);
                robot.driveLeftFront.setPower(0);
                robot.driveRightBack.setPower(0);
            }

            public void turnRight(int t){{
                robot.driveLeftBack.setPower(-1);
                robot.driveRightFront.setPower(1);
                robot.driveLeftFront.setPower(-1);
                robot.driveRightBack.setPower(1);
                sleep(t);
            }
            }
        }

    }
}
