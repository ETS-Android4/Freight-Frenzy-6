package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class meepmeep {
	public static void main(String[] args) {
		MeepMeep meepMeep = new MeepMeep(600);

		RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
				// Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
				.setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.63)
				.setDimensions(12.5,16)
				.followTrajectorySequence(drive ->
						drive.trajectorySequenceBuilder(new Pose2d(6.25, -64.25, Math.toRadians(-270)))

								.build()
				);

		meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
				.setDarkMode(true)
				.setBackgroundAlpha(0.95f)
				.addEntity(myBot)
				.start();
	}
}
