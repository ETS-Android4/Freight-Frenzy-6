package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class odometerpods extends robotcomponent {

	public Servo rightXAxis;
	public Servo leftXAxis;
	public Servo middleYAxis;

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
		leftXAxis.setPosition(.5);
		rightXAxis.setPosition(.4);
		middleYAxis.setPosition(.5);
	}

	public void raiseOdometerWheels() {
		leftXAxis.setPosition(0);
		rightXAxis.setPosition(1);
		middleYAxis.setPosition(0);
	}

	@Override
	public void stopEverything() {

	}

	@Override
	void logTeleOpData() {

	}
}
