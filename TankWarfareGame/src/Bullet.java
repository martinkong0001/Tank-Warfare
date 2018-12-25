
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

class Bullet implements Runnable
{
	
	private double x_coord;
	private double y_coord;
	private double direction;
	private double speed;
	private boolean existence;
	
	public static final int X_SIZE = 11;
	public static final int Y_SIZE = 18;
	
	public Bullet(double x_coord, double y_coord, double direction, double speed)
	{
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.direction = direction;
		this.speed = speed;
		existence = true;
	}
	
	public void run()
	{
		while (getExistence() == true)
		{
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
				
			x_coord += Math.sin(Math.toRadians(direction)) * speed;
			y_coord -= Math.cos(Math.toRadians(direction)) * speed;
				
			if (x_coord < - 100 || x_coord > GamePanel.X_SIZE + 100
					|| y_coord < - 100 || y_coord > GamePanel.Y_SIZE + 100)
			{
				existence = false;
			}
		}
	}
	
	public void destroyBullet()
	{
		existence = false;
	}
	
	public void draw(Graphics g, Image image)
	{
		AffineTransform at = new AffineTransform();
		at.translate(x_coord - X_SIZE / 2, y_coord - Y_SIZE / 2);
		at.rotate(Math.toRadians(direction), X_SIZE / 2, Y_SIZE / 2);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, at, null);
	}
	
	public Area getArea()
	{
		AffineTransform at = new AffineTransform();
		at.translate(x_coord - X_SIZE / 2, y_coord - Y_SIZE / 2);
		at.rotate(Math.toRadians(direction), X_SIZE / 2, Y_SIZE / 2);
		Rectangle2D.Double rect = new Rectangle2D.Double();
		rect.setRect(0, 0, X_SIZE, Y_SIZE);
		Shape shape = at.createTransformedShape(rect);
		return new Area(shape);
	}
	
	public double getX()
	{
		return x_coord;
	}

	public double getY()
	{
		return y_coord;
	}

	public boolean getExistence()
	{
		return existence;
	}
	
}
