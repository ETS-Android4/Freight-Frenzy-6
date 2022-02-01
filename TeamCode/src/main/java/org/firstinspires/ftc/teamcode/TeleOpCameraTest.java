package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.FreightFrenzyDeterminationPipeline;
import org.firstinspires.ftc.teamcode.robot.cameravision;
import org.firstinspires.ftc.teamcode.robot.robot;


/**
 * Created by shell bots on 9/26/2020.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(group = "Manual", name = "Camera Test Manual")
public class TeleOpCameraTest extends OpMode {

	private Logger logger = null;
	private cameravision cameraVision = null;

	@Override
	public void init() {
		cameraVision = new cameravision(hardwareMap);
		logger = new Logger(telemetry);
		this.msStuckDetectStop = 60000;

		// Step 0 - Initialized
		logger.statusLog(0, "Initialized");
		cameraVision.start();
	}

	@Override
	public void loop() {
		FreightFrenzyDeterminationPipeline.CapstonePosition rp = cameraVision.getPosition();
		int analysis1 = cameraVision.getAnalysis();
		logger.completeLog("14736: Capstone Position", rp.toString() + " " + String.valueOf(analysis1));
		logger.update();
	}

	/**
	 * Runs once after STOP is pushed
	 */
	@Override
	public void stop() {
		cameraVision.save();
		cameraVision.end();
		logger.completeLog("Status", "Stopped");
		logger.update();
	}
}