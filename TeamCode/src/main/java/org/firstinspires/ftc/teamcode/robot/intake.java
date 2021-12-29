package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
public class intake extends robotcomponent {

	private DcMotor intakeMotor = null;

	intake(OpMode opmode) {
		super(opmode);
	}

	public void init(Telemetry telemetry, DcMotor intakeMotor) {
		this.intakeMotor = intakeMotor;
	}

	public void setintakepower(double power) {
		intakeMotor.setPower(-power);
	}

	@Override
	public void stopAllMotors() {

	}

	@Override
	void logTeleOpData() {

	}
}
