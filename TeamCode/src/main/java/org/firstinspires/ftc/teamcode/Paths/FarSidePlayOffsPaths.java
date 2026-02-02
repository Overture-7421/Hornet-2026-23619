package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
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
                                mirrorPose(new Pose(56.815, 16.891))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(90)), (mirrorHeading(115)))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(56.815, 16.891)),
                                mirrorPose(new Pose(54.992, 29.247)),
                                mirrorPose(new Pose(62.964, 36.660)),
                                mirrorPose(new Pose(17.445, 34.445))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(115)), (mirrorHeading(180)))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(17.445, 34.445)),
                                mirrorPose(new Pose(52.038, 14.332))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(115)))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(52.038, 14.332)),
                                mirrorPose(new Pose(11.043, 19.014)),
                                mirrorPose(new Pose(12.284, 10.749))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(115)), (mirrorHeading(190)))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(12.284, 10.749)),
                                mirrorPose(new Pose(52.209, 14.332))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(190)), (mirrorHeading(115)))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(52.209, 14.332)),
                                mirrorPose(new Pose(42.142, 15.867))
                        )
                )
                .setConstantHeadingInterpolation((mirrorHeading(115)))
                .build();
    }
}