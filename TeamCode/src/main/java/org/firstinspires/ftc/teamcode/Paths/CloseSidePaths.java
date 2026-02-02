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

        startPose = mirrorPose(new Pose(27.393, 131.862, Math.toRadians(144)));

        path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(27.393, 131.862)),
                                mirrorPose(new Pose(47.773, 105.270))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(144)), (mirrorHeading(132)))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(47.773, 105.270)),
                                mirrorPose(new Pose(57.607, 77.820)),
                                mirrorPose(new Pose(22.228, 81.859))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(132)), (mirrorHeading(180)))
                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(22.228, 81.859)),
                                mirrorPose(new Pose(21.683, 73.930))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(90)))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(21.683, 73.930)),
                                mirrorPose(new Pose(55.748, 84.369))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(90)), (mirrorHeading(132)))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(55.748, 84.369)),
                                mirrorPose(new Pose(67.796, 50.844)),
                                mirrorPose(new Pose(18.414, 57.857))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(132)), (mirrorHeading(180)))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(18.414, 57.857)),
                                mirrorPose(new Pose(38.159, 52.616)),
                                mirrorPose(new Pose(52.493, 82.663))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(132)))
                .build();

        Path7 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(52.493, 82.663)),
                                mirrorPose(new Pose(69.951, 29.618)),
                                mirrorPose(new Pose(17.765, 35.309))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(132)), (mirrorHeading(180)))
                .build();

        Path8 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(17.765, 35.309)),
                                mirrorPose(new Pose(51.981, 111.319))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(142)))
                .build();
    }
}