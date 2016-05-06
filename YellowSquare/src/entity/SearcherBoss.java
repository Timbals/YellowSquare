package entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import graphics.Sprite;
import maths.Vector2D;

public class SearcherBoss extends BossEntity {

	private Image image;

	public SearcherBoss(int x, int y) {
		super(x, y, 16, 16, 20);
		image = Sprite.get("searcher.png");
	}

	@Override
	public void tick(long delta) {
		velocity = new Vector2D(distanceX(Player.instance.getX()), distanceY(Player.instance.getY())).normalise()
				.multiply(0.5);
		calculatePosition();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0, 0, image.getWidth(),
				image.getHeight());
	}

	@Override
	protected boolean onBulletHit(boolean isPlayer) {
		health--;
		b.remove();

		if (health <= 0)
			return true;

		if (health <= maxHealth / 2) {
			SearcherBossPart part1 = new SearcherBossPart(getX() + getWidth() / 2, getY() + getHeight() / 2, width / 2,
					image);
			SearcherBossPart part2 = new SearcherBossPart(getX() + getWidth() / 2, getY() + getHeight() / 2, width / 2,
					image);
			part1.setVelocity(new Vector2D(-3, 0));
			part2.setVelocity(new Vector2D(3, 0));
		}

		return false;
	}

	class SearcherBossPart extends Entity {

		private Image image;

		public SearcherBossPart(int x, int y, int size, Image image) {
			super(x, y, size, size);
			this.image = image;
		}

		@Override
		public void tick(long delta) {
			velocity = velocity.add(new Vector2D(distanceX(Player.instance.getX()), distanceY(Player.instance.getY()))
					.normalise().multiply(0.01)).normalise().multiply(0.5);
			calculatePosition();
		}

		@Override
		public void render(Graphics g) {
			g.drawImage(this.image, getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0, 0,
					this.image.getWidth(), this.image.getHeight());
			g.setColor(Color.cyan);
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}

		@Override
		protected boolean onBulletHit(boolean isPlayer) {
			return true;
		}

	}

}
