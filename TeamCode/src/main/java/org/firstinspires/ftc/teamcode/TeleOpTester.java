package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.robot;

import java.util.Locale;


/**
 * Created by shell bots on 12/31/2021.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "TeleOpTester")
public class TeleOpTester extends OpMode {

	private static final boolean TUNING = true;
	private robot robot = new robot();
	private Logger logger = null;
	private double speed = .5;
	private boolean isIntakeRunning = false;

	/**
	 * Run once after INIT is pushed
	 */

	@Override
	public void init() {
		robot.init(hardwareMap, telemetry, this, false);
		logger = new Logger(telemetry);

		// Step 0 - Initialized
		logger.statusLog(0, "Initialized");
	}

	/**
	 * Runs constantly after INIT is pushed but before PLAY is pushed
	 */

	@Override
	public void init_loop() {

	}

	/**
	 * Runs once after PLAY is pushed
	 */

	@Override
	public void start() {
		// Step 1 - Playing
		logger.statusLog(0, "Playing");
	}

	@Override
	public void loop() {

		/* Controller Layouts
		 *
		 * Controller 1 - "Body Controller" ------------------------------------------------------
		 *
		 *      Left Trigger - Set full speed
		 *      Right Trigger - Set half speed
		 *
		 *      Right Joystick X - Turn the robot
		 * 		Left Joystick X - Make the robot crab-walk
		 * 		Left Joystick Y - Make the robot drive forwards and backwards
		 *
		 * 		Dpad Up - Raise the dead wheels off the ground
		 * 		Dpad Down - Lower the dead wheels onto the ground
		 *
		 * 		Y button - Turn the Intake on forwards
		 * 		A button - Turn the Intake on backwards
		 *
		 * 		X button - Spin the duck-spinners for blue alliance
		 * 		B button - Spin the duck-spinners for red alliance
		 *
		 * Controller 2 - "Arm Controller" -------------------------------------------------------
		 *
		 * 		Right Trigger - extend the crane vertically
		 * 		Right Bumper - extend the crane horizontally
		 *
		 * 		left Trigger - collapse the crane horizontally
		 * 		left Bumper - collapse the crane horizontally
		 *
		 *		X button - flip the container open
		 * 		B button - flip the container closed
		 *
		 * 		up dpad - open the container
		 * 		down dpad - close the container
		 *
		 */

		/* Controller 1 settings --------------------------------------------------------------- */

		// Move according to player 1's joysticks
		singleJoystickDrive();

		// Change driving speed
		if (this.gamepad1.right_trigger > 0.5) {
			speed = 1.0;
		}
		else if (this.gamepad1.left_trigger > 0.5) {
			speed = 0.5;
		}

		telemetry.addData("Chassis Speed", speed);

		// Raise and Lower the dead-wheels
		if (this.gamepad1.dpad_up) {
			robot.odometerpods.raiseOdometerWheels();
		} else if (this.gamepad1.dpad_down) {
			robot.odometerpods.lowerOdometerWheels();
		}

		// Turn the intake on and off
		if (this.gamepad1.y) {
			if(isIntakeRunning) {
				robot.intake.setIntakePower(0);
			} else {
				robot.intake.setIntakePower(1);
			}
			isIntakeRunning = !isIntakeRunning;
		} else if (this.gamepad1.a) {
			if(isIntakeRunning) {
				robot.intake.setIntakePower(0);
			} else {
				robot.intake.setIntakePower(-1);
			}
			isIntakeRunning = !isIntakeRunning;
		}

		while(this.gamepad1.x) {
			robot.caroselspinner.supplySpinnersPower(1);
		}
		while(this.gamepad1.b) {
			robot.caroselspinner.supplySpinnersPower(-1);
		}
		robot.caroselspinner.supplySpinnersPower(0);

		/* Controller 2 settings --------------------------------------------------------------- */

		while (this.gamepad2.right_trigger > 0.5) {
			robot.outtake.freightcrane.verticalMotor.setPower(.5);
		} if (this.gamepad2.right_trigger < 0.5) {
			robot.outtake.freightcrane.verticalMotor.setPower(0);
		}

		while (this.gamepad2.left_trigger > 0.5) {
			robot.outtake.freightcrane.verticalMotor.setPower(-.5);
		} if (this.gamepad2.left_trigger < 0.5) {
			robot.outtake.freightcrane.verticalMotor.setPower(0);
		}


		if (this.gamepad2.dpad_up) {
			robot.outtake.freightcontainer.containerMotor.setPower(.5);
		} else if (this.gamepad2.dpad_down) {
			robot.outtake.freightcontainer.containerMotor.setPower(-.5);
		} else if (this.gamepad2.dpad_left){
			robot.outtake.freightcontainer.containerMotor.setPower(0);
		}

		if (this.gamepad2.y) {
			robot.outtake.freightcontainer.openContainer();
		} else if (this.gamepad2.a) {
			robot.outtake.freightcontainer.closeContainer();
		} else if (this.gamepad2.x) {
			robot.outtake.freightcontainer.openContainerCompletely();
		}


		telemetry.addData("Curr right Pos", robot.odometerpods.rightXAxis.getPosition());
		telemetry.addData("Curr left Pos", robot.odometerpods.leftXAxis.getPosition());
		telemetry.addData("Curr middle Pos", robot.odometerpods.middleYAxis.getPosition());
		telemetry.addData("CM Curr Pos",String.format(Locale.US, "%7d", robot.outtake.freightcontainer.containerMotor.getCurrentPosition()));
		telemetry.addData("CM power", robot.outtake.freightcontainer.containerMotor.getPower());
		telemetry.addData("VM Curr Pos", String.format(Locale.US, "%7d", robot.outtake.freightcrane.verticalMotor.getCurrentPosition()));
		telemetry.addData("VM power", robot.outtake.freightcrane.verticalMotor.getPower());
//		telemetry.addData("Curr OutTakePos", outTakePosition);
		telemetry.update();

/*
		while (this.gamepad2.dpad_up) {
			robot.drivetrain.frontLeft.setPower(1);
		}
		while (this.gamepad2.dpad_left) {
			robot.drivetrain.backRight.setPower(1);
		}
		while (this.gamepad2.dpad_right) {
			robot.drivetrain.backLeft.setPower(1);
		}
		while (this.gamepad2.dpad_down) {
			robot.drivetrain.frontRight.setPower(1);
		}
		robot.drivetrain.setAllPowers(0);
		*/
	}

	/**
	 * Runs once after STOP is pushed
	 */

	@Override
	public void stop() {
		robot.stopEverything();
		logger.completeLog("Status", "Stopped");
		logger.update();
	}

	private void singleJoystickDrive() {
		double leftX = this.gamepad1.left_stick_x;
		double leftY = this.gamepad1.left_stick_y;
		double rightX = this.gamepad1.right_stick_x;

		double[] motorPowers = new double[4];
		motorPowers[0] = (leftY-leftX-rightX);// -+
		motorPowers[1] = (leftY+leftX+rightX);// +-
		motorPowers[2] = (leftY+leftX-rightX);// ++
		motorPowers[3] = (leftY-leftX+rightX);// --

		double max = Math.abs(getLargestAbsVal(motorPowers));
		if(max < 1) { max = 1; }

		for(int i = 0; i < motorPowers.length; i++) {
			motorPowers[i] /= max;
		}

		for(int i = 0; i < motorPowers.length; i++) {
			if(motorPowers[i] < 0.05 && motorPowers[i] > -0.05) { motorPowers[i] = 0.0; }
			if(motorPowers[i] > 1.0) { motorPowers[i] = 1.0; }
			if(motorPowers[i] < -1.0) { motorPowers[i] = -1.0; }
			motorPowers[i] *= speed;
			//logger.numberLog("Motor" + i, motorPowers[i]);
		}

		robot.drivetrain.setIndividualPowers(motorPowers);
	}

	private double getLargestAbsVal(double[] values) {
		double max = 0;
		for(double val : values) {
			if(Math.abs(val) > max) { max = Math.abs(val); }
		}
		return max;
	}
}