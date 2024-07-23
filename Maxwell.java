
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
//import java.util.TimerTask;

import javax.swing.*;

public class Maxwell extends JPanel implements ActionListener, MouseListener {
	
	

	private static final long serialVersionUID = 1L;
	
	Maxwell game;
	JFrame f = new JFrame("Maxwell");
	JPanel centerArea = new JPanel();
	createGame walls = new createGame();
	JPanel buttons = new JPanel();
	JPanel temps = new JPanel();
	JPanel RstatsTop = new JPanel();
	JPanel RstatsBot = new JPanel();
	JButton makeBlack = new JButton("Black");
	JButton makeWhite = new JButton("White");
	JButton makePink = new JButton("Pink");
	JButton makeGreen = new JButton("Green");
	JButton makeCyan = new JButton("Cyan");
	JButton makeYellow = new JButton("Yellow");
	JPanel Rstats = new JPanel();
	JLabel timeKeep = new JLabel("TIME: ", SwingConstants.CENTER);
	JLabel currentScore = new JLabel("Current Temp Difference: ", SwingConstants.CENTER);
	JButton addB = new JButton("Add Particles");
	JButton resetB = new JButton("Reset");
	JLabel leftTemp = new JLabel("Left temp:", SwingConstants.CENTER);
    JLabel rightTemp = new JLabel("Right temp:", SwingConstants.CENTER);
	Hot[] hotBalls = new Hot[500];
	Cold[] coldBalls = new Cold[500];
	Color cBackground = Color.LIGHT_GRAY;
	Color c = Color.white;
	Color cWalls = Color.BLACK;
	int numHot ;
	int numCold ;
	double timeInterval = 0.02; //20 ms
	Timer timer;
	double totalTime = 0;
	double tempDif = 0;
	
	

	/**
	 * Main method to create game.
	 */
	public Maxwell() {
		
		//Creating window
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1200, 800);
        
        //Create timer that updates ball locations every 20 ms
    	timer = new Timer(20, this);
    	timer.start();
        
        //Create button panel and buttons
        buttons.setBackground(cBackground);
        
        addB.addActionListener(this);
        addB.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        resetB.addActionListener(this);
        resetB.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        buttons.add(addB);
        buttons.add(resetB);
        
        //Create temperature panel
        temps.setLayout(new GridLayout(1,2));
        temps.setBackground(cBackground);
        
        leftTemp.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        rightTemp.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        
        temps.add(leftTemp);
        temps.add(rightTemp);
        temps.setPreferredSize(new Dimension(50, 50));
        
        //TImekeeping and score panel
        Rstats.setLayout(new GridLayout(2,1));
        Rstats.setBackground(cBackground);
        Rstats.setPreferredSize(new Dimension(200, 20));
        
        RstatsTop.setLayout(new GridLayout(2,1));
        RstatsTop.setBackground(cBackground);
        
        timeKeep.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        currentScore.setFont(new Font("TimesRoman", Font.PLAIN, 17));
        
        RstatsTop.add(timeKeep);
        RstatsTop.add(currentScore);
        
        //Button panel to change color of game rectangle
        RstatsBot.setLayout(new GridLayout(3,2));
        
        makeBlack.addActionListener(this);
        makeBlack.setBackground(Color.black);
        makeBlack.setOpaque(true);
        RstatsBot.add(makeBlack);
        
        makeWhite.addActionListener(this);
        makeWhite.setBackground(Color.white);
        makeWhite.setOpaque(true);
        RstatsBot.add(makeWhite);
        
        makePink.addActionListener(this);
        makePink.setBackground(Color.pink);
        makePink.setOpaque(true);
        RstatsBot.add(makePink);
        
        makeGreen.addActionListener(this);
        makeGreen.setBackground(Color.green);
        makeGreen.setOpaque(true);
        RstatsBot.add(makeGreen);
        
        makeCyan.addActionListener(this);
        makeCyan.setBackground(Color.cyan);
        makeCyan.setOpaque(true);
        RstatsBot.add(makeCyan);
        
        makeYellow.addActionListener(this);
        makeYellow.setBackground(Color.yellow);
        makeYellow.setOpaque(true);
        RstatsBot.add(makeYellow);
        
        Rstats.add(RstatsTop);
        Rstats.add(RstatsBot);
        f.add(Rstats, BorderLayout.LINE_END);
       
        //Adds game elements
        centerArea.setLayout(new BorderLayout());
        centerArea.add(walls, BorderLayout.CENTER);
        centerArea.add(temps, BorderLayout.PAGE_END);
        centerArea.add(buttons,BorderLayout.PAGE_START);
        f.add(centerArea, BorderLayout.CENTER);
        
        
        //Adds mouse listener that detects action in the game window
        f.addMouseListener(this);
        
        //Adds initial 4 balls 
        numHot = 0;
    	numCold = 0;
        addBalls();
        setTemp();
        
