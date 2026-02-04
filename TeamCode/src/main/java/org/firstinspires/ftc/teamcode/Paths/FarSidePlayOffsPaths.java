package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.utils.MirrorPaths;
import org.firstinspires.ftc.teamcode.utils.Robot.Alliance;

public class FarSidePlayOffsPaths extends MirrorPaths {

    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;

    public Pose startPose;

    public FarSidePlayOffsPaths(Follower follower, Alliance allianceColor) {
        super(allianceColor);

        startPose = mirrorPose(new Pose(56.000, 8.000, Math.toRadians(90)));

        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(56.000, 8.000)),
                                mirrorPose(new Pose(49.649, 14.844))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(90)), (mirrorHeading(115)))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(49.649, 14.844)),
                                mirrorPose(new Pose(23.929, 35.640))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(115)), (mirrorHeading(150)))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(23.929, 35.640)),
                                mirrorPose(new Pose(49.308, 14.844))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(150)), (mirrorHeading(110)))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(49.308, 14.844)),
                                mirrorPose(new Pose(10.237, 7.678))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(110)), (mirrorHeading(180)))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(10.237, 7.678)),
                                mirrorPose(new Pose(49.308, 14.673))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(110)))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(49.308, 14.673)),
                                mirrorPose(new Pose(46.408, 18.768))
                        )
                )
                .setConstantHeadingInterpolation((mirrorHeading(110)))
                .build();
    }
}