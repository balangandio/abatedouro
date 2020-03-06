package ml.universo42.abatedouro.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Normalizer;
import java.util.Base64;

public class StringUtil {

	/**
	 * Diz se uma string está nula ou vazia.
	 * @return true, string nula ou vazia; false, caso contrário.
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	
	/**
	 * Verifica se alguma string é vazia, dentro de um conjunto.
	 * @param strs Conjunto de strings.
	 * @return true, alguma string no conjunto é vazia (nula ou vazia); false, caso contrário.
	 */
	public static boolean isAnyEmpty(String... strs) {
		if (strs == null) {
			return false;
		}
		
		boolean is = false;
		
		for (String s : strs) {
			if (isEmpty(s)) {
				is = true;
				break;
			}
		}
		
		return is;
	}
	
	/**
	 * Gera uma string contendo dígitos aletórios.
	 * @param maxDigitos Quantidade de dígitos.
	 * @return String.
	 */
	public static String gerarRandomStr(int maxDigitos) {
		String resultado = "";
		
		int seed;
		
		for (int i = 0; i < 6; i++) {
			seed = (int) (Math.random() * 123);
			while (!((seed >= 48 && seed <= 57) || (seed >= 97 && seed <= 122))) {
				seed = (int) (Math.random() * 100);
			}
			resultado += (char) seed;
		}
		
		return resultado;
	}
	
	/**
	 * Obtem os dígitos dentro de uma string.
	 * @param str String.
	 * @return String contendo somente os dígitos numéricos; null caso a string seja nula.
	 */
	public static String getDigits(String str) {
		if (str != null) {
			char[] digitos = new char[str.length()];
			int len = 0;
			
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				
				if (Character.isDigit(c)) {
					digitos[len++] = c;
				}
			}
			
			return new String(digitos, 0, len);
		}
		
		return null;
	}
	
	/**
	 * Remove a acentuação de um campo.
	 * @param campo Campo.
	 * @return Campo sem acentuação.
	 */
	public static String removeAcentuacao(String campo) {
		return campo == null ? null : Normalizer.normalize(campo, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").trim();
	}
	
	/**
	 * Transforma um array de bytes em uma Data URI.
	 * @param bytes Bytes.
	 * @param mimeType Mime type dos dados.
	 * @return String contendo a data uri.
	 */
	public static String toDataURI(byte[] bytes, String mimeType) {
		if (bytes == null) {
			return null;
		}
		
		return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(bytes); 
	}
	
	/**
	 * Verifica se dois nomes de humanos correspondem a ao outro.
	 * @param nome1 Nome 1.
	 * @param nome2 Nome 2.
	 * @return Nomes iguais.
	 */
	public static boolean equalsNomes(String nome1, String nome2) {
		nome1 = removeAcentuacao(nome1).replace(" ", "").toUpperCase();
		nome2 = removeAcentuacao(nome2).replace(" ", "").toUpperCase();
		
		return nome1.equals(nome2);
	}
	
	/**
	 * Verifica se uma string é igual a pelo menos uma em um array.
	 * @param str String.
	 * @param array Array de strings.
	 * @return True, array contém a dada string; false, caso contrário.
	 */
	public static boolean isIn(String str, String[] array) {
		boolean in = false;
		
		for (String string : array) {
			if (str.equals(string)) {
				in = true;
				break;
			}
		}
		
		return in;
	}
	
	/**
	 * Obtem o objeto cujo representação em string confere com uma dada string.
	 * @param str String.
	 * @param objects Conjunto de objetos.
	 * @return
	 */
	public static Object valueOf(String str, Object[] objects) {
		Object obj = null;
		
		if (str != null && !str.isEmpty()) {
			str = removeAcentuacao(str.toUpperCase().trim().replace(" ", "_"));
			
			for (Object o : objects) {
				if (o.toString().contains(str)) {
					obj = o;
					break;
				}
			}
		}
		
		return obj;
	}
	
	/**
	 * Realiza a formatação de CEP.
	 * @param digitosCep String contendo os 8 digitos do CEP.
	 * @return CEP formatado.
	 */
	public static String formatarCep(String digitosCep) throws RuntimeException {
		String cep = getDigits(digitosCep);
		
		if (cep != null && cep.length() == 8) {
			cep = cep.substring(0, 2) + "." + cep.substring(2, 5) + "-" + cep.substring(5, 8);
		} else {
			throw new RuntimeException("CEP não possue 8 dígitos");
		}
		
		return cep;
	}
	
	/**
	 * Remove espaçamentos compostos por mais de um caracter ESPAÇO.
	 * @param str String.
	 * @return String com espaçamento simples.
	 */
	public static String reduceSpaces(String str) {
		Integer removeBegin = null;
		Integer removeEnd = null;
		
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') {
				if (removeBegin == null && i < str.length()-1 && str.charAt(i+1) == ' ') {
					removeBegin = i;
				}
			} else {
				if (removeBegin != null) {
					removeEnd = i;
					break;
				}
			}
		}
		
		if (removeBegin != null) {
			if (removeEnd != null) {
				return reduceSpaces(str.substring(0, removeBegin+1) + str.substring(removeEnd, str.length()));
			} else {
				return str.substring(0, removeBegin+1);
			}
		}
		
		return str;
	}
	
	/**
	 * Obtem a stacktrace de um Throwable.
	 * @param e Throwable
	 * @return StackTrace string.
	 */
	public static String toString(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString(); 
	}

	/**
	 * Verifica se uma string contém apenas dígitos.
	 * @param str String.
	 * @return True, existem apenas caracteres dígitos; false, caso contrário.
	 */
	public static boolean isOnlyDigits(String str) {
		boolean isOnlyDigits = false;
		
		if (str != null) {
			isOnlyDigits = true;
			for (int i = 0; i < str.length(); i++) {
				if (!Character.isDigit(str.charAt(i))) {
					isOnlyDigits = false;
					break;
				}
			}
		}
		
		return isOnlyDigits;
	}
	
	/**
	 * Recorta uma string caso a mesma exceda uma quantidade máxima de caracteres.
	 * @param str String.
	 * @param maxLength Quantidade máxima de caracteres.
	 * @return String.
	 */
	public static String cutExceed(String str, int maxLength) {
		if (str != null && str.length() > maxLength) {
			return str.substring(0, maxLength);
		}
		
		return str;
	}
	
	/**
	 * Obtem parte de uma string limitada pelo encontro de uma subtring, em sentido inverso.
	 * @param str String.
	 * @param substring Substring limitadora.
	 * @return Substring, ou a string passada caso a mesma não possua a string limitadora.
	 */
	public static String findUntilLastIndexOf(String str, String substring) {
		int lastIndexOf = str.lastIndexOf(substring);
		
		if (lastIndexOf != -1) {
			return str.substring(lastIndexOf + 1, str.length());
		}
		
		return str;
	}
	
	public static String upperFistLetter(String str) {
		if (str == null || str.length() <= 1) {
			return str;
		}
		
		str = str.toLowerCase();
		
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String firstName(String nome) {
		if (nome == null) {
			return null;
		}
		
		nome = nome.trim();
		
		if (nome.isEmpty()) {
			return "";
		}
		
		int i = nome.indexOf(" ");
		
		if (i == -1) {
			return nome;
		}
		
		return upperFistLetter(nome.substring(0, i));
	}
	
	public static String toFileName(String str) {
		if (str == null) {
			return null;
		}
		
		return removeAcentuacao(str).replace(" ", "_");
	}

	public static String upper(String subRegiao) {
		return subRegiao != null ? subRegiao.toUpperCase() : null;
	}
	
}
