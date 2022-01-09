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
public abstract class BaseAutonomous extends LinearOpMode {

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
		//this turns the duck spinner off
		robot.arm.turnOnSpinner(0);
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
			robot.drivetrain.runDistance(-distance, distance, distance, -distance, maxSeconds, speed);
//		}
	}

	public void moveLeft(double distance, double maxSeconds, double speed) {
		distance *= 4.5;
//		if(getColor() == Color.RED) {
//			robot.drivetrain.runDistance(distance, -distance, -distance, distance, maxSeconds, speed);
//		} else {
			robot.drivetrain.runDistance(distance, -distance, -distance, distance, maxSeconds, speed);
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

	public void raiseArm(boolean isHoldingBlock) {
		if (isHoldingBlock) {
			robot.arm.raiseWithPower(1);
			sleep(500);
		} else {
			robot.arm.raiseWithPower(0.5);
			sleep(500);
		}
	}

	public void lowerArm(boolean isHoldingBlock) {
		if(isHoldingBlock) {
			robot.arm.lowerWithPower(0.5);
			sleep(500);
		} else {
			robot.arm.lowerWithPower(0.25);
			sleep(500);
		}
		robot.arm.stopAllMotors();
	}

	/*
	public void maintainPosition() {
		if(armRunMode == DcMotor.RunMode.RUN_TO_POSITION) {
			return;
		} else if(armRunMode != DcMotor.RunMode.RUN_USING_ENCODER) {
			setRunMode(DcMotor.RunMode.RUN_USING_ENCODER, rightArm);
		}
		leftArm.setTargetPosition(leftArm.getCurrentPosition() + 80);
		//leftArm.setTargetPosition(300);
		logger.completeLog("Pos", String.valueOf(leftArm.getCurrentPosition()));

		setRunMode(DcMotor.RunMode.RUN_TO_POSITION, leftArm);
		armRunMode = DcMotor.RunMode.RUN_TO_POSITION;

		leftArm.setPower(0.68);
		rightArm.setPower(0);
	}
	*/

	@Override
	public void runOpMode() {
		// Initialize motors/servos
		robot.init(hardwareMap, telemetry, this);
		logger = new Logger(telemetry);
	}
}
