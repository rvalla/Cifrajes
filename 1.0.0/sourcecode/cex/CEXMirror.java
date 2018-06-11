package cex;
/*/////////////////////////////////////////////////////////////////////////////
CEXMirror encrypt the message showing the symbols in reverse order. It offers
two options: 1) reverse all the string or 2) divide the message using a
character selected by the user and then reverse each "word".
/////////////////////////////////////////////////////////////////////////////*/
import javax.swing.JComponent;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.net.URL;
import tools.TextMethods;

public class CEXMirror implements ActionListener {

	//Global variables
	Color black = new Color(35, 35, 35);
	Color white = new Color(255, 255, 255);
	Color red = new Color(190, 40, 40);
	Color green = new Color(0, 255, 95);
	
	JTextArea message = new JTextArea("");
	JTextArea encrypted = new JTextArea("");
	JTextField character = new JTextField("");
	JLabel mode = new JLabel("");
	JButton erase = new JButton();
	JButton encrypt = new JButton();
	JButton decrypt = new JButton();
	JRadioButton option[] = new JRadioButton[2];

	Font frameFont;
	Font messageFont;
	Font buttonsFont;
	
	Dimension textAreaSize = new Dimension(650, 180);
	
	String language[] = new String[10];
	
	TextMethods tM;
	
	/*//////////
	Constructor
	/////////*/
	public CEXMirror (String os, String ln, ImageIcon ic){
		getLanguage(ln);
		tM = new TextMethods();
		buildFrame(os, ic);
	}
	
	/*///////////////////////////////////
	Methods for encryption and decryption 
	///////////////////////////////////*/
	
	
	/*////////////////
	Action management
	////////////////*/
	public void actionPerformed (ActionEvent ae) {
		
		if(ae.getSource() == encrypt) {
			
		}
		
		if(ae.getSource() == decrypt) {
			
		}
		
		if(ae.getSource() == erase) {
			message.setText("");
			encrypted.setText("");
		}
		
		if(ae.getSource() == option[0]) {
			if (option[0].isSelected() == true) {
				option[1].setSelected(false);
			} else {
				option[0].setSelected(true);
			}
		}
	
		if(ae.getSource() == option[1]) {
			if (option[1].isSelected() == true){
				option[0].setSelected(false);
			} else {
				option[1].setSelected(true);
			}
		}
		
	}
	
	/*////////////
	GUI management
	////////////*/
	//Building the frame...
	void buildFrame(String os, ImageIcon ic) {
		
		JFrame v = new JFrame(language[0]);
		v.setDefaultCloseOperation(2);
		v.setResizable(true);
		
		messageFont = new Font("sansserif", Font.PLAIN, 14);
		frameFont = new Font("monospace", Font.PLAIN, 12);
		buttonsFont = new Font("monospace", Font.BOLD, 13);
		
		if (os.startsWith("Windows") || os.startsWith("Linux")){
       		v.setIconImage(ic.getImage());
		}
				
		v.setSize(700, 650);		
		v.setLocationRelativeTo(null);
		v.add(builPanel());
		v.setVisible(true);

	}
	
