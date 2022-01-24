package org.firstinspires.ftc.teamcode.drive.opmode.autonomous.red;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.FreightFrenzyDeterminationPipeline;
import org.firstinspires.ftc.teamcode.robot.robot;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


/*
 * This is an example of a more complex path to really test the tuning.
 */
/*
 * This is an example of a more complex path to really test the tuning.
 */
@Config
@Autonomous(group = "drive")
public class RedWarehouse extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		robot robot = new robot();
		robot.init(hardwareMap, telemetry, this, true);

		robot.cameravision.start();

		Pose2d startPose = new Pose2d(6.25, -64.25, Math.toRadians(-270));

		drive.setPoseEstimate(startPose);

		TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
				.lineToLinearHeading(new Pose2d(0, -40, Math.toRadians(-225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					//DROP FREIGHT
				})
				// drop 1st freight

				.lineToLinearHeading(new Pose2d(14, -64.25, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,-64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN ON INTAKE
				})
				.lineToConstantHeading(new Vector2d(14,-64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(0, -40, Math.toRadians(-225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					//DROP FREIGHT
				})
				// drop 2nd freight

				.lineToLinearHeading(new Pose2d(14, -64.25, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,-64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN ON INTAKE
				})
				.lineToConstantHeading(new Vector2d(14,-64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(0, -40, Math.toRadians(-225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					//DROP FREIGHT
				})
				// drop 3rd freight

				.lineToLinearHeading(new Pose2d(14, -64.25, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,-64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN ON INTAKE
				})
				.lineToConstantHeading(new Vector2d(14,-64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(0, -40, Math.toRadians(-225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					//DROP FREIGHT
				})
				// drop 4th freight

				.lineToLinearHeading(new Pose2d(14, -64.25, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,-64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN ON INTAKE
				})
				.lineToConstantHeading(new Vector2d(14,-64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(0, -40, Math.toRadians(-225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					//DROP FREIGHT
				})
				// drop 5th freight

				.lineToLinearHeading(new Pose2d(14, -64.25, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,-64.25))
				.build();

		telemetry.addData("Status", "Ready to run");
		telemetry.update();

		waitForStart();

		if (isStopRequested()) return;

		FreightFrenzyDeterminationPipeline.CapstonePosition capstonePosition = robot.cameravision.getPosition();
		telemetry.addData("Capstone Pos", capstonePosition.toString());
		telemetry.update();

		drive.followTrajectorySequence(trajSeq);

/*
		if (capstonePosition == FreightFrenzyDeterminationPipeline.CapstonePosition.LEFT) {
		} else if (capstonePosition == FreightFrenzyDeterminationPipeline.CapstonePosition.MIDDLE) {
		} else {
 */
	}
}