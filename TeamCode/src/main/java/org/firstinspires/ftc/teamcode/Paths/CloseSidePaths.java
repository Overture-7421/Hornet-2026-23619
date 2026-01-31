package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.utils.MirrorPaths;
import org.firstinspires.ftc.teamcode.utils.Robot.Alliance;

public class CloseSidePaths extends MirrorPaths {

    public PathChain path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path7;
    public PathChain Path8;

    public Pose startPose;

    public CloseSidePaths(Follower follower, Alliance allianceColor) {
        super(allianceColor);

        startPose = mirrorPose(new Pose(26.199, 132.374, Math.toRadians(144)));

        path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(26.199, 132.374)),
                                mirrorPose(new Pose(47.211, 111.319))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(144)), (mirrorHeading(142)))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(47.211, 111.319)),
                                mirrorPose(new Pose(62.052, 78.488)),
                                mirrorPose(new Pose(18.986, 79.641))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(142)), (mirrorHeading(180)))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(18.986, 79.641)),
                                mirrorPose(new Pose(35.110, 77.225)),
                                mirrorPose(new Pose(18.783, 72.565))
                        )
                )
                .setConstantHeadingInterpolation((mirrorHeading(180)))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(18.783, 72.565)),
                                mirrorPose(new Pose(53.871, 89.146))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(132)))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(53.871, 89.146)),
                                mirrorPose(new Pose(67.626, 50.503)),
                                mirrorPose(new Pose(16.025, 55.128))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(132)), (mirrorHeading(180)))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(16.025, 55.128)),
                                mirrorPose(new Pose(54.199, 89.146))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(132)))
                .build();

        Path7 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(54.199, 89.146)),
                                mirrorPose(new Pose(64.150, 13.239)),
                                mirrorPose(new Pose(63.619, 37.521)),
                                mirrorPose(new Pose(17.424, 31.385))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(132)), (mirrorHeading(180)))
                .build();

        Path8 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(17.424, 31.385)),
                                mirrorPose(new Pose(53.858, 107.566))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(142)))
                .build();
    }
}