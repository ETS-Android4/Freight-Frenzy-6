package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.robot;

import java.util.Locale;


/**
 * Created by shell bots on 1/02/2022.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Manual Mode Double")
public class TeleOpDouble extends OpMode {

	private robot robot = new robot();
	private Logger logger = null;

	private double speed = .5;
	private boolean firstTime = true;
	private float extensionPower;
	private float rotationPower;
	private float tiltPower;
	private boolean isTurretControlEnabled = false;
	private boolean last_g2_x = false;
	private boolean last_g2_b = false;
	private boolean isIntakeRunning = false;
	private boolean last_g2_lb = false;
	private boolean last_g2_rb = false;
	private boolean containerIsLiftable = true;
	private boolean outtakeIsUseAble = true;


	ElapsedTime liftTimer = new ElapsedTime();

	private enum OutTakePosition {
		INTAKE,
		EXTENDINGLOWER,
		EXTENDINGTOP,
		LOWER,
		TOP,
		DROPLOWER,
		DROPTOP,
		RETRACKINGLOWER,
		RETRACKINGTOP
	}

	//TODO: make it automatic, and make different states?
	private enum DuckSpinnerState {
		RESTING
	}

	OutTakePosition outTakePosition = OutTakePosition.INTAKE;
	DuckSpinnerState duckSpinnerState = DuckSpinnerState.RESTING;

	@Override
	public void init() {
		robot.init(hardwareMap, telemetry, this, false);
		logger = new Logger(telemetry);
		outTakePosition = OutTakePosition.INTAKE;

		logger.statusLog(0, "Initialized");
	}

	/**
	 * Runs constantly after INIT is pushed but before PLAY is pushed
	 */

	@Override
	public void init_loop() {

	}

	/**
	 * Runs once after PLAY is pushed
	 */

	@Override
	public void start() {
		logger.statusLog(0, "Playing");
	}

	@Override
	public void loop() {

		/* Controller Layouts
		 *
		 * 	Controller 1 - "Body Controller" ------------------------------------------------------
		 *
		 *      Left Trigger - Set full speed
		 *      Right Trigger - Set half speed
		 *
		 *      Right Joystick X - Turn the robot
		 * 		Left Joystick X - Make the robot crab-walk
		 * 		Left Joystick Y - Make the robot drive forwards and backwards
		 *
		 * 		Y button - intake is no longer possible
		 * 		A button - intake is possible
		 *
		 * 		X button - Spin the duck-spinners for blue alliance
		 * 		B button - Spin the duck-spinners for red alliance
		 *
		 * 	Controller 2 - "Arm Controller" -------------------------------------------------------
		 *
		 *		Right Joystick X - turn the tape measure
		 * 		Right Joystick Y - tilt the tape measure
		 * 		Left Joystick Y - Extends the tape measure
		 *
		 * 		Right Bumper - toggle switch to spin the intake forwards
		 * 		Left Bumper - toggle switch to spin the intake backwards
		 *
		 * 		Y button - drop the freight (open the container)
		 * 		A button - lock the freight in the robot (close the container)
		 *
		 * 		Dpad up - get ready to drop a freight in the alliance shipping hub
		 * 		Dpad right - get ready to drop a freight in the shared shipping hub
		 * 		Dpad down - get ready to intake freight
		 * 		Dpad left - drop a freight
		 *
		 */

		/* Controller 1 settings --------------------------------------------------------------- */

		// Change driving speed
		if (this.gamepad1.right_trigger > 0.5) {
			speed = 1.0;
		}
		else if (this.gamepad1.left_trigger > 0.5) {
			speed = 0.5;
		}

		telemetry.addData("Chassis Speed", speed);

		// Move according to player 1 joysticks inputs
		singleJoystickDrive();

		if (this.gamepad1.y && containerIsLiftable) {
			robot.outtake.freightcontainer.containerMotor.setPower(.6);
		} else if (this.gamepad1.a && containerIsLiftable) {
			robot.outtake.freightcontainer.containerMotor.setPower(0);
		}

		//Turn the duck spinners off and on
		switch (duckSpinnerState) {
			case RESTING:
				if (gamepad1.b) {
					//rotate duck spinner for red alliance
					robot.caroselspinner.supplySpinnersPower(-1);
				} else if (gamepad1.x) {
					//rotate duck spinner for blue alliance
					robot.caroselspinner.supplySpinnersPower(1);
				} else {
					robot.caroselspinner.supplySpinnersPower(0);
				}
				break;
		}

		/* Controller 2 settings --------------------------------------------------------------- */

		if (this.gamepad2.b) {
			last_g2_b = !last_g2_b;
		}

		if (this.gamepad2.x) {
			last_g2_x = !last_g2_x;
			/*
			if (firstTime) {
				//sets the turret to a controller position
				robot.capstoneturret.setTurretToOptimalDriverPosition();
				firstTime = false;
			}
			 */
		}

		if (last_g2_x) {
			outtakeIsUseAble = false;
			telemetry.addLine ("Capping Turret Mode");
			extensionPower = gamepad2.left_stick_y;
			rotationPower = gamepad2.right_stick_x;
			tiltPower = gamepad2.right_stick_y;
			robot.capstoneturret.setTurretPowers(extensionPower, rotationPower, tiltPower);
		} else if (last_g2_b) {
			outtakeIsUseAble = false;
			telemetry.addLine("Setting Up Turret Mode");
			rotationPower = gamepad2.right_stick_x;
			robot.capstoneturret.setRotationServoPower(rotationPower);
		} else {
			outtakeIsUseAble = true;
			robot.capstoneturret.setTurretPowers(0, 0, 0);
		}

		// Turn the intake on and off
		if (this.gamepad2.right_bumper && !last_g2_rb) {
			if(isIntakeRunning) {
				robot.intake.setIntakePower(0);
			} else {
				robot.intake.setIntakePower(1);
			}
			isIntakeRunning = !isIntakeRunning;
		} else if (this.gamepad2.left_bumper && !last_g2_lb) {
			if(isIntakeRunning) {
				robot.intake.setIntakePower(0);
			} else {
				robot.intake.setIntakePower(-1);
			}
			isIntakeRunning = !isIntakeRunning;
		}

		last_g2_lb = this.gamepad2.left_bumper;
		last_g2_rb = this.gamepad2.right_bumper;

		//Open and close the freight container
		if (this.gamepad2.y) {
			robot.outtake.freightcontainer.openContainer();
		} else if (this.gamepad2.a) {
			robot.outtake.freightcontainer.closeContainer();
		}

		switch (outTakePosition) {
			case INTAKE:
				if (this.gamepad2.dpad_right && containerIsLiftable) {
					robot.outtake.freightcrane.craneVertically(1750, 1);
					containerIsLiftable = false;
					robot.outtake.freightcontainer.containerMotor.setPower(0);
					outTakePosition = OutTakePosition.EXTENDINGLOWER;
				} else if (this.gamepad2.dpad_up && containerIsLiftable) {
					robot.outtake.freightcrane.craneVertically(2850, 1);
					containerIsLiftable = false;
					robot.outtake.freightcontainer.containerMotor.setPower(0);;
					outTakePosition = OutTakePosition.EXTENDINGTOP;
				}
				break;
			case EXTENDINGLOWER:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() > 1700) {
					robot.outtake.freightcontainer.flipContainerForDrop();
					if (robot.outtake.freightcontainer.containerMotor.getCurrentPosition() >= 80) {
						robot.outtake.freightcontainer.containerMotor.setPower(0);
						robot.outtake.freightcrane.craneVertically(800, 1);
						outTakePosition = OutTakePosition.LOWER;
					}
				}
				break;
			case EXTENDINGTOP:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() > 2750) {
					robot.outtake.freightcontainer.flipContainerForDrop();
					if (robot.outtake.freightcontainer.containerMotor.getCurrentPosition() >= 80) {
						robot.outtake.freightcontainer.containerMotor.setPower(0);
						outTakePosition = OutTakePosition.TOP;
					}
				}
				break;
			case LOWER:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() < 850) {
					outTakePosition = OutTakePosition.DROPLOWER;
				}
				break;
			case TOP:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() > 2750) {
					outTakePosition = OutTakePosition.DROPTOP;
				}
			case DROPLOWER:
				if (this.gamepad2.dpad_left) {
					robot.outtake.freightcontainer.openContainerCompletely();
				}
				if (this.gamepad2.dpad_down) {
					robot.outtake.freightcrane.craneVertically(1750,1);
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
					liftTimer.reset();
					liftTimer.startTime();
					outTakePosition = OutTakePosition.RETRACKINGLOWER;
				}
				break;
			case DROPTOP:
				if (this.gamepad2.dpad_left) {
					robot.outtake.freightcontainer.openContainerCompletely();
				}
				if (this.gamepad2.dpad_down) {
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
					liftTimer.reset();
					liftTimer.startTime();
					outTakePosition = OutTakePosition.RETRACKINGTOP;
				}
				break;
			case RETRACKINGLOWER:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() > 1700) {
					if (robot.outtake.freightcontainer.containerMotor.getCurrentPosition() <= 80) {
						robot.outtake.freightcontainer.containerMotor.setPower(0);
						if (liftTimer.seconds() >= 3) {
							robot.outtake.freightcrane.craneVertically(0,1);
							robot.outtake.freightcontainer.openContainer();
							containerIsLiftable = true;
							outTakePosition = OutTakePosition.INTAKE;
						}
					}
				}
				break;
			case RETRACKINGTOP:
				if (robot.outtake.freightcontainer.containerMotor.getCurrentPosition() <= 80) {
					robot.outtake.freightcontainer.containerMotor.setPower(0);
					robot.outtake.freightcrane.craneVertically(0, 1);
					if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() < 50) {
						if (liftTimer.seconds() >= 2) {
							robot.outtake.freightcontainer.openContainer();
							containerIsLiftable = true;
							outTakePosition = OutTakePosition.INTAKE;
						}
					}
				}
				break;
			default:
				outTakePosition = OutTakePosition.INTAKE;
		}

