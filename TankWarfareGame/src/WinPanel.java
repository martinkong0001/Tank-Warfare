
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinPanel extends JPanel implements MouseListener
{
	
	private BufferedImage playerWonImage;
	private BufferedImage tryAgainButtonImage;
	private BufferedImage exitGameButtonImage;
	private BufferedImage checkMarkImage;
	
	private JLabel tryAgainButton;
	private JLabel exitGameButton;
	private boolean tryAgainCheckMark;
	private boolean exitGameCheckMark;

    private TankWarfare mainControl;
	
	public WinPanel(TankWarfare mainControl)
	{
		try
		{
			playerWonImage = ImageIO.read(new File("images/PlayerWon.png"));
			tryAgainButtonImage = ImageIO.read(new File("images/TryAgainButton.png"));
			exitGameButtonImage = ImageIO.read(new File("images/ExitGameButton.png"));
			checkMarkImage = ImageIO.read(new File("images/CheckMark.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		setLayout(null);
		
		tryAgainButton = new JLabel(new ImageIcon(tryAgainButtonImage));
		Dimension size = tryAgainButton.getPreferredSize();
		tryAgainButton.setBounds(720, 200, size.width, size.height);
		tryAgainButton.addMouseListener(this);
		add(tryAgainButton);
		
		exitGameButton = new JLabel(new ImageIcon(exitGameButtonImage));
		size = exitGameButton.getPreferredSize();
		exitGameButton.setBounds(720, 350, size.width, size.height);
		exitGameButton.addMouseListener(this);
		add(exitGameButton);
		
		this.mainControl = mainControl;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(playerWonImage, 0, 0, null);
		if (tryAgainCheckMark == true)
		{
			g.drawImage(checkMarkImage, 1030, 200, null);
		}
		else if (exitGameCheckMark == true)
		{
			g.drawImage(checkMarkImage, 1030, 350, null);
		}
	}

	public void mouseClicked(MouseEvent arg0)
	{
		if (arg0.getSource() == tryAgainButton)
		{
			mainControl.startNewGame();
		}
		else if (arg0.getSource() == exitGameButton)
		{
			mainControl.exitProgram();
		}
	}

	public void mouseEntered(MouseEvent arg0)
	{
		if (arg0.getSource() == tryAgainButton)
		{
			tryAgainCheckMark = true;
			repaint();
		}
		else if (arg0.getSource() == exitGameButton)
		{
			exitGameCheckMark = true;
			repaint();
		}
	}

	public void mouseExited(MouseEvent arg0)
	{
		if (arg0.getSource() == tryAgainButton)
		{
			tryAgainCheckMark = false;
			repaint();
		}
		else if (arg0.getSource() == exitGameButton)
		{
			exitGameCheckMark = false;
			repaint();
		}
	}

	public void mousePressed(MouseEvent arg0)
	{
	}

	public void mouseReleased(MouseEvent arg0)
	{
	}
	
}
