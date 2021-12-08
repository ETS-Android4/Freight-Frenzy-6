package org.firstinspires.ftc.teamcode.Autonomous.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonomous;

/**
 * Created by shell on 10/26/2021.
 */

@Autonomous(group = "Red Side", name = "Red Side: Carousel Spin Park")
public class RedCarouselSpinPark extends BaseAutonomous {
	@Override
	protected Color getColor() {
		return Color.RED;
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
		moveLeft(6.5,999,1);

		// Step 2 - drive towards the parking zone
		logger.statusLog(step++, "drive towards the parking zone");
		moveForwards(6.5,999,1);

		// Step 3 - align with the carousel
		logger.statusLog(step++, "align with the carousel");
		moveLeft(5.5,999,1);

		// Step 4 - drive against the carousel
		logger.statusLog(step++, "driving into the carousel");
		moveBackwards(3.5,999,1);

		// Step 5 - spin the duck off
		logger.statusLog(step++, "spinning the servo");
		turnOnDuckSpinner(7507);

		// Step 6 - drive off of the carousel
		logger.statusLog(step++, "Drive away from carousel");
		moveForwards(2,999,1);

		// Step 7 - drive into the parking zone
		logger.statusLog(step++, "drive into the parking zone");
		moveLeft(3,999,1);

		// Step 8 - drive into the parking zone
		logger.statusLog(step++, "Park");
		moveForwards(9,999,1);
	}

}