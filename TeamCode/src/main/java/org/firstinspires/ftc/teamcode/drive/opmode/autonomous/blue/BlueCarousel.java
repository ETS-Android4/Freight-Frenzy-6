package org.firstinspires.ftc.teamcode.drive.opmode.autonomous.blue;

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
public class BlueCarousel extends LinearOpMode {

	public static double aFirstPositionX = -40;
	public static double aFirstPositionY = 30;

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		robot robot = new robot();
		robot.init(hardwareMap, telemetry, this, true);
		robot.odometerpods.lowerOdometerWheels();
		robot.cameravision.start();

		Pose2d startPose = new Pose2d(-40, 64.25, Math.toRadians(270));

		drive.setPoseEstimate(startPose);

		TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
			//drive infront of the capstone
				.lineToLinearHeading(new Pose2d(aFirstPositionX, aFirstPositionY, Math.toRadians(0)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
				})
				/*
			//drive to alliance shipping hub
				.setReversed(true)
				.splineToConstantHeading(new Vector2d(-30, 24), Math.toRadians(0))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//PLACE FREIGHT
					robot.outtake.freightcontainer.openContainerCompletly();

				})
				.setReversed(false)
				.waitSeconds(2)
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//LOWER ARM
					robot.outtake.lowerBackToIntakePosition();
				})
			//drive to the carousel
				.lineToConstantHeading(new Vector2d(-60, 64.25))

			//spin a duck
				.addTemporalMarker(0, () -> {
					robot.caroselspinner.supplySpinnersPower(1);
				})
				.addTemporalMarker(5, () -> {
					robot.caroselspinner.supplySpinnersPower(0);
				})

			//drive infront of the capstone
				.lineToSplineHeading(new Pose2d(-40,47.5, Math.toRadians(270)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN ON INTAKE
					robot.intake.setIntakePower(1);
				})
			//pick up duck
				.lineToConstantHeading(new Vector2d(-40, 64.25))
			//drive infront of the capstone
				.lineToLinearHeading(new Pose2d(-40, 45, Math.toRadians(0)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN OFF INTAKE
					robot.intake.setIntakePower(0);
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
				})
			//drive to alliance shipping hub
				.setReversed(true)
				.splineToConstantHeading(new Vector2d(-30, 24), Math.toRadians(0))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
				//PLACE FREIGHT
					robot.outtake.freightcontainer.openContainerCompletly();
				})
				.setReversed(false)
				.waitSeconds(2)
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
				})
			//drive to the depot
				.lineToSplineHeading(new Pose2d(-64,38, Math.toRadians(180)))
				 */
				.build();

		telemetry.addData("Status", "Ready to run");
		telemetry.update();

		waitForStart();

		if (isStopRequested()) return;

		FreightFrenzyDeterminationPipeline.CapstonePosition capstonePosition = robot.cameravision.getPosition();
		telemetry.addData("Capstone Pos", capstonePosition.toString());
		telemetry.update();

		drive.followTrajectorySequence(trajSeq);

		robot.odometerpods.raiseOdometerWheels();

/*
		if (capstonePosition == FreightFrenzyDeterminationPipeline.CapstonePosition.LEFT) {
		} else if (capstonePosition == FreightFrenzyDeterminationPipeline.CapstonePosition.MIDDLE) {
		} else {
 */
		}
	}