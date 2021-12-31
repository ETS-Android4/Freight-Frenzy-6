package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class freightcontainer extends robotcomponent{

	freightcontainer(OpMode opmode) {
		super(opmode);
	}

	private DcMotor containerMotor;
	private Servo containerServo;

	public void init (Telemetry telemetry, DcMotor containerMotor, Servo containerServo) {
		this.containerMotor = containerMotor;
		this.containerServo = containerServo;

		containerMotor.setDirection(DcMotor.Direction.FORWARD);
	}

	public void setContainerFlipperPower(double power){
		containerMotor.setPower(power);
	}

	public void openContainer(){
		containerServo.setPosition(0.5);
	}

	public void closeContainer(){
		containerServo.setPosition(0);
	}

	@Override
	public void stopAllMotors() {

	}

	@Override
	void logTeleOpData() {

	}
}
