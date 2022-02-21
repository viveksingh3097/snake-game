import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	static  final  int SCREEN_WIDTH =  600;
	static  final  int SCREEN_HIGHT =  600;
	static final int UNIT_SIZE= 25;
	static final int Game_UNITS=(SCREEN_WIDTH * SCREEN_HIGHT)/UNIT_SIZE;
	static final int  DELAY = 75;
	final int []x = new  int[Game_UNITS]; 
	final int []y = new  int[Game_UNITS]; 
	int bodyParts = 6;
	int appleEaten;
	int appleX;
	int appleY;
	char direction ='R';
	boolean running  = false;
	Timer timer;
	Random random;
	GamePanel(){
	   random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HIGHT));
		this.setBackground(Color.black);
		 this.setFocusable(true);
		 this.addKeyListener(new myKeyAdapter());
		 startGame();
		}
	// starting a start method 
		public void startGame() {	
				newApple();
				running = true;
			  timer = new Timer(DELAY, this);
			timer.start();
			}
		// starting a paintComponentmethod.
		
		public void paintComponent(Graphics gg) {  
		   super.paintComponent(gg);
		   draw(gg);
		
				}
		// Starting a draw method ....
		public void draw(Graphics gg) {
			if(running) {
			 /*for (int i=0; i<SCREEN_HIGHT/UNIT_SIZE; i++) {
				 gg.drawLine(i*UNIT_SIZE,0, i*UNIT_SIZE,SCREEN_HIGHT);
				 gg.drawLine(0, i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);	 
			 }*/
			 	gg.setColor(Color.white);
			 	gg.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			 	
			 	for(int i= 0; i< bodyParts;i++) {
			 		 if(i==0)
			 		 {
			 			 gg.setColor(Color.green);
			 			 gg.fillRect(x[i], y[i],UNIT_SIZE ,UNIT_SIZE );
			 		 }
			 		 else {
			 			 gg.setColor(new Color(45,180,0));
			 			 gg.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
			 			 gg.fillRect(x[i], y[i],UNIT_SIZE ,UNIT_SIZE );
			 		 }
			 	   }
			 	gg.setColor(Color.blue);
				gg.setFont(new Font("Ink Free",Font.BOLD,40));
				FontMetrics metrics = getFontMetrics(gg.getFont());
				gg.drawString("Score: "+appleEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+appleEaten))/2,gg.getFont().getSize());
				}
		     	
			else {
				gameOver(gg);
			} 
}
		      
	 	public void newApple() {
	 		appleX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
	 		appleY= random.nextInt((int)SCREEN_HIGHT/UNIT_SIZE)*UNIT_SIZE;
	 		}
	 
		public void move() {
			for (int i= bodyParts;i>0;i-- ) {
				x[i] = x[i-1];
				y[i] = y[i-1];	
			}
				switch (direction) {
				case 'U':
					y[0] = y[0] - UNIT_SIZE;
					break;
				case 'D':
					y[0] = y[0] + UNIT_SIZE;
					break;
				case 'L':
					x[0] = x[0] - UNIT_SIZE;
					break;
				case 'R':
					x[0] = x[0] + UNIT_SIZE;
					break;
				}
				
			}
		public void checkApple(){	
			
			if((x[0] == appleX)&&(y[0] == appleY)) {
				bodyParts ++;
				appleEaten ++;
				newApple();
			}
			
			}
		public void checkCollisions() {
			//  check if  head collides with body...
			  for(int i=bodyParts; i> 0; i-- ) { 
				  if((x[0] == x[i])&&(y[0] == y[i])) {
					  running = false;
					    
				  }
			  }
			//  check  if head touch left border 
			  if(x[0]< 0){		
				  running= false;	  
				  }
			  //   check if head touches right border 
			  if(x[0] > SCREEN_WIDTH ) {
				  running = false;
			  
			  }
			  // if check had touches top border 
			  if(y[0] < 0) {  
				running = false;
			  }
			  //if check had touches bottom border 
			  if(y[0] > SCREEN_HIGHT) {
				  running = false;
			  }
			  if(!running) {   
				  timer.stop();
			  }
		  }
		
		public void gameOver(Graphics gg) {
			
			gg.setColor(Color.red);
			gg.setFont(new Font("Ink Free",Font.BOLD,40));
			FontMetrics metrics = getFontMetrics(gg.getFont());
			gg.drawString("Score: "+appleEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+appleEaten))/2,gg.getFont().getSize());
			
			//game over text 
			
			
			gg.setColor(Color.red);
			gg.setFont(new Font("Ink Free",Font.BOLD,75));
			FontMetrics metrics2 = getFontMetrics(gg.getFont());
			gg.drawString("Game Over",(SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2,SCREEN_HIGHT/2);
			
		
			}
		 
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class myKeyAdapter extends KeyAdapter
	{
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
			}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
		}	
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
	                }
				break;
              }
	      }
    }
}

