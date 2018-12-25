
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Collision
{
	
	public static boolean bulletTank(Bullet bullet, Tank tank)
	{
		Area aBullet = bullet.getArea();
		Area aTank= tank.getArea();
		aTank.intersect(aBullet);
		if (aTank.equals(aBullet) == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean tankBoundary(Tank tank)
	{
		Rectangle2D.Double rect = new Rectangle2D.Double
				(0, 0, GamePanel.X_SIZE, GamePanel.Y_SIZE);
		Area aBoundary = new Area(rect);
		Area aTank = tank.getArea();
		aTank.subtract(aBoundary);
		if (aTank.isEmpty() == false)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
