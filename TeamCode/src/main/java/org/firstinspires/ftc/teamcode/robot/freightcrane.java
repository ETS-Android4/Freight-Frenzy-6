package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

import java.util.Locale;

public class freightcrane extends robotcomponent{

	public DcMotor verticalMotor;
	public CRServo leftHorizontalServo;
	public CRServo rightHorizontalServo;

	private Logger logger = null;

	freightcrane(OpMode opmode) {
		super(opmode);
	}

	 void init(Telemetry telemetry, DcMotor verticalMotor, CRServo leftHorizontalServo, CRServo rightHorizontalServo) {

		logger = new Logger(telemetry);

		this.verticalMotor = verticalMotor;
		this.leftHorizontalServo = leftHorizontalServo;
		this.rightHorizontalServo = rightHorizontalServo;

		verticalMotor.setTargetPosition(0);
		verticalMotor.setDirection(DcMotor.Direction.FORWARD);
		verticalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}

	public void setVerticalCranePower(double power) {
		verticalMotor.setPower(power);
	}

	public void extendCraneVertically(double position, double power, double maxSeconds) {
		int newVerticalMotorTarget = verticalMotor.getCurrentPosition() + (int) (-position * COUNTS_PER_INCH);

		verticalMotor.setTargetPosition(newVerticalMotorTarget);

		verticalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		ElapsedTime runtime = new ElapsedTime();

		setVerticalCranePower(power);

		while (opModeIsActive() && (runtime.seconds() < maxSeconds) && (verticalMotor.isBusy())) {
			logger.addData("Path1", String.format(Locale.US, "Running to VL:%7d", newVerticalMotorTarget));
			logger.addData("Path2", String.format(Locale.US, "Running at VL:%7d", verticalMotor.getCurrentPosition()));
			logger.update();

			logger.completeLog("Is busy? FL", verticalMotor.isBusy() ? "yes" : "no");
			logger.numberLog("Curr Time", runtime.seconds());
			logger.numberLog("Max Time", maxSeconds);
			logger.completeLog("Opmode is active? ", opModeIsActive() ? "yes" : "no");
		}
	}

	public void extendCraneHorizontally(long sleep) {
		leftHorizontalServo.setPower(0.5);
		sleep(sleep);
		rightHorizontalServo.setPower(0);
	}

	public void collapseCraneHorizontally(long sleep) {
		leftHorizontalServo.setPower(-0.5);
		sleep(sleep);
		rightHorizontalServo.setPower(0);
	}

	@Override
	public void stopEverything() {
		verticalMotor.setPower(0);
	}

	@Override
	void logTeleOpData() {
		logger.numberLog("verticalMotor", verticalMotor.getPower());
		logger.numberLog("verticalMotor", verticalMotor.getCurrentPosition());
	}
}
