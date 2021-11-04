package org.firstinspires.ftc.teamcode.Autonomous.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonoumous;

/**
 * Created by shell on 10/26/2021.
 */

@Autonomous(group = "Blue Side", name = "Blue Side: Warehouse Park")
public class BlueWarehousePark extends BaseAutonoumous {
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

		// Step 1 - move to align with the warehouse
		logger.statusLog(step++, "align with warehouse");
		moveTowardsAudience(8, 999, 0.25);

		// Step 2 - drive into the warehouse over the barrier
		logger.statusLog(step++, "driving into warehouse");
		moveTowardsRedAlliance(20,  999, 1);

		// Step 3 - move out of the way of the blocks
		logger.statusLog(step++, "align with the wall");
		moveTowardsAudience(4, 999, 0.5);

		// Step 4 - park against the wall
		logger.statusLog(step++, "Parking");
		moveTowardsRedAlliance(12,  999, 0.5);

	}

}