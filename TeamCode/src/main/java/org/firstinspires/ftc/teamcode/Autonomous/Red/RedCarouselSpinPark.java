package org.firstinspires.ftc.teamcode.Autonomous.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonoumous;

/**
 * Created by shell on 10/26/2019.
 */

@Autonomous(group = "Red Side", name = "Red Side: Red Carousel Spin Park")
public class RedCarouselSpinPark extends BaseAutonoumous {
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

		// Step 1 - Making sure base plate servos are up
		logger.statusLog(step++, "");
		robot.drivetrain.runDistance(-4, -4, 999, 0.4);

		//
		logger.statusLog(step++, "");
		robot.arm.turnOffSpinner();

		//
		logger.statusLog(step++, "");
		sleep(1000);

		//
		logger.statusLog(step++, "");
		robot.arm.turnOffSpinner();
		moveTowardsRedAlliance(4, 4);
	}

}