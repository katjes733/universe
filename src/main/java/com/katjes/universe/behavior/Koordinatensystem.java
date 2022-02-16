package com.katjes.universe.behavior;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3d;

import com.katjes.universe.Universe;
import com.sun.j3d.utils.geometry.Box;

public class Koordinatensystem extends Shape3D {
	private final QuadArray coo = new QuadArray(12, GeometryArray.COORDINATES);
	private final Shape3D shape = new Shape3D();
	private LineArray LA;
	Point3d[] p;;

	public Koordinatensystem() {
		// erzeuge_Koordinatensystem(10.0);
		erzeuge_Koordinatensystem_mit_Box(10.0f);
	}

	public Koordinatensystem(final double gr) {
		// erzeuge_Koordinatensystem (gr);
	}

	public Koordinatensystem(final float gr) {
		erzeuge_Koordinatensystem_mit_Box(gr);
	}

	public Koordinatensystem(final double gr, final int Unterteilungen) {
		erzeuge_Koordinatensystem_aus_Linien(gr, Unterteilungen);
	}

	private void erzeuge_Koordinatensystem(final double gr) {
		p = new Point3d[12];
		p[0] = new Point3d(-gr, -gr, 0.0);
		p[1] = new Point3d(gr, -gr, 0.0);
		p[2] = new Point3d(gr, gr, 0.0);
		p[3] = new Point3d(-gr, gr, 0.0); // x,y
		p[4] = new Point3d(-gr, 0.0, -gr);
		p[5] = new Point3d(-gr, 0.0, gr);
		p[6] = new Point3d(gr, 0.0, gr);
		p[7] = new Point3d(gr, 0.0, -gr); // x,z
		p[8] = new Point3d(0.0, gr, gr);
		p[9] = new Point3d(0.0, -gr, gr);
		p[10] = new Point3d(0.0, -gr, -gr);
		p[11] = new Point3d(0.0, gr, -gr); // y,z

		for (int i = 0; i < 11; i++) {
			coo.setCoordinate(i, p[i]);
		}
		shape.setGeometry(coo);
		final Appearance app = new Appearance();

		final PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		pa.setPolygonMode(PolygonAttributes.POLYGON_LINE);
		app.setPolygonAttributes(pa);

		shape.setAppearance(app);
		// this.addChild(shape);
	}

	private void erzeuge_Koordinatensystem_mit_Box(final float gr) {
		final Appearance app = new Appearance();
		final PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		pa.setPolygonMode(PolygonAttributes.POLYGON_LINE);
		app.setPolygonAttributes(pa);
		final Box b1 = new Box(2 * gr / 20.0f, 2 * gr / 20.0f, 0.00001f, app);
		Universe.Universum.addChild(b1);
	}

	private void erzeuge_Koordinatensystem_aus_Linien(final double gr,
			final int Unterteilungen) {
		LA = new LineArray((Unterteilungen + 1) * 2,
				GeometryArray.COORDINATES | GeometryArray.COLOR_3);
		p = new Point3d[(Unterteilungen + 1) * 2];
		int j;
		for (int i = 0; i < (Unterteilungen + 1) * 2; i++) {
			j = i / 2;
			if (i % 2 == 0) {
				p[i] = new Point3d(-gr, -gr + 2 * j / Unterteilungen * gr, 0.0);
			} else {
				p[i] = new Point3d(gr, -gr + 2 * j / Unterteilungen * gr, 0.0);
			}
		}
		shape.setGeometry(LA);
	}
}