package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class outtake extends robotcomponent {

	private HardwareMap hardwareMap = null;
	private Telemetry telemetry = null;
	private OpMode opmode = null;

	public freightcrane freightcrane = null;
	public freightcontainer freightcontainer = null;

	outtake(OpMode opmode) {
		super(opmode);
	}

	void init (HardwareMap hardwareMap, Telemetry telemetry, OpMode opmode) {

		this.hardwareMap = hardwareMap;
		this.telemetry = telemetry;
		this.opmode = opmode;

		freightcrane = new freightcrane(opmode);
		freightcontainer = new freightcontainer(opmode);

		freightcrane.init(
				telemetry,
				this.hardwareMap.get(DcMotor.class, "verticalMotor") //Hub: Port:
		);

		freightcontainer.init(
				telemetry,
				this.hardwareMap.get(DcMotor.class, "containerMotor"), //Hub: Port:
				this.hardwareMap.get(Servo.class, "containerServo")  //Hub: Port:
		);
	}

	@Override
	public void stopEverything() {
		freightcrane.stopEverything();
		freightcontainer.stopEverything();
	}

	@Override
	void logTeleOpData() {
		freightcrane.logTeleOpData();
		freightcontainer.logTeleOpData();
	}
}
