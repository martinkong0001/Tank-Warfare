
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

public class LosePanel extends JPanel implements MouseListener
{
	
	private BufferedImage playerLostImage;
	private BufferedImage tryAgainButtonImage;
	private BufferedImage giveUpButtonImage;
	private BufferedImage checkMarkImage;
	
	private JLabel tryAgainButton;
	private JLabel giveUpButton;
	private boolean tryAgainCheckMark;
	private boolean giveUpCheckMark;

    private TankWarfare mainControl;
	
	public LosePanel(TankWarfare mainControl)
	{
		try
		{
			playerLostImage = ImageIO.read(new File("images/PlayerLost.png"));
			tryAgainButtonImage = ImageIO.read(new File("images/TryAgainButton.png"));
			giveUpButtonImage = ImageIO.read(new File("images/giveUpButton.png"));
			checkMarkImage = ImageIO.read(new File("images/CheckMark.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		setLayout(null);
		
		tryAgainButton = new JLabel(new ImageIcon(tryAgainButtonImage));
		Dimension size = tryAgainButton.getPreferredSize();
		tryAgainButton.setBounds(450, 325, size.width, size.height);
		tryAgainButton.addMouseListener(this);
		add(tryAgainButton);
		
		giveUpButton = new JLabel(new ImageIcon(giveUpButtonImage));
		size = giveUpButton.getPreferredSize();
		giveUpButton.setBounds(450, 475, size.width, size.height);
		giveUpButton.addMouseListener(this);
		add(giveUpButton);
		
		this.mainControl = mainControl;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(playerLostImage, 0, 0, null);
		if (tryAgainCheckMark == true)
		{
			g.drawImage(checkMarkImage, 760, 325, null);
		}
		else if (giveUpCheckMark == true)
		{
			g.drawImage(checkMarkImage, 760, 475, null);
		}
	}

	public void mouseClicked(MouseEvent arg0)
	{
		if (arg0.getSource() == tryAgainButton)
		{
			mainControl.startNewGame();
		}
		else if (arg0.getSource() == giveUpButton)
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
		else if (arg0.getSource() == giveUpButton)
		{
			giveUpCheckMark = true;
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
		else if (arg0.getSource() == giveUpButton)
		{
			giveUpCheckMark = false;
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
