package MyJavaGUIPackage;
import MyJavaPackage.MyJava;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Enumeration;

import javax.swing.*;

import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;

public class MyJavaGUI {
    
    static private JFrame frame = new JFrame();
    static private Toolkit kit = Toolkit.getDefaultToolkit();
	static private GridBagLayout layout = new GridBagLayout();
	static private GridBagConstraints gbc = new GridBagConstraints();
	static private JButton jbSubmit = new JButton();
	static private JButton jbOpen = new JButton();
	static private JTextField jtfPath = new JTextField();
	static private JLabel jlPath = new JLabel();
	static private JLabel jlResult = new JLabel();
	static private JLabel jlext1 = new JLabel();
	static private JPanel jp1 = new JPanel();
	static private JPanel jp2 = new JPanel();
	static private JRadioButton jrbtxt = new JRadioButton(".txt",true);
	static private JRadioButton jrbjava = new JRadioButton(".java",true);
	static private JRadioButton jrbc = new JRadioButton(".c",true);
	static private JRadioButton jrbcpp = new JRadioButton(".cpp",true);
	static private JRadioButton jrbasm = new JRadioButton(".asm",true);
	static private ButtonGroup radioGroup1 = new ButtonGroup() ;
	static private JScrollPane jsp = new JScrollPane(jp2) ;
	static private ButtonGroup radioGroup2 = new ButtonGroup() ;
	
