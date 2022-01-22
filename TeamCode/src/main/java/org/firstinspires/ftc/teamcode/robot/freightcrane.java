package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

public class freightcrane extends robotcomponent{

	public DcMotor verticalMotor;

	private Logger logger = null;

	freightcrane(OpMode opmode) {
		super(opmode);
	}

	void init(Telemetry telemetry, DcMotor verticalMotor) {

		logger = new Logger(telemetry);

		this.verticalMotor = verticalMotor;

		verticalMotor.setTargetPosition(0);
		verticalMotor.setDirection(DcMotor.Direction.FORWARD);
		verticalMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		verticalMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}

	public void craneVertically(int position, double power) {
		if (opModeIsActive()) {
			verticalMotor.setTargetPosition(position);
			verticalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			verticalMotor.setPower(power);
		}
	}

	@Override
	public void stopEverything() {
		verticalMotor.setPower(0);
	}

	@Override
	void logTeleOpData() {
		logger.numberLog("verticalMotor", verticalMotor.getPower());
		logger.numberLog("verticalMotor", verticalMotor.getCurrentPosition());
	}
}
