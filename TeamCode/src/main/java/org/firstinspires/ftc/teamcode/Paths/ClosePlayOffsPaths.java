package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.utils.MirrorPaths;
import org.firstinspires.ftc.teamcode.utils.Robot.Alliance;

public class ClosePlayOffsPaths extends MirrorPaths {

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

    public ClosePlayOffsPaths(Follower follower, Alliance allianceColor) {
        super(allianceColor);

        startPose = mirrorPose(new Pose(27.734, 132.374, Math.toRadians(144)));

        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(27.734, 132.374)),
                                mirrorPose(new Pose(40.777, 121.649))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(144)), (mirrorHeading(146)))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(40.777, 121.649)),
                                mirrorPose(new Pose(65.236, 49.281)),
                                mirrorPose(new Pose(17.280, 58.655))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(146)), (mirrorHeading(180)))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(17.280, 58.655)),
                                mirrorPose(new Pose(38.900, 65.687)),
                                mirrorPose(new Pose(54.085, 83.261))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(136)))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(54.085, 83.261)),
                                mirrorPose(new Pose(39.166, 65.336)),
                                mirrorPose(new Pose(13.479, 58.351))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(136)), (mirrorHeading(145)))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(13.479, 58.351)),
                                mirrorPose(new Pose(39.118, 64.967)),
                                mirrorPose(new Pose(54.597, 83.943))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(145)), (mirrorHeading(135)))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(54.597, 83.943)),
                                mirrorPose(new Pose(39.256, 66.270)),
                                mirrorPose(new Pose(13.308, 58.351))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(135)), (mirrorHeading(145)))
                .build();

        Path7 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(13.308, 58.351)),
                                mirrorPose(new Pose(39.645, 65.185)),
                                mirrorPose(new Pose(53.915, 83.431))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(145)), (mirrorHeading(132)))
                .build();

        Path8 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(53.915, 83.431)),
                                mirrorPose(new Pose(22.180, 83.602))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(132)), (mirrorHeading(180)))
                .build();

        Path9 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(22.180, 83.602)),
                                mirrorPose(new Pose(58.692, 104.758))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(144)))
                .build();
    }
}