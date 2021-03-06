package cex;
/*/////////////////////////////////////////////////////////////////////////////
CEXPrime reorders the symbols from a message using multiplication in a Z_p set
(a set of prime cardinality). The prime cardinality ensures that the function
can be reversed to recover the message.
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
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.net.URL;
import tools.TextMethods;

public class CEXKey implements ActionListener {

	//Global variables
	Color black = new Color(35, 35, 35);
	Color white = new Color(255, 255, 255);
	Color red = new Color(190, 40, 40);
	Color green = new Color(0, 255, 95);
	
	JTextArea message = new JTextArea("");
	JTextArea encrypted = new JTextArea("");
	JButton erase = new JButton();
	JButton encrypt = new JButton();
	JButton decrypt = new JButton();
	JRadioButton bKeys[] = new JRadioButton[4]; 
	
	Font frameFont;
	Font messageFont;
	Font buttonsFont;
	
	Dimension textAreaSize = new Dimension(650, 180);
	
	String language[] = new String[9];
	
	String sReference = new String("");
	String sKeys[] = new String[4];
	char reference[] = new char[75];
	char keys[][] = new char[4][75];
	//The keys can be changed in "getSKeys" method
	
	int selectedKey = 0;
	
	TextMethods tM;
	
	/*//////////
	Constructor
	/////////*/
	public CEXKey (String os, String ln, ImageIcon ic){
		getLanguage(ln);
		getSReference(ln);
		getSKeys();
		getKeys();
		tM = new TextMethods();
		buildFrame(os, ic);
	}
	
	/*///////////////////////////////////
	Methods for encryption and decryption 
	///////////////////////////////////*/
	char[] encryptChain (char[] c, char[] r, char[] sK){
		for (int i = 0; i < c.length; i++){
			for (int e = 0; e < sK.length; e++){
				if (c[i] == r[e]){
					c[i] = sK[e];
					break;
				}
			}
		}
		return c;
	}
	
	/*////////////////
	Action management
	////////////////*/
	public void actionPerformed (ActionEvent ae) {
		
		if(ae.getSource() == encrypt) {
			String m = message.getText();
			if (m != null && !m.isEmpty()){
				char[] c = tM.textChain(m);
				c = encryptChain(c, reference, keys[selectedKey]);
				encrypted.setText(tM.getMessage(c));
			} else {
				nullMessage();
			}		
		}
		
		if(ae.getSource() == decrypt) {
			String m = encrypted.getText();
			if (m != null && !m.isEmpty()){
				char[] c = tM.textChain(m);
				c = encryptChain(c, keys[selectedKey], reference);
				message.setText(tM.getMessage(c));
			} else {
				nullMessage();
			}		
		}
		
		if(ae.getSource() == erase) {
			message.setText("");
			encrypted.setText("");
		}
		
		if(ae.getSource() == bKeys[0]) {
			bKeys[0].setSelected(true);
			manageRadioButtons(0, false);
			selectedKey = 0;
		}
	
		if(ae.getSource() == bKeys[1]) {
			bKeys[1].setSelected(true);
			manageRadioButtons(1, false);
			selectedKey = 1;
		}
		
		if(ae.getSource() == bKeys[2]) {
			bKeys[2].setSelected(true);
			manageRadioButtons(2, false);
			selectedKey = 2;
		}
		
		if(ae.getSource() == bKeys[3]) {
			bKeys[3].setSelected(true);
			manageRadioButtons(3, false);
			selectedKey = 3;
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
		
		JLabel selectKeys = new JLabel(language[6]);
		selectKeys.setFont(buttonsFont);
		selectKeys.setVisible(true);
		
		Dimension d = new Dimension(10, 0);
		
		p.add(Box.createRigidArea(d));
		p.add(selectKeys);
		p.add(Box.createRigidArea(new Dimension(30, 0)));
		
		for (int i = 0; i < bKeys.length; i++){
			bKeys[i] = new JRadioButton();
			bKeys[i].setText(language[7] + String.valueOf(i));
			bKeys[i].setFont(buttonsFont);
			bKeys[i].setVisible(true);
			bKeys[i].addActionListener(this);
			p.add(bKeys[i]);
			p.add(Box.createRigidArea(d));
		}
		
		bKeys[0].setSelected(true);
	
		p.setPreferredSize(new Dimension(650, 35));
	
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
	
	void manageRadioButtons (int b, boolean state){
		for (int i = 0; i < bKeys.length; i++){
			if (i != b){
				bKeys[i].setSelected(state);
			}
		}
	}
	
	/*///////////////////////
	Loading language strings
	///////////////////////*/
	void getLanguage(String s){
	
		if (s.equals("es")){
			language[0] = "CryptoEX con clave secreta";
			language[1] = "Mensaje";
			language[2] = "Cifrado";
			language[3] = "Encriptar";
			language[4] = "Descifrar";
			language[5] = "Borrar";
			language[6] = "Elegir clave:";
			language[7] = "Clave ";
			language[8] = "<html>No encuentro ningún mensaje.<br>¡Escriba un mensaje!</html>";
		} else {
			language[0] = "CryptoEX with secret key";
			language[1] = "Message";
			language[2] = "Encrypted";
			language[3] = "Encrypt";
			language[4] = "Decrypt";
			language[5] = "Erase";
			language[6] = "Select key:";
			language[7] = "Key ";
			language[8] = "<html>I don't find any messages.<br>Write a message!</html>";
		}	
		
	}
	
	/*///////////////////////
	Loading keys and alphabet
	///////////////////////*/
	void getSReference (String ln){
		if (ln.equals("es")){
			sReference = " eaosrnidlctumpbgvyqhfzjñxkw0123456789"
						+"EAOSRNIDLCTUMPBGVYQHFZJÑXKW.,;:¿?¡!()";
		} else {
			sReference = " etaoinshrdlcumwfgypbvkjxqzñ0123456789"
						+"ETAOINSHRDLCUMWFGYPBVKJXQZÑ.,;:?!()¡¿";
		}
	}
	
	//Here you can change the secret keys!
	void getSKeys(){
		sKeys[0] = " qwertyuiopasdfghjklñzxcvbnm0123456789"
				+"QWERTYUIOPASDFGHJKLÑZXCVBNM.,;:¿?¡!)(";
		sKeys[1] = " mnbvcxzñlkjhgfdsapoiuytrewq9876543210"
				+"MNBVCXZÑLKJHGFDSAPOIUYTREWQ.,;:?!()¿¡";
		sKeys[2] = "ø#@√%&/()=^*+{}[]-_.,;:<>¬ç|0123456789"
				+"ñplokimjunhybgtvfrcdexswzaqå∫∂ƒ©µ¶§†~";
		sKeys[3] = "®∑ºª=)(/¬&%$·#@!*+^}>]<:;.,_abcdefghij"
				+"ZAQXSWCDEVFRBGTNHYMJUKILOÑP0123456789";
				
	}
	
	void getKeys() {
		reference = sReference.toCharArray();
		keys[0] = sKeys[0].toCharArray();
		keys[1] = sKeys[1].toCharArray();
		keys[2] = sKeys[2].toCharArray();
		keys[3] = sKeys[3].toCharArray();
	}
	
	/*////////////
	Error handling
	////////////*/
	void nullMessage(){
		JOptionPane.showMessageDialog(new JFrame(),
   				language[8],
  				"Ups",
			JOptionPane.WARNING_MESSAGE);
	}
	
}