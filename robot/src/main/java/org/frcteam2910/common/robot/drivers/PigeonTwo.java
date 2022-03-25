package org.frcteam2910.common.robot.drivers;

import com.ctre.phoenix.sensors.Pigeon2;
import org.frcteam2910.common.drivers.Gyroscope;
import org.frcteam2910.common.math.Rotation2;

public class PigeonTwo extends Gyroscope {
    private final Pigeon2 pigeon;

    public PigeonTwo(int canId) {
        pigeon = new Pigeon2(canId);
    }

    public PigeonTwo(int canId, String canBus) {
        pigeon = new Pigeon2(canId, canBus);
    }

    /* 
        This method does nothing
    */
    @Override
    public void calibrate() {
        //pigeon.setFusedHeading(0);
    }

    @Override
    public Rotation2 getUnadjustedAngle() {
        return Rotation2.fromRadians(getAxis(Axis.YAW));
    }

    @Override
    public double getUnadjustedRate() {
        return 0; // TODO
    }

    public double getAxis(Axis axis) {
        double[] ypr = new double[3];
        pigeon.getYawPitchRoll(ypr);
        switch (axis) {
            case PITCH:
                return Math.toRadians(ypr[1]);
            case ROLL:
                return Math.toRadians(ypr[2]);
            case YAW:
                return Math.toRadians(ypr[0]);
            default:
                return 0.0;
        }
    }

    public enum Axis {
        PITCH,
        ROLL,
        YAW
    }
}
