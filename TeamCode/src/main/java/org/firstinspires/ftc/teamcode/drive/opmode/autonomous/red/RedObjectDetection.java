package org.firstinspires.ftc.teamcode.drive.opmode.autonomous.red;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.FreightFrenzyDeterminationPipeline;
import org.firstinspires.ftc.teamcode.robot.robot;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.TRACK_WIDTH;

/*
 * This is an example of a more complex path to really test the tuning.
 */

@Config
@Autonomous(group = "drive")
public class RedObjectDetection extends LinearOpMode {
	TrajectoryVelocityConstraint number = SampleMecanumDrive.getVelocityConstraint(15, MAX_ANG_VEL, TRACK_WIDTH);
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

		Pose2d startPose = new Pose2d(6.25, -64.25, Math.toRadians(-270));

		drive.setPoseEstimate(startPose);

/*
				TrajectorySequence trajSeqLowerGoal = drive.trajectorySequenceBuilder(startPose)
				.addTemporalMarker(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
					robot.outtake.freightcontainer.flipContainerForDrop();
				})
				.waitSeconds(1.5)
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.lowerBackToIntakePosition();
				})
				.lineToLinearHeading(new Pose2d(4, -45, Math.toRadians(-180)))
				.lineToLinearHeading(new Pose2d(4.125, -32.25, Math.toRadians(-215)))
				.waitSeconds(.5)
				.addTemporalMarker(4, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(.5)
				.addTemporalMarker(6, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
				})
				.waitSeconds(.5)
				.lineToLinearHeading(new Pose2d(4, -67, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.freightcontainer.flipContainerForIntake();
					robot.outtake.raiseToPlaceInTopGoal();
					//LOWER ARM
				})
				.UNSTABLE_addTemporalMarkerOffset(1, () -> {
					robot.outtake.lowerBackToIntakePosition();
//robot.intake.setIntakePower(-0.85);
					//TURN OFF INTAKE
				})
				.setConstraints(number, number2)
				.lineToConstantHeading(new Vector2d(45, -67))
				.UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
					robot.outtake.freightcontainer.closeContainer();
//robot.intake.setIntakePower(0.5);
					//TURN OFF INTAKE
				})
				.build();







			TrajectorySequence trajSeqMiddleGoal = drive.trajectorySequenceBuilder(startPose)
				.addTemporalMarker(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
					robot.outtake.freightcontainer.flipContainerForDrop();
				})
				.waitSeconds(.5)
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInMiddleGoal();
				})
				.lineToLinearHeading(new Pose2d(4.5, -32.5, Math.toRadians(-215)))
				.waitSeconds(.5)
				.addTemporalMarker(2, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(.5)
				.addTemporalMarker(2.5, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();

				})
				.lineToLinearHeading(new Pose2d(4, -67, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.freightcontainer.flipContainerForIntake();
					//LOWER ARM
				})
				.UNSTABLE_addTemporalMarkerOffset(2, () -> {
					robot.outtake.lowerBackToIntakePosition();
					//LOWER ARM
				})
				.setConstraints(number, number2)
				.lineToConstantHeading(new Vector2d(45, -65))
				.UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
					robot.outtake.freightcontainer.closeContainer();
//robot.intake.setIntakePower(0.5);
					//TURN OFF INTAKE
				})
				.build();

*/




		TrajectorySequence trajSeqUpperGoal = drive.trajectorySequenceBuilder(startPose)
				.lineToLinearHeading(new Pose2d(10, -50, Math.toRadians(-180)))
				.lineToLinearHeading(new Pose2d(7.25, -36.75, Math.toRadians(-247.5)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
					robot.outtake.freightcontainer.flipContainerForDrop();
				})
				.waitSeconds(.5)
				.UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(.5)
				.UNSTABLE_addTemporalMarkerOffset(1, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
				})
				.waitSeconds(.75)
				.lineToLinearHeading(new Pose2d(9, -64, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();

					//LOWER ARM
				})
				.UNSTABLE_addTemporalMarkerOffset(1, () -> {
					robot.outtake.freightcontainer.openContainer();
//					robot.intake.setIntakePower(-0.85);
					//TURN OFF INTAKE
				})
				.setConstraints(number, number2)
				.lineToLinearHeading(new Pose2d(45, -63, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
//					robot.intake.setIntakePower(0.85);
					//TURN OFF INTAKE
				})
				/*
				.setConstraints(number3, number2)
				.lineToConstantHeading(new Vector2d(9, 67.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.freightcontainer.closeContainer();
//					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})

				/*
				.lineToLinearHeading(new Pose2d(4, -32, Math.toRadians(-215)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
					robot.outtake.freightcontainer.flipContainerForDrop();
				})
				.waitSeconds(.5)
				.UNSTABLE_addTemporalMarkerOffset(2.5, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(.5)
				.UNSTABLE_addTemporalMarkerOffset(2.5, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
				})
				.waitSeconds(.75)
				.lineToLinearHeading(new Pose2d(4, -67, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();

					//LOWER ARM
				})
				.UNSTABLE_addTemporalMarkerOffset(1, () -> {
					robot.outtake.freightcontainer.openContainer();
					robot.intake.setIntakePower(-0.85);
					//TURN OFF INTAKE
				})
				.setConstraints(number, number2)
				.lineToLinearHeading(new Pose2d(42.5, -70, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(1.25, () -> {

					robot.intake.setIntakePower(0.85);
					//TURN OFF INTAKE
				})
				.setConstraints(number3, number2)
				.lineToConstantHeading(new Vector2d(9, -67.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.freightcontainer.closeContainer();
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})
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