	//Building the frame's panels...
	JPanel builPanel(){
	
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));		
		p.add(Box.createRigidArea(new Dimension (0, 10)));
		p.add(upPanel());
		p.add(Box.createRigidArea(new Dimension (0, 10)));
		p.add(buttonsPanel1());
		p.add(Box.createRigidArea(new Dimension (0, 10)));
		p.add(buttonsPanel2());
		p.add(Box.createRigidArea(new Dimension (0, 10)));
		p.add(downPanel());
		p.add(Box.createRigidArea(new Dimension (0, 10)));
		p.add(classInfoPanel());
		p.add(Box.createRigidArea(new Dimension (0, 10)));
		
		return p;

	}
	
	JPanel upPanel(){
	
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));		
		p.add(Box.createRigidArea(new Dimension (10, 0)));
		p.add(buildMessagePanel());
		p.add(Box.createRigidArea(new Dimension (10, 0)));
		p.setPreferredSize(textAreaSize);
		
		return p;

	}
	
	JPanel downPanel(){
	
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));		
		p.add(Box.createRigidArea(new Dimension (10, 0)));
		p.add(buildEncryptionPanel());
		p.add(Box.createRigidArea(new Dimension (10, 0)));
		p.setPreferredSize(textAreaSize);
		
		return p;

	}
	
	JPanel buildMessagePanel(){
	
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBorder(BorderFactory.createTitledBorder(language[1]));
		p.add(Box.createRigidArea(new Dimension(10, 0)));
		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		message.setFont(messageFont);
		message.setMargin(new Insets(15,15,15,15));
		message.setBackground(white);
		message.setForeground(red);
		p.add(message);
		p.add(Box.createRigidArea(new Dimension(10, 0)));
	
		return p;
		
	}
	
	JPanel buildEncryptionPanel(){
	
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setBorder(BorderFactory.createTitledBorder(language[2]));
		p.add(Box.createRigidArea(new Dimension(10, 0)));
		encrypted.setLineWrap(true);
		encrypted.setWrapStyleWord(true);
		encrypted.setFont(messageFont);
		encrypted.setMargin(new Insets(15,15,15,15));
		encrypted.setForeground(green);
		encrypted.setBackground(black);
		p.add(encrypted);
		p.add(Box.createRigidArea(new Dimension(10, 0)));
	
		return p;
		
	}
	
	JPanel buttonsPanel1(){
	
		JPanel p = new JPanel();
		p.setOpaque(true);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		
		mode.setText(language[6]);
		mode.setFont(buttonsFont);
		mode.setVisible(true);
		
		option[0] = new JRadioButton();
		option[0].setText(language[7]);
		option[0].setFont(buttonsFont);
		option[0].setVisible(true);
		option[0].setSelected(true);
		option[0].addActionListener(this);
		
		option[1] = new JRadioButton();
		option[1].setText(language[8]);
		option[1].setFont(buttonsFont);
		option[1].setVisible(true);
		option[1].addActionListener(this);

		Dimension d = new Dimension(10, 0);
		
		p.add(Box.createRigidArea(d));
		p.add(mode);
		p.add(Box.createRigidArea(new Dimension(30, 0)));
		p.add(option[0]);
		p.add(Box.createRigidArea(d));
		p.add(option[1]);
		p.add(Box.createRigidArea(d));
		p.add(characterPanel());
		p.add(Box.createRigidArea(d));
	
		p.setPreferredSize(new Dimension(650, 35));
	
		return p;
	
	}
	
	JPanel characterPanel(){
		
		JPanel p = new JPanel();
		p.setOpaque(true);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setMaximumSize(new Dimension(40, 30));
	
		character.setFont(buttonsFont);
		character.setHorizontalAlignment(SwingConstants.CENTER);
		character.setVisible(true);
		
		p.add(character);
		
		return p;
		
	}
	
	JPanel buttonsPanel2(){
	
		JPanel p = new JPanel();
		p.setOpaque(true);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		
		erase.setText(language[5]);
		erase.setFont(buttonsFont);
		erase.setVisible(true);
		erase.addActionListener(this);

		encrypt.setText(language[3]);
		encrypt.setFont(buttonsFont);
		encrypt.setVisible(true);
		encrypt.addActionListener(this);
		
		decrypt.setText(language[4]);
		decrypt.setFont(buttonsFont);
		decrypt.setVisible(true);
		decrypt.addActionListener(this);
		
		Dimension d = new Dimension(25, 0);
		
		p.add(Box.createRigidArea(d));
		p.add(encrypt);
		p.add(Box.createRigidArea(d));
		p.add(decrypt);
		p.add(Box.createRigidArea(d));
		p.add(erase);
		p.add(Box.createRigidArea(d));
	
		p.setPreferredSize(new Dimension(650, 35));
	
		return p;
	
	}
	
	JPanel classInfoPanel(){
	
		JPanel p = new JPanel();
		p.setOpaque(true);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));	
		JLabel classInfo = new JLabel("<html><div align='center'>github.com/rvalla/CryptoEX"
									+ "<br>Rodrigo Valla - 2018</div><html>", JLabel.CENTER);
		classInfo.setFont(frameFont);
		p.add(classInfo);
	
		return p;
	
	}
	
	/*///////////////////////
	Loading language strings
	///////////////////////*/
	void getLanguage(String s){
	
		if (s.equals("es")){
			language[0] = "CryptoEX en espejo";
			language[1] = "Mensaje";
			language[2] = "Cifrado";
			language[3] = "Encriptar";
			language[4] = "Descifrar";
			language[5] = "Borrar";
			language[6] = "Modo:";
			language[7] = "Completo";			
			language[8] = "Con caracter:";
			language[9] = "<html>El caracter no está presente en el mensaje.<br>¡Elija otro caracter!</html>";
		} else {
			language[0] = "CryptoEX Mirror";
			language[1] = "Message";
			language[2] = "Encrypted";
			language[3] = "Encrypt";
			language[4] = "decrypt";
			language[5] = "Erase";
			language[6] = "Mode:";
			language[7] = "Complete";			
			language[8] = "With character";
			language[9] = "<html>The message doesn't contain the character.<br>Try another one!</html>";
		}	
		
	}
	
	/*////////////
	Error handling
	////////////*/
	void errorCaracter(){
		JOptionPane.showMessageDialog(new JFrame(),
   				language[8],
  				"Ups",
			JOptionPane.WARNING_MESSAGE);
	}
	
}