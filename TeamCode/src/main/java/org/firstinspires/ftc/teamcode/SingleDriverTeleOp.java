package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot.Robot;


/**
 * Created by shell on 11/04/2021.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Single Driver Manual Mode")
public class SingleDriverTeleOp extends OpMode {

	private Robot robot = new Robot();
	private Logger logger = null;

	private double speed = 0.5;
	private double armSpeed = 0.25;
	private double duckPower = 1;

	private boolean last_x = false;
	private boolean last_a = false;
	private boolean last_y = false;
	private boolean last_b = false;

	/**
	 * Run once after INIT is pushed
	 */
	@Override
	public void init() {
		robot.init(hardwareMap, telemetry, this);
		logger = new Logger(telemetry);
		this.msStuckDetectStop = 60000;

		// Step 0 - Initialized
		logger.statusLog(0, "Initialized");

	}

	/**
	 * Runs constantly after INIT is pushed but before PLAY is pushed
	 */
	@Override
	public void init_loop() {

	}

	int X = 0;
	int Y = 0;

	/**
	 * Runs once after PLAY is pushed
	 */
	@Override
	public void start() {
		logger.statusLog(0, "Playing");
	}

	@Override
	public void loop() {
		/* Controller Layouts
		 *
		 * Controller 1 - "Body Controller"
		 *      Left Trigger     - extend the arm
		 *      Right Trigger    - retrack the arm
		 *
		 * 		X Button 		 - Grab blocks
		 * 		B Button		 - Release blocks
		 * 		Y Button		 - Turn on the duck spinner
		 * 		A Button		 - Turn on the duck spinner
		 *
		 * 		Right Bumper	 - Half speed
		 * 		left Bumper 	 - Half speed
		 *
		 * 		Up Dpad			 - Up arm
		 * 		Down Dpad		 - Down arm
		 *
		 *      Right Joystick X - Turn the robot
		 *
		 */

		/*
		 * Controller 1 settings
		 */

		if (this.gamepad1.right_trigger > 0.5) {
			robot.arm.extendWithPower(0.55);
		} else if (this.gamepad1.left_trigger > 0.5) {
			robot.arm.extendWithPower(-0.55);
		} else {
			robot.arm.extendWithPower(0);
		}

		if(this.gamepad1.x) {
			robot.arm.grabHand();
		} else if (this.gamepad1.b) {
			robot.arm.releaseHand();
		}

		if(this.gamepad1.y) {
			robot.arm.turnOnSpinner(1);
		} else if (this.gamepad1.a) {
			robot.arm.turnOnSpinner(-1);
		} else {
			robot.arm.turnOffSpinner();
		}

		if (this.gamepad1.right_bumper) {
			speed = 1.0;
		} else if (this.gamepad1.left_bumper) {
			speed = 0.5;
		}

		if (this.gamepad1.dpad_up) {
			robot.arm.lowerWithPower(armSpeed);
		} else if (this.gamepad1.dpad_down) {
			robot.arm.raiseWithPower(0.2);
		} else {
			robot.arm.lowerWithPower(0.05);
		}

		singleJoystickDrive();

		logger.numberLog("Drive Speed", speed);
		logger.numberLog("Arm Speed", armSpeed);
		logger.update();

	}

	/**
	 * Runs once after STOP is pushed
	 */
	@Override
	public void stop() {
		robot.stopAllMotors();
		logger.completeLog("Status", "Stopped");
		logger.update();
	}

	private void singleJoystickDrive() {
		float leftX = this.gamepad1.left_stick_x;
		float leftY = this.gamepad1.left_stick_y;
		float rightX = this.gamepad1.right_stick_x;

		float[] motorPowers = new float[4];
		motorPowers[0] = (leftY-leftX-rightX);// -+
		motorPowers[1] = (leftY+leftX+rightX);// +-
		motorPowers[2] = (leftY+leftX-rightX);// ++
		motorPowers[3] = (leftY-leftX+rightX);// --

		float max = getLargestAbsVal(motorPowers);
		if(max < 1) { max = 1; }

		for(int i = 0; i < motorPowers.length; i++) {
			motorPowers[i] *= (speed / max);

			float abs = Math.abs(motorPowers[i]);
			if(abs < 0.05) { motorPowers[i] = 0.0f; }
			if(abs > 1.0) { motorPowers[i] /= abs; }
			logger.numberLog("Motor" + i, motorPowers[i]);
		}

		robot.drivetrain.setIndividualPowers(motorPowers);
	}

	private float getLargestAbsVal(float[] values) {
		float max = 0;
		for(float val : values) {
			if(Math.abs(val) > max) { max = Math.abs(val); }
		}
		return max;
	}

}