package org.firstinspires.ftc.teamcode.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonomous;

/**
 * Created by shell on 10/26/2021.
 */

@Autonomous(group = "Blue Side", name = "Blue Side: Carousel Spin Park")
public class BlueCarouselSpinPark extends BaseAutonomous {
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

		// Step 1 - drive towards the carousel
		logger.statusLog(step++, "drive towards the carousel");
		moveBackwards(6.5,999,1);

		// Step 2 - drive towards the parking zone
		logger.statusLog(step++, "drive towards the parking zone");
		moveRight(7,999,1);

		// Step 3 - align with the carousel
		logger.statusLog(step++, "align with the carousel");
		moveBackwards(7,999,1);

		// Step 4 - drive against the carousel
		logger.statusLog(step++, "driving into the carousel");
		moveLeft(3.5,999,1);

		// Step 5 - spin the duck off
		logger.statusLog(step++, "spinning the servo");
		turnOnDuckSpinner(7507);

		// Step 6 - drive off of the carousel
		logger.statusLog(step++, "Drive away from carousel");
		moveRight(2,999,1);

		// Step 7 - drive into the parking zone
		logger.statusLog(step++, "drive into the parking zone");
		moveBackwards(3,999,1);

		// Step 8 - drive into the parking zone
		logger.statusLog(step++, "Park");
		moveRight(8.5,999,1);
	}
}