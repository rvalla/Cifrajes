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
import javax.swing.JSlider;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.net.URL;
import tools.TextMethods;

public class CEXPrime implements ActionListener {

	//Global variables
	Color black = new Color(35, 35, 35);
	Color white = new Color(255, 255, 255);
	Color red = new Color(190, 40, 40);
	Color green = new Color(0, 255, 95);
	
	JTextArea message = new JTextArea("");
	JTextArea encrypted = new JTextArea("");
	JLabel factor = new JLabel("47");
	JButton erase = new JButton();
	JButton encrypt = new JButton();
	JButton decrypt = new JButton();
	JButton add = new JButton();
	JButton subtract = new JButton();
	JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 8, 1);

	Font frameFont;
	Font messageFont;
	Font buttonsFont;
	
	Dimension textAreaSize = new Dimension(650, 180);
	
	static int[] primes = new int[8];
	String language[] = new String[9];
	
	TextMethods tM;
	
	/*//////////
	Constructor
	/////////*/
	public CEXPrime (String os, String ln, ImageIcon ic){
		getLanguage(ln);
		loadPrimes();
		tM = new TextMethods();
		buildFrame(os, ic);
	}
	
	/*///////////////////////////////////
	Methods for encryption and decryption 
	///////////////////////////////////*/
	char[] encryptChain (char[] c, int p, int f){
		char[] r = getMessage(c, p, f);
		return r;
	}
	
	char[] getMessage (char[] c, int p, int f){
		char[] m = tM.newChain(p, ' ');
		for (int i = 0; i < c.length; i++){
			m[(i * f) % p] = c[i];
		}
		return m;
	}
	
	boolean isConfigValid(char c[], int p){
		boolean v;
		if (c.length > p){
			v = false;
		} else {
			v = true;
		}
		return v;
	}
	
	int getInverse(int f, int p){
		int r = 0;
		int i = 1;
		while (r != 1){
			r = (f * i) % p;
			if (r != 1){
				i++;
			}
		}
		return i;
	}
	
	/*//////////////////////////////////////////
	Here we load our desired prime numbers. Take
	into account that they limit the maximum
	size of the message we can encrypt.
	//////////////////////////////////////////*/
	static void loadPrimes () {
		
		primes[0]=53;
		primes[1]=107;
		primes[2]=163;
		primes[3]=223;
		primes[4]=307;
		primes[5]=563;
		primes[6]=709;
		primes[7]=1117;
	
	}
	
	/*////////////////
	Action management
	////////////////*/
	public void actionPerformed (ActionEvent ae) {
		
		if(ae.getSource() == add) {	
			String t = factor.getText();
			int n = Integer.parseInt(t);
			n = n + 1;
			factor.setText(String.valueOf(n));	
		}
		
		if(ae.getSource() == subtract) {
			String t = factor.getText();
			int n = Integer.parseInt(t);
			n = n - 1;
			if (n<1){n=1;}
			factor.setText(String.valueOf(n));
		}
		
		if(ae.getSource() == encrypt) {
			int p = primes[slider.getValue() - 1];
			String m = message.getText();
			if (m != null && !m.isEmpty()){
				char[] c = tM.textChain(m);
				int f = Integer.parseInt(factor.getText());
				if (f < p){
					if (isConfigValid(c, p)){	
						encrypted.setText(tM.getMessage(encryptChain(c, p, f)));
					} else {
						bigMessage();
					}
				} else {
					bigFactor();
				}
			} else {
				nullMessage();
			}		
		}
		
		if(ae.getSource() == decrypt) {
			int p = primes[slider.getValue() - 1];
			String m = encrypted.getText();
			if (m != null && !m.isEmpty()){
				char[] c = tM.textChain(m);
				int f = getInverse(Integer.parseInt(factor.getText()), p);
				if (f < p){
					if (isConfigValid(c, p)){	
						message.setText(tM.getMessage(encryptChain(c, p, f)));
					} else {
						bigMessage();
					}
				} else {
					bigFactor();
				}
			} else {
				nullMessage();
			}		
		}
		
		if(ae.getSource() == erase) {
			message.setText("");
			encrypted.setText("");
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
		
		add.setText("+");
		add.setFont(buttonsFont);
		add.setVisible(true);
		add.addActionListener(this);
		
		subtract.setText("-");
		subtract.setFont(buttonsFont);
		subtract.setVisible(true);
		subtract.addActionListener(this);
		
		factor.setFont(buttonsFont);
		factor.setVisible(true);
		
		slider.setVisible(true);
		slider.setPaintTicks(false);
		slider.setPaintLabels(false);
		slider.setMaximumSize(new Dimension(200, 40));

		Dimension d = new Dimension(10, 0);
		
		p.add(Box.createRigidArea(d));
		p.add(factor);
		p.add(Box.createRigidArea(new Dimension(30, 0)));
		p.add(add);
		p.add(subtract);
		p.add(Box.createRigidArea(new Dimension(30, 0)));
		p.add(slider);
		p.add(Box.createRigidArea(d));
	
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
	
	/*///////////////////////
	Loading language strings
	///////////////////////*/
	void getLanguage(String s){
	
		if (s.equals("es")){
			language[0] = "CryptoEX con primos";
			language[1] = "Mensaje";
			language[2] = "Cifrado";
			language[3] = "Encriptar";
			language[4] = "Descifrar";
			language[5] = "Borrar";
			language[6] = "<html>El mensaje es demasiado largo.<br>Pruebe ajustando el tamaño.</html>";
			language[7] = "<html>No encuentro ningún mensaje.<br>¡Escriba un mensaje!</html>";
			language[8] = "<html>El factor es demasiado grande.<br>¡Ajuste el factor o el tamaño del mensaje!</html>";
		} else {
			language[0] = "CryptoEX with primes";
			language[1] = "Message";
			language[2] = "Encrypted";
			language[3] = "Encrypt";
			language[4] = "Decrypt";
			language[5] = "Erase";
			language[6] = "<html>The message is so long.<br>Try adjusting the size.</html>";
			language[7] = "<html>I don't find any messages.<br>Write a message!</html>";
			language[8] = "<html>The factor es too big.<br>Try Adjusting the factor or the message size!</html>";
		}	
		
	}
	
	/*////////////
	Error handling
	////////////*/
	void bigMessage(){
		JOptionPane.showMessageDialog(new JFrame(),
				language[6],
				"Ups",
			JOptionPane.WARNING_MESSAGE);
	}
	
	void nullMessage(){
		JOptionPane.showMessageDialog(new JFrame(),
   				language[7],
  				"Ups",
			JOptionPane.WARNING_MESSAGE);
	}
	
	void bigFactor(){
		JOptionPane.showMessageDialog(new JFrame(),
   				language[8],
  				"Ups",
			JOptionPane.WARNING_MESSAGE);
	}
	
}