package com.katjes.universe.graphics;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

import com.sun.j3d.utils.image.TextureLoader;

public class TexturedPlane extends Shape3D {

	private static final int NUM_VERTS = 4;

	public TexturedPlane(final Point3f p1, final Point3f p2, final Point3f p3,
			final Point3f p4, final String fnm) {
		createGeometry(p1, p2, p3, p4);
		createAppearance(fnm);
	} // end of TexturedPlane()

	private void createGeometry(final Point3f p1, final Point3f p2,
			final Point3f p3, final Point3f p4) {
		final QuadArray plane = new QuadArray(NUM_VERTS,
				GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);

		// anti-clockwise from bottom left
		plane.setCoordinate(0, p1);
		plane.setCoordinate(1, p2);
		plane.setCoordinate(2, p3);
		plane.setCoordinate(3, p4);

		final TexCoord2f q = new TexCoord2f();
		q.set(0.0f, 0.0f);
		plane.setTextureCoordinate(0, 0, q);
		q.set(1.0f, 0.0f);
		plane.setTextureCoordinate(0, 1, q);
		q.set(1.0f, 1.0f);
		plane.setTextureCoordinate(0, 2, q);
		q.set(0.0f, 1.0f);
		plane.setTextureCoordinate(0, 3, q);

		setGeometry(plane);
	} // end of createGeometry()

	private void createAppearance(final String fnm) {

		final TextureLoader loader = new TextureLoader(fnm, null);
		final ImageComponent2D im = loader.getImage();
		if (im == null) {
			System.out.println("Load failed for texture: " + fnm);
		} else {
			final Appearance app = new Appearance();

			// blended transparency so texture can be irregular
			final TransparencyAttributes tra = new TransparencyAttributes();
			tra.setTransparencyMode(TransparencyAttributes.BLENDED);
			app.setTransparencyAttributes(tra);

			// Create a two dimensional texture
			// Set the texture from the first loaded image
			final Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL,
					Texture.RGBA, im.getWidth(), im.getHeight());
			texture.setImage(0, im);
			app.setTexture(texture);

			setAppearance(app);
		}
	} // end of createAppearance()

} // end of TexturedPlane class
