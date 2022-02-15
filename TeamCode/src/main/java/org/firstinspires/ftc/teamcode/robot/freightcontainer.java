package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

//date and time
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
		containerMotor.setDirection(DcMotor.Direction.REVERSE);
		containerMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		containerMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}

	public void flipContainerForDrop() {
		if (opModeIsActive()) {
			containerMotor.setTargetPosition(150);
			containerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			containerMotor.setPower(.75);
		}
	}

	public void flipContainerForIntake() {
		if (opModeIsActive()) {
			containerMotor.setTargetPosition(0);
			containerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			containerMotor.setPower(.75);
		}
	}
	public void openContainerCompletely() {
		containerServo.setPosition(.3);
	}

	public void openContainer() {
		containerServo.setPosition(.25);
	}

	public void closeContainer() {
		containerServo.setPosition(0.125);
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
