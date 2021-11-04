package org.firstinspires.ftc.teamcode.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonoumous;

/**
 * Created by shell on 10/26/2019.
 */

@Autonomous(group = "Blue Side", name = "Blue Side: Carousel Park")
public class BlueCarouselPark extends BaseAutonoumous {
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
		moveTowardsBlueAlliance(5.5,999,0.5);

	}

}

