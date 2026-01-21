package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Delivery;
import org.firstinspires.ftc.teamcode.subsystems.Holder;
import org.firstinspires.ftc.teamcode.subsystems.Intake24349;
import org.firstinspires.ftc.teamcode.subsystems.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

@TeleOp(name = "24349 Field Centric Driving")

public class FieldCentricTeleOp extends OpMode {
    private Delivery delivery;
    private Outtake outtake;
    private Intake24349 intake;
    private MecanumDrive mecanumDrive;
    private Holder holder;
    private IntakeServo intakeServo;

    @Override
    public void init() {
        delivery = new Delivery(this);
        outtake = new Outtake(this);
        mecanumDrive = new MecanumDrive(this);
        holder = new Holder(this);
        intake = new Intake24349(this);
        intakeServo = new IntakeServo(this);

    }

    @Override
    public void loop() {
        mecanumDrive.driveFieldRelative();
        delivery.teleOp();
        intake.teleOp();
        outtake.teleOp();
        holder.teleOp();
        intakeServo.teleOp();
        outtake.addTelemetry(this);
    }
}
