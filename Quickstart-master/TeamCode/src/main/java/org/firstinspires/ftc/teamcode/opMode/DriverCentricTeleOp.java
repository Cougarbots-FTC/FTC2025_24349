package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.*;


@TeleOp(name = "TeleOp - THIS ONE 24349")
public class DriverCentricTeleOp extends OpMode {

    //private Intake intake;
    private Delivery delivery;
    private Outtake outtake;
    private Intake24349 intake;
    private MecanumDrive mecanumDrive;
    private Holder holder;

    @Override
    public void init() {
        //intake = new Intake(this);
        delivery = new Delivery(this);
        outtake = new Outtake(this);
        mecanumDrive = new MecanumDrive(this);
        holder = new Holder(this);
        intake = new Intake24349(this);

    }

    @Override
    public void loop() {
        mecanumDrive.mecanumDrive();
        delivery.teleOp();
        intake.teleOp();
        outtake.teleOp();
        holder.teleOp();
        mecanumDrive.addTelemetry(this);
    }
}
