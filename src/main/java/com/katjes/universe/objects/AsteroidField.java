package com.katjes.universe.objects;

import java.util.Random;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

public class AsteroidField extends TransformGroup {
	private Object_3D[] Asteroid;
	private Random r;

	public AsteroidField(final int Elements, final double Range) {
		r = new Random();
		generate_AsteroidField(Elements, Range, 0.0, 0.0, 0.0);
	}

	public AsteroidField(final int Elements, final double Range,
			final double Rescale_all) {
		generate_AsteroidField(Elements, Range, Rescale_all, Rescale_all,
				Rescale_all);
	}

	private void generate_AsteroidField(final int Elements, final double Range,
			double factorX, double factorY, double factorZ) {
		r = new Random();
		Asteroid = new Object_3D[Elements];
		boolean randomlyChange = false;
		final Transform3D rotateX = new Transform3D();
		final Transform3D rotateY = new Transform3D();
		final Transform3D rotateZ = new Transform3D();
		final Transform3D result = new Transform3D();

		if (factorZ == 0.0 && factorX == 0.0 && factorZ == 0.0) {
			randomlyChange = true;
		}

		for (int i = 0; i < Elements; i++) {
			if (randomlyChange) {
				factorX = r.nextDouble();
				factorY = r.nextDouble();
				factorZ = r.nextDouble();

				rotateX.rotX(r.nextInt(360) / 180 * Math.PI);
				rotateY.rotY(r.nextInt(360) / 180 * Math.PI);
				rotateZ.rotZ(r.nextInt(360) / 180 * Math.PI);

				result.mul(rotateX, rotateY);
				result.mul(result, rotateZ);
			}

			if (i % 2 == 0) {
				Asteroid[i] = new Object_3D("asteroid.3ds", factorX, factorY,
						factorZ);
				Asteroid[i].setTransform(result);
				Asteroid[i].move_to(r.nextDouble() * Range,
						r.nextDouble() * Range, r.nextDouble() * Range);

				addChild(Asteroid[i]);
			} else {
				Asteroid[i] = new Object_3D("asteroid2.3ds", factorX * 2.0,
						factorY * 2.0, factorZ * 2.0);
				Asteroid[i].setTransform(result);
				Asteroid[i].move_to(r.nextDouble() * Range,
						r.nextDouble() * Range, r.nextDouble() * Range);

				addChild(Asteroid[i]);
			}
		}
	}

	public void move_to(final double x, final double y, final double z) {
		final Transform3D move = new Transform3D();

		move.setTranslation(new Vector3d(x, y, z));
		this.setTransform(move);
	}
}