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
public class BlueWarehouse extends LinearOpMode {

	@Override
	public void runOpMode() throws InterruptedException {
		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
		robot robot = new robot();
		robot.init(hardwareMap, telemetry, this, true);

		robot.cameravision.start();

		Pose2d startPose = new Pose2d(6.25, 64.25, Math.toRadians(270));

		drive.setPoseEstimate(startPose);

		TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
				.lineToLinearHeading(new Pose2d(0, 40, Math.toRadians(225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();

				})
				.UNSTABLE_addTemporalMarkerOffset(5, () -> {
					//DROP FREIGHT #1
					robot.outtake.freightcontainer.openContainerCompletly();
				})

				.lineToLinearHeading(new Pose2d(14, 64.25, Math.toRadians(180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.intake.setIntakePower(1);
					//TURN ON INTAKE
				})
				.lineToConstantHeading(new Vector2d(14,64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.freightcontainer.closeContainer();
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(0, 40, Math.toRadians(225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();

				})
				.UNSTABLE_addTemporalMarkerOffset(5, () -> {
					//DROP FREIGHT # 2
					robot.outtake.freightcontainer.openContainerCompletly();
				})

				.lineToLinearHeading(new Pose2d(14, 64.25, Math.toRadians(180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.intake.setIntakePower(1);
					//TURN ON INTAKE
				})
				.lineToConstantHeading(new Vector2d(14,64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.freightcontainer.closeContainer();
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(0, 40, Math.toRadians(225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();

				})
				.UNSTABLE_addTemporalMarkerOffset(5, () -> {
					//DROP FREIGHT # 3
					robot.outtake.freightcontainer.openContainerCompletly();
				})

				.lineToLinearHeading(new Pose2d(14, 64.25, Math.toRadians(180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.intake.setIntakePower(1);
					//TURN ON INTAKE
				})
				.lineToConstantHeading(new Vector2d(14,64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.freightcontainer.closeContainer();
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(0, 40, Math.toRadians(225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();

				})
				.UNSTABLE_addTemporalMarkerOffset(5, () -> {
					//DROP FREIGHT # 4
					robot.outtake.freightcontainer.openContainerCompletly();
				})

				.lineToLinearHeading(new Pose2d(14, 64.25, Math.toRadians(180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.intake.setIntakePower(1);
					//TURN ON INTAKE
				})
				.lineToConstantHeading(new Vector2d(14,64.25))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.freightcontainer.closeContainer();
					robot.intake.setIntakePower(0);
					//TURN OFF INTAKE
				})
				.lineToLinearHeading(new Pose2d(0, 40, Math.toRadians(225)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					//RAISE ARM
					robot.outtake.raiseToPlaceInTopGoal();

				})
				.UNSTABLE_addTemporalMarkerOffset(5, () -> {
					//DROP FREIGHT # 5
					robot.outtake.freightcontainer.openContainerCompletly();
				})

				.lineToLinearHeading(new Pose2d(14, 64.25, Math.toRadians(180)))
				.UNSTABLE_addTemporalMarkerOffset(0, () -> {
					robot.outtake.lowerBackToIntakePosition();
					//LOWER ARM
				})
				.lineToConstantHeading(new Vector2d(56,64.25))
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