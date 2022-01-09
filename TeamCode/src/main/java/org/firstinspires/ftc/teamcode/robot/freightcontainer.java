package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Logger;

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
		containerMotor.setDirection(DcMotor.Direction.FORWARD);
	}

	public void setContainerFlipperPower(double power)
	{
		containerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		containerMotor.setPower(power);
		logger.numberLog("containerMotor Power", containerMotor.getPower());
	}

	public void flipContainer(double position, double power, double maxSeconds) {
		int newContainerMotorTarget = containerMotor.getCurrentPosition() + (int) (-position * COUNTS_PER_INCH);

		containerMotor.setTargetPosition(newContainerMotorTarget);

		containerMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		ElapsedTime runtime = new ElapsedTime();

		setContainerFlipperPower(power);

		while (opModeIsActive() && (runtime.seconds() < maxSeconds) && (containerMotor.isBusy())) {
		//	logger.addData("CM Target", String.format(Locale.US, "Running to VL:%7d", newContainerMotorTarget));
		//	logger.addData("CM Curr Pos", String.format(Locale.US, "Running at VL:%7d", containerMotor.getCurrentPosition()));

			logger.completeLog("Is busy? CM", containerMotor.isBusy() ? "yes" : "no");
		//	logger.numberLog("Curr Time", runtime.seconds());
		//	logger.numberLog("Max Time", maxSeconds);
		//	logger.completeLog("OpMode is active? ", opModeIsActive() ? "yes" : "no");
			logger.update();
		}
	}

	public void openContainer() {
		containerServo.setPosition(0.5);
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
