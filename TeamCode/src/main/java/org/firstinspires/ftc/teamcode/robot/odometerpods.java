package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

@Config
public class odometerpods extends robotcomponent {

	private Servo rightXAxis;
	private Servo leftXAxis;
	private Servo middleYAxis;

	private Logger logger = null;

	odometerpods(OpMode opmode) {
		super(opmode);
	}

	@Override
	public void stopAllMotors() {

	}

	public void init (Telemetry telemetry, Servo rightXAxis, Servo leftXAxis, Servo middleYAxis) {
		// save all passed dead wheel servos
		this.rightXAxis = rightXAxis;
		this.leftXAxis = leftXAxis;
		this.middleYAxis = middleYAxis;
	}

	public void lowerOdometerWheels() {
		leftXAxis.setPosition(.5);
		rightXAxis.setPosition(.5);
		middleYAxis.setPosition(.5);
	}

	public void raiseOdometerWheels() {
		leftXAxis.setPosition(0);
		rightXAxis.setPosition(0);
		middleYAxis.setPosition(0);
	}

	@Override
	void logTeleOpData() {

	}
}
