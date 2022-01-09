package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.robot;

import java.util.Locale;


/**
 * Created by shell bots on 1/08/2022.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Manual Mode OuttakeTester")
public class TeleOpOuttakeTester extends OpMode {

	private static final boolean TUNING = true;
	private robot robot = new robot();
	private Logger logger = null;
	private double speed = 0.5;
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
		 *
		 * Controller 2 - "Arm Controller" -------------------------------------------------------
		 *
		 *
		 */

		/* Controller 1 settings --------------------------------------------------------------- */

		while (this.gamepad1.right_trigger > 0.5) {
			robot.outtake.freightcrane.setVerticalCranePower(1);
		} if (this.gamepad1.right_trigger < 0.5) {
			robot.outtake.freightcrane.setVerticalCranePower(0);
		}

		while (this.gamepad1.left_trigger > 0.5) {
			robot.outtake.freightcrane.setVerticalCranePower(-1);
		} if (this.gamepad1.left_trigger < 0.5) {
			robot.outtake.freightcrane.setVerticalCranePower(0);
		}


		while (this.gamepad1.y) {
			robot.outtake.freightcrane.setCraneHorizontalPower(1);
		}

		while (this.gamepad1.x) {
			robot.outtake.freightcrane.setCraneHorizontalPower(-1);
		}

		robot.outtake.freightcrane.setCraneHorizontalPower(0);


		if (this.gamepad1.dpad_up) {
			robot.outtake.freightcontainer.setContainerFlipperPower(1);
		} else if (this.gamepad1.dpad_down) {
			robot.outtake.freightcontainer.setContainerFlipperPower(-1);
		} else if (this.gamepad1.dpad_left){
			robot.outtake.freightcontainer.setContainerFlipperPower(0);
		}

		/* Controller 2 settings --------------------------------------------------------------- */

		if (this.gamepad2.dpad_up) {
			robot.outtake.awwoooooogsetOutTakeToTopPosition();
		} else if (this.gamepad2.dpad_right) {
			robot.outtake.awwoooooogsetOutTakeToLowerPosition();
		} else if (this.gamepad2.dpad_down) {
			robot.outtake.collapseOutTake();
		} else if (this.gamepad2.dpad_left) {
			robot.outtake.freightcrane.verticalMotor.setPower(0);
			robot.outtake.freightcrane.leftHorizontalServo.setPower(0);
			robot.outtake.freightcrane.rightHorizontalServo.setPower(0);
			robot.outtake.freightcontainer.containerMotor.setPower(0);
		}


		logger.addData("CM Curr Pos", String.format(Locale.US, "Running at VL:%7d", robot.outtake.freightcontainer.containerMotor.getCurrentPosition()));
		logger.addData("VM Curr Pos", String.format(Locale.US, "Running at VL:%7d", robot.outtake.freightcrane.verticalMotor.getCurrentPosition()));
		logger.update();

		telemetry.addData("current outtake Position", robot.outtake.getOutTakePosition());
		telemetry.addData("containerMotor power", robot.outtake.freightcontainer.containerMotor.getPower());
		telemetry.addData("verticalMotor power", robot.outtake.freightcrane.verticalMotor.getPower());
		telemetry.update();
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