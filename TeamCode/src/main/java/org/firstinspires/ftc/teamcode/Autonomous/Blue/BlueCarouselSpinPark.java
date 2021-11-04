package org.firstinspires.ftc.teamcode.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonoumous;

/**
 * Created by shell on 10/26/2019.
 */

@Autonomous(group = "Blue Side", name = "Blue Side: Carousel Spin Park")
public class BlueCarouselSpinPark extends BaseAutonoumous {
	@Override
	protected Color getColor() {
		return Color.BLUE;
	}

	@Override
	public void runOpMode() {

		super.runOpMode();

		robot.drivetrain.defaultSpeed = 0.4;

		// Step 0 - Ready to run
		logger.statusLog(step++, "Ready To Run");
		// Waiting until user presses start
		waitForStart();

		// Step 1 - Making sure base plate servos are up
		logger.statusLog(step++, "");
		moveTowardsWareHouse(5.5,999,0.5);

		// Step 1 - Making sure base plate servos are up
		logger.statusLog(step++, "");
		moveTowardsBlueAlliance(5.5,999,0.5);

		// Step 1 - Making sure base plate servos are up
		logger.statusLog(step++, "");
		moveTowardsWareHouse(5.5,999,0.5);

		// Step 1 - Making sure base plate servos are up
		logger.statusLog(step++, "");
		moveTowardsRedAlliance(3.5,999,0.5);

		//
		logger.statusLog(step++, "");
		robot.arm.turnOnSpinner(1);

		//
		logger.statusLog(step++, "");
		sleep(7507);

		//
		logger.statusLog(step++, "");
		robot.arm.turnOffSpinner();

		// Step 1 - Making sure base plate servos are up
		logger.statusLog(step++, "");
		moveTowardsBlueAlliance(2,999,0.5);

		// Step 1 - Making sure base plate servos are up
		logger.statusLog(step++, "");
		moveTowardsWareHouse(3,999,0.25);

		// Step 1 - Making sure base plate servos are up
		logger.statusLog(step++, "");
		moveTowardsBlueAlliance(6,999,0.5);
	}

}