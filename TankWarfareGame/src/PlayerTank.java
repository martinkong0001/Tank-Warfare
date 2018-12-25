
class PlayerTank extends Tank
{
	
	public PlayerTank(double x_coord, double y_coord, double direction, double speed, int health)
	{
		super(x_coord, y_coord, direction, speed, health);
	}
	
	public void moveForward()
	{
		super.moveForward();
		if (Collision.tankBoundary(this) == true)
		{
			super.moveBackward();
		}
	}
	
	public void moveBackward()
	{
		super.moveBackward();
		if (Collision.tankBoundary(this) == true)
		{
			super.moveForward();
		}
	}
	
	public void turnLeft()
	{
		super.turnLeft();
		if (Collision.tankBoundary(this) == true)
		{
			super.turnRight();
		}
	}
	
	public void turnRight()
	{
		super.turnRight();
		if (Collision.tankBoundary(this) == true)
		{
			super.turnLeft();
		}
	}
	
}
