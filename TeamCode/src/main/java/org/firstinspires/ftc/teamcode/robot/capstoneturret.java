package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class capstoneturret extends robotcomponent{

	private CRServo extensionServo;
	private CRServo rotationServo;
	private DcMotor tiltMotor;

	private Logger logger = null;

	capstoneturret(OpMode opmode) {
		super(opmode);
	}

	public void init (Telemetry telemetry, CRServo extensionServo, CRServo rotationServo, DcMotor tiltMotor) {

		logger = new Logger(telemetry);

		this.extensionServo = extensionServo;
		this.rotationServo = rotationServo;
		this.tiltMotor = tiltMotor;

		tiltMotor.setDirection(DcMotor.Direction.FORWARD);
		tiltMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}

	public void setTurretPowers(float extensionServoPower, float rotationServoPower, float tiltMotorPower) {
		extensionServo.setPower(extensionServoPower);
		rotationServo.setPower(rotationServoPower/10);
		tiltMotor.setPower(tiltMotorPower/5);
	}

	public void setTurretToOptimalDriverPosition() {
		tiltMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		tiltMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		setCRServoPosition(rotationServo, .5);
		tiltMotor.setTargetPosition(100);
		tiltMotor.setPower(.2);
	}

	public void setRotationServoPower(float rotationServoPower) {
		rotationServo.setPower(rotationServoPower);
	}

	public static void setCRServoPosition(CRServo crservo, double position) {
		crservo.getController().setServoPosition(crservo.getPortNumber(), position);
	}


	@Override
	public void stopEverything() {
		extensionServo.setPower(0);
		rotationServo.setPower(0);
		tiltMotor.setPower(0);
	}

	@Override
	void logTeleOpData() {

	}
}
