package com.katjes.universe.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.loaders.Scene;

// Loaders
import ncsa.j3d.*;
import ncsa.j3d.loaders.*;

public class E3D_Object extends BranchGroup {
	// for specifying moves and rotations
	private static final int X_AXIS = 0;
	private static final int Y_AXIS = 1;
	private static final int Z_AXIS = 2;
	private static final int INCR = 0;
	private static final int DECR = 1;

	private static final double MOVE_INCR = 0.1; // move increment for an object
	private static final double ROT_INCR = 10; // rotation increment (in degrees)
	private static final double ROT_AMT = Math.toRadians(ROT_INCR); // in radians

	// TGs which the loaded object (the prop) hangs off:
	// moveTG-->rotTG-->scaleTG-->objBoundsTG-->obj
	private TransformGroup moveTG, rotTG, scaleTG;
	private final Transform3D t3d; // for accessing a TG's transform
	private final Transform3D chgT3d; // holds current change to the posn, rot, or scale

	private final String filename; // of loaded object
	private final double xRot, yRot, zRot; // total of rotation angles carried out
	private final ArrayList rotInfo; // stores the sequence of rotation numbers
	private final double scale; // current object scaling

	private final DecimalFormat df; // for debugging

	public E3D_Object(final String loadFnm) {
		filename = loadFnm;
		xRot = 0.0;
		yRot = 0.0;
		zRot = 0.0; // initial loaded object settings
		rotInfo = new ArrayList();
		scale = 1.0;

		t3d = new Transform3D(); // setup reusable Transform3D objects
		chgT3d = new Transform3D();

		df = new DecimalFormat("0.###"); // 3 dp

		loadFile(loadFnm);
	} // end of PropManager()

	private void loadFile(final String fnm)
	/*
	 * The 3D object file is loaded using a Portfolio loader. The loaded object has 4 transform groups above it --
	 * objBoundsTG is for adjusting the object's bounded sphere so it is centered at (0,0,0) and has unit radius. The
	 * other TGs are for doing separate moves, rotations, and scaling of the object.
	 * moveTG-->rotTG-->scaleTG-->objBoundsTG-->object
	 */
	{
		System.out.println("Loading object file: " + fnm);

		Scene s = null;
		final ModelLoader modelLoader = new ModelLoader();
		try {
			s = modelLoader.load(fnm); // handles many types of file
		} catch (final Exception e) {
			System.err.println(e);
			System.exit(1);
		}

		// get the branch group for the loaded object
		final BranchGroup sceneGroup = s.getSceneGroup();

		// create a transform group for the object's bounding sphere
		final TransformGroup objBoundsTG = new TransformGroup();
		objBoundsTG.addChild(sceneGroup);

		// resize loaded object's bounding sphere (and maybe rotate)
		final String ext = getExtension(fnm);
		final BoundingSphere objBounds = (BoundingSphere) sceneGroup
				.getBounds();
		setBSPosn(objBoundsTG, objBounds.getRadius(), ext);

		// create a transform group for scaling the object
		scaleTG = new TransformGroup();
		scaleTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		scaleTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		scaleTG.addChild(objBoundsTG);

		// create a transform group for rotating the object
		rotTG = new TransformGroup();
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotTG.addChild(scaleTG);

		// create a transform group for moving the object
		moveTG = new TransformGroup();
		moveTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		moveTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		moveTG.addChild(rotTG);

	} // end of loadFile()

	private String getExtension(final String fnm)
	// return the extension of fnm, or "(none)"
	{
		final int dotposn = fnm.lastIndexOf(".");
		if (dotposn == -1) {
			return "(none)";
		} else {
			return fnm.substring(dotposn + 1).toLowerCase();
		}
	}

	private void setBSPosn(final TransformGroup objBoundsTG,
			final double radius, final String ext)
	// Scale the object to unit radius, and rotate -90 around x-axis if the
	// file contains a 3ds model
	{
		final Transform3D objectTrans = new Transform3D();
		objBoundsTG.getTransform(objectTrans);

		// System.out.println("radius: " + df.format(radius));

		// scale the object so its bounds are within a 1 unit radius sphere
		final Transform3D scaleTrans = new Transform3D();
		final double scaleFactor = 1.0 / radius;
		// System.out.println("scaleFactor: " + df.format(scaleFactor) );
		scaleTrans.setScale(scaleFactor);

		// final transform = [original] * [scale] (and possible *[rotate])
		objectTrans.mul(scaleTrans);

		if (ext.equals("3ds")) { // the file is a 3ds model
			// System.out.println("Rotating -90 around x-axis");
			final Transform3D rotTrans = new Transform3D();
			rotTrans.rotX(-Math.PI / 2.0); // 3ds models are often on their face; fix that
			objectTrans.mul(rotTrans);
		}

		objBoundsTG.setTransform(objectTrans);
	} // end of setBSPosn()

	public TransformGroup getTG() {
		return moveTG;
	}

} // end of PropManager class
