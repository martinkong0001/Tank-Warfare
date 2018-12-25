
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

class Explosion implements Runnable
{
	
	private double x_coord;
	private double y_coord;
	private double x_size;
	private double y_size;
	private int duration = 15;
	private int timePassed = 0;
	
	public Explosion(double x_coord, double y_coord, double x_size, double y_size)
	{
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.x_size = x_size;
		this.y_size = y_size;
	}
	
	public void run()
	{
		while (timePassed <= duration)
		{
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			timePassed ++;
		}
	}
	
	public double getExplosionPhase()
	{
		return (double)timePassed / duration;
	}
	
	public void draw(Graphics g, Image image)
	{
		AffineTransform at = new AffineTransform();
		at.translate(x_coord - x_size / 2, y_coord - y_size / 2);
		at.scale(x_size / image.getWidth(null), y_size / image.getHeight(null));
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, at, null);
	}
	
}
