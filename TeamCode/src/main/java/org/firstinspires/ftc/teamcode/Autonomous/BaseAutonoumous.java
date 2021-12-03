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

	public void turnOnDuckSpinner(long time) {
		if(getColor() == Color.RED) {
			robot.arm.turnOnSpinner(-1);
		} else {
			robot.arm.turnOnSpinner(1);
		}
		sleep(time);
		robot.arm.turnOffSpinner();
	}

	public void moveBackwards(double distance, double maxSeconds, double speed) {
			distance *= 4.5;
//		if(getColor() == Color.RED) {
//			robot.drivetrain.runDistance(distance, distance, distance, distance, maxSeconds, speed);
//		} else {
			robot.drivetrain.runDistance(-distance, -distance, -distance, -distance, maxSeconds, speed);
//		}
	}

	public void moveRight(double distance, double maxSeconds, double speed) {
		distance *= 4.5;
//		if(getColor() == Color.RED) {
//			robot.drivetrain.runDistance(-distance, distance, distance, -distance, maxSeconds, speed);
//		} else {
			robot.drivetrain.runDistance(distance, -distance, -distance, distance, maxSeconds, speed);
//		}
	}

	public void moveLeft(double distance, double maxSeconds, double speed) {
		distance *= 4.5;
//		if(getColor() == Color.RED) {
//			robot.drivetrain.runDistance(distance, -distance, -distance, distance, maxSeconds, speed);
//		} else {
			robot.drivetrain.runDistance(-distance, distance, distance, -distance, maxSeconds, speed);
//		}
	}

	public void moveForwards(double distance, double maxSeconds, double speed) {
		distance *= 4.5;
//		if(getColor() == Color.RED) {
//			robot.drivetrain.runDistance(-distance, -distance, -distance, -distance, maxSeconds, speed);
//		} else {
			robot.drivetrain.runDistance(distance, distance, distance, distance, maxSeconds, speed);
//		}
	}

	@Override
	public void runOpMode() {
		// Initialize motors/servos
		robot.init(hardwareMap, telemetry, this);
		logger = new Logger(telemetry);
	}

}
