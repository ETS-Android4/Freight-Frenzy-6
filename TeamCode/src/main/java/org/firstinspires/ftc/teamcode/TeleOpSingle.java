package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.robot;


/**
 * Created by shell bots on 1/02/2022.
 */
@Disabled
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Manual Mode Single")
public class TeleOpSingle extends OpMode {

	private robot robot = new robot();
	private Logger logger = null;
	private double speed = 1.0;
	private boolean isIntakeRunning = false;
	//last_gamepad_1 left bumper
	private boolean last_g1_lb = false;
	//last_gamepad_1 right bumper
	private boolean last_g1_rb = false;


	@Override
	public void init() {
		robot.init(hardwareMap, telemetry, this, false);
		logger = new Logger(telemetry);

		// Step 0 - Initialized
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
		// Step 1 - Playing
		logger.statusLog(0, "Playing");
	}

	@Override
	public void loop() {

		/* Controller Layouts
		 *
		 *      Left Trigger - Set full speed
		 *      Right Trigger - Set half speed
		 *
		 * 		left bumper - Turn the Intake on forwards
		 * 		right bumper - Turn the Intake on backwards
		 *
		 *      Right Joystick X - Turn the robot
		 * 		Left Joystick X - Make the robot crab-walk
		 * 		Left Joystick Y - Make the robot drive forwards and backwards
		 *
		 * 		X button - Spin the duck-spinners for blue alliance
		 * 		A button - Spin the duck-spinners for red alliance
		 *
		 * 		Y button - drop the freight (open the container)
		 * 		A button - lock the freight in the robot (close the container)
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

		// Change driving speed
		if (this.gamepad1.right_trigger > 0.5) {
			speed = 1.0;
		}
		else if (this.gamepad1.left_trigger > 0.5) {
			speed = 0.5;
		}

		telemetry.addData("Chassis Speed", speed);

		// Turn the intake on and off
		if (this.gamepad1.left_bumper && !last_g1_lb) {
			if(isIntakeRunning) {
				robot.intake.setintakepower(0);
			} else {
				robot.intake.setintakepower(1);
			}
			isIntakeRunning = !isIntakeRunning;
		} else if (this.gamepad1.right_bumper && !last_g1_rb) {
			if(isIntakeRunning) {
				robot.intake.setintakepower(0);
			} else {
				robot.intake.setintakepower(-1);
			}
			isIntakeRunning = !isIntakeRunning;
		}

		last_g1_lb = this.gamepad2.left_bumper;
		last_g1_rb = this.gamepad2.right_bumper;

		// Move according to player joysticks inputs
		singleJoystickDrive();

		// Turn the duck spinners off and on
		while(this.gamepad1.x) {
			robot.caroselspinner.turnOnSpinners(1);
		}
		robot.caroselspinner.turnOnSpinners(0);
		while(this.gamepad1.b) {
			robot.caroselspinner.turnOnSpinners(-1);
		}
		robot.caroselspinner.turnOnSpinners(0);


		// Open and close the freight container
		if (this.gamepad1.b) {
			robot.outtake.freightcontainer.openContainer();
		} else if (this.gamepad1.x) {
			robot.outtake.freightcontainer.closeContainer();
		}

		// set the outtake so the robot can pickup new freight, and place it
		if (this.gamepad1.dpad_up) {
			robot.outtake.setOutTakeToTopPosition();
		}  else if (this.gamepad1.dpad_down) {
			robot.outtake.setOutTakeToLowerPosition();
		}

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