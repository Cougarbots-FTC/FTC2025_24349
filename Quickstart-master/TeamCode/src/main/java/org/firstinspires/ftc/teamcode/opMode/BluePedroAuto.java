package org.firstinspires.ftc.teamcode.opMode;

import static android.os.SystemClock.sleep;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Delivery;
import org.firstinspires.ftc.teamcode.subsystems.Intake24349;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.PIDOuttake;


@Autonomous(name = "Blue Auto")

public class BluePedroAuto extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;
    private PIDOuttake outtake;
    private Intake24349 intake;
    private Delivery delivery;
    private int shootCount;

    public enum PathState {
        DRIVE_STARTPOS_SHOOT_POS,
        SHOOT,
        DRIVE_SHOOT_FIRSTSPIKE,
        DRIVE_FIRSTSPIKE_COLLECT,
        DRIVE_COLLECT_SHOOTPOS,
        DRIVE_SHOOTPOS_ENDPOS,
        DRIVE_SECONDSPIKE,
        DRIVE_COLLECT_SECOND_SPIKE,
        DRIVE_SHOOT_SECOND_SPIKE,
        DONE
    }
    PathState pathState;

    private final Pose startPos = new Pose(56, 8.4, Math.toRadians(90));
    private final Pose shootPosition = new Pose(59,12, Math.toRadians(115));//change y val?? math.toRad is robot heading
    private final Pose secondShootPos = new Pose(59,12.8, Math.toRadians(114));//NEW FROM BLUE AUTO
    private final Pose firstSpike = new Pose(55, 31, Math.toRadians(0));
    private final Pose secondSpike = new Pose(45, 59, Math.toRadians(0)); //new Pose(96, 38.3, Math.toRadians(90)); NEW
    private final Pose collectFirstSpike = new Pose(10, 31);

    private final Pose collectSecondSpike = new Pose(15, 57); //new Pose(131, 38.3); FROM BLUE AUTO
    private final Pose endPose = new Pose(53.5, 27.7, Math.toRadians(114));

    private PathChain driveStartPosShootPos, driveShootPosEndPos,
            driveShootFirstSpike, driveFirstSpikeCollect, driveSecondSpikeCollect, driveCollectSecondSpike,
            driveShootSecondSpike, driveCollectShoot; //same names as blue..dont wanna change

    public void buildPath() {
        driveStartPosShootPos = follower.pathBuilder()
                .addPath(new BezierLine(startPos, shootPosition))
                .setLinearHeadingInterpolation(startPos.getHeading(), shootPosition.getHeading())
                .build();
        driveShootPosEndPos = follower.pathBuilder()
                .addPath(new BezierLine(shootPosition, endPose))
                .setLinearHeadingInterpolation(shootPosition.getHeading(), endPose.getHeading())
                .build();

        driveShootFirstSpike = follower.pathBuilder()
                .addPath(new BezierCurve(shootPosition, firstSpike))
                .setLinearHeadingInterpolation(shootPosition.getHeading(), firstSpike.getHeading())
                .build();

        //TODO added a reversed to hopefully cut down time and to analyze blue auto the same as red
        driveFirstSpikeCollect = follower.pathBuilder()
                .addPath(new BezierLine(firstSpike, collectFirstSpike))
                .setTangentHeadingInterpolation()
                .setReversed() //this!
                .build();

        driveCollectShoot = follower.pathBuilder()
                .addPath(new BezierCurve(collectFirstSpike, secondShootPos))
                .setLinearHeadingInterpolation(collectFirstSpike.getHeading(), secondShootPos.getHeading()) //maybe change to endPos???
                .build();


        driveSecondSpikeCollect = follower.pathBuilder()
                .addPath(new BezierCurve(shootPosition, secondSpike))
                .setLinearHeadingInterpolation(shootPosition.getHeading(), secondSpike.getHeading())
                .setReversed() //this for path
                .build();

        driveCollectSecondSpike = follower.pathBuilder()
                .addPath(new BezierCurve(secondSpike, collectSecondSpike))
                .setTangentHeadingInterpolation()
                .setReversed() //this for path

                .build();
        driveShootSecondSpike = follower.pathBuilder()
                .addPath(new BezierCurve(collectSecondSpike, secondShootPos))
                .setLinearHeadingInterpolation(shootPosition.getHeading(), secondShootPos.getHeading())
                .build();

    }

    public void startPathUpdate() {
        switch (pathState) {
            case DRIVE_STARTPOS_SHOOT_POS: //robot angles its butt to prepare for shooting
                follower.followPath(driveStartPosShootPos, true);
                setPathState(PathState.SHOOT); //reset the timer and make a new state
                break;
            case DRIVE_SHOOT_FIRSTSPIKE: //shoot
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 2.8) {
                    follower.followPath(driveShootFirstSpike, true);
                    setPathState(PathState.DRIVE_FIRSTSPIKE_COLLECT);
                    intake.turnOnIntake(1); //maybe move if it doesn't turn on soon enough
                }
                break;
            case DRIVE_FIRSTSPIKE_COLLECT: //from start position to line up with the first spike
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 1.5) {
                    follower.setMaxPower(0.7);
                    follower.followPath(driveFirstSpikeCollect, true);
                    setPathState(PathState.DRIVE_COLLECT_SHOOTPOS);
                }
                break;
            case DRIVE_COLLECT_SECOND_SPIKE:
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 1.5) {
                    follower.setMaxPower(0.7);
                    intake.turnOnIntake(1);
                    follower.followPath(driveCollectSecondSpike, true);
                    setPathState(PathState.DRIVE_SHOOT_SECOND_SPIKE);
                }
                break;
            case DRIVE_COLLECT_SHOOTPOS://drive, after collecting the first spike, to the shoot position
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 4) {
                    follower.setMaxPower(0.8);
                    intake.turnOnIntake(0);
                    follower.followPath(driveCollectShoot, true);
                    setPathState(PathState.SHOOT);
                }
                break;
            case DRIVE_SECONDSPIKE:
                if (!follower.isBusy()) {
                    follower.followPath(driveSecondSpikeCollect, true);
                    setPathState(PathState.DRIVE_COLLECT_SECOND_SPIKE);
                }
                break;
            case DRIVE_SHOOT_SECOND_SPIKE:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 2) {
                    follower.setMaxPower(0.8);
                    follower.followPath(driveShootSecondSpike, true);
                    setPathState(PathState.SHOOT);
                }

                break;
            case SHOOT:
                //check is the follower done
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 3) {
                    shootCount++;
                    outtake.turnOnOuttake(1740);
                    if (shootCount == 1) {
                        setPathState(PathState.DRIVE_SHOOT_FIRSTSPIKE);
                    } else {//if (shootCount == 2) {
                        //setPathState(PathState.DRIVE_SECONDSPIKE);
                   // } else if (shootCount == 3) {
                        setPathState(PathState.DRIVE_SHOOTPOS_ENDPOS);
                    }
                    telemetry.addData("heading", follower.getHeading());

                    //TODO add logic for flywheel shooter
                    //TODO add logic for flywheel shooter THIS IS FROM RED COPY PASTE!
                    sleep(1500);//4500
                    delivery.turnOnDelivery(-1);
                    intake.turnOnIntake(1);
                    sleep(5000);//3000
                    telemetry.addLine("Done Path 1");

                    delivery.turnOnDelivery(0);
                    intake.turnOnIntake(0);
                    outtake.turnOffOuttake();

                }
                break;
            case DRIVE_SHOOTPOS_ENDPOS:
                if (!follower.isBusy()) {
                    follower.followPath(driveShootPosEndPos, true);
                    setPathState(PathState.DONE);
                    delivery.turnOnDelivery(0);
                    outtake.turnOffOuttake();
                    intake.turnOnIntake(0);

                }
                break;

            case DONE:
                if (!follower.isBusy()) {
                    telemetry.addLine("All paths done");
                }
            default:
                telemetry.addLine("No State Commanded");
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);

        follower.setPose(startPos);

        outtake = new PIDOuttake(this);
        intake = new Intake24349(this);
        delivery = new Delivery(this);

        pathTimer = new Timer();
        opModeTimer = new Timer();

        buildPath();

        pathState = PathState.DRIVE_STARTPOS_SHOOT_POS;

        shootCount = 0;
    }

    @Override
    public void start() {
        pathTimer.resetTimer();
        opModeTimer.resetTimer();
    }


    public void loop() {
        follower.update();
        startPathUpdate();

        telemetry.addData("Pose", follower.getPose());
        telemetry.update();

    }
}
