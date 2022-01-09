package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class outtake extends robotcomponent {

	//
	//bottom is 1
	//lower is 2
	//top is 3
	private int outTakePosition = 1;

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
				this.hardwareMap.get(DcMotor.class, "verticalMotor"), //Hub: Port:
				this.hardwareMap.get(CRServo.class, "leftHorizontalServo"), //Hub: Port:
				this.hardwareMap.get(CRServo.class, "rightHorizontalServo")  //Hub: Port:
		);

		freightcontainer.init(
				telemetry,
				this.hardwareMap.get(DcMotor.class, "containerMotor"), //Hub: Port:
				this.hardwareMap.get(Servo.class, "containerServo")  //Hub: Port:
		);
	}

	public void setOutTakeToTopPosition() {
		if (outTakePosition == 1) {
			freightcrane.extendCraneVertically(1, 0.5, 10);
			if (freightcrane.verticalMotor.getCurrentPosition() > 1000) {
				freightcrane.extendCraneHorizontally(1000);
			}
			if (freightcrane.verticalMotor.getCurrentPosition() > 2000) {
				freightcontainer.flipContainer(0.5,0.5, 10);
			}
			outTakePosition = 3;
		} else if (outTakePosition == 2) {
			freightcrane.extendCraneVertically(1, 0.5, 10);
			if (freightcrane.verticalMotor.getCurrentPosition() > 1000) {
				freightcrane.extendCraneHorizontally(1000);
			}
			if (freightcrane.verticalMotor.getCurrentPosition() > 2000) {
				freightcontainer.flipContainer(0.5,0.5, 10);
			}
			outTakePosition = 3;
		} else {
			collapseOutTake();
		}
	}

	public void setOutTakeToLowerPosition() {
		if (outTakePosition == 1) {
			freightcrane.extendCraneVertically(1, 0.5, 10);
			if (freightcrane.verticalMotor.getCurrentPosition() > 1000) {
				freightcrane.extendCraneHorizontally(1000);
			}
			if (freightcrane.verticalMotor.getCurrentPosition() > 2000) {
				freightcontainer.flipContainer(0.5,0.5, 10);
			}
			outTakePosition = 2;
		} else if (outTakePosition == 3) {
			freightcrane.extendCraneVertically(1, 0.5, 10);
			if (freightcrane.verticalMotor.getCurrentPosition() > 1000) {
				freightcrane.extendCraneHorizontally(1000);
			}
			if (freightcrane.verticalMotor.getCurrentPosition() > 2000) {
				freightcontainer.flipContainer(0.5,0.5, 10);
			}
			outTakePosition = 2;
		} else {
			collapseOutTake();
		}
	}

	public void awwoooooogsetOutTakeToTopPosition() {
		if (outTakePosition != 3) {
			freightcrane.extendCraneVertically(1, 0.5, 10);
			if (freightcrane.verticalMotor.getCurrentPosition() > 1000) {
				freightcrane.extendCraneHorizontally(1000);
			}
			if (freightcrane.verticalMotor.getCurrentPosition() > 2000) {
				freightcontainer.flipContainer(0.5,0.5, 10);
			}
			outTakePosition = 3;
		} else { }
	}

	public void awwoooooogsetOutTakeToLowerPosition() {
		if (outTakePosition != 2) {
			freightcrane.extendCraneVertically(1, 0.5, 10);
			if (freightcrane.verticalMotor.getCurrentPosition() > 1000) {
				freightcrane.extendCraneHorizontally(1000);
			}
			if (freightcrane.verticalMotor.getCurrentPosition() > 2000) {
				freightcontainer.flipContainer(0.5,0.5, 10);
			}
			outTakePosition = 2;
		} else { }
	}

	public void collapseOutTake() {
		if (outTakePosition != 1) {
			freightcrane.extendCraneVertically(0.75, 0.5, 10);
			freightcrane.extendCraneHorizontally(1000);
			freightcontainer.flipContainer(0.5,0.5, 10);
			outTakePosition = 1;
		}
	}

	public int getOutTakePosition() {
		return outTakePosition;
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
