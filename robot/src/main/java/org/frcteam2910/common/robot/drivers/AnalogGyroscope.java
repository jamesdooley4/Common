package org.frcteam2910.common.robot.drivers;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.AnalogGyroSim;
import org.frcteam2910.common.drivers.Gyroscope;
import org.frcteam2910.common.math.Rotation2;

public final class AnalogGyroscope extends Gyroscope {

	private final AnalogGyro gyro;
	private final AnalogGyroSim gyroSim;

	public AnalogGyroscope(int analogPort) {
		this(new AnalogGyro(analogPort));
	}

	public AnalogGyroscope(AnalogGyro gyro) {
		this.gyro = gyro;
		this.gyroSim = RobotBase.isSimulation() ? new AnalogGyroSim(gyro) : null;
	}

	@Override
	public void calibrate() {
		gyro.calibrate();
	}

	@Override
	public Rotation2 getUnadjustedAngle() {
		return Rotation2.fromDegrees(gyro.getAngle());
	}

	@Override
	public double getUnadjustedRate() {
		return gyro.getRate();
	}

	@Override
	public void setSimAngle(Rotation2 angle) {
		gyroSim.setAngle(angle.toDegrees());
	}
}