    public static JRadioButton getJrbtxt() {
		return jrbtxt;
	}
	public static JRadioButton getJrbjava() {
		return jrbjava;
	}
	public static JRadioButton getJrbc() {
		return jrbc;
	}
	public static JRadioButton getJrbcpp() {
		return jrbcpp;
	}
	public static JRadioButton getJrbasm() {
		return jrbasm;
	}
	private static void setupFrame() {
    	JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("�ؼ�������");
        frame.setLocationRelativeTo(null);
        Image img = kit.getImage("search.png");
        frame.setIconImage(img);
        frame.setVisible(true);
        //----------------------���빹��---------------
        frame.add(jlPath);
        frame.add(jp1);
        frame.add(jtfPath);
        frame.add(jbSubmit);
        frame.add(jrbtxt);
        frame.add(jrbjava);
        frame.add(jrbc);
        frame.add(jrbcpp);
        frame.add(jrbasm);
        frame.add(jlResult);
        frame.add(jlext1);
        frame.add(jsp);
        frame.add(jbOpen);
    }
    private static void addLayout(){
    	frame.setLayout(layout);
    	gbc.fill = GridBagConstraints.BOTH;
    }
    private static void addRadioButton(){
    	radioGroup1.add(jrbtxt);
    	radioGroup1.add(jrbjava);
    	radioGroup1.add(jrbc);
    	radioGroup1.add(jrbcpp);
    	radioGroup1.add(jrbasm);
    	
    	
    	gbc.gridwidth = 1;
    	gbc.weightx = 0;
    	gbc.weighty = 0;
    	gbc.gridx = 0;
    	gbc.gridy = 5;
        layout.setConstraints(jrbtxt, gbc);
        gbc.gridy = 6;
        layout.setConstraints(jrbjava, gbc);
        gbc.gridy = 7;
        layout.setConstraints(jrbc, gbc);
        gbc.gridy = 8;
        layout.setConstraints(jrbcpp, gbc);
        gbc.gridy = 9;
        layout.setConstraints(jrbasm, gbc);
    }
    private static void addTextField(){
    	gbc.gridwidth = 3;
    	gbc.weightx = 1;
    	gbc.weighty = 0;
    	gbc.gridx = 0;
    	gbc.gridy = 1;
        layout.setConstraints(jtfPath, gbc);
    }
    private static void addLabel(){
    	jlPath.setText("������ؼ���");
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.gridwidth = 1;
    	gbc.weightx = 0;
    	gbc.weighty = 1;
        layout.setConstraints(jlPath, gbc);
        jlResult.setText("��ѯ���Ϊ");
    	gbc.gridx = 3;
    	gbc.gridy = 3;
    	gbc.gridwidth = 2;
        layout.setConstraints(jlResult, gbc);
        jlext1.setText("��ѡ�������ļ��ĺ�׺��:");
    	gbc.gridx = 0;
    	gbc.gridy = 3;
    	gbc.gridwidth = 1;
        layout.setConstraints(jlext1, gbc);
    }
    private static void addPanel(){
    	gbc.gridwidth = 4;
    	gbc.weightx = 1;
    	gbc.weighty = 0;
    	gbc.gridx = 1;
    	gbc.gridy = 0;
        layout.setConstraints(jp1, gbc);
    }
    private static void setPanel(){
    	jp2.setPreferredSize(new Dimension(50,500));
    	jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    private static void addScrollPane(){
    	jsp.setPreferredSize(new Dimension(5,5));
    	
        setPanel();
    	gbc.weightx = 0;
    	gbc.weighty = 0;
    	gbc.gridheight = 6;
    	gbc.gridwidth = 4;
    	gbc.gridx = 2;
    	gbc.gridy = 4;
    	gbc.insets = new Insets(2, 2, 2, 2);
        layout.setConstraints(jsp, gbc);
    }
    private static void addButton(){
    	//---------------------���á�ѡ���ļ��С���ť------------------------
    	jbSubmit.setText("ѡ���ļ���");
    	jbSubmit.addActionListener( new ActionListener() {
    	   
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jp2.removeAll();			//ÿ�β�ѯ�����ԭ���
			MyJava.setKeyword(jtfPath.getText());
			
			if(MyJavaGUI.getJrbtxt().isSelected()){
				text = new String("*.txt");
			}else if(MyJavaGUI.getJrbjava().isSelected()){
				text = new String("*.java");
			}else if(MyJavaGUI.getJrbc().isSelected()){
				text = new String("*.c");
			}else if(MyJavaGUI.getJrbcpp().isSelected()){
				text = new String("*.cpp");
			}else if(MyJavaGUI.getJrbasm().isSelected()){
				text = new String("*.asm");
			}
			MyJava.setExt(text);
			
			MyJava.selectDictionary();
		}
		private String text = null;
              } );
    	gbc.gridwidth = 1;		//���ð�ť����
        gbc.weightx = 0;
        gbc.weighty = 0;
    	gbc.gridx = 4;
    	gbc.gridy = 1;
        layout.setConstraints(jbSubmit, gbc);
        
        jbOpen.setText("���ļ�");
        jbOpen.addActionListener( new ActionListener() {//��Ӽ������������ť����ļ�
     	   
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			try {
    				Enumeration<AbstractButton>rbs = 
    						radioGroup2.getElements();	//��radioGroup����ѡ�еİ�ť
    				while(rbs.hasMoreElements()){
    					JRadioButton jrbOpen =
    							(JRadioButton) rbs.nextElement();
    					if(jrbOpen.isSelected()){
    						s = new String(jrbOpen.getText());
    						Desktop.getDesktop().open(
    								Paths.get(s).toFile()) ;
    					}
    				}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		String s = null;		//s������ʵ�·����
           } );
    	gbc.gridx = 4;			//���ð�ť����
    	gbc.gridy = 11;
    	gbc.insets = new Insets(2, 2, 2, 2);
        layout.setConstraints(jbOpen, gbc);
    }
    private static void constructGUI() {		//��������
    	setupFrame();
    	addLayout();
    	addLabel();
    	addPanel();
    	addTextField();
    	addButton();
    	addRadioButton();
    	addScrollPane();
    }
    public static void setRadioButton(String s){
    	JRadioButton jrb = new JRadioButton(s);
    	radioGroup2.add(jrb);
    	jp2.add(jrb);
    	jp2.validate();			//������ʹ��ÿ����ӵ����������ʾ����
    	jp2.repaint();			//���������ˢ�¸��º��JPanel
    }
    public static void main(String[] args) {
    	//Ҳ������EventQueue.invokeLater���ο�GridBagLayoutTest.java
    	 SwingUtilities.invokeLater(new Runnable() {
             public void run() {
            	 MyJavaGUI.constructGUI();
             }
         });
    }
}
