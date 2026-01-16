package org.firstinspires.ftc.teamcode.opMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "Pedro Auto Test")

public class SampleAutoPathing extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    public enum PathState {
        //START POSITION_END POSITION
        //DRIVE > MOVEMENT STATE
        // SHOOT > ATTEMPT
        DRIVE_STARTPOS_SHOOT_POS,

        SHOOT_PRELOAD,
        DRIVE_SHOOTPOS_ENDPOS
    }
    PathState pathState;

    private final Pose startPos = new Pose(63.65731166912851, 8.212703101920235, Math.toRadians(90));
    private final Pose shootPosition = new Pose(67.91137370753323,19.834564254062023, Math.toRadians(113));
    private final Pose endPose = new Pose(62.85819793205318, 36.87592319054652, Math.toRadians(113));

    private PathChain driveStartPosShootPos, driveShootPosEndPos;

    public void buildPath() {
        driveStartPosShootPos = follower.pathBuilder()
                .addPath(new BezierLine(startPos, shootPosition))
                .setLinearHeadingInterpolation(startPos.getHeading(), shootPosition.getHeading())
                .build();
        driveShootPosEndPos = follower.pathBuilder()
                .addPath(new BezierLine(shootPosition, endPose))
                .setLinearHeadingInterpolation(shootPosition.getHeading(), endPose.getHeading())
                .build();
    }

    public void startPathUpdate() {
        switch (pathState) {
            case DRIVE_STARTPOS_SHOOT_POS:
                follower.followPath(driveStartPosShootPos, true);
                setPathState(PathState.SHOOT_PRELOAD); //reset the timer and make a new state
                break;
            case SHOOT_PRELOAD:
                //check is the follower done
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 5) {
                    follower.followPath(driveShootPosEndPos, true);
                    setPathState(PathState.DRIVE_SHOOTPOS_ENDPOS);
                    //TODO add logic for flywheel shooter
                    telemetry.addLine("Done Path 1");
                }
                break;
            case DRIVE_SHOOTPOS_ENDPOS:
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
    public void init() {

    }

    public void loop() {
        follower.update();


    }
}
