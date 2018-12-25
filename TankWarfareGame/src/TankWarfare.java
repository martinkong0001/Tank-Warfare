
import java.util.*;
import javax.swing.*;

public class TankWarfare extends JFrame implements Runnable
{
	
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private StagePanel stagePanel;
	private WinPanel winPanel;
	private LosePanel losePanel;
	
	private int currentStage;
	public static final int TOTAL_STAGES = 5;
	
	public TankWarfare()
	{
		setTitle("Tank Warfare");
		setLocation(200, 100);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		menuPanel = new MenuPanel(this);
		add(menuPanel);
		pack();
		setVisible(true);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void startNewGame()
	{
		if (menuPanel != null)
		{
			remove(menuPanel);
			menuPanel = null;
			currentStage = 0;
			goToNextStage();
		}
		else if (winPanel != null)
		{
			remove(winPanel);
			winPanel = null;
			currentStage = 0;
			goToNextStage();
		}
		else if (losePanel != null)
		{
			remove(losePanel);
			losePanel = null;
			currentStage = 0;
			goToNextStage();
		}
	}
	
	public void exitProgram()
	{
		System.exit(0);
	}
	
	public void goToNextStage()
	{
		currentStage ++;
		stagePanel = new StagePanel(currentStage);
		add(stagePanel);
		setVisible(true);
		java.util.Timer timer = new java.util.Timer();
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				remove(stagePanel);
				stagePanel = null;
				gamePanel = new GamePanel(currentStage * 2, 3, currentStage + 2);
				Thread thread = new Thread(gamePanel);
				thread.start();
				add(gamePanel);
				addKeyListener(gamePanel);
				setVisible(true);
				timer.cancel();
			}
		}, 3000);
	}
	
	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			if (gamePanel != null)
			{
				int gameState = gamePanel.getGameState();
				if (gameState == GamePanel.GAMESTATE_LOSE)
				{
					remove(gamePanel);
					gamePanel = null;
					losePanel = new LosePanel(this);
					add(losePanel);
					setVisible(true);
				}
				if (gameState == GamePanel.GAMESTATE_WIN)
				{
					if (currentStage == TOTAL_STAGES)
					{
						remove(gamePanel);
						gamePanel = null;
						winPanel = new WinPanel(this);
						add(winPanel);
						setVisible(true);
					}
					else
					{
						remove(gamePanel);
						gamePanel = null;
						goToNextStage();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) 
	{
		new TankWarfare();
	}
	
}
