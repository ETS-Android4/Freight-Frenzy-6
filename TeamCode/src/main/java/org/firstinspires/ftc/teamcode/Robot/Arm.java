package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class Arm extends RobotComponent {

	public DcMotor pivotMotor = null;
	public DcMotor extensionMotor = null;
	private CRServo leftHand = null;
	private CRServo rightHand = null;
	private CRServo duckSpinner = null;
	private boolean isGrabbing = false;
	private DcMotor.RunMode armRunMode = DcMotor.RunMode.RUN_USING_ENCODER;

	private Logger logger = null;

	Arm(OpMode opmode) {
		super(opmode);
	}

	void init(Telemetry telemetry, DcMotor pivotMotor, DcMotor extensionMotor, CRServo leftHand, CRServo rightHand, CRServo duckSpinner) {
		logger = new Logger(telemetry);

		this.pivotMotor = pivotMotor;
		this.extensionMotor = extensionMotor;
		this.rightHand = rightHand;
		this.leftHand = leftHand;
		this.duckSpinner = duckSpinner;

		pivotMotor.setDirection(DcMotor.Direction.FORWARD);
		extensionMotor.setDirection(DcMotor.Direction.FORWARD);
		rightHand.setDirection(CRServo.Direction.FORWARD);
		leftHand.setDirection(CRServo.Direction.FORWARD);
		duckSpinner.setDirection(CRServo.Direction.FORWARD);

		pivotMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		pivotMotor.setTargetPosition(0);
		extensionMotor.setTargetPosition(0);

		// Set all motors to zero power
		setSpecificPowers(0, pivotMotor, extensionMotor);

		// Set all motors to run with encoders.
		setRunMode(DcMotor.RunMode.RUN_USING_ENCODER, pivotMotor, extensionMotor);
	}

	@Override
	public void logTeleOpData() {
		logger.numberLog("pivotMotor", pivotMotor.getPower());
		logger.numberLog("extensionMotor", extensionMotor.getPower());
	}

	@Override
	public <T> void init(Telemetry telemetry, T pivotMotor, T extensionMotor, T rightHand, T leftHand, T duckSpinner) {

	}

	public void extendWithPower(double power) {extensionMotor.setPower(power);}

	public void raiseWithPower(double power) {
		if (armRunMode != DcMotor.RunMode.RUN_USING_ENCODER) {
			setRunMode(DcMotor.RunMode.RUN_USING_ENCODER, pivotMotor);
			armRunMode = DcMotor.RunMode.RUN_USING_ENCODER;
		}
		pivotMotor.setPower(power);
	}

	public void lowerWithPower(double power) {
		if (armRunMode != DcMotor.RunMode.RUN_USING_ENCODER) {
			setRunMode(DcMotor.RunMode.RUN_USING_ENCODER, pivotMotor);
			armRunMode = DcMotor.RunMode.RUN_USING_ENCODER;
		}
		pivotMotor.setPower(-power);
	}

	public void raiseArm(boolean isHoldingBlock) {
		if (isHoldingBlock) {
			raiseWithPower(1);
			sleep(500);
		} else {
			raiseWithPower(0.5);
			sleep(500);
		}
	}

	public void lowerArm(boolean isHoldingBlock) {
		if(isHoldingBlock) {
			lowerWithPower(0.5);
			sleep(500);
		} else {
			lowerWithPower(0.25);
			sleep(500);
		}
		stopAllMotors();
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
	public void grabHand() {
		isGrabbing = true;
		setServoPosition(rightHand, 1);
		setServoPosition(leftHand,0);
	}

	public void releaseHand() {
		isGrabbing = false;
		setServoPosition(rightHand, 0);
		setServoPosition(leftHand, 1);
	}

	public boolean isGrabbing() {
		return isGrabbing;
	}

	public void turnOnSpinner(double power) {
		duckSpinner.setPower(power);
	}
	public void turnOffSpinner() {
		duckSpinner.setPower(0);
	}
	public void toggleSpinner() {

	}

	@Override
	public void stopAllMotors() {
		setSpecificPowers(0, pivotMotor, extensionMotor);
	}

}
