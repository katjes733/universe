package com.katjes.universe;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.MemoryImageSource;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.katjes.universe.behavior.InteraktBehavior;
import com.katjes.universe.behavior.Koordinatensystem;
import com.katjes.universe.graphics.TexturedPlane;
import com.katjes.universe.objects.AsteroidField;
import com.katjes.universe.objects.Object_3D;
import com.katjes.universe.objects.Planet;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

/*
 * Universe.java Created on October 29, 2004, 9:54 Changed on November 13, 2004, 12:23
 */

/**
 *
 * @author Katjes
 */
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class Universe extends JFrame {
	// Festlegung eines Hauptpfades
	private static final String PARENT_PATH_GRAPHICS = "graphics/";
	private static final String HEADUP_DISPLAY_IMAGE = "Headup01.gif";
	static Component beobachter;

	static Universe Universe_u;
	static SimpleUniverse u;
	static Background bg;
	static Koordinatensystem k;

	static InteraktBehavior IB;
	static ViewingPlatform vp;

	static boolean Maus_in_Canvas3D;
	static long wz = 0; // Wartezeit beim laden
	static boolean Pause = false;
	static int Zaehler_Bild = 0;

	static DirectionalLight Licht;
	static AmbientLight al;

	static boolean ESC_pressed = true;

	static int Maus_Position_X = 0;
	static int Maus_Position_Y = 0;
	static int Maus_Position_X_alt = 0;
	static int Maus_Position_Y_alt = 0;

	static int BuGr02_selected_RB = 1;
	static float AL01_C_r = 0.5f;
	static float AL01_C_g = 0.5f;
	static float AL01_C_b = 0.5f;
	static float DL01_C_r = 1.0f;
	static float DL01_C_g = 1.0f;
	static float DL01_C_b = 1.0f;

	static float BG01_C_r = 0.9f;
	static float BG01_C_g = 0.9f;
	static float BG01_C_b = 0.9f;

	static Thread myThread = Thread.currentThread();

	public static TransformGroup Universum;
	static BoundingSphere Begrenzung;

	static Alpha a1;
	static Alpha a;
	static Transform3D Mond_Rot;
	static Transform3D Mond_Rot_t;
	static Vector3d Mond_V_Rot;
	static Transform3D Erde_Rot;
	static Transform3D Erde_Rot_t;
	static Vector3d Erde_V_Rot;
	static RotationInterpolator Erde_Rotation;
	static RotationInterpolator Mond_Rotation;
	static RotPosPathInterpolator Path01;

	static TransformGroup Erde_TG;
	static TransformGroup Mond_TG;
	static TransformGroup Mars_TG;
	static Sphere Erde_K;
	static Sphere Mond_K;
	static Sphere Mars_K;
	static TransformGroup T_Squadron;

	static Transform3D Mars_T3D;
	static Transform3D move_T3D;

	static float m_rot_v_x = 0.0f;
	static float m_rot_v_y = 0.0f;
	static float m_rot_v_z = 5.0f;
	static float m_rot_r_x = 0.0f;
	static float m_rot_r_y = 0.0f;
	static float m_rot_r_z = 0.0f;

	static Color3f blau = new Color3f(0f, 0f, 1f);
	static Color3f schwarz = new Color3f(0f, 0f, 0f);
	static Color3f weiss = new Color3f(1f, 1f, 1f);
	static Color3f gelb = new Color3f(1f, 1f, 0f);
	static float glanz = 60f;
	static Material mat01 = new Material(blau, schwarz, blau, blau, glanz);
	static Material mat02 = new Material(gelb, schwarz, gelb, gelb, glanz);
	static Material mat03 = new Material(schwarz, schwarz, schwarz, schwarz,
			glanz);

	Wait myWait01 = new Wait();

	JTabbedPane TP01 = new JTabbedPane();
	JPanel P01 = new JPanel();
	JPanel P02 = new JPanel();
	JPanel P03 = new JPanel();
	JPanel P04 = new JPanel();
	JPanel P05 = new JPanel();

	ButtonGroup buGroup = new ButtonGroup();
	JRadioButton RB01 = new JRadioButton();
	JRadioButton RB02 = new JRadioButton();
	JRadioButton RB03 = new JRadioButton();
	JRadioButton RB04 = new JRadioButton();
	JRadioButton RB05 = new JRadioButton();
	JRadioButton RB06 = new JRadioButton();
	JRadioButton RB07 = new JRadioButton();

	ButtonGroup buGroup02 = new ButtonGroup();
	JRadioButton RB08 = new JRadioButton();
	JRadioButton RB09 = new JRadioButton();

	ButtonGroup buGroup03 = new ButtonGroup();
	JRadioButton RB10 = new JRadioButton();
	JRadioButton RB11 = new JRadioButton();

	JButton B01 = new JButton();
	JButton B02 = new JButton();
	JButton B03 = new JButton();

	JTextField TF01 = new JTextField();
	JTextField TF02 = new JTextField();
	JTextField TF03 = new JTextField();
	JTextField TF04 = new JTextField();
	JTextField TF05 = new JTextField();
	JTextField TF06 = new JTextField();

	JLabel L01 = new JLabel();
	JLabel L02 = new JLabel();
	JLabel L03 = new JLabel();
	JLabel L04 = new JLabel();
	JLabel L05 = new JLabel();
	JLabel L06 = new JLabel();
	JLabel L07 = new JLabel();
	JLabel L08 = new JLabel();
	JLabel L09 = new JLabel();
	JLabel L10 = new JLabel();
	JLabel L11 = new JLabel();
	JLabel L12 = new JLabel();
	JLabel L13 = new JLabel();
	JLabel L14 = new JLabel();

	JCheckBox CB01 = new JCheckBox();
	JCheckBox CB02 = new JCheckBox();
	JCheckBox CB03 = new JCheckBox();
	JCheckBox CB04 = new JCheckBox();

	JCheckBox CB05 = new JCheckBox();

	JSlider S01 = new JSlider();
	JSlider S02 = new JSlider();
	JSlider S03 = new JSlider();

	JSlider S04 = new JSlider();
	JSlider S05 = new JSlider();
	JSlider S06 = new JSlider();

	ImageComponent2D Hintergrund_B01;
	ImageComponent2D Hintergrund_B02;
	ImageComponent2D Hintergrund_B03;
	ImageComponent2D Hintergrund_B04;
	ImageComponent2D Hintergrund_B05;
	ImageComponent2D Hintergrund_B06;
	ImageComponent2D Hintergrund_B07;

	static Appearance App_Erde = new Appearance();
	static Appearance App_Mond = new Appearance();
	static Appearance App_Mars = new Appearance();
	static Appearance Durchsichtig = new Appearance();
	static Appearance backgroundApp;

	static Canvas3D c;

	Cursor CDefault = new Cursor(DEFAULT_CURSOR);
	int[] pixels = new int[16 * 16];
	Image image = Toolkit.getDefaultToolkit()
			.createImage(new MemoryImageSource(16, 16, pixels, 0, 16));
	Cursor CInvisible = Toolkit.getDefaultToolkit().createCustomCursor(image,
			new Point(0, 0), "InvisibleCursor");

	public Universe() {
		Texturen_und_Bilder_laden();
		initGUI();
	}

	public static void main(final String[] args) {
		Universe_u = new Universe();
	}

	public void initGUI() {
		System.out.println("Initialisiere 3D-Welt...");

		getContentPane().setLayout(null);
		final GraphicsConfiguration CG = SimpleUniverse
				.getPreferredConfiguration();
		myWait01.progress(70);
		c = new Canvas3D(CG);
		c.setDoubleBufferEnable(true);
		c.addMouseListener(new Mausbetritt());
		c.addKeyListener(new Key_Pressed());
		c.addMouseMotionListener(new Mausbewegung());
		c.setBounds(449, 0, 512, 512);
		getContentPane().add(c);
		u = new SimpleUniverse(c);
		myWait01.progress(80);
		u.getViewingPlatform().setNominalViewingTransform();
		myWait01.progress(85);
		u.addBranchGraph(Scene_generieren());
		myWait01.progress(90);

		// Frame definieren
		setTitle("Universum");
		setSize(960, 548);
		setResizable(false);

		// Panel Konfiguration
		P01.setLayout(null);
		P02.setLayout(null);
		P03.setLayout(null);
		P04.setLayout(null);
		P05.setLayout(null);

		// RadioButtons Konfiguration
		final RadioButtonActionAdapter rbaa = new RadioButtonActionAdapter(
				this);

		RB01.setText("Galaxy 1");
		RB01.setActionCommand("rb01");
		RB01.setBounds(2, 10, 96, 22);
		RB01.setSelected(false);
		RB01.addActionListener(rbaa);
		RB01.setOpaque(false);

		RB02.setText("Galaxy 2");
		RB02.setActionCommand("rb02");
		RB02.setBounds(2, 32, 96, 22);
		RB02.setSelected(false);
		RB02.addActionListener(rbaa);
		RB02.setOpaque(false);

		RB03.setText("Galaxy 3");
		RB03.setActionCommand("rb03");
		RB03.setBounds(2, 54, 96, 22);
		RB03.setSelected(true);
		RB03.addActionListener(rbaa);
		RB03.setOpaque(false);

		RB04.setText("Galaxy 4");
		RB04.setActionCommand("rb04");
		RB04.setBounds(2, 76, 96, 22);
		RB04.setSelected(false);
		RB04.addActionListener(rbaa);
		RB04.setOpaque(false);

		RB05.setText("Galaxy 5");
		RB05.setActionCommand("rb05");
		RB05.setBounds(2, 98, 96, 22);
		RB05.setSelected(false);
		RB05.addActionListener(rbaa);
		RB05.setOpaque(false);

		RB06.setText("Galaxy 6");
		RB06.setActionCommand("rb06");
		RB06.setBounds(2, 120, 96, 22);
		RB06.setSelected(false);
		RB06.addActionListener(rbaa);
		RB06.setOpaque(false);

		RB07.setText("Galaxy 7");
		RB07.setActionCommand("rb07");
		RB07.setBounds(2, 142, 96, 22);
		RB07.setSelected(false);
		RB07.addActionListener(rbaa);
		RB07.setOpaque(false);

		RB08.setText("Farbe DL");
		RB08.setActionCommand("rb08");
		RB08.setBounds(2, 250, 96, 22);
		RB08.setSelected(true);
		RB08.addActionListener(rbaa);
		RB08.setOpaque(false);

		RB09.setText("Farbe AL");
		RB09.setActionCommand("rb09");
		RB09.setBounds(2, 272, 96, 22);
		RB09.setSelected(false);
		RB09.addActionListener(rbaa);
		RB09.setOpaque(false);

		RB10.setText("Texturierung");
		RB10.setActionCommand("rb09");
		RB10.setBounds(2, 200, 96, 22);
		RB10.setSelected(true);
		RB10.addActionListener(rbaa);
		RB10.setOpaque(false);

		RB11.setText("Solid");
		RB11.setActionCommand("rb09");
		RB11.setBounds(2, 222, 96, 22);
		RB11.setSelected(false);
		RB11.addActionListener(rbaa);
		RB11.setOpaque(false);

		buGroup.add(RB01);
		buGroup.add(RB02);
		buGroup.add(RB03);
		buGroup.add(RB04);
		buGroup.add(RB05);
		buGroup.add(RB06);
		buGroup.add(RB07);

		buGroup02.add(RB08);
		buGroup02.add(RB09);

		buGroup03.add(RB10);
		buGroup03.add(RB11);

		P01.add(RB01);
		P01.add(RB02);
		P01.add(RB03);
		P01.add(RB04);
		P01.add(RB05);
		P01.add(RB06);
		P01.add(RB07);

		P03.add(RB08);
		P03.add(RB09);

		P04.add(RB10);
		P04.add(RB11);

		// Buttons Konfiguration
		final ImageButtonActionAdapter baa = new ImageButtonActionAdapter(this);

		B01.setText("Pause");
		B01.setActionCommand("b01");
		B01.setBounds(102, 142, 90, 22);
		B01.addActionListener(baa);
		B01.setOpaque(false);

		B02.setText("Übernehmen");
		B02.setActionCommand("b02");
		B02.setBounds(2, 250, 90, 22);
		B02.addActionListener(baa);
		B02.setOpaque(false);

		B03.setText("Startsicht");
		B03.setActionCommand("b03");
		B03.setBounds(102, 174, 90, 22);
		B03.addActionListener(baa);
		B03.setOpaque(false);

		P05.add(B01);
		P02.add(B02);
		P05.add(B03);

		myWait01.progress(95);

		// TextFelder Konfiguration
		String temp = "";

		temp = String.valueOf(m_rot_v_x);
		TF01.setText(temp);
		TF01.setBounds(42, 170, 50, 20);
		TF01.setOpaque(false);

		temp = String.valueOf(m_rot_v_y);
		TF02.setText(temp);
		TF02.setBounds(42, 192, 50, 20);
		TF02.setOpaque(false);

		temp = String.valueOf(m_rot_v_z);
		TF03.setText(temp);
		TF03.setBounds(42, 214, 50, 20);
		TF03.setOpaque(false);

		temp = String.valueOf(m_rot_r_x);
		TF04.setText(temp);
		TF04.setBounds(142, 170, 50, 20);
		TF04.setOpaque(false);

		temp = String.valueOf(m_rot_r_y);
		TF05.setText(temp);
		TF05.setBounds(142, 192, 50, 20);
		TF05.setOpaque(false);

		temp = String.valueOf(m_rot_r_z);
		TF06.setText(temp);
		TF06.setBounds(142, 214, 50, 20);
		TF06.setOpaque(false);

		P02.add(TF01);
		P02.add(TF02);
		P02.add(TF03);
		P02.add(TF04);
		P02.add(TF05);
		P02.add(TF06);

		// Labels Konfiguration
		L01.setText("Vec X :");
		L01.setBounds(2, 170, 38, 20);
		L01.setOpaque(false);

		L02.setText("Vec Y :");
		L02.setBounds(2, 192, 38, 20);
		L02.setOpaque(false);

		L03.setText("Vec Z :");
		L03.setBounds(2, 214, 38, 20);
		L03.setOpaque(false);

		L04.setText("Rot X :");
		L04.setBounds(102, 170, 38, 20);
		L04.setOpaque(false);

		L05.setText("Rot Y :");
		L05.setBounds(102, 192, 38, 20);
		L05.setOpaque(false);

		L06.setText("Rot Z :");
		L06.setBounds(102, 214, 38, 20);
		L06.setOpaque(false);

		L07.setText(Float.toString(DL01_C_r));
		L07.setBounds(12, 422, 50, 20);
		L07.setOpaque(false);
		L07.setBackground(Color.white);

		L08.setText(Float.toString(DL01_C_g));
		L08.setBounds(75, 422, 50, 20);
		L08.setOpaque(false);
		L08.setBackground(Color.white);

		L09.setText(Float.toString(DL01_C_b));
		L09.setBounds(138, 422, 50, 20);
		L09.setOpaque(false);
		L09.setBackground(Color.white);

		L10.setText(Float.toString(BG01_C_r));
		L10.setBounds(202, 138, 50, 20);
		L10.setOpaque(false);
		L10.setBackground(Color.white);
		L10.setEnabled(false);

		L11.setText(Float.toString(BG01_C_g));
		L11.setBounds(262, 138, 50, 20);
		L11.setOpaque(false);
		L11.setBackground(Color.white);
		L11.setEnabled(false);

		L12.setText(Float.toString(BG01_C_b));
		L12.setBounds(322, 138, 50, 20);
		L12.setOpaque(false);
		L12.setBackground(Color.white);
		L12.setEnabled(false);

		L13.setText(Integer.toString(Maus_Position_X));
		L13.setBounds(2, 300, 50, 20);
		L13.setOpaque(false);
		L13.setBackground(Color.white);
		L13.setEnabled(false);

		L14.setText(Integer.toString(Maus_Position_Y));
		L14.setBounds(54, 300, 50, 20);
		L14.setOpaque(false);
		L14.setBackground(Color.white);
		L14.setEnabled(false);

		P02.add(L01);
		P02.add(L02);
		P02.add(L03);
		P02.add(L04);
		P02.add(L05);
		P02.add(L06);

		P03.add(L07);
		P03.add(L08);
		P03.add(L09);

		P01.add(L10);
		P01.add(L11);
		P01.add(L12);
		P01.add(L13);
		P01.add(L14);

		// CheckBoxes Konfiguration
		final CheckBoxActionAdapter cbaa = new CheckBoxActionAdapter(this);

		/*
		 * CB01.setText("Erde"); CB01.setActionCommand("cb01"); CB01.setSelected(true); CB01.setBounds(102,10,96,22);
		 * CB01.addActionListener(cbaa); CB01.setOpaque(false); CB02.setText("Mond"); CB02.setActionCommand("cb02");
		 * CB02.setSelected(true); CB02.setBounds(102,32,96,22); CB02.addActionListener(cbaa); CB02.setOpaque(false);
		 */

		CB03.setText("DL ein");
		CB03.setActionCommand("cb03");
		CB03.setSelected(true);
		CB03.setBounds(102, 54, 96, 22);
		CB03.addActionListener(cbaa);
		CB03.setOpaque(false);

		CB04.setText("AL ein");
		CB04.setActionCommand("cb04");
		CB04.setSelected(true);
		CB04.setBounds(102, 76, 96, 22);
		CB04.addActionListener(cbaa);
		CB04.setOpaque(false);

		CB05.setText("Hintergrundbild");
		CB05.setActionCommand("cb05");
		CB05.setSelected(true);
		CB05.setBounds(102, 10, 96, 22);
		CB05.addActionListener(cbaa);
		CB05.setOpaque(false);

		P04.add(CB01);
		P04.add(CB02);
		P04.add(CB03);
		P04.add(CB04);
		P01.add(CB05);

		// Slider Konfiguration

		final SliderListener01 saa01 = new SliderListener01(this);
		final SliderListener02 saa02 = new SliderListener02(this);
		final SliderListener03 saa03 = new SliderListener03(this);
		final SliderListener04 saa04 = new SliderListener04(this);
		final SliderListener05 saa05 = new SliderListener05(this);
		final SliderListener06 saa06 = new SliderListener06(this);

		S01.setOrientation(JSlider.VERTICAL);
		S01.setMaximum(255);
		S01.setMajorTickSpacing(50);
		S01.setPaintTicks(true);
		S01.setPaintLabels(true);
		S01.setMinorTickSpacing(10);
		S01.setValue((int) (DL01_C_r * 255));
		S01.setBounds(12, 300, 50, 120);
		S01.addChangeListener(saa01);

		S02.setOrientation(JSlider.VERTICAL);
		S02.setMaximum(255);
		S02.setMajorTickSpacing(50);
		S02.setPaintTicks(true);
		S02.setPaintLabels(true);
		S02.setMinorTickSpacing(10);
		S02.setValue((int) (DL01_C_r * 255));
		S02.setBounds(75, 300, 50, 120);
		S02.addChangeListener(saa02);

		S03.setOrientation(JSlider.VERTICAL);
		S03.setMaximum(255);
		S03.setMajorTickSpacing(50);
		S03.setPaintTicks(true);
		S03.setPaintLabels(true);
		S03.setMinorTickSpacing(10);
		S03.setValue((int) (DL01_C_r * 255));
		S03.setBounds(138, 300, 50, 120);
		S03.addChangeListener(saa03);

		S04.setOrientation(JSlider.VERTICAL);
		S04.setMaximum(255);
		S04.setMajorTickSpacing(50);
		S04.setPaintTicks(true);
		S04.setPaintLabels(true);
		S04.setMinorTickSpacing(10);
		S04.setValue((int) (BG01_C_r * 255));
		S04.setBounds(202, 10, 50, 120);
		S04.addChangeListener(saa04);
		S04.setEnabled(false);

		S05.setOrientation(JSlider.VERTICAL);
		S05.setMaximum(255);
		S05.setMajorTickSpacing(50);
		S05.setPaintTicks(true);
		S05.setPaintLabels(true);
		S05.setMinorTickSpacing(10);
		S05.setValue((int) (BG01_C_g * 255));
		S05.setBounds(262, 10, 50, 120);
		S05.addChangeListener(saa05);
		S05.setEnabled(false);

		S06.setOrientation(JSlider.VERTICAL);
		S06.setMaximum(255);
		S06.setMajorTickSpacing(50);
		S06.setPaintTicks(true);
		S06.setPaintLabels(true);
		S06.setMinorTickSpacing(10);
		S06.setValue((int) (BG01_C_b * 255));
		S06.setBounds(322, 10, 50, 120);
		S06.addChangeListener(saa06);
		S06.setEnabled(false);

		P03.add(S01);
		P03.add(S02);
		P03.add(S03);

		P01.add(S04);
		P01.add(S05);
		P01.add(S06);

		// TabPanel Konfiguration
		TP01.setBounds(0, 0, 456, 508);
		TP01.addTab("Hintergrund", P01);
		TP01.addTab("Rotations-Achse", P02);
		TP01.addTab("Licht", P03);
		TP01.addTab("Darstellung", P04);
		TP01.addTab("Steuerung", P05);

		getContentPane().add(TP01);

		addKeyListener(new Key_Pressed());
		setVisible(true);

		System.out.println("fertig...");
		myWait01.progress(100);
	}

	public void Texturen_und_Bilder_laden() {
		Hintergrund_B01 = loadImage("galaxy01.jpg");
		myWait01.progress(5);
		Hintergrund_B02 = loadImage("galaxy02.jpg");
		myWait01.progress(10);
		Hintergrund_B03 = loadImage("galaxy05.jpg");
		myWait01.progress(15);
		Hintergrund_B04 = loadImage("galaxy06.jpg");
		myWait01.progress(20);
		Hintergrund_B05 = loadImage("galaxy07.jpg");
		myWait01.progress(25);
		Hintergrund_B06 = loadImage("galaxy08.jpg");
		myWait01.progress(30);
		Hintergrund_B07 = loadImage("galaxy09.jpg");
		myWait01.progress(35);

		Zaehler_Bild = 0;

		/*
		 * Textur_laden("galaxy05.jpg",backgroundApp); PolygonAttributes pa = new PolygonAttributes();
		 * pa.setCullFace(PolygonAttributes.CULL_NONE); app_Universum_K.setPolygonAttributes(pa);
		 */
		myWait01.progress(45);
		backgroundApp = getAppearance("earth01.jpg", false);
		myWait01.progress(45);
		App_Mond = getAppearance("lunar01.jpg", false);
		myWait01.progress(50);
		App_Mars = getAppearance("mars01.jpg", false);
		myWait01.progress(55);

		Zaehler_Bild = 0;
	}

	void radioButtonActionPerformed(final ActionEvent e) {

		final String cmd = e.getActionCommand();

		if ("rb01".equals(cmd)) {
			bg.setImage(Hintergrund_B01);
			backgroundApp = getAppearance("galaxy01.jpg", false);

			// System.out.println("1");
		} else if ("rb02".equals(cmd)) {
			bg.setImage(Hintergrund_B02);
			backgroundApp = getAppearance("galaxy02.jpg", false);

			// System.out.println("2");
		} else if ("rb03".equals(cmd)) {
			bg.setImage(Hintergrund_B03);
			backgroundApp = getAppearance("galaxy05.jpg", false);

			// System.out.println("3");
		} else if ("rb04".equals(cmd)) {
			bg.setImage(Hintergrund_B04);
			backgroundApp = getAppearance("galaxy06.jpg", false);

			// System.out.println("4");
		} else if ("rb05".equals(cmd)) {
			bg.setImage(Hintergrund_B05);
			backgroundApp = getAppearance("galaxy07.jpg", false);

			// System.out.println("5");
		} else if ("rb06".equals(cmd)) {
			bg.setImage(Hintergrund_B06);
			backgroundApp = getAppearance("galaxy08.jpg", false);

			// System.out.println("6");
		} else if ("rb07".equals(cmd)) {
			bg.setImage(Hintergrund_B07);
			backgroundApp = getAppearance("galaxy09.jpg", false);

			// System.out.println("7");
		} else if ("rb08".equals(cmd)) {
			BuGr02_selected_RB = 1;
			S01.setValue((int) (DL01_C_r * 255));
			S02.setValue((int) (DL01_C_g * 255));
			S03.setValue((int) (DL01_C_b * 255));
		} else if ("rb09".equals(cmd)) {
			BuGr02_selected_RB = 2;
			S01.setValue((int) (AL01_C_r * 255));
			S02.setValue((int) (AL01_C_g * 255));
			S03.setValue((int) (AL01_C_b * 255));
		}

		repaint();

	}

	void ButtonActionPerformed(final ActionEvent e) {
		final String cmd = e.getActionCommand();

		if ("b01".equals(cmd)) {
			if (Pause == false) {
				B01.setText("Fortsetzen");
				Pause = true;
				a.pause();
				a1.pause();
			} else {
				B01.setText("Pause");
				Pause = false;
				a.resume();
				a1.resume();
			}
		} else if ("b02".equals(cmd)) {
			m_rot_v_x = Float.parseFloat(TF01.getText());
			m_rot_v_y = Float.parseFloat(TF02.getText());
			m_rot_v_z = Float.parseFloat(TF03.getText());
			m_rot_r_x = Float.parseFloat(TF04.getText());
			m_rot_r_y = Float.parseFloat(TF05.getText());
			m_rot_r_z = Float.parseFloat(TF06.getText());

			Mond_V_Rot = new Vector3d(m_rot_v_x, m_rot_v_y, m_rot_v_z);
			Mond_Rot = new Transform3D();
			Mond_Rot_t = new Transform3D();
			Mond_Rot.rotX(m_rot_r_x);
			Mond_Rot_t.rotY(m_rot_r_y);
			Mond_Rot.mul(Mond_Rot, Mond_Rot_t);
			Mond_Rot_t.rotZ(m_rot_r_z);
			Mond_Rot.mul(Mond_Rot, Mond_Rot_t);
			Mond_Rot_t.setTranslation(Mond_V_Rot);
			Mond_Rot.mul(Mond_Rot_t, Mond_Rot);
			Erde_Rotation.setAxisOfRotation(Mond_Rot);
		} else if ("b03".equals(cmd)) {
			final TransformGroup Pos_TG = vp.getViewPlatformTransform();
			final Transform3D Pos_t3d = new Transform3D();
			Pos_t3d.setTranslation(new Vector3d(0.0, 0.0, 10.0));
			Pos_TG.setTransform(Pos_t3d);
		}
		repaint();
	}

	void checkBoxAction(final ActionEvent e) {
		final String cmd = e.getActionCommand();

		/*
		 * if("cb01".equals(cmd)) { if (CB01.isSelected()==true) { Erde_K.setAppearance(App_Erde); a1.resume(); } else {
		 * a1.resume(); Erde_K.setAppearance(Durchsichtig); } } else if("cb02".equals(cmd)) { if
		 * (CB02.isSelected()==true) { Mond_K.setAppearance(App_Mond); a.resume(); } else { a.pause();
		 * Mond_K.setAppearance(Durchsichtig); } }
		 */
		if ("cb03".equals(cmd)) {
			if (CB03.isSelected() == true) {
				al.setEnable(true);
			} else {
				al.setEnable(false);
			}
		} else if ("cb04".equals(cmd)) {
			if (CB04.isSelected() == true) {
				Licht.setEnable(true);
			} else {
				Licht.setEnable(false);
			}
		} else if ("cb05".equals(cmd)) {
			if (CB05.isSelected() == true) {
				RB01.setEnabled(true);
				RB02.setEnabled(true);
				RB03.setEnabled(true);
				RB04.setEnabled(true);
				RB05.setEnabled(true);
				RB06.setEnabled(true);
				RB07.setEnabled(true);

				bg = new Background();
				bg.setImage(Hintergrund_B01);

				S04.setEnabled(false);
				S05.setEnabled(false);
				S06.setEnabled(false);

				L10.setEnabled(false);
				L11.setEnabled(false);
				L12.setEnabled(false);
			} else {
				RB01.setEnabled(false);
				RB02.setEnabled(false);
				RB03.setEnabled(false);
				RB04.setEnabled(false);
				RB05.setEnabled(false);
				RB06.setEnabled(false);
				RB07.setEnabled(false);

				bg = new Background(BG01_C_r, BG01_C_g, BG01_C_b);

				S04.setEnabled(true);
				S05.setEnabled(true);
				S06.setEnabled(true);

				L10.setEnabled(true);
				L11.setEnabled(true);
				L12.setEnabled(true);
			}
		}
		repaint();
	}

	void SliderAction01(final ChangeEvent e) {
		final float value = S01.getValue();
		switch (BuGr02_selected_RB) {
		case 1: {
			DL01_C_r = value / 255;
			L07.setText(Float.toString(DL01_C_r));
			Licht.setColor(new Color3f(DL01_C_r, DL01_C_g, DL01_C_b));
			break;
		}
		case 2: {
			AL01_C_r = value / 255;
			L07.setText(Float.toString(AL01_C_r));
			al.setColor(new Color3f(AL01_C_r, AL01_C_g, AL01_C_b));
			break;
		}
		}
	}

	void SliderAction02(final ChangeEvent e) {
		final float value = S02.getValue();
		switch (BuGr02_selected_RB) {
		case 1: {
			DL01_C_g = value / 255;
			L08.setText(Float.toString(DL01_C_g));
			Licht.setColor(new Color3f(DL01_C_r, DL01_C_g, DL01_C_b));
			break;
		}
		case 2: {
			AL01_C_g = value / 255;
			L08.setText(Float.toString(AL01_C_g));
			al.setColor(new Color3f(AL01_C_r, AL01_C_g, AL01_C_b));
			break;
		}
		}
	}

	void SliderAction03(final ChangeEvent e) {
		final float value = S03.getValue();
		switch (BuGr02_selected_RB) {
		case 1: {
			DL01_C_b = value / 255;
			L09.setText(Float.toString(DL01_C_b));
			Licht.setColor(new Color3f(DL01_C_r, DL01_C_g, DL01_C_b));
			break;
		}
		case 2: {
			AL01_C_b = value / 255;
			L09.setText(Float.toString(AL01_C_b));
			al.setColor(new Color3f(AL01_C_r, AL01_C_g, AL01_C_b));
			break;
		}
		}
	}

	void SliderAction04(final ChangeEvent e) {
		final float value = S04.getValue();

		BG01_C_r = value / 255;
		L10.setText(Float.toString(BG01_C_r));
		bg.setColor(BG01_C_r, BG01_C_g, BG01_C_b);
	}

	void SliderAction05(final ChangeEvent e) {
		final float value = S05.getValue();

		BG01_C_g = value / 255;
		L11.setText(Float.toString(BG01_C_g));
		bg.setColor(BG01_C_r, BG01_C_g, BG01_C_b);
	}

	void SliderAction06(final ChangeEvent e) {
		final float value = S06.getValue();

		BG01_C_b = value / 255;
		L12.setText(Float.toString(BG01_C_b));
		bg.setColor(BG01_C_r, BG01_C_g, BG01_C_b);
	}

	private Appearance getAppearance(final String relativePathToTextureFile,
			final boolean isTransparent) {
		final Appearance returnValue = new Appearance();
		final String absolutePathToTexture = getClass().getClassLoader()
				.getResource(PARENT_PATH_GRAPHICS + relativePathToTextureFile)
				.getPath();
		System.out.println("Loading texture: " + absolutePathToTexture);

		final TextureLoader loader = new TextureLoader(absolutePathToTexture,
				null);

		final Texture texture = loader.getTexture();

		if (isTransparent) {
			final TransparencyAttributes tra = new TransparencyAttributes();
			tra.setTransparencyMode(TransparencyAttributes.BLENDED);
			tra.setTransparency(0f);
			returnValue.setTransparencyAttributes(tra);
		}
		final PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);
		returnValue.setPolygonAttributes(pa);

		final TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);

		returnValue.setTextureAttributes(texAttr);

		final Material m = new Material(weiss, schwarz, weiss, schwarz, 1.0f);
		m.setLightingEnable(true);
		returnValue.setMaterial(m);

		returnValue.setTexture(texture);
		return returnValue;
	}

	public ImageComponent2D loadImage(final String relativePathToImageFile) {
		TextureLoader textureLoader;
		ImageComponent2D returnValue;
		final String absolutePathToTexture = getAbsolutePathToReourceFile(
				relativePathToImageFile);
		System.out.println("Loading image: " + absolutePathToTexture);

		textureLoader = new TextureLoader(absolutePathToTexture, beobachter);
		returnValue = textureLoader.getImage();

		try {
			Thread.sleep(wz);
		} catch (final InterruptedException e) {
			throw new UnsupportedOperationException(e);
		}

		if (returnValue == null) {
			System.out.println(
					"  " + absolutePathToTexture + " could not be loaded...");
		}
		return returnValue;
	}

	private String getAbsolutePathToReourceFile(
			final String relativePathToResourceFile) {
		final String returnValue = getClass().getClassLoader()
				.getResource(PARENT_PATH_GRAPHICS + relativePathToResourceFile)
				.getPath();
		return returnValue;
	}

	private PlatformGeometry createHeadupDisplay() {

		// Adds a 'Head up Display' image to the platform geometry

		final PlatformGeometry pg = new PlatformGeometry();
		final Point3f p1 = new Point3f(-0.3f, -0.3f, -0.7f);
		final Point3f p2 = new Point3f(0.3f, -0.3f, -0.7f);
		final Point3f p3 = new Point3f(0.3f, 0.3f, -0.7f);
		final Point3f p4 = new Point3f(-0.3f, 0.3f, -0.7f);
		final String absolutePathToHeadupDisplayImage = getAbsolutePathToReourceFile(
				HEADUP_DISPLAY_IMAGE);
		System.out.println("Loading Headup Display Image: "
				+ absolutePathToHeadupDisplayImage);
		final TexturedPlane tp = new TexturedPlane(p1, p2, p3, p4,
				absolutePathToHeadupDisplayImage);
		pg.addChild(tp);
		return pg;
	}

	public BranchGroup Scene_generieren() {
		final BranchGroup Ursprung = new BranchGroup();

		Universum = new TransformGroup();
		Universum.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Universum.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		Ursprung.addChild(Universum);

		Begrenzung = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 4000.0);

		// Hintergrund:
		bg = new Background();
		bg.setApplicationBounds(Begrenzung);
		final BranchGroup backGeoBranch = new BranchGroup();
		final Sphere sphereObj = new Sphere(1.0f,
				Sphere.GENERATE_NORMALS | Sphere.GENERATE_NORMALS_INWARD
						| Sphere.GENERATE_TEXTURE_COORDS,
				45);
		backgroundApp = sphereObj.getAppearance();
		backgroundApp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
		backgroundApp.setCapability(Appearance.ALLOW_TEXTURE_READ);
		backGeoBranch.addChild(sphereObj);
		bg.setGeometry(backGeoBranch);
		Universum.addChild(bg);

		backgroundApp = getAppearance("galaxy05.jpg", false);

		bg.setCapability(Background.ALLOW_IMAGE_WRITE);
		bg.setCapability(Background.ALLOW_IMAGE_READ);
		bg.setCapability(Background.ALLOW_COLOR_READ);
		bg.setCapability(Background.ALLOW_COLOR_WRITE);

		final Color3f Farbe_Licht = new Color3f(DL01_C_r, DL01_C_g, DL01_C_b);
		final Vector3f Richtung_Licht = new Vector3f(-1.0f, -1.0f, -1.0f);
		Licht = new DirectionalLight(Farbe_Licht, Richtung_Licht);
		Licht.setCapability(DirectionalLight.ALLOW_COLOR_READ);
		Licht.setCapability(DirectionalLight.ALLOW_COLOR_WRITE);
		Licht.setCapability(DirectionalLight.ALLOW_STATE_READ);
		Licht.setCapability(DirectionalLight.ALLOW_STATE_WRITE);
		Licht.setInfluencingBounds(Begrenzung);
		Universum.addChild(Licht);

		// Noch ein Licht:
		al = new AmbientLight(new Color3f(AL01_C_r, AL01_C_g, AL01_C_b));
		al.setCapability(AmbientLight.ALLOW_COLOR_READ);
		al.setCapability(AmbientLight.ALLOW_COLOR_WRITE);
		al.setCapability(DirectionalLight.ALLOW_STATE_READ);
		al.setCapability(DirectionalLight.ALLOW_STATE_WRITE);
		al.setInfluencingBounds(Begrenzung);
		Universum.addChild(al);

		// allgemein Konfigurationen
		final TransparencyAttributes ta = new TransparencyAttributes();
		ta.setTransparencyMode(TransparencyAttributes.NICEST);
		ta.setTransparency(1.0f);
		Durchsichtig.setTransparencyAttributes(ta);

		// Transformationsgruppen
		// Anfang

		final Color3f blau = new Color3f(0f, 0f, 1f);
		final Color3f schwarz = new Color3f(0f, 0f, 0f);
		final Color3f weiss = new Color3f(1f, 1f, 1f);
		final Color3f gelb = new Color3f(1f, 1f, 0f);
		final float glanz = 60f;

		Erde_TG = new TransformGroup();
		Mond_TG = new TransformGroup();
		Mond_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Mond_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Erde_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Erde_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		Mars_TG = new TransformGroup();
		Mars_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Mars_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		final Transform3D Erde_T3D = new Transform3D();
		final Transform3D Mond_T3D = new Transform3D();
		Mars_T3D = new Transform3D();

		final Material Mat_Erde = new Material(blau, schwarz, blau, blau,
				glanz);
		final Material Mat_Mond = new Material(gelb, schwarz, gelb, gelb,
				glanz);

		final double Erde_rot_Pos;
		final double Mond_rot_Pos;

		final Planet Merkur = new Planet(1.0f, Planet.MERKUR, "merkur01.jpg");
		Universum.addChild(Merkur);
		final Planet Pluto = new Planet(1.0f, Planet.PLUTO, "pluto01.jpg");
		Universum.addChild(Pluto);
		final Planet Mars = new Planet(1.0f, Planet.MARS, "mars01.jpg");
		Universum.addChild(Mars);
		final Planet Venus = new Planet(1.0f, Planet.VENUS, "venus01.jpg");
		Universum.addChild(Venus);
		final Planet Erde = new Planet(1.0f, Planet.ERDE, "earth01.jpg");
		Universum.addChild(Erde);
		final Planet Neptun = new Planet(1.0f, Planet.NEPTUN, "neptun01.jpg");
		Universum.addChild(Neptun);
		final Planet Uranus = new Planet(1.0f, Planet.URANUS, "uranus01.jpg");
		Universum.addChild(Uranus);
		final Planet Saturn = new Planet(1.0f, Planet.SATURN, "saturn01.jpg");
		Universum.addChild(Saturn);
		final Planet Jupiter = new Planet(1.0f, Planet.JUPITER,
				"jupiter02.jpg");
		Universum.addChild(Jupiter);

		// Planet Sonne = new Planet (1.0f,Planet.SONNE,"sun01.jpg");
		// Universum.addChild (Sonne);

		final Object_3D OrbitalStation = new Object_3D("STATION2.3DS", 0.5);
		OrbitalStation.move_to(Erde.getX() + 1.5, 0.0, Erde.getZ());
		Universum.addChild(OrbitalStation);

		/*
		 * Object_3D DISCOVA = new Object_3D ("DISCOVA.3DS",0.1); //DISCOVA.scale_all(0.01); DISCOVA.rotate(0.0,0,0.0);
		 * DISCOVA.move_to(Erde.getX()+1.5,0.05,Erde.getZ()+0.325) ; Universum.addChild (DISCOVA);
		 */

		final Object_3D spacesta = new Object_3D("spacest.3ds", 0.3);
		// spacesta.rotate(0.0,0,90.0);
		spacesta.move_to(Erde.getX() - 1.5, 0.0, Erde.getZ());
		Universum.addChild(spacesta);

		// sehr Speicherlastig - also ggf. überarbeiten ;)
		final AsteroidField AsteroidField01 = new AsteroidField(20, 1.0);
		AsteroidField01.move_to(-3.0, 4.0, -10.0);
		Universum.addChild(AsteroidField01);

		// Transform3D tt= new Transform3D();
		// tt.rotY(-Math.PI/2);
		// t_invader.setTransform(tt);
		T_Squadron = new TransformGroup();
		T_Squadron.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		T_Squadron.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		final Object_3D t_Invader = new Object_3D("T-INVADR.3DS", 0.075);
		T_Squadron.addChild(t_Invader);

		final Object_3D t_Fighter01 = new Object_3D("TIEFIGHT.3DS", 1.0);
		t_Fighter01.move_to(-0.25, 0.0, -0.5);
		T_Squadron.addChild(t_Fighter01);

		final Object_3D t_Fighter02 = new Object_3D("TIEFIGHT.3DS", 1.0);
		t_Fighter02.move_to(-0.25, 0.0, 0.5);
		T_Squadron.addChild(t_Fighter02);

		Universum.addChild(T_Squadron);

		final Object_3D t_Fighter03 = new Object_3D("TIEFIGHT.3DS", 1.0);
		t_Fighter03.rotate(0.0, 90.0, 0.0);
		t_Fighter03.move_to(0.0, 0.0, 5.0);
		Universum.addChild(t_Fighter03);

		vp = u.getViewingPlatform();
		final PlatformGeometry pg = createHeadupDisplay();
		vp.setPlatformGeometry(pg);

		// Start-Position festlegen
		final TransformGroup Pos_TG = vp.getViewPlatformTransform();
		final Transform3D Pos_t3d = new Transform3D();
		Pos_t3d.setTranslation(new Vector3d(0.0, 0.0, 10.0));
		Pos_TG.setTransform(Pos_t3d);

		// Interaktionsmöglichkeit festlegen
		IB = new InteraktBehavior(Universe_u, Universum);
		IB.setSchedulingBounds(Begrenzung);
		vp.setViewPlatformBehavior(IB);

		// Tortenstueck t1 = new Tortenstueck (30.0f,1.0f,1.0f,10);
		// Universum.addChild(t1);
		// k = new Koordinatensystem (10.0);
		// Universum.addChild(k);
		// ENDE

		// Rotation
		// Anfang

		Mond_V_Rot = new Vector3d(m_rot_v_x, m_rot_v_y, m_rot_v_z);

		Mond_Rot = new Transform3D();
		/*
		 * Mond_Rot_t = new Transform3D(); Mond_Rot.rotX(0.0f); Mond_Rot_t.setTranslation(Mond_V_Rot);
		 * Mond_Rot.mul(Mond_Rot_t,Mond_Rot);
		 */
		Mond_Rot.setTranslation(Mond_V_Rot);

		Erde_V_Rot = new Vector3d(0.0f, 0.0f, 4.5f);

		Erde_Rot = new Transform3D();
		/*
		 * Erde_Rot_t = new Transform3D(); Erde_Rot.rotX(Math.PI/4); Erde_Rot_t.set(Erde_V_Rot);
		 * Erde_Rot.mul(Erde_Rot_t,Erde_Rot);
		 */
		Erde_Rot.setTranslation(Erde_V_Rot);

		a1 = new Alpha(-1, 20000);
		Erde_Rotation = new RotationInterpolator(a1, T_Squadron, Mond_Rot,
				(float) Math.PI * 2.0f, 0.0f);
		Erde_Rotation.setSchedulingBounds(Begrenzung);
		Universum.addChild(Erde_Rotation);

		a = new Alpha(-1, 20000);

		final float[] Knoten = { 0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f,
				0.8f, 0.9f, 1.0f };
		final Point3f[] p = { new Point3f(0.0f, 0.0f, 0.0f),
				new Point3f(1.0f, 0.0f, 0.0f), new Point3f(2.0f, 0.0f, -4.0f),
				new Point3f(4.0f, 0.0f, -3.0f), new Point3f(3.0f, 0.0f, 0.0f),
				new Point3f(1.0f, 0.0f, 4.0f), new Point3f(0.0f, 0.0f, 5.0f),
				new Point3f(-2.0f, 0.0f, 6.0f), new Point3f(-2.0f, 0.0f, 4.0f),
				new Point3f(-1.0f, 0.0f, 3.0f), new Point3f(0.0f, 0.0f, 2.0f) };

		final Quat4f[] q = { new Quat4f(1.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(2.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(4.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(0.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(0.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(0.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(0.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(0.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(0.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(0.0f, 0.0f, 0.0f, 0.0f),
				new Quat4f(0.0f, 0.0f, 0.0f, 0.0f) };

		Path01 = new RotPosPathInterpolator(a, t_Fighter03, Erde_Rot, Knoten, q,
				p);
		Path01.setSchedulingBounds(Begrenzung);
		Universum.addChild(Path01);
		/*
		 * Mond_Rotation = new RotationInterpolator (a,t_Fighter,Erde_Rot,(float)Math.PI*2.0f,0.0f);
		 * Mond_Rotation.setSchedulingBounds(Begrenzung); Universum.addChild (Mond_Rotation); /*
		 * Mond_TG.addChild(Mond_Rotation); Mond_T3D.set(Mond_V); Mond_TG.setTransform(Mond_T3D);
		 */
		// ENDE

		Ursprung.compile();
		return Ursprung;
	}

	class RadioButtonActionAdapter implements ActionListener {
		Universe obj;

		RadioButtonActionAdapter(final Universe obj) {
			this.obj = obj;
		}

		public void actionPerformed(final ActionEvent e) {
			obj.radioButtonActionPerformed(e);
		}
	}

	class ImageButtonActionAdapter implements ActionListener {
		Universe obj;

		ImageButtonActionAdapter(final Universe obj) {
			this.obj = obj;
		}

		public void actionPerformed(final ActionEvent e) {
			obj.ButtonActionPerformed(e);
		}
	}

	class CheckBoxActionAdapter implements ActionListener {
		Universe obj;

		CheckBoxActionAdapter(final Universe obj) {
			this.obj = obj;
		}

		public void actionPerformed(final ActionEvent e) {
			obj.checkBoxAction(e);
		}
	}

	class SliderListener01 implements ChangeListener {
		Universe obj;

		public SliderListener01(final Universe obj) {
			this.obj = obj;
		}

		public void stateChanged(final ChangeEvent e) {
			obj.SliderAction01(e);
		}
	}

	class SliderListener02 implements ChangeListener {
		Universe obj;

		public SliderListener02(final Universe obj) {
			this.obj = obj;
		}

		public void stateChanged(final ChangeEvent e) {
			obj.SliderAction02(e);
		}
	}

	class SliderListener03 implements ChangeListener {
		Universe obj;

		public SliderListener03(final Universe obj) {
			this.obj = obj;
		}

		public void stateChanged(final ChangeEvent e) {
			obj.SliderAction03(e);
		}
	}

	class SliderListener04 implements ChangeListener {
		Universe obj;

		public SliderListener04(final Universe obj) {
			this.obj = obj;
		}

		public void stateChanged(final ChangeEvent e) {
			obj.SliderAction04(e);
		}
	}

	class SliderListener05 implements ChangeListener {
		Universe obj;

		public SliderListener05(final Universe obj) {
			this.obj = obj;
		}

		public void stateChanged(final ChangeEvent e) {
			obj.SliderAction05(e);
		}
	}

	class SliderListener06 implements ChangeListener {
		Universe obj;

		public SliderListener06(final Universe obj) {
			this.obj = obj;
		}

		public void stateChanged(final ChangeEvent e) {
			obj.SliderAction06(e);
		}
	}

	class Wait extends JFrame {
		JProgressBar ProgressBar = new JProgressBar();
		JLabel laufzeitLabel = new JLabel();

		Wait() {
			getContentPane().setLayout(null);
			setTitle("Bitte warten...");
			setSize(320, 110);
			setResizable(false);

			ProgressBar.setMaximum(100);
			ProgressBar.setForeground(new Color(0, 0, 113));
			ProgressBar.setBounds(20, 40, 274, 20);
			ProgressBar.setStringPainted(true);

			laufzeitLabel.setText("Fortschritt in %");
			laufzeitLabel.setBounds(22, 20, 123, 15);

			getContentPane().add(ProgressBar);
			getContentPane().add(laufzeitLabel);

			setVisible(true);
		}

		public void progress(final int i) {
			ProgressBar.setValue(i);

			if (i == 100) {
				dispose();
			}
		}
	}

	class Mausbetritt extends MouseAdapter {
		@Override
		public void mouseEntered(final MouseEvent event) {
			// Universe.c.setCursor(CInvisible);
			Maus_in_Canvas3D = true;
			// ESC_pressed = false;
			// IB.setESC_pressed (false);
			Universe.c.requestFocus();
		}

		@Override
		public void mouseExited(final MouseEvent event) {
			// Universe.c.setCursor(CDefault);
			Maus_in_Canvas3D = false;
			Universe.c.transferFocus();
		}
	}

	class Key_Pressed extends KeyAdapter {

		@Override
		public void keyPressed(final KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ESCAPE
					&& Maus_in_Canvas3D == true) {
				Universe.c.setCursor(CDefault);
				ESC_pressed = true;
				IB.setESC_pressed(true);
			}
		}

		@Override
		public void keyReleased(final KeyEvent event) {

		}
	}

	class Mausbewegung extends MouseMotionAdapter {

		@Override
		public void mouseMoved(final MouseEvent event) {
			if (event.getX() >= Universe.c.getWidth() / 2 - 10
					&& event.getX() <= Universe.c.getWidth() / 2 + 10
					&& event.getY() >= Universe.c.getHeight() / 2 - 10
					&& event.getY() <= Universe.c.getHeight() / 2 + 10) {
				Universe.c.setCursor(CInvisible);
				ESC_pressed = false;
				IB.setESC_pressed(false);
			}

		}

		@Override
		public void mouseDragged(final MouseEvent event) {
			mouseMoved(event);
		}

	}
}
