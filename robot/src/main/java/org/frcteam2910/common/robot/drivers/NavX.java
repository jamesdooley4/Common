package org.frcteam2910.common.robot.drivers;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;
import org.frcteam2910.common.drivers.Gyroscope;
import org.frcteam2910.common.math.Rotation2;

public final class NavX extends Gyroscope {
    private final AHRS navX;
    private final SimDouble simYaw;

    public NavX(SPI.Port port) {
        this(port, (byte) 200);
    }
    public NavX(SerialPort.Port port){
        this(port, (byte) 200);
    }

    public NavX(SPI.Port port, byte updateRate) {
        this(new AHRS(port, updateRate));
    }

    public NavX(SerialPort.Port port, byte updateRate) {
        this(new AHRS(port, AHRS.SerialDataType.kProcessedData, updateRate));
    }

    private NavX(AHRS navX) {
        this.navX = navX;

        // Hack, but it works...
        SimDeviceDataJNI.SimDeviceInfo[] deviceInfo = RobotBase.isSimulation() ? SimDeviceSim.enumerateDevices("navX-Sensor") : null;
        if ((deviceInfo != null) && (deviceInfo.length > 0)) {
            SimDevice simDevice = new SimDevice(deviceInfo[0].handle);
            simYaw   = simDevice.createDouble("Yaw", SimDevice.Direction.kBidir, 0.0f);
        } else {
            simYaw = null;
        }
    }

    @Override
    public void calibrate() {
        navX.reset();
    }

    @Override
    public Rotation2 getUnadjustedAngle() {
        return Rotation2.fromRadians(getAxis(Axis.YAW));
    }

    @Override
    public double getUnadjustedRate() {
        return Math.toRadians(navX.getRate());
    }

    @Override
    public void setSimAngle(Rotation2 angle) {
        simYaw.set(angle.toDegrees());
    }

    public double getAxis(Axis axis) {
        switch (axis) {
            case PITCH:
                return Math.toRadians(navX.getPitch());
            case ROLL:
                return Math.toRadians(navX.getRoll());
            case YAW:
                return Math.toRadians(navX.getYaw());
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
