package org.firstinspires.ftc.teamcode.opMode;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Delivery;
import org.firstinspires.ftc.teamcode.subsystems.Intake24349;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

@Autonomous(name = "Auto By Time From Back")
public class AutoByTime extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive mecanumDrive;
    private Outtake outtake;
    private Delivery delivery;
    private Intake24349 intake;

    public void init() {
        mecanumDrive = new MecanumDrive(this);
        outtake = new Outtake(this);
        delivery = new Delivery(this);
        intake = new Intake24349(this);

    }
    public void start() {
        mecanumDrive.move(200, -0.5);
        mecanumDrive.turnLeft(250, -0.65);
        mecanumDrive.move(150, -0.5);

        outtake.turnOnOuttake(-1880);
        sleep(5000);
        delivery.turnOnDelivery(-1);
        sleep(5000);
        intake.turnOnIntake(1);
        sleep(5000);
        
        outtake.turnOnOuttake(0);
        delivery.turnOnDelivery(0);
        intake.turnOnIntake(0);

    }
    @Override
    public void loop(){
        telemetry.addData("Time", runtime.time());

    }
}
