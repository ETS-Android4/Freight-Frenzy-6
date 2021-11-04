package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Logger;
import org.firstinspires.ftc.teamcode.Robot.Robot;

/**
 * Created by shell on 10/27/2021.
 */

@Disabled
@Autonomous(group = "Base", name = "Base: Autonomous")
public abstract class BaseAutonoumous extends LinearOpMode {

	protected abstract Color getColor();

	public Robot robot = new Robot();
	public Logger logger = null;
	public int step = 0;

	protected enum Color {
		RED,
		BLUE
	}



	public void moveTowardsRedAlliance(double distance, double maxSeconds, double speed) {
		distance *= 2.25;
		if(getColor() == Color.RED) {
			robot.drivetrain.runDistance(distance, distance, distance, distance, maxSeconds, speed);
		} else {
			robot.drivetrain.runDistance(-distance, -distance, -distance, -distance, maxSeconds, speed);
		}
	}

	public void moveTowardsWareHouse(double distance, double maxSeconds, double speed) {
		distance *= 2.25;
		if(getColor() == Color.RED) {
			robot.drivetrain.runDistance(-distance, distance, distance, -distance, maxSeconds, speed);
		} else {
			robot.drivetrain.runDistance(distance, -distance, -distance, distance, maxSeconds, speed);
		}
	}

	public void moveTowardsAudience(double distance, double maxSeconds, double speed) {
		distance *= 2.25;
		if(getColor() == Color.RED) {
			robot.drivetrain.runDistance(distance, -distance, -distance, distance, maxSeconds, speed);
		} else {
			robot.drivetrain.runDistance(-distance, distance, distance, -distance, maxSeconds, speed);
		}
	}

	public void moveTowardsBlueAlliance(double distance, double maxSeconds, double speed) {
		distance *= 2.25;
		if(getColor() == Color.RED) {
			robot.drivetrain.runDistance(-distance, -distance, -distance, -distance, maxSeconds, speed);
		} else {
			robot.drivetrain.runDistance(distance, distance, distance, distance, maxSeconds, speed);
		}
	}

	@Override
	public void runOpMode() {
		// Initialize motors/servos
		robot.init(hardwareMap, telemetry, this);
		logger = new Logger(telemetry);
	}

}
