package graphics.shadows;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import entity.Entity;
import entity.Player;
import graphics.TileMap;
import main.GamePanel;

public class Shadows {

	private static Image shadowMap;
	private static Image lightMap;
	private static Point lightPos;

	private static Image lightImage;

	public static void calc(GameContainer gc, Graphics g) {
		try {
			if (lightImage == null)
				lightImage = new Image("res/sprites/light.png");

			if (shadowMap == null)
				shadowMap = new Image(GamePanel.WIDTH, GamePanel.HEIGHT);

			Graphics shadowG = shadowMap.getGraphics();

			shadowG.setColor(Color.white);
			shadowG.fillRect(0, 0, shadowMap.getWidth(), shadowMap.getHeight());

			shadowG.setColor(Color.black);

			walls.clear();

			for (int x = 0; x < TileMap.width; x++) {
				for (int y = 0; y < TileMap.width; y++) {
					if (TileMap.instance.isSolid(x, y)) {
						shadowG.fillRect(x, y, 1, 1);
					}
				}
			}

			for (Entity e : Entity.getEntities()) {
				shadowG.fillRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
				if (!(e instanceof Player))
					walls.add(new Wall(e.getX(), e.getY(), e.getWidth(), e.getHeight()));
			}

			// g.drawImage(shadowMap, 0, 0);

			if (lightMap == null)
				lightMap = new Image(GamePanel.WIDTH, GamePanel.HEIGHT);

			Graphics lightG = lightMap.getGraphics();

			Color ambient = new Color(0, 0, 0, 220);
			lightG.setColor(ambient);
			lightG.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());

			if (Player.instance != null) {
				lightPos = new Point(Player.instance.getX() + Player.instance.getWidth() / 2,
						Player.instance.getY() + Player.instance.getHeight() / 2);

				rayPointX = lightPos.x;
				rayPointY = lightPos.y;

				g.setLineWidth(1f);
				if (singleBar) {
					rayCast(rayPointX, rayPointY, goX, goY, g, stepLimit, gc);
				} else {
					for (float diffx = -1F; diffx < 1F; diffx += resol) {
						for (float diffy = -1; diffy < 1F; diffy += resol) {
							if (diffx < -0.9F || diffx > 0.9F || diffy < -0.9F || diffy > 0.9F) {

								lightG.setColor(Color.white);
								PointF p = rayCast(rayPointX, rayPointY, diffx, diffy, g, stepLimit, gc);
								lightG.setColor(Color.cyan);
								lightG.fillRect(p.x, p.y, 1, 1);

								/*
								 * if (gradient) { g.drawGradientLine(rayPointX,
								 * rayPointY, new Color(255, 245, 25, 255),
								 * fx.x, fx.y, new Color(255, 245, 25, 0));
								 * }else{ g.drawLine(rayPointX, rayPointY, fx.x,
								 * fx.y); }
								 */
							}
						}
					}
				}

				lightImage.setAlpha(254);
				int width = (int) (lightImage.getWidth() * 1.5);
				int height = (int) (lightImage.getHeight() * 1.5);
				lightG.drawImage(lightImage, lightPos.x - width, lightPos.y - height, lightPos.x + width,
						lightPos.y + height, 0, 0, lightImage.getWidth(), lightImage.getHeight());
			}

			g.drawImage(lightMap, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// copy-paste

	public static List<Wall> walls = new ArrayList<Wall>();

	static float rayPointX = 0F;
	static float rayPointY = 0F;
	static float alpha = 96F;

	static class Wall {
		float x;
		float y;
		float w;
		float h;

		public Wall(float x, float y, float w, float h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

	}

	static class PointF {
		public float x;
		public float y;

		public PointF(float x, float y) {
			this.x = x;
			this.y = y;
		}

	}

	public static boolean bounceOff = false;
	public static float resol = 0.1F;

	public static PointF rayCast(float startx, float starty, float diffx, float diffy, Graphics g, int steplimit,
			GameContainer gc) {
		float dx = startx;
		float dy = starty;
		float latx = startx;
		float laty = starty;
		boolean dox = true;
		int step = 0;
		for (Wall w : walls) {
			if (dx > w.x && dx < w.x + w.w && dy > w.y && dy < w.y + w.h) {
				dox = false;
				break;
			}
		}
		if (dox) {
			while (true) {
				step++;
				if (step > steplimit) {
					if (!(infiniteBounce))
						break;
				}
				float lx = dx;
				dx += diffx;
				if (bounceOff) {
					for (Wall w : walls) {
						if (dx > w.x && dx < w.x + w.w && dy > w.y && dy < w.y + w.h) {
							dx = lx;
							diffx = diffx * -1;
							if (gradient) {
								g.drawGradientLine(latx, laty, new Color(255, 0, 0, 255), dx, dy,
										new Color(255, 0, 0, 0));
							} else {
								g.drawLine(latx, laty, dx, dy);
							}
							latx = dx;
							laty = dy;
							break;
						}
					}
				}

				float ly = dy;
				dy += diffy;
				if (bounceOff) {
					for (Wall w : walls) {
						if (dx > w.x && dx < w.x + w.w && dy > w.y && dy < w.y + w.h) {
							dy = ly;
							diffy = diffy * -1;
							if (gradient) {
								g.drawGradientLine(latx, laty, new Color(255, 0, 0, 255), dx, dy,
										new Color(255, 0, 0, 0));
							} else {
								g.drawLine(latx, laty, dx, dy);
							}
							latx = dx;
							laty = dy;
							break;
						}
					}
				}
				if (!(bounceOff)) {
					boolean outerbreak = false;
					for (Wall w : walls) {
						if (dx > w.x && dx < w.x + w.w && dy > w.y && dy < w.y + w.h) {
							outerbreak = true;
							break;
						}
					}
					if (outerbreak)
						break;
				}

				if (dx > gc.getWidth())
					break;
				if (dx < 0)
					break;
				if (dy > gc.getHeight())
					break;
				if (dy < 0)
					break;
			}
			if (gradient) {
				g.drawGradientLine(latx, laty, new Color(255, 0, 0, 255), dx, dy, new Color(255, 0, 0, 0));
			} else {
				g.drawLine(latx, laty, dx, dy);
			}
		}

		return new PointF(dx, dy);
	}

	static boolean renderPlat = true;

	static boolean dragging = false;
	static int startx = 0;
	static int starty = 0;
	static int endx = 0;
	static int endy = 0;

	public static boolean gradient = false;

	public static int stepLimit = 10000;

	public static boolean infiniteBounce = false;

	public static boolean singleBar = false;

	public static float goX = 0F;
	public static float goY = 0F;

}
