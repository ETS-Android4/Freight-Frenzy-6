package org.firstinspires.ftc.teamcode.Autonomous.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonoumous;

/**
 * Created by shell on 10/26/2021.
 */

@Autonomous(group = "Red Side", name = "Red Side: Carousel Park")
public class RedCarouselPark extends BaseAutonoumous {
	@Override
	protected Color getColor() {
		return Color.RED	;
	}

	@Override
	public void runOpMode() {

		super.runOpMode();

		robot.drivetrain.defaultSpeed = 0.4;

		// Step 0 - Ready to run
		logger.statusLog(step++, "Ready To Run");
		// Waiting until user presses start
		waitForStart();

		// Step 1 - Drive towards the carousel
		logger.statusLog(step++, "Avoid Randomization");
		moveTowardsBlueAlliance(5.5,999,0.5);

		// Step 2 - Drive towards the other team
		logger.statusLog(step++, "move out of the way of the carousel");
		moveTowardsWareHouse(5.5,999,0.5);

		// Step 3 - Drive up against the wall
		logger.statusLog(step++, "Aligning with wall");
		moveTowardsBlueAlliance(5,999,0.5);

		// Step 4 - drive into the parking zone
		logger.statusLog(step++, "Parking");
		moveTowardsWareHouse(8,999,0.5);

	}

}

