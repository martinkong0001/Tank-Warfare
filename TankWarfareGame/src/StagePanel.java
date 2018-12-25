
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class StagePanel extends JPanel
{
	
	private int stageNumber;
	private BufferedImage stageBackground;
	
	public StagePanel(int stageNumber)
	{
		this.stageNumber = stageNumber;
		try
		{
			stageBackground = ImageIO.read(new File("images/StageBackground.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(stageBackground, 0, 0, null);
		g.setFont(new Font("Arial", Font.BOLD, 60));
		g.setColor(Color.BLACK);
		g.drawString("Stage " + stageNumber, 480, 360);
	}
	
}
