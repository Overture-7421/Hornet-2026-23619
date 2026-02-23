package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.utils.MirrorPaths;
import org.firstinspires.ftc.teamcode.utils.MyRobot.Alliance;

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
                                mirrorPose(new Pose(55.280, 12.967))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(90)), (mirrorHeading(110)))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(55.280, 12.967)),
                                mirrorPose(new Pose(43.405, 39.659)),
                                mirrorPose(new Pose(10.962, 36.664))
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(10.962, 36.664)),
                                mirrorPose(new Pose(47.261, 12.455))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(150)), (mirrorHeading(110)))

                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(47.261, 12.455)),
                                mirrorPose(new Pose(9.896, 8.701))
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(9.896, 8.701)),
                                mirrorPose(new Pose(20.815, 10.066))
                        )
                )
                .setConstantHeadingInterpolation(mirrorHeading(185))
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(20.815, 10.066)),
                                mirrorPose(new Pose(9.896, 9.213))
                        )
                )
                .setConstantHeadingInterpolation(mirrorHeading(185))
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(9.896, 9.213)),
                                mirrorPose(new Pose(47.090, 12.455))
                        )
                )
                .setLinearHeadingInterpolation(mirrorHeading(185), mirrorHeading(110))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(47.090, 12.455)),
                                mirrorPose(new Pose(11.472, 10.483)),
                                mirrorPose(new Pose(11.000, 35.858))
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(11.000, 35.858)),
                                mirrorPose(new Pose(47.090, 12.455))
                        )
                )
                .setLinearHeadingInterpolation(mirrorHeading(91), mirrorHeading(115))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(47.090, 12.455)),
                                mirrorPose(new Pose(47.090, 23.204))
                        )
                )
                .setConstantHeadingInterpolation((mirrorHeading(110)))
                .build();
    }
}