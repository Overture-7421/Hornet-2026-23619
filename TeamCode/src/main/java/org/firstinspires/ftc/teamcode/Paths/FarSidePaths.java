package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.utils.MirrorPaths;
import org.firstinspires.ftc.teamcode.utils.Robot.Alliance;

public class FarSidePaths extends MirrorPaths {

    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path7;
    public PathChain Path8;
    public PathChain Path9;

    public Pose startPose;

    public FarSidePaths(Follower follower, Alliance allianceColor) {
        super(allianceColor);

        startPose = mirrorPose(new Pose(56.000, 8.000, Math.toRadians(90)));

        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(56.000, 8.000)),
                                mirrorPose(new Pose(56.986, 14.673))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(90)), (mirrorHeading(100)))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(56.986, 14.673)),
                                mirrorPose(new Pose(31.441, 19.635)),
                                mirrorPose(new Pose(9.896, 8.701))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(100)), (mirrorHeading(180)))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(9.896, 8.701)),
                                mirrorPose(new Pose(56.316, 16.294))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(110)))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(56.316, 16.294)),
                                mirrorPose(new Pose(35.165, 11.717))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(110)), (mirrorHeading(90)))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(35.165, 11.717)),
                                mirrorPose(new Pose(70.007, 12.868)),
                                mirrorPose(new Pose(62.964, 36.660)),
                                mirrorPose(new Pose(17.445, 34.445))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(90)), (mirrorHeading(180)))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(17.445, 34.445)),
                                mirrorPose(new Pose(57.839, 17.915))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(110)))
                .build();

        Path7 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(57.839, 17.915)),
                                mirrorPose(new Pose(65.776, 63.226)),
                                mirrorPose(new Pose(15.220, 59.647))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(110)), (mirrorHeading(180)))
                .build();

        Path8 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(15.220, 59.647)),
                                mirrorPose(new Pose(58.180, 18.938))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(110)))
                .build();

        Path9 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(58.180, 18.938)),
                                mirrorPose(new Pose(36.682, 17.232))
                        )
                )
                .setConstantHeadingInterpolation((mirrorHeading(110)))
                .build();
    }
}