
class EnemyTank extends Tank implements Runnable
{
	
	public EnemyTank(double x_coord, double y_coord, double direction, double speed, int health)
	{
		super(x_coord, y_coord, direction, speed, health);
	}
	
	public void run()
	{
		boolean stuckMode = false;
		
		outmostloop:
		while (getExistence() == true)
		{
			if (stuckMode == true)
			{
				double x = getX() - GamePanel.X_SIZE / 2;
				double y = getY() - GamePanel.Y_SIZE / 2;
				double theta = Math.toDegrees(Math.atan2(y, x)) + 270;
				double direction = getDirection();
				double turningAngle = (theta - direction + 720) % 360;
				int turns = (int) (turningAngle / getSpeed());
				for (int i = 0; i < turns; i++)
				{
					try
					{
						Thread.sleep(50);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					turnRight();
				}
				for (int i = 0; i < 50; i++)
				{
					try
					{
						Thread.sleep(50);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					moveForward();
				}
				stuckMode = false;
			}
			
			int numMoves = (int)(Math.random() * 60 + 30);
			for (int i = 0; i < numMoves; i++)
			{
				try
				{
					Thread.sleep(50);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				moveForward();
				if (Collision.tankBoundary(this) == true)
				{
					moveBackward();
					stuckMode = true;
					continue outmostloop;
				}
				if (Math.random() < 0.075)
				{
					shootBullet();
				}
			}
			
			int numTurns = (int)(Math.random() * 20 + 10);
			double typeTurns = Math.random();
			if (typeTurns < 0.5)
			{
				for (int i = 0; i < numTurns; i++)
				{
					try
					{
						Thread.sleep(50);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					turnLeft();
					if (Collision.tankBoundary(this) == true)
					{
						turnRight();
						stuckMode = true;
						continue outmostloop;
					}
				}
			}
			else
			{
				for (int i = 0; i < numTurns; i++)
				{
					try
					{
						Thread.sleep(50);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					turnRight();
					if (Collision.tankBoundary(this) == true)
					{
						turnLeft();
						stuckMode = true;
						continue outmostloop;
					}
				}
			}
		}
	}
	
}
