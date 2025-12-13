package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Delivery;
import org.firstinspires.ftc.teamcode.subsystems.Holder;
import org.firstinspires.ftc.teamcode.subsystems.Intake24349;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

@TeleOp(name = "24349 Field Centric Driving")

public class FieldCentricTeleOp extends OpMode {
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
        outtake.addTelemetry(this);
    }
}
