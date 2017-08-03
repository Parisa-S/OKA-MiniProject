import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GUI extends JFrame{
	private Map<String,ImageIcon> alreadyPick;
	private Map<String,ImageIcon> tempPick;
	private static int frameSize = 800;
	private static int frameSizeH = 820;
	private JPanel panel;
	private List<String> result;
	private long millis;
	private static GridLayout gridLayoutJFrame = new GridLayout(1,1);
	private static GridLayout gridLayoutJPanel = new GridLayout(5,5);
	private static GridLayout gridLayoutButtonJPanel = new GridLayout(1,1);
	private JPanel buttonPanel;
	
	private int numOfPic;
	
	public GUI(){
		
		alreadyPick = new LinkedHashMap<String,ImageIcon>();
		tempPick = new LinkedHashMap<String,ImageIcon>();
		result = new ArrayList<String>();
		getContentPane().setPreferredSize( Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel = new JPanel();
		this.setBackground(Color.blue);
		this.setLayout(gridLayoutJFrame);
		panel.setLayout(gridLayoutJPanel);
		
		add(panel);
		init(result);
	}
	public void init(List<String> result){	
		//File dir = new File("D:/SKE/year3/Okayama U/MiniProject");
		File dir = new File("src/image/");
		if(dir.isDirectory()){
			System.out.println();
			numOfPic = dir.listFiles().length;
			if(numOfPic == alreadyPick.size()){
				writeLogFile(result);
				this.setVisible(false);
				this.dispose();
				System.exit(0);
			}
			randomTemp();
		}
		this.addImageButton();
	}
	
	private void randomTemp(){
		for(int i=1;i<=4;i++){
			int randompic = (int)(Math.random()*numOfPic)+1;
			if(alreadyPick.containsKey(randompic+"")){
				i--;
				continue;
			}
			ImageIcon icon = new ImageIcon("src/image/" + randompic+".jpg");
			Image img = icon.getImage();
			Image newImg = img.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
			ImageIcon file = new ImageIcon(newImg);
			
			alreadyPick.put(randompic+"",file);
			tempPick.put(randompic+"",file);		
		}
	}
	
	private void addImageButton(){
		List<JButton> jButtonList = new ArrayList<JButton>();
		for(Map.Entry<String,ImageIcon> entry: tempPick.entrySet()){
			JButton button = new JButton(entry.getValue());
			//button.setPreferredSize(new Dimension(200,200));
			//button.setMargin(new Insets(300, 300, 100, 100));
			button.setHorizontalAlignment(JButton.CENTER);
			button.setIcon(entry.getValue());
			button.addMouseListener(new MouseListener() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void mouseClicked(MouseEvent e) {
					long millis = System.currentTimeMillis();
					int x = e.getX();
					int y = e.getY();
					String sentence = millis+" "+x+" "+y+" ";
					for(Map.Entry<String,ImageIcon> item: tempPick.entrySet()){
						sentence += item.getKey()+" ";
					}
					sentence += entry.getKey();
					result.add(sentence);

					panel.removeAll();
					panel.revalidate();
//					panel.repaint();
					panel.setBackground(Color.BLACK);
					panel.paintImmediately(0,0,1920,1080);
					
					tempPick.clear();
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					init(result);
					panel.revalidate();
					panel.repaint();

				}

				@Override
				public void mouseEntered(MouseEvent e) {
				
				}

				@Override
				public void mouseExited(MouseEvent e) {
				
				}

				@Override
				public void mousePressed(MouseEvent e) {
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				
				}
			});
			buttonPanel = new JPanel();
			jButtonList.add(button);
		}
		int counter =0;
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				if((i==1||i==3)&&(j==1||j==3)){
					panel.add(jButtonList.get(counter));
					counter++;
				}
				else{
					panel.add(new JPanel());
				}
			}
		}

	}
	public void update(){
		setVisible(false);
		repaint();
		revalidate();
		int timer = 0;
		while(timer <= 1000){
			timer += 1;
			try {
//				setVisible(false);
				System.out.println("test");
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		setVisible(true);
	}
	
	public void writeLogFile(List<String> finalresult){
		try {
			FileWriter writer = new FileWriter("result.txt");
			for(String result: finalresult){
				writer.write(result);
				writer.write(System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.pack();
	}
}
