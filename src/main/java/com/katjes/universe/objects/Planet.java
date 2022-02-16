package com.katjes.universe.objects;

import java.awt.Component;
import java.util.Random;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class Planet extends BranchGroup {
	private static TransformGroup Planet_TG;
	private static Transform3D Planet_T3D;
	private static Appearance Planet_app;
	private static Sphere Planet_K;
	private static BoundingSphere Bound;

	private static Random r;
	/*
	 * private static int r_Merkur = r.nextInt(360); private static int r_Pluto = r.nextInt(360); private static int
	 * r_Mars = r.nextInt(360); private static int r_Venus = r.nextInt(360); private static int r_Erde = r.nextInt(360);
	 * private static int r_Neptun = r.nextInt(360); private static int r_Uranus = r.nextInt(360); private static int
	 * r_Saturn = r.nextInt(360); private static int r_Jupiter = r.nextInt(360); private static int r_Sonne =
	 * r.nextInt(360);
	 */

	private static float r_Merkur = 0.0f;
	private static float r_Pluto = 40.0f;
	private static float r_Mars = 80.0f;
	private static float r_Venus = 120.0f;
	private static float r_Erde = 160.0f;
	private static float r_Neptun = 200.0f;
	private static float r_Uranus = 240.0f;
	private static float r_Saturn = 280.0f;
	private static float r_Jupiter = 320.0f;
	private static float r_Sonne = 0.0f;

	private static Component beobachter;

	private static final String PARENT_PATH_GRAPHICS = "graphics/";

	private static final Color3f GELB = new Color3f(1.0f, 1.0f, 0.0f); // Sonne
	private static final Color3f SCHWARZ = new Color3f(0.0f, 0.0f, 0.0f); // naja...
	private static final Color3f ORANGE = new Color3f(1.0f, 0.4f, 0.0f); // Venus
	private static final Color3f WUESTENBLAU = new Color3f(0.2f, 0.4f, 0.6f); // Neptun
	private static final Color3f HIMMELBLAU = new Color3f(0.0f, 0.8f, 1.0f); // Uranus
	private static final Color3f DUNKELGELB = new Color3f(1.0f, 0.8f, 0.0f); // Saturn
	private static final Color3f BRAUN = new Color3f(0.6f, 0.4f, 0.2f); // Jupiter,Pluto
	private static final Color3f GOLD = new Color3f(0.8f, 0.6f, 0.2f); // Mars
	private static final Color3f GRAU = new Color3f(0.7f, 0.7f, 0.7f); // Merkur
	private static final Color3f BLAU = new Color3f(0.0f, 0.0f, 1.0f); // Erde
	private static final Color3f WEISS = new Color3f(1.0f, 1.0f, 1.0f);

	// Durchmesser der einzelnen Planeten relativ zur Erde
	public static final float MERKUR = 0.38f;
	public static final float PLUTO = 0.46f;
	public static final float MARS = 0.53f;
	public static final float VENUS = 0.97f;
	public static final float ERDE = 1.00f;
	public static final float NEPTUN = 3.50f;
	public static final float URANUS = 3.73f;
	public static final float SATURN = 9.47f;
	public static final float JUPITER = 11.20f;
	public static final float SONNE = 109.0f;

	// Radien der einzelnen Planeten - feste Werte
	private static final float R_MERKUR = 10.0f;
	private static final float R_PLUTO = 45.0f;
	private static final float R_MARS = 25.0f;
	private static final float R_VENUS = 15.0f;
	private static final float R_ERDE = 20.0f;
	private static final float R_NEPTUN = 42.0f;
	private static final float R_URANUS = 40.0f;
	private static final float R_SATURN = 35.0f;
	private static final float R_JUPITER = 30.0f;
	private static final float R_SONNE = 0.0f;

	// Position der einzelnen Planeten relativ zur Sonne
	public static final Vector3d P_MERKUR = new Vector3d(
			Math.cos(r_Merkur / 180 * Math.PI) * R_MERKUR, 0.0f,
			-Math.sin(r_Merkur / 180 * Math.PI) * R_MERKUR);
	public static final Vector3d P_PLUTO = new Vector3d(
			Math.cos(r_Pluto / 180 * Math.PI) * R_PLUTO, 0.0f,
			-Math.sin(r_Pluto / 180 * Math.PI) * R_PLUTO);
	public static final Vector3d P_MARS = new Vector3d(
			Math.cos(r_Mars / 180 * Math.PI) * R_MARS, 0.0f,
			-Math.sin(r_Mars / 180 * Math.PI) * R_MARS);
	public static final Vector3d P_VENUS = new Vector3d(
			Math.cos(r_Venus / 180 * Math.PI) * R_VENUS, 0.0f,
			-Math.sin(r_Venus / 180 * Math.PI) * R_VENUS);
	public static final Vector3d P_ERDE = new Vector3d(
			Math.cos(r_Erde / 180 * Math.PI) * R_ERDE, 0.0f,
			-Math.sin(r_Erde / 180 * Math.PI) * R_ERDE);
	public static final Vector3d P_NEPTUN = new Vector3d(
			Math.cos(r_Neptun / 180 * Math.PI) * R_NEPTUN, 0.0f,
			-Math.sin(r_Neptun / 180 * Math.PI) * R_NEPTUN);
	public static final Vector3d P_URANUS = new Vector3d(
			Math.cos(r_Uranus / 180 * Math.PI) * R_URANUS, 0.0f,
			-Math.sin(r_Uranus / 180 * Math.PI) * R_URANUS);
	public static final Vector3d P_SATURN = new Vector3d(
			Math.cos(r_Saturn / 180 * Math.PI) * R_SATURN, 0.0f,
			-Math.sin(r_Saturn / 180 * Math.PI) * R_SATURN);
	public static final Vector3d P_JUPITER = new Vector3d(
			Math.cos(r_Jupiter / 180 * Math.PI) * R_JUPITER, 0.0f,
			-Math.sin(r_Jupiter / 180 * Math.PI) * R_JUPITER);
	public static final Vector3d P_SONNE = new Vector3d(
			Math.cos(r_Sonne / 180 * Math.PI) * R_SONNE, 0.0f,
			-Math.sin(r_Sonne / 180 * Math.PI) * R_SONNE);
	public static final Vector3d P_STD = new Vector3d(17.0f, 0.0f, -17.0f);

	// Materialeigenschaften
	private static final float GLANZ = 100.0f;

	private static final Material mat_Std = new Material(WEISS, SCHWARZ, WEISS,
			WEISS, GLANZ);
	private static final Material mat_Merkur = new Material(GRAU, SCHWARZ, GRAU,
			WEISS, GLANZ);
	private static final Material mat_Pluto = new Material(BRAUN, SCHWARZ,
			BRAUN, WEISS, GLANZ);
	private static final Material mat_Mars = new Material(GOLD, SCHWARZ, GOLD,
			WEISS, GLANZ);
	private static final Material mat_Venus = new Material(ORANGE, SCHWARZ,
			ORANGE, WEISS, GLANZ);
	private static final Material mat_Erde = new Material(BLAU, SCHWARZ, BLAU,
			WEISS, GLANZ);
	private static final Material mat_Neptun = new Material(WUESTENBLAU,
			SCHWARZ, WUESTENBLAU, WEISS, GLANZ);
	private static final Material mat_Uranus = new Material(HIMMELBLAU, SCHWARZ,
			HIMMELBLAU, WEISS, GLANZ);
	private static final Material mat_Saturn = new Material(DUNKELGELB, SCHWARZ,
			DUNKELGELB, WEISS, GLANZ);
	private static final Material mat_Jupiter = new Material(BRAUN, SCHWARZ,
			BRAUN, WEISS, GLANZ);
	private static final Material mat_Sonne = new Material(SCHWARZ, GELB,
			SCHWARZ, SCHWARZ, GLANZ);

	private double x, y, z;

	public Planet(final float Erddurchmesser, final float P_Durchmesser_Faktor,
			final String P_Textur, final Vector3d P_Position) {
		make_Planet(Erddurchmesser, P_Durchmesser_Faktor, P_Textur, P_Position);
	}

	public Planet(final float Erddurchmesser, final float P_Durchmesser_Faktor,
			final String P_Textur) {
		make_Planet(Erddurchmesser, P_Durchmesser_Faktor, P_Textur,
				Vergleich_vec(P_Durchmesser_Faktor, Erddurchmesser));
	}

	public static TransformGroup getTG() {
		return Planet_TG;
	}

	private void make_Planet(final float Erddurchmesser,
			final float P_Durchmesser_Faktor, final String P_Textur,
			final Vector3d P_Position) {
		Planet_TG = new TransformGroup();
		Planet_T3D = new Transform3D();
		Planet_app = new Appearance();

		addChild(Planet_TG);
		Planet_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Planet_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		final Material mat = Vergleich_mat(P_Durchmesser_Faktor);
		Planet_app = getAppearance(P_Textur, false);
		Planet_app.setMaterial(mat);

		Planet_K = new Sphere(
				(float) Math.sqrt(Erddurchmesser * P_Durchmesser_Faktor / 2),
				Primitive.GENERATE_TEXTURE_COORDS
						+ Primitive.ENABLE_APPEARANCE_MODIFY,
				50, Planet_app);
		Planet_K.setCollisionBounds(Bound);
		Planet_K.setCollidable(true);
		Planet_K.setCapability(Sphere.ENABLE_COLLISION_REPORTING);
		Planet_K.setCapability(Sphere.ALLOW_COLLISION_BOUNDS_READ);
		Planet_K.setCapability(Sphere.ALLOW_COLLISION_BOUNDS_WRITE);

		Planet_TG.addChild(Planet_K);
		Planet_T3D.set(P_Position);
		Planet_TG.setTransform(Planet_T3D);

	}

	private static Material Vergleich_mat(final float P_Durchmesser_Faktor) {
		Material mat;

		if (P_Durchmesser_Faktor == MERKUR) {
			mat = mat_Merkur;
		} else if (P_Durchmesser_Faktor == PLUTO) {
			mat = mat_Pluto;
		} else if (P_Durchmesser_Faktor == MARS) {
			mat = mat_Mars;
		} else if (P_Durchmesser_Faktor == VENUS) {
			mat = mat_Venus;
		} else if (P_Durchmesser_Faktor == ERDE) {
			mat = mat_Erde;
		} else if (P_Durchmesser_Faktor == NEPTUN) {
			mat = mat_Neptun;
		} else if (P_Durchmesser_Faktor == URANUS) {
			mat = mat_Uranus;
		} else if (P_Durchmesser_Faktor == SATURN) {
			mat = mat_Saturn;
		} else if (P_Durchmesser_Faktor == JUPITER) {
			mat = mat_Jupiter;
		} else if (P_Durchmesser_Faktor == SONNE) {
			mat = mat_Sonne;
		} else {
			mat = mat_Std;
		}

		return mat;
	}

	private Vector3d Vergleich_vec(final float P_Durchmesser_Faktor,
			final float Erddurchmesser) {
		Vector3d vec;
		r = new Random();
		final int ri = r.nextInt(360);

		if (P_Durchmesser_Faktor == MERKUR) {
			x = Math.cos((float) ri / 180 * Math.PI) * 10.0;
			z = -Math.sin((float) ri / 180 * Math.PI) * 10.0;
		} else if (P_Durchmesser_Faktor == PLUTO) {
			x = Math.cos((float) ri / 180 * Math.PI) * 45.0;
			z = -Math.sin((float) ri / 180 * Math.PI) * 45.0;
		} else if (P_Durchmesser_Faktor == MARS) {
			x = Math.cos((float) ri / 180 * Math.PI) * 25.0;
			z = -Math.sin((float) ri / 180 * Math.PI) * 25.0;
		} else if (P_Durchmesser_Faktor == VENUS) {
			x = Math.cos((float) ri / 180 * Math.PI) * R_VENUS;
			z = -Math.sin((float) ri / 180 * Math.PI) * R_VENUS;
		} else if (P_Durchmesser_Faktor == ERDE) {
			x = Math.cos((float) ri / 180 * Math.PI) * R_ERDE;
			z = -Math.sin((float) ri / 180 * Math.PI) * R_ERDE;
			System.out.println("Erde x=" + x + " ; z=" + z);
		} else if (P_Durchmesser_Faktor == NEPTUN) {
			x = Math.cos((float) ri / 180 * Math.PI) * R_NEPTUN;
			z = -Math.sin((float) ri / 180 * Math.PI) * R_NEPTUN;
		} else if (P_Durchmesser_Faktor == URANUS) {
			x = Math.cos((float) ri / 180 * Math.PI) * R_URANUS;
			z = -Math.sin((float) ri / 180 * Math.PI) * R_URANUS;
		} else if (P_Durchmesser_Faktor == SATURN) {
			x = Math.cos((float) ri / 180 * Math.PI) * R_SATURN;
			z = -Math.sin((float) ri / 180 * Math.PI) * R_SATURN;
		} else if (P_Durchmesser_Faktor == JUPITER) {
			x = Math.cos((float) ri / 180 * Math.PI) * R_JUPITER;
			z = -Math.sin((float) ri / 180 * Math.PI) * R_JUPITER;
			System.out.println("Jupiter x=" + x + " ; z=" + z);
		} else if (P_Durchmesser_Faktor == SONNE) {
			x = Math.cos((float) ri / 180 * Math.PI) * R_SONNE;
			z = -Math.sin((float) ri / 180 * Math.PI) * R_SONNE;
		} else {
			x = 17.0;
			z = -17.0;
		}

		vec = new Vector3d(x, 0.0, z);
		Bound = new BoundingSphere(new Point3d(x, 0.0, z),
				Math.sqrt(Erddurchmesser * P_Durchmesser_Faktor / 2));

		return vec;
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

		final Material m = new Material(WEISS, SCHWARZ, WEISS, SCHWARZ, 1.0f);
		m.setLightingEnable(true);
		returnValue.setMaterial(m);

		returnValue.setTexture(texture);
		return returnValue;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}
}
