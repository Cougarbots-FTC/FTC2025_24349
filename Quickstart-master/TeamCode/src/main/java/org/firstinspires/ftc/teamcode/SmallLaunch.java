package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@Autonomous(name = "Front Auto")
public class StartFrontAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive mecanumDrive;

    public void runOpMode() {
        mecanumDrive = new MecanumDrive(this);
        waitForStart();
        runtime.reset();

        mecanumDrive.Auto(700);

        while (opModeIsActive()){// && (runtime.seconds() < 10.0)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }


    }
}
*/

