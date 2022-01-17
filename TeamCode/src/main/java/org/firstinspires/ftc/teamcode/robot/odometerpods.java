package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class odometerpods extends robotcomponent {

	private Servo rightXAxis;
	private Servo leftXAxis;
	private Servo middleYAxis;

	odometerpods(OpMode opmode) {
		super(opmode);
	}

	public void init (Telemetry telemetry, Servo rightXAxis, Servo leftXAxis, Servo middleYAxis) {
		// save all passed dead wheel servos
		this.rightXAxis = rightXAxis;
		rightXAxis.setDirection(Servo.Direction.REVERSE);
		this.leftXAxis = leftXAxis;
		this.middleYAxis = middleYAxis;
	}

	public void lowerOdometerWheels() {
		leftXAxis.setPosition(.4);
		rightXAxis.setPosition(.35);
		middleYAxis.setPosition(.4);
	}

	public void raiseOdometerWheels() {
		leftXAxis.setPosition(0);
		rightXAxis.setPosition(.9);
		middleYAxis.setPosition(0);
	}

	@Override
	public void stopEverything() {

	}

	@Override
	void logTeleOpData() {

	}
}
