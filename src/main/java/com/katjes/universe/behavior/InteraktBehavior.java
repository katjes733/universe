package com.katjes.universe.behavior;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.Node;
import javax.media.j3d.SceneGraphPath;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Vector3d;

import com.katjes.universe.Universe;
import com.katjes.universe.graphics.TexturedPlane;
import com.sun.j3d.utils.behaviors.vp.ViewPlatformBehavior;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class InteraktBehavior extends ViewPlatformBehavior {
	private static final double ROT_AMT = Math.PI / 90.0; // 2,5 degrees
	private static final double MOVE_STEP = 0.2;

	// hardwired movement vectors
	private static final Vector3d FWD = new Vector3d(0, 0, -MOVE_STEP);
	private static final Vector3d BACK = new Vector3d(0, 0, MOVE_STEP);
	private static final Vector3d LEFT = new Vector3d(-MOVE_STEP, 0, 0);
	private static final Vector3d RIGHT = new Vector3d(MOVE_STEP, 0, 0);
	// Not yet used
	// private static final Vector3d UP = new Vector3d(0,MOVE_STEP,0);
	// private static final Vector3d DOWN = new Vector3d(0,-MOVE_STEP,0);

	// key names
	private final int forwardKey = KeyEvent.VK_W;
	private final int backKey = KeyEvent.VK_S;
	private final int strafeleftKey = KeyEvent.VK_A;
	private final int straferightKey = KeyEvent.VK_D;
	private final int leftKey = KeyEvent.VK_LEFT;
	private final int rightKey = KeyEvent.VK_RIGHT;
	private final int downKey = KeyEvent.VK_UP;
	private final int upKey = KeyEvent.VK_DOWN;

	private final WakeupCriterion[] Interaktion_WuC = new WakeupCriterion[3];
	private final Universe universe;

	// for repeated calcs
	private final Transform3D t3d = new Transform3D();
	private final Transform3D toMove = new Transform3D();
	private final Transform3D toRot = new Transform3D();

	private int x_old = 256;
	private int y_old = 256;

	private static boolean ESC_pressed = true;

	WakeupOr Interaktion;

	public InteraktBehavior(final Universe uni) {
		universe = uni;
		Interaktion_WuC[1] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		Interaktion_WuC[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
		Interaktion = new WakeupOr(Interaktion_WuC);

	} // end of InteraktBehavior()

	public InteraktBehavior(final Universe uni, final ViewingPlatform vp) {
		universe = uni;
		Interaktion_WuC[1] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		Interaktion_WuC[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
		Interaktion = new WakeupOr(Interaktion_WuC);

	} // end of InteraktBehavior()

	public InteraktBehavior(final Universe uni, final TransformGroup col_TG) {
		universe = uni;
		Interaktion_WuC[1] = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		Interaktion_WuC[0] = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
		Interaktion_WuC[2] = new WakeupOnCollisionEntry(col_TG);
		Interaktion = new WakeupOr(Interaktion_WuC);

	} // end of InteraktBehavior()

	@Override
	public void initialize() {
		wakeupOn(Interaktion);
	}

	@Override
	public void processStimulus(final Enumeration criteria) {
		// respond to a keypress

		WakeupCriterion wakeup;
		AWTEvent[] event;

		while (criteria.hasMoreElements()) {
			wakeup = (WakeupCriterion) criteria.nextElement();

			// Collision detection
			if (wakeup instanceof WakeupOnCollisionEntry) {

				final WakeupOnCollisionEntry w = (WakeupOnCollisionEntry) wakeup;
				// Kollisionsobjekt ermitteln:
				final SceneGraphPath p = w.getTriggeringPath();
				final Node n = p.getObject();
				if (n instanceof TexturedPlane) {
					System.out.println("unwichtige Kollision !!");
				} else {
					System.out.println("Kollision mit Planeten mit " + n);
				}

			}

			// Movement detection
			if (wakeup instanceof WakeupOnAWTEvent) {
				event = ((WakeupOnAWTEvent) wakeup).getAWTEvent();
				for (final AWTEvent element : event) {
					if (element.getID() == KeyEvent.KEY_PRESSED
							&& ESC_pressed != true) {
						processKeyEvent((KeyEvent) element);
					} else if (element.getID() == MouseEvent.MOUSE_MOVED
							&& ESC_pressed != true) {
						processMouseEvent((MouseEvent) element);
					}
				}
			}
		}
		wakeupOn(Interaktion);
	} // end of processStimulus()

	private void processKeyEvent(final KeyEvent eventKey) {
		final int keyCode = eventKey.getKeyCode();

		standardMove(keyCode);
	} // end of processKeyEvent()

	private void processMouseEvent(final MouseEvent eventMouse) {
		int x;
		int y;

		// System.out.println("Mouse event...");

		x = eventMouse.getX();
		y = eventMouse.getY();

		if (x > x_old) {
			// rotate right
			// System.out.println("rotate right...");
			rotateY(-ROT_AMT);
		} else if (x < x_old) {
			// rotate left
			// System.out.println("rotate left...");
			rotateY(ROT_AMT);
		}

		if (y < y_old) {
			// rotate down
			// System.out.println("rotate down...");
			rotateX(ROT_AMT);
		} else if (y > y_old) {
			// rotate up
			// System.out.println("rotate up...");
			rotateX(-ROT_AMT);
		}

		x_old = x;
		y_old = y;
	} // end of processMouseEvent()

	private void standardMove(final int keycode)
	/*
	 * Make viewer moves forward or backward; rotate left or right;
	 */
	{
		if (keycode == forwardKey) {
			doMove(FWD);
			// System.out.println("Bewege vorwärts...");
		} else if (keycode == backKey) {
			doMove(BACK);
			// System.out.println("Bewege rückwärts...");
		} else if (keycode == leftKey) {
			rotateY(ROT_AMT);
			// System.out.println("Drehe links...");
		} else if (keycode == rightKey) {
			rotateY(-ROT_AMT);
			// System.out.println("Drehe rechts...");
		} else if (keycode == straferightKey) {
			doMove(RIGHT);
			// System.out.println("Gleite rechts...");
		} else if (keycode == strafeleftKey) {
			doMove(LEFT);
			// System.out.println("Gleite links...");
		} else if (keycode == downKey) {
			rotateX(ROT_AMT);
			// System.out.println("Kippe vorne...");
		} else if (keycode == upKey) {
			rotateX(-ROT_AMT);
			// System.out.println("Kippe hinten...");
		}

	} // end of standardMove()

	private void rotateY(final double radians)
	// rotate about y-axis by radians
	{
		targetTG.getTransform(t3d); // targetTG is the ViewPlatform's tranform
		toRot.rotY(radians);
		t3d.mul(toRot);
		targetTG.setTransform(t3d);
	} // end of rotateY()

	private void rotateX(final double radians)
	// rotate about x-axis by radians
	{
		targetTG.getTransform(t3d); // targetTG is the ViewPlatform's tranform
		toRot.rotX(radians);
		t3d.mul(toRot);
		targetTG.setTransform(t3d);
	} // end of rotateX()

	private void doMove(final Vector3d theMove) {
		// performs movement
		targetTG.getTransform(t3d);
		toMove.setTranslation(theMove);
		t3d.mul(toMove);
		targetTG.setTransform(t3d);
	} // end of doMove()

	public static void setESC_pressed(final boolean e) {
		ESC_pressed = e;
	}

} // end of KeyBehavior class
