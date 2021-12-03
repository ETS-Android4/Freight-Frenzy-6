package org.firstinspires.ftc.teamcode.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonoumous;

/**
 * Created by shell on 10/26/2021.
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

		// Step 1 - Drive towards the carousel
		logger.statusLog(step++, "Avoid Randomization");
		moveRight(5.5,999,1);

		// Step 2 - Drive towards the other team
		logger.statusLog(step++, "move out of the way of the carousel");
		moveBackwards(10,999,1);

		// Step 3 - Drive up against the wall
		logger.statusLog(step++, "Aligning with wall");
		moveRight(6.5,999,1);

		// Step 4 - drive into the parking zone
		logger.statusLog(step++, "Parking");
		moveBackwards(5.5,999,1);

	}

}

