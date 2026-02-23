package org.firstinspires.ftc.teamcode.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.utils.MirrorPaths;
import org.firstinspires.ftc.teamcode.utils.MyRobot.Alliance;

public class CloseSidePaths extends MirrorPaths {

    public PathChain Path1;
    public PathChain Path2;
    public PathChain Path3;
    public PathChain Path4;
    public PathChain Path5;
    public PathChain Path6;
    public PathChain Path6V2;

    public PathChain Path7;
    public PathChain End;

    public Pose startPose;

    public CloseSidePaths(Follower follower, Alliance allianceColor) {
        super(allianceColor);

        startPose = mirrorPose(new Pose(27.393, 131.862, Math.toRadians(144)));

        Path1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(27.393, 131.862)),
                                mirrorPose(new Pose(46.066, 112.095))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(144)), (mirrorHeading(132)))
                .build();

        Path2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(46.066, 112.095)),
                                mirrorPose(new Pose(53.256, 61.095)),
                                mirrorPose(new Pose(63.882, 56.607)),
                                mirrorPose(new Pose(11.773, 57.498))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(132)), (mirrorHeading(180)))
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(11.773, 57.498)),
                                mirrorPose(new Pose(43.858, 65.052)),
                                mirrorPose(new Pose(57.839, 89.232))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(136)))

                .build();

        Path3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(57.839, 89.232)),
                                mirrorPose(new Pose(45.043, 62.569)),
                                mirrorPose(new Pose(23.545, 66.028))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(136)), (mirrorHeading(180)))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(23.545, 66.028)),
                                mirrorPose(new Pose(20.910, 57.365)),
                                mirrorPose(new Pose(14.673, 57.327))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(136)))
                .build();

        Path5 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(14.673, 57.327)),
                                mirrorPose(new Pose(46.592, 61.190)),
                                mirrorPose(new Pose(54.597, 91.962))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(136)), (mirrorHeading(136)))
                .build();

        Path6 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(54.597, 91.962)),
                                mirrorPose(new Pose(46.649, 80.616)),
                                mirrorPose(new Pose(23.033, 84.114))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(136)), (mirrorHeading(180)))
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(23.033, 84.114)),
                                mirrorPose(new Pose(60.739, 84.114))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(136)))
                .build();

        Path6V2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(54.597, 91.962)),
                                mirrorPose(new Pose(46.649, 80.616)),
                                mirrorPose(new Pose(23.033, 84.114))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(136)), (mirrorHeading(180)))
                .addPath(
                        new BezierLine(
                                mirrorPose(new Pose(23.033, 84.114)),
                                mirrorPose(new Pose(59.716, 102.370))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(136)))
                .build();

        Path7 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                mirrorPose(new Pose(60.739, 84.114)),
                                mirrorPose(new Pose(65.199, 29.322)),
                                mirrorPose(new Pose(12.967, 34.123))
                        )
                )
                .setLinearHeadingInterpolation((mirrorHeading(136)), (mirrorHeading(180)))
                .addPath(
                    new BezierLine(
                            mirrorPose(new Pose(12.967, 34.123)),
                            mirrorPose(new Pose(59.716, 102.370))
                    )
                )
                .setLinearHeadingInterpolation((mirrorHeading(180)), (mirrorHeading(142)))
                .build();

        End = follower
                .pathBuilder()
                .addPath(
                    new BezierLine(
                            mirrorPose(new Pose(60.739, 84.114)),
                            mirrorPose(new Pose(59.716, 102.370))
                    )
                )
                .setLinearHeadingInterpolation((mirrorHeading(136)), (mirrorHeading(136)))
                .build();
    }
}