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
/*
 * This is an example of a more complex path to really test the tuning.
 */
@Config
@Autonomous(group = "drive")
public class RedWarehouse extends LinearOpMode {
	TrajectoryVelocityConstraint number = SampleMecanumDrive.getVelocityConstraint(15,MAX_ANG_VEL,TRACK_WIDTH);
	TrajectoryAccelerationConstraint number2 = SampleMecanumDrive.getAccelerationConstraint(MAX_ACCEL);

	TrajectoryVelocityConstraint number3 = SampleMecanumDrive.getVelocityConstraint(MAX_VEL,MAX_ANG_VEL,TRACK_WIDTH);

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		robot robot = new robot();
		robot.init(hardwareMap, telemetry, this, true);
		robot.odometerpods.lowerOdometerWheels();
		robot.outtake.freightcontainer.closeContainer();
		robot.cameravision.start();

		Pose2d startPose = new Pose2d(6.25, -64.25, Math.toRadians(-270));

		drive.setPoseEstimate(startPose);

		TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
				.lineToLinearHeading(new Pose2d(4, -32, Math.toRadians(-215)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
				})
				.waitSeconds(.5)
				.addTemporalMarker(2.5, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(.5)
				.addTemporalMarker(2.75, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
				})
				.waitSeconds(.5)
				.lineToLinearHeading(new Pose2d(11, -67.25, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();

					//LOWER ARM
				})
				.UNSTABLE_addTemporalMarkerOffset(1, () -> {
					robot.outtake.freightcontainer.openContainer();
					//robot.intake.setIntakePower(-0.85);
					//TURN OFF INTAKE
				})
				.setConstraints(number, number2)
				.lineToConstantHeading(new Vector2d(42, -67.25))
				.UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
					robot.outtake.freightcontainer.closeContainer();
					//TURN OFF INTAKE
				})
				/*
				.setConstraints(number3, number2)
				.lineToConstantHeading(new Vector2d(11, -67.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})



				.lineToLinearHeading(new Pose2d(4, -32, Math.toRadians(-215)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
				})
				.waitSeconds(.5)
				.addTemporalMarker(15.5, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(.5)
				.addTemporalMarker(15.75, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
				})
				.waitSeconds(.5)
				.lineToLinearHeading(new Pose2d(11, -67.25, Math.toRadians(-180)))
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
				.lineToConstantHeading(new Vector2d(43.5, -67.25))
				.UNSTABLE_addTemporalMarkerOffset(1.25, () -> {
					robot.outtake.freightcontainer.closeContainer();
					//TURN OFF INTAKE
				})
				.UNSTABLE_addTemporalMarkerOffset(5, () -> {
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})
/*
				.setConstraints(number3, number2)
				.lineToConstantHeading(new Vector2d(11, -67.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})

/*
				.lineToLinearHeading(new Pose2d(4, -32, Math.toRadians(-215)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
				})
				.waitSeconds(.5)
				.addTemporalMarker(2.5, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(.5)
				.addTemporalMarker(3, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
				})
				.waitSeconds(.5)
				.lineToLinearHeading(new Pose2d(14, -67.5, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					robot.outtake.freightcontainer.openContainer();
					robot.intake.setIntakePower(-1);
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(47.5, -67.5))
				.UNSTABLE_addTemporalMarkerOffset(3, () -> {
					robot.outtake.freightcontainer.closeContainer();
					//TURN OFF INTAKE
				})
				.lineToConstantHeading(new Vector2d(14, -64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(4, -32, Math.toRadians(-215)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
				})
				.waitSeconds(.5)
				.addTemporalMarker(2.5, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(.5)
				.addTemporalMarker(3, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
				})
				.waitSeconds(.5)
				.lineToLinearHeading(new Pose2d(14, -67.5, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					robot.outtake.freightcontainer.openContainer();
					robot.intake.setIntakePower(-1);
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(47.5, -67.5))
				.UNSTABLE_addTemporalMarkerOffset(3, () -> {
					robot.outtake.freightcontainer.closeContainer();
					//TURN OFF INTAKE
				})
				.lineToConstantHeading(new Vector2d(14, -64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(4, -32, Math.toRadians(-215)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();
				})
				.waitSeconds(.5)
				.addTemporalMarker(2.5, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletely();
				})
				.waitSeconds(.5)
				.addTemporalMarker(3, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.closeContainer();
					robot.outtake.freightcontainer.flipContainerForIntake();
				})
				.waitSeconds(.5)
				.lineToLinearHeading(new Pose2d(14, -67.5, Math.toRadians(-180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					robot.outtake.freightcontainer.openContainer();
					robot.intake.setIntakePower(-1);
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(47.5, -67.5))
				.UNSTABLE_addTemporalMarkerOffset(3, () -> {
					robot.outtake.freightcontainer.closeContainer();
					//TURN OFF INTAKE
				})
				.lineToConstantHeading(new Vector2d(14, -64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})
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