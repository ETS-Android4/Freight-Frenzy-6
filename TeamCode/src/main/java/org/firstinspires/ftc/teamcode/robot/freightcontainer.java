package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

import java.util.Locale;

public class freightcontainer extends robotcomponent{

	public DcMotor containerMotor;
	public Servo containerServo;

	private Logger logger = null;

	freightcontainer(OpMode opmode) {
		super(opmode);
	}

	public void init (Telemetry telemetry, DcMotor containerMotor, Servo containerServo) {

		logger = new Logger(telemetry);

		this.containerMotor = containerMotor;
		this.containerServo = containerServo;

		containerMotor.setTargetPosition(0);
		containerMotor.setDirection(DcMotor.Direction.FORWARD);
		containerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}

	public void setContainerFlipperPower(double power){
		containerMotor.setPower(power);
	}

	public void flipContainer(double position, double power, double maxSeconds) {
		int newVerticalMotorTarget = containerMotor.getCurrentPosition() + (int) (-position * COUNTS_PER_INCH);

		containerMotor.setTargetPosition(newVerticalMotorTarget);

		containerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		ElapsedTime runtime = new ElapsedTime();

		setContainerFlipperPower(power);

		while (opModeIsActive() && (runtime.seconds() < maxSeconds) && (containerMotor.isBusy())) {
			logger.addData("Path1", String.format(Locale.US, "Running to VL:%7d", newVerticalMotorTarget));
			logger.addData("Path2", String.format(Locale.US, "Running at VL:%7d", containerMotor.getCurrentPosition()));
			logger.update();

			logger.completeLog("Is busy? FL", containerMotor.isBusy() ? "yes" : "no");
			logger.numberLog("Curr Time", runtime.seconds());
			logger.numberLog("Max Time", maxSeconds);
			logger.completeLog("Opmode is active? ", opModeIsActive() ? "yes" : "no");
		}
	}

	public void openContainer() {
		containerServo.setPosition(0.5);
	}

	public void closeContainer() {
		containerServo.setPosition(0);
	}

	@Override
	public void stopEverything() {
		containerMotor.setPower(0);
	}

	@Override
	void logTeleOpData() {
		logger.numberLog("containerMotor", containerMotor.getPower());
		logger.numberLog("containerMotor", containerMotor.getCurrentPosition());
	}
}
