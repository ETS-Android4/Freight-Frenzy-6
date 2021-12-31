package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class freightcrane extends robotcomponent{

	private DcMotor verticalMotor;
	private Servo leftHorizontalServo;
	private Servo rightHorizontalServo;

	freightcrane(OpMode opmode) {
		super(opmode);
	}

	public void init(Telemetry telemetry, DcMotor verticalMotor, Servo leftHorizontalServo, Servo rightHorizontalServo) {
		this.verticalMotor = verticalMotor;
		this.leftHorizontalServo = leftHorizontalServo;
		this.rightHorizontalServo = rightHorizontalServo;

		verticalMotor.setDirection(DcMotor.Direction.FORWARD);
	}

	public void setVerticalCranePower(double power) {
		verticalMotor.setPower(power);
	}

	public void extendCraneHorizontally() {
		leftHorizontalServo.setPosition(.5);
		rightHorizontalServo.setPosition(.5);
	}

	public void collapseCraneHorizontally() {
		leftHorizontalServo.setPosition(0);
		rightHorizontalServo.setPosition(0);
	}

	@Override
	public void stopAllMotors() {

	}

	@Override
	void logTeleOpData() {

	}
}
