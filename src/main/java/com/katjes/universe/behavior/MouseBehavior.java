package com.katjes.universe.behavior;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.media.j3d.Transform3D;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;

import com.katjes.universe.Universe;
import com.sun.j3d.utils.behaviors.vp.ViewPlatformBehavior;

public class MouseBehavior extends ViewPlatformBehavior {
	private static final double ROT_AMT = Math.PI / 180.0; // 1 degree
	private static final int MouseSensitivity = 5; // how Mouse reacts on moves
	// for repeated calcs
	private final Transform3D t3d = new Transform3D();
	private final Transform3D toRot = new Transform3D();

	private final WakeupCondition mouseMove;
	private final Universe universe;

	private int x_old = 256;
	private int y_old = 256;

	public MouseBehavior(final Universe uni) {
		universe = uni;
		mouseMove = new WakeupOnAWTEvent(MouseEvent.MOUSE_MOVED);
	} // end of MouseBehavior()

	@Override
	public void initialize() {
		wakeupOn(mouseMove);
	}

	@Override
	public void processStimulus(final Enumeration criteria) {
		// respond to a mouse move
		WakeupCriterion wakeup;
		AWTEvent[] event;

		while (criteria.hasMoreElements()) {
			wakeup = (WakeupCriterion) criteria.nextElement();
			if (wakeup instanceof WakeupOnAWTEvent) {
				event = ((WakeupOnAWTEvent) wakeup).getAWTEvent();
				for (final AWTEvent element : event) {
					if (element.getID() == MouseEvent.MOUSE_MOVED) {
						processMouseEvent((MouseEvent) element);
					}
				}
			}
		}
		wakeupOn(mouseMove);
	} // end of processStimulus

	private void processMouseEvent(final MouseEvent eventMouse) {
		int x;
		int y;

		System.out.println("Mouse event...");

		x = eventMouse.getX();
		y = eventMouse.getY();

		if (x > x_old) {
			// rotate right
			System.out.println("rotate right...");
			rotateY(-ROT_AMT);
		} else if (x < x_old) {
			// rotate left
			System.out.println("rotate left...");
			rotateY(ROT_AMT);
		}

		if (y < y_old) {
			// rotate down
			System.out.println("rotate down...");
			rotateX(ROT_AMT);
		} else if (y > y_old) {
			// rotate up
			System.out.println("rotate up...");
			rotateX(-ROT_AMT);
		}

		x_old = x;
		y_old = y;
	} // end of processMouseEvent()

	private void rotateY(final double radians) {
		// rotate about y-axis by radians
		targetTG.getTransform(t3d); // targetTG is the ViewPlatform's tranform
		toRot.rotY(radians);
		t3d.mul(toRot);
		targetTG.setTransform(t3d);
	} // end of rotateY()

	private void rotateX(final double radians) {
		// rotate about x-axis by radians
		targetTG.getTransform(t3d); // targetTG is the ViewPlatform's tranform
		toRot.rotX(radians);
		t3d.mul(toRot);
		targetTG.setTransform(t3d);
	} // end of rotateX()
}