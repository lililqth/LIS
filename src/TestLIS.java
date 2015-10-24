import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


public class TestLIS {
	private static final int initPos = 15; 
	private JFrame frame;
	private static int length;
	private static String inputStr;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestLIS window = new TestLIS();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestLIS() {
		TestLIS.length = initPos; 
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame("求解最长递增子序列");
		frame.setBounds(300, 300, 650, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gridBagLayout);
		JLabel labelTop = new JLabel("请选择输入序列的长度");
		JSlider slider = new JSlider(JSlider.HORIZONTAL,0,30,TestLIS.initPos);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(2);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(new SliderListener());
		final JTextPane input = new JTextPane();
		
		  
		JButton jbt1 = new JButton("随机生成序列");
		jbt1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				StringBuffer buf = new StringBuffer();
				Random r = new Random();
				for (int i=0; i<TestLIS.length; i++ ){
					int x = r.nextInt(10);
					buf.append(String.valueOf(x));
				}
				TestLIS.inputStr = buf.toString();
				Document doc = input.getStyledDocument();
				try {
					doc.remove(0, doc.getLength());
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				insertDocument(input, inputStr, Color.BLACK);
			}
		});
		final JTextPane output = new JTextPane();
		JButton jbt2 = new JButton("计算最长递增子序列");
		jbt2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String str = input.getText();
				int[] inputArr = new int[str.length()];
				for (int i=0; i<str.length(); i++){
					inputArr[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
				}
				LIS lis= new LIS(inputArr);
				int length = lis.getLISLength();
				int[] ans = lis.getLISArray();
				String outputStr = "";
				StringBuffer buf = new StringBuffer();
				for (int i=0; i<length; i++){
					buf.append(String.valueOf(ans[i]));
				}
				outputStr = buf.toString();
				output.setText(outputStr);
				HashSet<Integer> index = lis.getIndex();
				String tempIn = input.getText();
				input.setText("");
				for (int i=0; i<tempIn.length(); i++){
					if (index.contains(i)){
						input.setForeground(Color.RED);
						Document doc = input.getStyledDocument();
						
						//doc.insertString(doc.getLength(), String.valueOf(tempIn.charAt(i)));// 插入文字

						insertDocument(input, String.valueOf(tempIn.charAt(i)), Color.RED);
					}
					else{
						insertDocument(input, String.valueOf(tempIn.charAt(i)), Color.BLACK);
					}
				}
			}
		});
		
		GridBagConstraints s0 = new GridBagConstraints();
		s0.fill = GridBagConstraints.HORIZONTAL;
		s0.gridx = 0;
		s0.gridy = 0;
		s0.gridwidth=2;
		s0.gridheight=1;
		s0.insets = new Insets(10,20,10,20);
		frame.getContentPane().add(labelTop, s0);
		GridBagConstraints s1= new GridBagConstraints();
		s1.fill = GridBagConstraints.HORIZONTAL;
		s1.gridx = 0;
		s1.gridy = 1;
		s1.gridwidth=2;
		s1.gridheight=1;
		s1.insets = new Insets(10,20,10,20);
		frame.getContentPane().add(jbt1, s1);
		
		GridBagConstraints s2= new GridBagConstraints();
		s2.fill = GridBagConstraints.HORIZONTAL;
		s2.gridx = 0;
		s2.gridy = 2;
		s2.gridwidth=2;
		s2.gridheight=1;
		s2.insets = new Insets(10,20,10,20);
		frame.getContentPane().add(jbt2, s2);
		
		GridBagConstraints s3= new GridBagConstraints();
		s3.weightx = 0.3;
		s3.fill = GridBagConstraints.HORIZONTAL;
		s3.gridx = 2;
		s3.gridy = 0;
		s3.gridwidth=5;
		s3.gridheight=1;
		s3.insets = new Insets(10,20,10,20);
		frame.getContentPane().add(slider, s3);
		
		GridBagConstraints s4= new GridBagConstraints();
		s4.weightx = 0.3;
		s4.fill = GridBagConstraints.HORIZONTAL;
		s4.gridx = 2;
		s4.gridy = 1;
		s4.gridwidth=5;
		s4.gridheight=1;
		s4.insets = new Insets(10,20,10,20);
		frame.getContentPane().add(input, s4);
		
		GridBagConstraints s5 = new GridBagConstraints();
		s5.weightx = 0.3;
		s5.fill = GridBagConstraints.HORIZONTAL;
		s5.gridx = 2;
		s5.gridy = 2;
		s5.gridwidth=5;
		s5.gridheight=1;
		s5.insets = new Insets(10,20,10,20);
		frame.getContentPane().add(output, s5);
	}

	public static int getLength() {
		return length;
	}

	public static void setLength(int length) {
		TestLIS.length = length;
	}

	public void insertDocument(JTextPane pane ,String text, Color textColor)// 根据传入的颜色及文字，将文字插入文本域
	{
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, textColor);// 设置文字颜色
		StyleConstants.setFontSize(set, 12);// 设置字体大小
		Document doc = pane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), text, set);// 插入文字
		} catch (BadLocationException e) {
		}
	}
}


class SliderListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int fps = (int)source.getValue();
            TestLIS.setLength(fps);
        }    
    }
}