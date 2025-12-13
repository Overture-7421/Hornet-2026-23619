package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
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
    public Pose startPose;

    public FarSidePaths(Follower follower, Alliance allianceColor) {
        super(allianceColor);

        startPose = mirrorPose(new Pose(56.000, 8.000, Math.toRadians(90)));

        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(56.000, 8.000)),
                                mirrorPose(new Pose(58.009, 19.109))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(90)), (mirrorHeading(110)))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(58.009, 19.109)),
                                mirrorPose(new Pose(9.896, 8.019))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(110)), (mirrorHeading(180)))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(9.896, 8.019)),
                                mirrorPose(new Pose(19.791, 8.531))
                        )
                )
                .setConstantHeadingInterpolation((mirrorHeading(180)))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(19.791, 8.531)),
                                mirrorPose(new Pose(9.555, 8.019))
                        )
                )
                .setConstantHeadingInterpolation((mirrorHeading(180)))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(9.555, 8.019)),
                                mirrorPose(new Pose(48.468, 15.953))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(110)))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(48.468, 15.953)),
                                mirrorPose(new Pose(30.729, 11.888))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(110)), (mirrorHeading(90)))
                .build();
    }
}
