
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements MouseListener
{
	
	private BufferedImage backgroundImage;
	private BufferedImage playButtonImage;
	private BufferedImage tutorialButtonImage;
	private BufferedImage exitButtonImage;
	private BufferedImage checkMarkImage;
	
	private JLabel playButton;
	private JLabel tutorialButton;
	private JLabel exitButton;
	private boolean playCheckMark;
	private boolean tutorialCheckMark;
	private boolean exitCheckMark;
	
    private TankWarfare mainControl;
	
	public MenuPanel(TankWarfare mainControl)
	{
		try
		{
			backgroundImage = ImageIO.read(new File("images/MenuBackground.png"));
			playButtonImage = ImageIO.read(new File("images/PlayButton.png"));
			tutorialButtonImage = ImageIO.read(new File("images/TutorialButton.png"));
			exitButtonImage = ImageIO.read(new File("images/ExitButton.png"));
			checkMarkImage = ImageIO.read(new File("images/CheckMark.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		setLayout(null);
		setPreferredSize(new Dimension(GamePanel.X_SIZE, GamePanel.Y_SIZE));
		
		playButton = new JLabel(new ImageIcon(playButtonImage));
		Dimension size = playButton.getPreferredSize();
		playButton.setBounds(120, 100, size.width, size.height);
		playButton.addMouseListener(this);
		add(playButton);
		
		tutorialButton = new JLabel(new ImageIcon(tutorialButtonImage));
		size = tutorialButton.getPreferredSize();
		tutorialButton.setBounds(120, 250, size.width, size.height);
		tutorialButton.addMouseListener(this);
		add(tutorialButton);
		
		exitButton = new JLabel(new ImageIcon(exitButtonImage));
		size = exitButton.getPreferredSize();
		exitButton.setBounds(120, 400, size.width, size.height);
		exitButton.addMouseListener(this);
		add(exitButton);
		
		this.mainControl = mainControl;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
		if (playCheckMark == true)
		{
			g.drawImage(checkMarkImage, 430, 100, null);
		}
		else if (tutorialCheckMark == true)
		{
			g.drawImage(checkMarkImage, 430, 250, null);
		}
		else if (exitCheckMark == true)
		{
			g.drawImage(checkMarkImage, 430, 400, null);
		}
	}

	public void mouseClicked(MouseEvent arg0)
	{
		if (arg0.getSource() == playButton)
		{
			mainControl.startNewGame();
		}
		else if (arg0.getSource() == tutorialButton)
		{
			JLabel message = new JLabel();
			message.setText("<html>Press W to move forward."
					+ "<br>Press S to move backward."
					+ "<br>Press A to turn left."
					+ "<br>Press D to turn right."
					+ "<br>Press J to shoot bullets.</html>");
			JOptionPane.showMessageDialog(this, message, 
					"Instructions", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (arg0.getSource() == exitButton)
		{
			mainControl.exitProgram();
		}
	}
	
	public void mouseEntered(MouseEvent arg0)
	{
		if (arg0.getSource() == playButton)
		{
			playCheckMark = true;
			repaint();
		}
		else if (arg0.getSource() == tutorialButton)
		{
			tutorialCheckMark = true;
			repaint();
		}
		else if (arg0.getSource() == exitButton)
		{
			exitCheckMark = true;
			repaint();
		}
	}

	public void mouseExited(MouseEvent arg0)
	{
		if (arg0.getSource() == playButton)
		{
			playCheckMark = false;
			repaint();
		}
		else if (arg0.getSource() == tutorialButton)
		{
			tutorialCheckMark = false;
			repaint();
		}
		else if (arg0.getSource() == exitButton)
		{
			exitCheckMark = false;
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