        f.setVisible(true);
	}

	/**
	 * 
	 * Class to draw game graphics and track wall changes
	 * @author sarahgreen
	 *
	 */
	
	public class createGame extends JComponent{
		private static final long serialVersionUID = 1L;
		private boolean isWall = true;
		
		/**
		 * Setter method of wall truth value.
		 * @param x true if wall is open, false if closed
		 */
		public void setWall(boolean x) {
			isWall = x;
		}
		/**
		 * Getter method to get status of the wall
		 * @return truth value of the wall
		 */
		public boolean getWall() {
			return isWall;
		}
		/**
		 * Method to paint the game box and balls
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(c);
            g.fillRect(50, 100, 900, 500);
            g.setColor(cWalls);
            g.drawLine(50, 100, 50, 600);
            g.drawLine(50, 100, 950, 100);
            g.drawLine(950, 100, 950, 600);
            g.drawLine(50, 600, 950, 600);
            
            g.setColor(Color.black);
            g.setFont(new Font("TimesRoman", Font.ITALIC, 40));
            g.drawString("MAXWELL'S DEMON", 300, 60);
            
            if (getWall()) {
            	g.setColor(cWalls);
                g.drawLine(499, 100, 499, 600);
                g.drawLine(500, 100, 500, 600);
                g.drawLine(501, 100, 501, 600);
            } 
            if (!getWall()) {
            	g.setColor(cWalls);
            	g.drawLine(499, 100, 499, 600);
                g.drawLine(500, 100, 500, 600);
                g.drawLine(501, 100, 501, 600);
                
                g.setColor(c);
                g.drawLine(499, 266, 499, 434);
                g.drawLine(500, 266, 500, 434);
                g.drawLine(501, 266, 501, 434);
            }
            
            for ( int i=0; i<numHot; i++) {
            	hotBalls[i].createBall(g);
                coldBalls[i].createBall(g);
            }
           
            
		}
		
	}
	/**
	 * Class to create balls and update their movement.
	 * @author sarahgreen
	 *
	 */
	
	public abstract class Ball {
		double x;
		double y;
		double vX;
		double vY;
		double v0;
		float angle;
		
		public Ball() {
			
		}
		
		public Ball(double xLoc, double yLoc) {
			x = xLoc;
			y = yLoc;
			
		}
		/**
		 * Method to update ball position based on their x and y velocities.
		 * @param timeInterval frequency of position updates
		 */
		public void moveBalls(double timeInterval) {
			x += vX * timeInterval;
			y += vY * timeInterval;
				
			if (walls.getWall()) {
				this.BounceWall();
			} else {
				this.BounceNoWall();
			}
			
				
		}
		/**
		 * Method to keep balls in wall bounds when the wall is closed.
		 */
		public void BounceWall() {
			
			if (x < 50 || x > 940) {
				vX *= -1;
			}
			if (y < 100 || y > 590) {
				vY *= -1;
			}
			if (x > 493 && x < 507) {
				vX *= -1;
			}
			
		}
		/**
		 * Method to keep balls in wall bounds when the wall is open.
		 */
		public void BounceNoWall() {
			if (x < 50 || x > 940) {
				vX *= -1;
			}
			if (y < 100 || y > 590) {
				vY *= -1;
			}
			if (x > 493 && x < 507 && (y > 434 || y < 260)) {
				vX *= -1;
			}
			if (x > 493 && x < 507 && y < 434 && y > 260) {
				setTemp(); //Ball passes through door - recalculate temperature
				
			}
			
		}
		
		public void createBall (Graphics g) {
			
		}

	}
	/**
	 * Derived class from Ball() to create a Hot ball. 
	 * @author sarahgreen
	 *
	 */
	public class Hot extends Ball{
		
		public Hot() {
			super();
		}
		/**
		 * Constructor to create a new Hot ball.
		 * @param xLoc x position of ball
		 * @param yLoc y position of ball
		 */
		Hot(double xLoc, double yLoc) {
			super(xLoc,yLoc);
			
			Random rn = new Random();
			angle = rn.nextFloat((float)Math.PI * 2);
			
			while (angle == 0) {
				angle = rn.nextFloat((float)Math.PI * 2);
			}
			
			double n = rn.nextDouble();
			
			while (n == 0) {
				n = rn.nextDouble();
			}
			
			v0 = 50*(n*2 + 4);
			vX = v0 * Math.cos(angle);
			vY = v0 * Math.sin(angle);
			//System.out.println("FAST"+vX);
			
		}
		
		@Override
		public void createBall (Graphics g) {
			g.setColor( Color.red );
			g.fillOval((int)x, (int)y, 8, 8);
		}

	}
	/**
	 * Derived class from Ball() to create a Cold ball. 
	 * @author sarahgreen
	 *
	 */
	public class Cold extends Ball{
		
		public Cold() {
			super();
		}
		/**
		 * Constructor to create a new Cold ball.
		 * @param xLoc x position of ball
		 * @param yLoc y position of ball
		 */
		Cold(double xLoc, double yLoc) {
			super(xLoc, yLoc);
			
			Random rn = new Random();
			angle = rn.nextFloat((float)Math.PI * 2);
			
			while (angle == 0) {
				angle = rn.nextFloat((float)Math.PI * 2);
			}
			
			double n = rn.nextDouble();
			while (n == 0) {
				n = rn.nextDouble();
			}
			
			v0 = 50*(n*2 + 2);
			vX = v0 * Math.cos(angle);
			vY = v0 * Math.sin(angle);
			//System.out.println("Slow"+v0);
			
		}
		@Override
		public void createBall (Graphics g) {
			g.setColor( Color.blue );
			g.fillOval((int)x, (int)y, 8, 8);
		}

	}

	/**
	 * Method to add four new balls to the game arena. 
	 * Should add one Hot ball and one Cold ball to each side of the game arena.
	 */
	public void addBalls() {
		Hot f1 = new Hot(200, 250);
		hotBalls[numHot] = f1;
		numHot++;
		
		Hot f2 = new Hot(800,250);
		hotBalls[numHot] = f2;
		numHot++;
		
		Cold s1 = new Cold(200, 250);
		coldBalls[numCold] = s1;
		numCold++;
		
		Cold s2 = new Cold(800,250);
		coldBalls[numCold] = s2;
		numCold++;
		
		setTemp();
		
	}
	/**
	 * Setter method to update temperature every time a ball passes through the door.
	 */
	public void setTemp() {
		
		int numInLeft = 0;
		int numInRight = 0;
		double LTemp = 0;
		double RTemp = 0;
		
		for (int i = 0; i < numHot; i++) {
			if (hotBalls[i].x < 500) {
				numInLeft++;
				LTemp += (hotBalls[i].v0/50)*(hotBalls[i].v0/50);
			} else {
				numInRight++;
				RTemp += (hotBalls[i].v0/50)*(hotBalls[i].v0/50);
			}
			if (coldBalls[i].x < 500) {
				numInLeft++;
				LTemp += (coldBalls[i].v0/50)*(coldBalls[i].v0/50);
			} else {
				numInRight++;
				RTemp += (coldBalls[i].v0/50)*(coldBalls[i].v0/50);
			}
		}
		if (numInLeft == 0) {
			LTemp = 0;
			RTemp = RTemp/numInRight;
		} else if (numInRight == 0) {
			LTemp = LTemp/numInLeft;
			RTemp = 0;
		} else {
			LTemp = LTemp/numInLeft;
			RTemp = RTemp/numInRight;
		}
		
		tempDif = Math.abs(RTemp-LTemp);
		
		leftTemp.setText("Left temp: " + String.valueOf(String.format("%.2f", LTemp)) + "°");
		rightTemp.setText("Right temp: " + String.valueOf(String.format("%.2f", RTemp)) + "°");
		currentScore.setText("<html>Current Temp Difference: <br>" 
				+ String.valueOf(String.format("%.2f", tempDif)) + "°</html>");
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		if (e.getSource() == timer) {
			for (int i=0; i<numHot; i++ ) {
    			hotBalls[i].moveBalls(timeInterval);
    			coldBalls[i].moveBalls(timeInterval);
    		}
			totalTime += timeInterval;
			timeKeep.setText("TIME: " + String.valueOf(String.format("%.1f", totalTime)) + " s");
			
		} else if (e.getSource() == addB) {
			addBalls();
			
		} else if (e.getSource() == resetB) {
			double difference = tempDif;
			double timeee = totalTime;
			
			Hot[] newHotBalls = new Hot[500];
			Cold[] newColdBalls = new Cold[500];
			
			hotBalls = newHotBalls;
			coldBalls = newColdBalls;
			
			numHot = 0;
			numCold = 0;
			
			addBalls();
			totalTime = 0;
			
			JOptionPane.showMessageDialog(null,"Your final temperature difference was " 
					+ String.valueOf(String.format("%.2f", difference)) + "°" + 
					" and your final time was " + String.valueOf(String.format("%.1f", timeee)) + " s",
					"Maxwell Score",1);
			
						
		} else if (e.getSource() == makeBlack) {
			c = Color.black;
			cWalls = Color.white;
		} else if (e.getSource() == makeWhite) {
			c = Color.white;
			cWalls = Color.black;
		} else if (e.getSource() == makePink) {
			c = Color.pink;
			cWalls = Color.black;
		} else if (e.getSource() == makeGreen) {
			c = Color.green;
			cWalls = Color.black;
		} else if (e.getSource() == makeCyan) {
			c = Color.cyan;
			cWalls = Color.black;
		} else if (e.getSource() == makeYellow) {
			c = Color.yellow;
			cWalls = Color.black;
		}
		walls.repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent mc) {
		walls.setWall(false);
		walls.repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent mu) {
		walls.setWall(true);
		walls.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	
	public static void main(String[] args) {
		new Maxwell();
	}

	
}
