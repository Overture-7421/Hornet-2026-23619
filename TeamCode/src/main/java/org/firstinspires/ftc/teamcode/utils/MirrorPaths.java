package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import org.firstinspires.ftc.teamcode.utils.Robot.Alliance;

public abstract class MirrorPaths {

    protected final boolean mirror;

    protected MirrorPaths(Alliance alliance) {
        mirror = (alliance == Alliance.Red);
    }

    protected Pose mirrorPose(Pose p) {
        return mirror ? p.mirror() : p;
    }

    protected double h(double radians) {
        return mirror
                ? MathFunctions.normalizeAngle(Math.PI - radians)
                : radians;
    }

    protected double mirrorHeading(double degrees) {
        return h(Math.toRadians(degrees));
    }
}
