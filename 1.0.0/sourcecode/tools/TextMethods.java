package tools;
/*//////////////////////////////////////
Methods shared by all clases of CryptoEX
//////////////////////////////////////*/
public class TextMethods {

	public String mostrarMensaje(char[] c){
		String m = new String(c);
		return m;
	}
	
	public char[] cadenaTexto (String m){
		char[] cadena = m.toCharArray();
		return cadena;
	}
	
	public char[] newChain(int l, char s){
		char[] c = new char[l];
		for (int i = 0; i < c.length; i++){
			c[i]=s;
		}
		return c;
	}
	
	public String catString(String[] s, String c){
		String cat = new String();
		for (int i = 0; i < s.length; i++){
			cat += s[i];
			cat += c;
		}
		return cat;
	}
	
}