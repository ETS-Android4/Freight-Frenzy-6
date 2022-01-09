package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

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
	}

	public void setVerticalCranePower(double power)
	{
		verticalMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		verticalMotor.setPower(power);
	}

	public void extendCraneVertically(double position, double power, double maxSeconds) {
		int newVerticalMotorTarget = verticalMotor.getCurrentPosition() + (int) (-position * COUNTS_PER_INCH);

		verticalMotor.setTargetPosition(newVerticalMotorTarget);

		verticalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		ElapsedTime runtime = new ElapsedTime();

		setVerticalCranePower(power);

		while (opModeIsActive() && (runtime.seconds() < maxSeconds) && (verticalMotor.isBusy())) {
		//	logger.addData("VM Target", String.format(Locale.US, "Running to VL:%7d", newVerticalMotorTarget));
		//	logger.addData("VM Curr Pos", String.format(Locale.US, "Running at VL:%7d", verticalMotor.getCurrentPosition()));

			logger.completeLog("Is busy? VM", verticalMotor.isBusy() ? "yes" : "no");
		//	logger.numberLog("Curr Time", runtime.seconds());
		//	logger.numberLog("Max Time", maxSeconds);
		//	logger.completeLog("Opmode is active? ", opModeIsActive() ? "yes" : "no");
			logger.update();
		}
	}

	public void extendCraneHorizontally(long sleep) {
		leftHorizontalServo.setPower(1);
		sleep(sleep);
		rightHorizontalServo.setPower(0);
	}

	public void collapseCraneHorizontally(long sleep) {
		leftHorizontalServo.setPower(-1);
		sleep(sleep);
		rightHorizontalServo.setPower(0);
	}

	public void setCraneHorizontalPower(double power) {
		leftHorizontalServo.setPower(power);
		rightHorizontalServo.setPower(power);
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
