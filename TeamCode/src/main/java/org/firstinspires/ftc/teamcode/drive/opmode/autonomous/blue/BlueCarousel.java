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

	TrajectoryVelocityConstraint number3 = SampleMecanumDrive.getVelocityConstraint(MAX_VEL, MAX_ANG_VEL, TRACK_WIDTH);

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

		TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
				.lineToLinearHeading(new Pose2d(-32, 30, Math.toRadians(345)))
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
				.lineToLinearHeading(new Pose2d(-60, 55, Math.toRadians(250)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					//LOWER ARM
				})
				.setConstraints(number, number2)
				.lineToConstantHeading(new Vector2d(-58.5,65))
				//spin a duck
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.caroselspinner.supplySpinnersPower(1);
				})
				.UNSTABLE_addTemporalMarkerOffset(5, () -> {
					robot.caroselspinner.supplySpinnersPower(0);
				})
				.setConstraints(number3, number2)
				.waitSeconds(5)
				.lineToLinearHeading(new Pose2d(-60, 35, Math.toRadians(200)))
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
		drive.followTrajectorySequence(trajSeq);
		robot.cameravision.end();
		robot.odometerpods.raiseOdometerWheels();
		sleep(2000);

		}
	}