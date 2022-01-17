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

	}

	public void setContainerFlipperPower(double power)
	{
		containerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		containerMotor.setPower(power);
	}


	public void openContainer() {
		containerServo.setPosition(0.55);
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
