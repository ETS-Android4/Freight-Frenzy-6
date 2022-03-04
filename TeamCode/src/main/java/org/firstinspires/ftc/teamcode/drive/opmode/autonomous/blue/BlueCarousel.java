package org.firstinspires.ftc.teamcode.drive.opmode.autonomous.blue;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.TRACK_WIDTH;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.FreightFrenzyDeterminationPipeline;
import org.firstinspires.ftc.teamcode.robot.robot;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

/*
 * This is an example of a more complex path to really test the tuning.
 */

@Config
@Autonomous(group = "drive")
public class BlueCarousel extends LinearOpMode {

	TrajectoryVelocityConstraint number = SampleMecanumDrive.getVelocityConstraint(7.5, MAX_ANG_VEL, TRACK_WIDTH);

	TrajectoryAccelerationConstraint number2 = SampleMecanumDrive.getAccelerationConstraint(MAX_ACCEL);

	TrajectoryVelocityConstraint defaults = SampleMecanumDrive.getVelocityConstraint(MAX_VEL, MAX_ANG_VEL, TRACK_WIDTH);

	TrajectoryVelocityConstraint number4 = SampleMecanumDrive.getVelocityConstraint(7, MAX_ANG_VEL, TRACK_WIDTH);

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		robot robot = new robot();
		robot.init(hardwareMap, telemetry, this, true);
		robot.odometerpods.lowerOdometerWheels();
		robot.outtake.freightcontainer.closeContainer();
		robot.cameravision.start();

		FreightFrenzyDeterminationPipeline.CapstonePosition capstonePosition = robot.cameravision.getPosition();
		telemetry.addData("Capstone Pos", capstonePosition.toString());
		telemetry.update();

		Pose2d startPose = new Pose2d(-40, 64.25, Math.toRadians(270));

		drive.setPoseEstimate(startPose);

		TrajectorySequence trajSeqMiddleGoal = drive.trajectorySequenceBuilder(startPose)
				.setConstraints(number4, number2)
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInMiddleGoal();
					robot.outtake.freightcontainer.flipContainerForDrop();
				})
				.lineToLinearHeading(new Pose2d(-32, 30, Math.toRadians(335)))
				.waitSeconds(0.5)
				.UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(0.5)
				.UNSTABLE_addTemporalMarkerOffset(1, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
				})
				.setConstraints(number, number2)
				.waitSeconds(.75)
				.lineToLinearHeading(new Pose2d(-55, 55, Math.toRadians(-95)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					//LOWER ARM
				})
				.setConstraints(number, number2)
				.lineToConstantHeading(new Vector2d(-57.5,73.5))
				//spin a duck
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.caroselspinner.supplySpinnersPower(1);
				})
				.UNSTABLE_addTemporalMarkerOffset(5, () -> {
					robot.caroselspinner.supplySpinnersPower(0);
				})
				.setConstraints(defaults, number2)
				.waitSeconds(5)
				.setReversed(true)
				.lineToLinearHeading(new Pose2d(-57.5, 41, Math.toRadians(180)))
				.setReversed(false)
				/*
				//drive infront of the capstone
				.lineToSplineHeading(new Pose2d(-40,-47.5, Math.toRadians(-270)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN ON INTAKE
					robot.intake.setIntakePower(1);
				})
				//pick up duck
				.lineToConstantHeading(new Vector2d(-40, -64.25))
				//drive infront of the capstone
				.lineToLinearHeading(new Pose2d(-40, -45, Math.toRadians(-0)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN OFF INTAKE
					robot.intake.setIntakePower(0);
					//RAISE ARM\
					robot.outtake.raiseToPlaceInTopGoal();
				})
				//drive to alliance shipping hub
				.setReversed(true)
				.splineToConstantHeading(new Vector2d(-30, -24), Math.toRadians(-0))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//PLACE FREIGHT
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.setReversed(false)
				.waitSeconds(2)
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
				})
				//drive to the depot
				.lineToSplineHeading(new Pose2d(-64,-38, Math.toRadians(-180)))
				 */
				.build();

		TrajectorySequence trajSeqUpperGoal = drive.trajectorySequenceBuilder(startPose)
				.lineToLinearHeading(new Pose2d(-32, 30, Math.toRadians(335)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
					robot.outtake.freightcontainer.flipContainerForDrop();
				})
				.waitSeconds(0.5)
				.UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(0.5)
				.UNSTABLE_addTemporalMarkerOffset(1, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
				})
				.waitSeconds(.75)
				.lineToLinearHeading(new Pose2d(-55, 55, Math.toRadians(-95)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					//LOWER ARM
				})
				.setConstraints(number, number2)
				.lineToConstantHeading(new Vector2d(-57,72))
				//spin a duck
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.caroselspinner.supplySpinnersPower(1);
				})
				.UNSTABLE_addTemporalMarkerOffset(5, () -> {
					robot.caroselspinner.supplySpinnersPower(0);
				})
				.setConstraints(defaults, number2)
				.waitSeconds(5)
				.setReversed(true)
				.lineToLinearHeading(new Pose2d(-57.5, 41, Math.toRadians(180)))
				.setReversed(false)
				/*
				//drive infront of the capstone
				.lineToSplineHeading(new Pose2d(-40,-47.5, Math.toRadians(-270)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN ON INTAKE
					robot.intake.setIntakePower(1);
				})
				//pick up duck
				.lineToConstantHeading(new Vector2d(-40, -64.25))
				//drive infront of the capstone
				.lineToLinearHeading(new Pose2d(-40, -45, Math.toRadians(-0)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//TURN OFF INTAKE
					robot.intake.setIntakePower(0);
					//RAISE ARM\
					robot.outtake.raiseToPlaceInTopGoal();
				})
				//drive to alliance shipping hub
				.setReversed(true)
				.splineToConstantHeading(new Vector2d(-30, -24), Math.toRadians(-0))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//PLACE FREIGHT
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.setReversed(false)
				.waitSeconds(2)
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
				})
				//drive to the depot
				.lineToSplineHeading(new Pose2d(-64,-38, Math.toRadians(-180)))
				 */
				.build();


		telemetry.addData("Status", "Ready to run");
		telemetry.update();

		waitForStart();

		if (isStopRequested()) return;

		capstonePosition = robot.cameravision.getPosition();
		telemetry.addData("Capstone Pos", capstonePosition.toString());
		telemetry.update();

/*
		if (capstonePosition == FreightFrenzyDeterminationPipeline.CapstonePosition.LEFT) {
			drive.followTrajectorySequence(trajSeqLowerGoal);
		} else if (capstonePosition == FreightFrenzyDeterminationPipeline.CapstonePosition.MIDDLE) {
			drive.followTrajectorySequence(trajSeqMiddleGoal);
		} else {
			drive.followTrajectorySequence(trajSeqUpperGoal);
		}
 */
		drive.followTrajectorySequence(trajSeqUpperGoal);
		robot.cameravision.end();
		robot.odometerpods.raiseOdometerWheels();
		sleep(2000);

		}
	}