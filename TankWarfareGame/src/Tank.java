
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

class Tank
{
	
	private double x_coord;
	private double y_coord;
	private double direction;
	private double speed;
	private int health;
	private int totalHealth;
	private boolean existence;
	
	public ArrayList<Bullet> bullets;
	private long lastShootingTime;

	public static final int X_SIZE = 80;
	public static final int Y_SIZE = 114;
	public static final int GUN_BARREL_LENGTH = 14;
	
	public Tank(double x_coord, double y_coord, double direction, double speed, int health)
	{
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.direction = direction;
		this.speed = speed;
		this.health = health;
		totalHealth = health;
		existence = true;
		bullets = new ArrayList<Bullet>();
	}
	
	public void moveForward()
	{
		x_coord += Math.sin(Math.toRadians(direction)) * speed;
		y_coord -= Math.cos(Math.toRadians(direction)) * speed;
	}
	
	public void moveBackward()
	{
		x_coord -= Math.sin(Math.toRadians(direction)) * speed;
		y_coord += Math.cos(Math.toRadians(direction)) * speed;
	}
	
	public void turnLeft()
	{
		direction -= speed;
		direction = (direction + 360) % 360;
	}
	
	public void turnRight()
	{
		direction += speed;
		direction = direction % 360;
	}
	
	public void shootBullet()
	{
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastShootingTime > 300)
		{
			double hypo = Y_SIZE / 2 + Bullet.Y_SIZE / 2;
			double x = x_coord + Math.sin(Math.toRadians(direction)) * hypo;
			double y = y_coord - Math.cos(Math.toRadians(direction)) * hypo;
			Bullet bullet = new Bullet(x, y, direction, 15);
			Thread thread = new Thread(bullet);
			thread.start();
			bullets.add(bullet);
			lastShootingTime = System.currentTimeMillis();
		}
	}
	
	public boolean getAttacked()
	{
		health --;
		if (health <= 0)
		{
			existence = false;
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g, Image image)
	{
		AffineTransform at = new AffineTransform();
		at.translate(x_coord - X_SIZE / 2, y_coord - Y_SIZE / 2);
		at.rotate(Math.toRadians(direction), X_SIZE / 2, Y_SIZE / 2);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, at, null);
	}
	
	public void drawHealthBar(Graphics g)
	{
		Color originalColor = g.getColor();
		int x = (int) (x_coord - X_SIZE / 2);
		int y = (int) (y_coord - Y_SIZE / 2) - 15;
		int length = (int) ((double) health / totalHealth * X_SIZE);
		g.setColor(Color.BLACK);
		g.draw3DRect(x, y, X_SIZE, 10, true);
		g.setColor(Color.RED);
		g.fill3DRect(x, y, length, 10, true);
		if (health != totalHealth)
		{
			g.setColor(Color.LIGHT_GRAY);
			g.fill3DRect(x + length, y, X_SIZE - length, 10, true);
		}
		g.setColor(originalColor);
	}
	
	public Area getArea()
	{
		AffineTransform at = new AffineTransform();
		at.translate(x_coord - X_SIZE / 2, y_coord - Y_SIZE / 2);
		at.rotate(Math.toRadians(direction), X_SIZE / 2, Y_SIZE / 2);
		Rectangle2D.Double rect = new Rectangle2D.Double();
		rect.setRect(0, 0, X_SIZE, Y_SIZE);
		Shape shapeWhole = at.createTransformedShape(rect);
		Area areaWhole = new Area(shapeWhole);
		rect.setRect(0, 0, X_SIZE, GUN_BARREL_LENGTH);
		Shape shapeGunBarrel = at.createTransformedShape(rect);
		Area areaGunBarrel = new Area(shapeGunBarrel);
		areaWhole.subtract(areaGunBarrel);
		return areaWhole;
	}
	
	public double getX()
	{
		return x_coord;
	}

	public double getY()
	{
		return y_coord;
	}
	
	public double getDirection()
	{
		return direction;
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
	public int getHealth()
	{
		return health;
	}

	public boolean getExistence()
	{
		return existence;
	}
	
}
