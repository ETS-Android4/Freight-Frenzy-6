package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

import java.util.Locale;

public class Drivetrain extends RobotComponent {

	public DcMotor frontLeft;
	public DcMotor frontRight;
	public DcMotor backLeft;
	public DcMotor backRight;

	private Logger logger = null;
	public double defaultSpeed = 0.2;

	Drivetrain(OpMode opmode) {
		super(opmode);
	}

	void init(Telemetry telemetry, DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {

		logger = new Logger(telemetry);

		this.frontLeft = frontLeft;
		this.frontRight = frontRight;
		this.backLeft = backLeft;
		this.backRight = backRight;

		frontLeft.setTargetPosition(0);
		frontRight.setTargetPosition(0);
		backLeft.setTargetPosition(0);
		backRight.setTargetPosition(0);

		frontLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
		backLeft.setDirection(DcMotor.Direction.FORWARD);
		frontRight.setDirection(DcMotor.Direction.REVERSE);
		backRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

		frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);// Set to FORWARD if using AndyMark motors
		frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);// Set to FORWARD if using AndyMark motors
		backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);// Set to FORWARD if using AndyMark motors
		backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);// Set to FORWARD if using AndyMark motors

		setAllPowers(0);

		setRunMode(DcMotor.RunMode.RUN_USING_ENCODER, frontLeft, frontRight, backLeft, backRight);
	}

	@Override
	void logTeleOpData() {
		logger.numberLog("Frontleft", frontLeft.getPower());
		logger.numberLog("Frontleft", frontLeft.getCurrentPosition());
		logger.numberLog("Frontright", frontRight.getPower());
		logger.numberLog("Frontright", frontRight.getCurrentPosition());
		logger.numberLog("Backleft", backLeft.getPower());
		logger.numberLog("Backleft", backLeft.getCurrentPosition());
		logger.numberLog("Backright", backRight.getPower());
		logger.numberLog("Backright", backRight.getCurrentPosition());

	}

	@Override
	public <T> void init(Telemetry telemetry, T pivotMotor, T extensionMotor, T rightHand, T leftHand, T duckSpinner) {

	}

	public void runDistance(double frontLeftInches, double backLeftInches, double frontRightInches, double backRightInches, double maxSeconds, double maxSpeed) {
		if (!opModeIsActive()) {
			return;
		}

		// Reset encoder values
		setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER, frontLeft, frontRight, backLeft, backRight);
		setRunMode(DcMotor.RunMode.RUN_USING_ENCODER, frontLeft, frontRight, backLeft, backRight);
		int newFrontLeftTarget = frontLeft.getCurrentPosition() + (int) (-frontLeftInches * COUNTS_PER_INCH);
		int newBackLeftTarget = backLeft.getCurrentPosition() + (int) (-backLeftInches * COUNTS_PER_INCH);
		int newFrontRightTarget = frontRight.getCurrentPosition() + (int) (-frontRightInches * COUNTS_PER_INCH);
		int newBackRightTarget = backRight.getCurrentPosition() + (int) (-backRightInches * COUNTS_PER_INCH);
		frontLeft.setTargetPosition(newFrontLeftTarget);
		backLeft.setTargetPosition(newBackLeftTarget);
		frontRight.setTargetPosition(newFrontRightTarget);
		backRight.setTargetPosition(newBackRightTarget);

		// Turn On RUN_TO_POSITION
		setRunMode(DcMotor.RunMode.RUN_TO_POSITION, frontLeft, frontRight, backLeft, backRight);

		// reset the timeout time and start motion.
		ElapsedTime runtime = new ElapsedTime();

		// Log the path
		logger.completeLog("Path1", String.format(Locale.US,
				"Running to FL:%7d, BL:%7d, FR:%7d, BR:%7d", newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget));

		setAllPowers(maxSpeed);
		// While game is still going, maxtime has not been reached, and none of the motors have reached their position
		while (opModeIsActive() && (runtime.seconds() < maxSeconds) &&
				(frontLeft.isBusy() && backLeft.isBusy()) &&
				(frontRight.isBusy() && backRight.isBusy())) {

			logger.addData("Path1", String.format(Locale.US,
					"Running to FL:%7d, BL:%7d, FR:%7d, BR:%7d", newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget));
			logger.addData("Path2", String.format(Locale.US,
					"Running at FL:%7d BL:%7d FR:%7d BR:%7d",
					frontLeft.getCurrentPosition(),
					frontRight.getCurrentPosition(),
					backLeft.getCurrentPosition(),
					backRight.getCurrentPosition()));
			logger.update();
		}

		logger.completeLog("Is busy? FL", frontLeft.isBusy() ? "yes" : "no");
		logger.completeLog("Is busy? FR", frontRight.isBusy() ? "yes" : "no");
		logger.completeLog("Is busy? BL", backLeft.isBusy() ? "yes" : "no");
		logger.completeLog("Is busy? BR", backRight.isBusy() ? "yes" : "no");
		logger.numberLog("Curr Time", runtime.seconds());
		logger.numberLog("Max Time", maxSeconds);
		logger.completeLog("Opmode is active? ", opModeIsActive() ? "yes" : "no");

		// Stop all motion
		setAllPowers(0);

		// Turn off RUN_TO_POSITION
		setRunMode(DcMotor.RunMode.RUN_USING_ENCODER, frontLeft, frontRight, backLeft, backRight);

		// Stop all motion
		setAllPowers(0);
	}

	/**
	 * Sets all 4 motors on the drivetrain to a given power
	 *
	 * @param power The power to set the motors to (-1.0 to 1.0)
	 */
	public void setAllPowers(double power) {
		setSpecificPowers(power, frontLeft, frontRight, backLeft, backRight);
	}

	public void setIndividualPowers(float[] motorPowers) {
		if (motorPowers.length != 4) {
			return;
		}
		frontLeft.setPower(motorPowers[0]);
		frontRight.setPower(motorPowers[1]);
		backLeft.setPower(motorPowers[2]);
		backRight.setPower(motorPowers[3]);
	}

	@Override
	public void stopAllMotors() {
		setSpecificPowers(0, frontLeft, frontRight, backLeft, backRight);
	}

}