//		telemetry.addData("containerMotor Curr Pos",String.format(Locale.US, "%7d", robot.outtake.freightcontainer.containerMotor.getCurrentPosition()));
//		telemetry.addData("containerMotor Power", robot.outtake.freightcontainer.containerMotor.getPower());
//		telemetry.addData("verticalMotor Curr Pos", String.format(Locale.US, "%7d", robot.outtake.freightcrane.verticalMotor.getCurrentPosition()));
//		telemetry.addData("verticalMotor Power", robot.outtake.freightcrane.verticalMotor.getPower());
		telemetry.addData("Outtake Curr Usability", outtakeIsUseAble);
		telemetry.addData("Curr OutTakePos", outTakePosition);
		telemetry.update();
	}

	/**
	 * Runs once after STOP is pushed
	 */

	@Override
	public void stop() {
		robot.stopEverything();
		logger.completeLog("Status", "Stopped");
		logger.update();
	}

	private void singleJoystickDrive() {
		double leftX = this.gamepad1.left_stick_x;
		double leftY = this.gamepad1.left_stick_y;
		double rightX = this.gamepad1.right_stick_x;

		double[] motorPowers = new double[4];
		motorPowers[0] = (leftY-leftX-rightX);// -+
		motorPowers[1] = (leftY+leftX+rightX);// +-
		motorPowers[2] = (leftY+leftX-rightX);// ++
		motorPowers[3] = (leftY-leftX+rightX);// --

		double max = Math.abs(getLargestAbsVal(motorPowers));
		if(max < 1) { max = 1; }

		for(int i = 0; i < motorPowers.length; i++) {
			motorPowers[i] /= max;
		}

		for(int i = 0; i < motorPowers.length; i++) {
			if(motorPowers[i] < 0.05 && motorPowers[i] > -0.05) { motorPowers[i] = 0.0; }
			if(motorPowers[i] > 1.0) { motorPowers[i] = 1.0; }
			if(motorPowers[i] < -1.0) { motorPowers[i] = -1.0; }
			motorPowers[i] *= speed;
			//logger.numberLog("Motor" + i, motorPowers[i]);
		}

		robot.drivetrain.setIndividualPowers(motorPowers);
	}

	private double getLargestAbsVal(double[] values) {
		double max = 0;
		for (double val : values) {
			if (Math.abs(val) > max) {
				max = Math.abs(val);
			}
		}
		return max;
	}
}