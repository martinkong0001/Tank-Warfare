
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class GamePanel extends JPanel implements KeyListener, Runnable
{
	
	private PlayerTank player;
	private ArrayList<EnemyTank> enemies;
	private ArrayList<Explosion> explosions;
	
	public static final int X_SIZE = 1200;
	public static final int Y_SIZE = 675;
	public static final int GAMESTATE_WIN = 1000;
	public static final int GAMESTATE_LOSE = 2000;
	public static final int GAMESTATE_NOTDONE = 3000;
	
	private BufferedImage playerImage;
	private BufferedImage enemyImage;
	private BufferedImage bulletImage;
	private BufferedImage explosion1;
	private BufferedImage explosion2;
	private BufferedImage explosion3;
	private BufferedImage gameBackground;
	
	public GamePanel(int enemyAmount, int enemySpeed, int enemyHealth)
	{
		initializeGame(enemyAmount, enemySpeed, enemyHealth);
		try
		{
			playerImage = ImageIO.read(new File("images/PlayerTank.png"));
			enemyImage = ImageIO.read(new File("images/EnemyTank.png"));
			bulletImage = ImageIO.read(new File("images/Bullet.png"));
			explosion1 = ImageIO.read(new File("images/Explosion1.jpeg"));
			explosion2 = ImageIO.read(new File("images/Explosion2.jpeg"));
			explosion3 = ImageIO.read(new File("images/Explosion3.jpeg"));
			gameBackground = ImageIO.read(new File("images/GameBackground.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void initializeGame(int enemyAmount, int enemySpeed, int enemyHealth)
	{
		player = new PlayerTank(GamePanel.X_SIZE / 2, GamePanel.Y_SIZE / 2, 0, 5, 5);
		enemies = new ArrayList<EnemyTank>();
		for (int i = 0; i < enemyAmount; i++)
		{
			double x = Math.random() * X_SIZE;
			double y = Math.random() * Y_SIZE;
			double direction = Math.random() * 360;
			EnemyTank enemy = new EnemyTank(x, y, direction, enemySpeed, enemyHealth);
			if (Collision.tankBoundary(enemy) == false)
			{
				Thread thread = new Thread(enemy);
				thread.start();
				enemies.add(enemy);
			}
			else 
			{
				i--;
				continue;
			}
		}
		explosions = new ArrayList<Explosion>();
	}
	
	public int getGameState()
	{
		if (player.getExistence() == false)
		{
			return GAMESTATE_LOSE;
		}
		if (enemies.size() == 0)
		{
			return GAMESTATE_WIN;
		}
		return GAMESTATE_NOTDONE;
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			for (int i = 0; i < player.bullets.size(); i++)
			{
				Bullet bullet = player.bullets.get(i);
				for (int j = 0; j < enemies.size(); j++)
				{
					EnemyTank enemy = enemies.get(j);
					if (player.getExistence() && bullet.getExistence()
							&& enemy.getExistence() && Collision.bulletTank(bullet, enemy))
					{
						bullet.destroyBullet();
						if (enemy.getAttacked() == true)
						{
							Explosion explosion = new Explosion
									(enemy.getX(), enemy.getY(), Tank.X_SIZE, Tank.Y_SIZE);
							Thread thread = new Thread(explosion);
							thread.start();
							explosions.add(explosion);
						}
					}
				}
			}
			
			for (int i = 0; i < enemies.size(); i++)
			{
				EnemyTank enemy = enemies.get(i);
				for (int j = 0; j < enemy.bullets.size(); j++)
				{
					Bullet bullet = enemy.bullets.get(j);
					if (enemy.getExistence() && bullet.getExistence()
							&& player.getExistence() && Collision.bulletTank(bullet, player))
					{
						bullet.destroyBullet();
						if (player.getAttacked() == true)
						{
							Explosion explosion = new Explosion
									(player.getX(), player.getY(), Tank.X_SIZE, Tank.Y_SIZE);
							Thread thread = new Thread(explosion);
							thread.start();
							explosions.add(explosion);
						}
					}
				}
			}
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(gameBackground, 0, 0, X_SIZE, Y_SIZE, null);
		if (player.getExistence() == true)
		{
			player.draw(g, playerImage);
			player.drawHealthBar(g);
			for (int i = 0; i < player.bullets.size(); i++)
			{
				Bullet bullet = player.bullets.get(i);
				if (bullet.getExistence() == true)
				{
					bullet.draw(g, bulletImage);
				}
				else
				{
					player.bullets.remove(i);
					i--;
				}
			}
		}
		
		for (int i = 0; i < enemies.size(); i++)
		{
			EnemyTank enemy = enemies.get(i);
			if (enemy.getExistence() == true)
			{
				enemy.draw(g, enemyImage);
				enemy.drawHealthBar(g);
				for (int j = 0; j < enemy.bullets.size(); j++)
				{
					Bullet bullet = enemy.bullets.get(j);
					if (bullet.getExistence() == true)
					{
						bullet.draw(g, bulletImage);
					}
					else
					{
						enemy.bullets.remove(j);
						j--;
					}
				}
			}
			else
			{
				enemies.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < explosions.size(); i++)
		{
			Explosion explosion = explosions.get(i);
			double phase = explosion.getExplosionPhase();
			if (phase < 0.33)
			{
				explosion.draw(g, explosion1);
			}
			else if (phase < 0.66)
			{
				explosion.draw(g, explosion2);
			}
			else if (phase < 1)
			{
				explosion.draw(g, explosion3);
			}
			else
			{
				explosions.remove(i);
				i--;
			}
		}
	}
	
	public void keyPressed(KeyEvent arg0)
	{
		if (arg0.getKeyCode() == KeyEvent.VK_W)
		{
			player.moveForward();
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_S)
		{
			player.moveBackward();
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_A)
		{
			player.turnLeft();
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_D)
		{
			player.turnRight();
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_J)
		{
			player.shootBullet();
		}
		this.repaint();
	}

	public void keyReleased(KeyEvent arg0)
	{
	}

	public void keyTyped(KeyEvent arg0)
	{
	}
	
}
