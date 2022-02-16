package com.katjes.universe.objects;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.katjes.universe.Universe;

public class Tortenstueck extends BranchGroup {
	private static final int format = GeometryArray.COORDINATES
			| GeometryArray.NORMALS;
	private final TriangleArray TA;
	private final QuadArray QA;
	private Shape3D shape;
	private final Point3d[] p_oben;
	private final Point3d[] p_unten;

	public Tortenstueck(final float Beta, final float Radius, final float Höhe,
			int Unterteilungen) {
		if (Unterteilungen < 0) {
			Unterteilungen = Math.abs(Unterteilungen);
		}
		if (Unterteilungen == 0) {
			Unterteilungen = 1;
		}
		TA = new TriangleArray(Unterteilungen * 6, format);
		QA = new QuadArray((Unterteilungen + 2) * 4, format);
		p_unten = new Point3d[Unterteilungen + 2];
		p_oben = new Point3d[Unterteilungen + 2];
		p_unten[0] = new Point3d(0.0, 0.0, 0.0);
		p_oben[0] = new Point3d(0.0, Höhe, 0.0);
		berechne_Torten_Punkte(Beta, Radius, Höhe, Unterteilungen);
		erzeuge_Tortenstueck(Unterteilungen);
	}

	private void berechne_Torten_Punkte(final float Beta, final float Radius,
			final float Höhe, final int Unterteilungen) {
		double x;
		double z;

		for (int i = 0; i < Unterteilungen; i++) {
			x = Math.cos(Beta / Unterteilungen * i) * Radius;
			z = -Math.sqrt(1 - x * x);

			p_unten[i + 1] = new Point3d(x, 0.0, z);
			p_oben[i + 1] = new Point3d(x, Höhe, z);
		}
	}

	private void erzeuge_Tortenstueck(final int Unterteilungen) {
		shape = new Shape3D();
		for (int i = 1; i < Unterteilungen; i++) {
			TA.setCoordinate(i, p_unten[0]);
			TA.setCoordinate(i + 1, p_unten[i]);
			TA.setCoordinate(i + 2, p_unten[i + 1]);

			TA.setCoordinate(i + Unterteilungen * 3, p_oben[0]);
			TA.setCoordinate(i + 1 + Unterteilungen * 3, p_oben[i]);
			TA.setCoordinate(i + 2 + Unterteilungen * 3, p_oben[i + 1]);
		}

		/*
		 * for (int i=1; i < Unterteilungen*2 ; i=i+2) { QA.setCoordinate(i,p_unten[(i-1)/2+1]);
		 * QA.setCoordinate(i,p_unten[(i-1)/2+2]); QA.setCoordinate(i,p_oben[(i-1)/2+1]);
		 * QA.setCoordinate(i,p_oben[(i-1)/2+2]); }
		 */

		Normalen_setzen(Unterteilungen);
		shape.setGeometry(TA);
		shape.addGeometry(QA);
		Universe.Universum.addChild(shape);
	}

	protected void Normalen_setzen(final int Unterteilungen) {
		final float[] c_TA = new float[9];

		for (int i = 1; i < Unterteilungen * 2; i++) {
			TA.getCoordinates(3 * i, c_TA);
			final Vector3f v = new Vector3f(c_TA[6] - c_TA[0],
					c_TA[7] - c_TA[1], c_TA[8] - c_TA[2]);
			final Vector3f w = new Vector3f(c_TA[3] - c_TA[0],
					c_TA[4] - c_TA[1], c_TA[5] - c_TA[2]);
			final Vector3f n = new Vector3f();
			n.cross(v, w);
			n.normalize();

			for (int j = 0; j < 3; j++) {
				TA.setNormal(3 * i + j, n);
			}
		}
	}
}