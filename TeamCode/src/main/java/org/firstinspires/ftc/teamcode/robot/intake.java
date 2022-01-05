package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
public class intake extends robotcomponent {

	private DcMotor intakeMotor;

	intake(OpMode opmode) {
		super(opmode);
	}

	public void init(Telemetry telemetry, DcMotor intakeMotor) {
		this.intakeMotor = intakeMotor;

		intakeMotor.setDirection(DcMotor.Direction.FORWARD);
		intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}

	public void setintakepower(double power) {
		intakeMotor.setPower(-power);
	}

	@Override
	public void stopEverything() {
		intakeMotor.setPower(0);
	}

	@Override
	void logTeleOpData() {

	}
}
