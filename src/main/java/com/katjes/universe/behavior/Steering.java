package com.katjes.universe.behavior;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Vector3f;

public class Steering extends Behavior {
	private WakeupOnAWTEvent wakeupOne = null;
	private final WakeupCriterion[] wakeupArray = new WakeupCriterion[1];
	private WakeupCondition wakeupCondition = null;
	private final float TRANSLATE_LEFT = -0.05f;
	private final float TRANSLATE_RIGHT = 0.05f;
	TransformGroup m_TransformGroup = null;

	public Steering(final TransformGroup tg) {
		m_TransformGroup = tg;
		wakeupOne = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		wakeupArray[0] = wakeupOne;
		wakeupCondition = new WakeupOr(wakeupArray);
		System.out.println("Steering konstruiert...");
	}

	// Override Behavior's initialize method to set up wakeup criteria
	@Override
	public void initialize() {
		// Establish initial wakeup criteria
		wakeupOn(wakeupCondition);
		System.out.println("Steering initialized...");
	}

	// Override Behavior's stimulus method to handle the event.
	@Override
	public void processStimulus(final Enumeration criteria) {
		WakeupOnAWTEvent ev;
		WakeupCriterion genericEvt;
		AWTEvent[] events;
		while (criteria.hasMoreElements()) {
			genericEvt = (WakeupCriterion) criteria.nextElement();
			if (genericEvt instanceof WakeupOnAWTEvent) {
				ev = (WakeupOnAWTEvent) genericEvt;
				events = ev.getAWTEvent();
				processAWTEvent(events);
			}
		}

		// Set wakeup criteria for next time
		wakeupOn(wakeupCondition);

		System.out.println("Steering processStimulus...");
	}

	// Process a keyboard event
	private void processAWTEvent(final AWTEvent[] events) {
		System.out.println("Steering KeyEvent...");
		for (final AWTEvent event : events) {
			if (event instanceof KeyEvent) {
				final KeyEvent eventKey = (KeyEvent) event;
				if (eventKey.getID() == KeyEvent.KEY_PRESSED) {
					final int keyCode = eventKey.getKeyCode();
					final int keyChar = eventKey.getKeyChar();
					final Vector3f translate = new Vector3f();
					final Transform3D t3d = new Transform3D();
					m_TransformGroup.getTransform(t3d);
					t3d.get(translate);
					switch (keyCode) {
					case KeyEvent.VK_LEFT:
						translate.x += TRANSLATE_LEFT;
						System.out.println("Moving left...");
						break;
					case KeyEvent.VK_RIGHT:
						translate.x += TRANSLATE_RIGHT;
						System.out.println("Moving right...");
						break;
					}
					t3d.setTranslation(translate);
					m_TransformGroup.setTransform(t3d);
				}
			}
		}
	}
}