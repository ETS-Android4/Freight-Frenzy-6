package org.firstinspires.ftc.teamcode.Autonomous.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.BaseAutonoumous;

/**
 * Created by shell on 10/26/2021.
 */

@Autonomous(group = "Red Side", name = "Red Side: Warehouse Park")
public class RedWarehousePark extends BaseAutonoumous {
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

		// Step 1 - move to align with the warehouse
		logger.statusLog(step++, "align with warehouse");
		moveBackwards(25, 999, 1);
	}
}