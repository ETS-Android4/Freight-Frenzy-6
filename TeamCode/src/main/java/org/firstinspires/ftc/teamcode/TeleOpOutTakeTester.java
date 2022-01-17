package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.robot;

import java.util.Locale;


/**
 * Created by shell bots on 1/14/2022.
 */
@Disabled
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "TeleOpOutTakeTester")
public class TeleOpOutTakeTester extends OpMode {

	private robot robot = new robot();
	private Logger logger = null;

	ElapsedTime OutTakeTimer = new ElapsedTime();

	private enum OutTakePosition {
		INTAKE,
		EXTENDINGTOP,
		EXTENDINGLOWER,
		LOWER,
		TOP,
		DROP,
		RETRACKING
	}

	OutTakePosition outTakePosition = OutTakePosition.INTAKE;

	@Override
	public void init() {
		robot.init(hardwareMap, telemetry, this, false);
		logger = new Logger(telemetry);

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
		 * 	Controller 2 - "Arm Controller" -------------------------------------------------------
		 *
		 * 		Y button - drop the freight (open the container)
		 * 		A button - lock the freight in the robot (close the container)
		 *
		 * 		X button - Spin the duck-spinners for blue alliance
		 * 		B button - Spin the duck-spinners for red alliance
		 *
		 * 		Dpad up - get ready to place a freight in the highest goal
		 * 				  (extend the crane vertically, extend the the crane horizontally, and flip
		 * 				  the container into the drop position.)
		 * 				  if pressed when already in the highest position lower the goal so more
		 * 				  freight can be grabbed
		 * 		Dpad down - get ready to place a freight in the lowest goal
		 * 					(flip he container into the upright position, collapse the crane
		 * 					horizontally, and collapse the crone vertically.)
		 * 					if pressed when already in the lowest position lower the goal so more
		 * 					freight can be grabbed
		 *
		 */

		/* Controller 1 settings --------------------------------------------------------------- */

		while (this.gamepad1.right_trigger > 0.5) {
			robot.outtake.freightcrane.setVerticalCranePower(1);
		} if (this.gamepad1.right_trigger < 0.5) {
			robot.outtake.freightcrane.setVerticalCranePower(0);
		}

		while (this.gamepad1.left_trigger > 0.5) {
			robot.outtake.freightcrane.setVerticalCranePower(-1);
		} if (this.gamepad1.left_trigger < 0.5) {
			robot.outtake.freightcrane.setVerticalCranePower(0);
		}

		if (this.gamepad1.dpad_up) {
			robot.outtake.freightcontainer.setContainerFlipperPower(.5);
		} else if (this.gamepad1.dpad_down) {
			robot.outtake.freightcontainer.setContainerFlipperPower(-.5);
		} else if (this.gamepad1.dpad_left){
			robot.outtake.freightcontainer.setContainerFlipperPower(0);
		}

		if (this.gamepad1.right_bumper) {
			robot.outtake.freightcontainer.openContainer();
		} else if (this.gamepad1.left_bumper) {
			robot.outtake.freightcontainer.closeContainer();
		}

		/* Controller 2 settings --------------------------------------------------------------- */

		switch (outTakePosition) {
			case INTAKE:
				if (this.gamepad2.dpad_up) {
					robot.outtake.setOutTakeToTopPosition();
					robot.outtake.freightcontainer.setContainerFlipperPower(-.5);
					outTakePosition = OutTakePosition.EXTENDINGTOP;
				} else if (this.gamepad2.dpad_right) {
					robot.outtake.setOutTakeToLowerPosition();
					robot.outtake.freightcontainer.setContainerFlipperPower(-.5);
					outTakePosition = OutTakePosition.EXTENDINGLOWER;
				}
				break;
			case EXTENDINGLOWER:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() > 4000) {
					robot.outtake.freightcontainer.setContainerFlipperPower(.5);
					outTakePosition = OutTakePosition.LOWER;
				}
				break;
			case EXTENDINGTOP:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() > 4500) {
					robot.outtake.freightcontainer.setContainerFlipperPower(.5);
					outTakePosition = OutTakePosition.TOP;
				}
				break;
			case LOWER:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() > 4800) {
					robot.outtake.freightcrane.setVerticalCranePower(0);
					outTakePosition = OutTakePosition.DROP;
				}
				break;
			case TOP:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() > 5750) {
					outTakePosition = OutTakePosition.DROP;
					OutTakeTimer.reset();
					OutTakeTimer.startTime();

				}
			case DROP:
				if (this.gamepad2.dpad_down) {
					if (OutTakeTimer.seconds() >= 1) {
						robot.outtake.freightcontainer.setContainerFlipperPower(-.5);
						robot.outtake.setOutTakeToIntakePosition();
						outTakePosition = OutTakePosition.RETRACKING;
					}
				}
				break;
			case RETRACKING:
				if (robot.outtake.freightcrane.verticalMotor.getCurrentPosition() < 100) {
					robot.outtake.freightcontainer.setContainerFlipperPower(0);
					robot.outtake.freightcontainer.openContainer();
					outTakePosition = OutTakePosition.INTAKE;
				}
			default:
				outTakePosition = OutTakePosition.INTAKE;
		}

		telemetry.addData("VM Curr Pos", String.format(Locale.US, "%7d", robot.outtake.freightcrane.verticalMotor.getCurrentPosition()));
		telemetry.addData("VM power", robot.outtake.freightcrane.verticalMotor.getPower());
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
}