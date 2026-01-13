package org.firstinspires.ftc.teamcode.opMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class StartGoalAutoPedroPath {

    public static class Paths {
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(123.068, 123.256),

                                    new Pose(104.470, 84.751)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(220), Math.toRadians(0))

                    .build();

            Path2 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(104.470, 84.751),

                                    new Pose(131.035, 83.358)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                    .build();

            Path3 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(131.035, 83.358),

                                    new Pose(90.000, 99.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            Path4 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(90.000, 99.000),

                                    new Pose(90.000, 99.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(50), Math.toRadians(50))

                    .build();
        }
    }

}
