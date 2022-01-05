package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class caroselspinner extends robotcomponent {

	private CRServo leftSpinner;
	private CRServo rightSpinner;

	caroselspinner(OpMode opmode) {
		super(opmode);
	}

	void init(Telemetry telemetry, CRServo leftSpinner, CRServo rightSpinner) {

		this.leftSpinner = leftSpinner;
		this.rightSpinner = rightSpinner;
	}

	public void turnOnSpinners(double power) {
		leftSpinner.setPower(power);
		rightSpinner.setPower(power);
	}

	@Override
	public void stopEverything() {
		leftSpinner.setPower(0);
		rightSpinner.setPower(0);
	}

	@Override
	void logTeleOpData() {

	}
}
