import java.util.*;

public class FractionCalculator {
	
	public static void main(String [] args){
		Scanner console = new Scanner(System.in);
		String input = "who lives in a pineapple under the sea?";
		while(!input.equals("quit")){
			try {
				System.out.println(processInput(input));
			} catch (Exception e) {
				System.out.println("INVALID INPUT");
			}
			System.out.print("Input: ");
			input = console.nextLine();
		}
		System.out.println("Goodbye ");
		console.close();
	}
	
	public static int locateNextChar(String str, int currentIndex, char findMe){
		str = str.substring(currentIndex);
		return currentIndex + str.indexOf(findMe);
	}
	
	public static String doMath(int whole1, int whole2, int n1, int n2, int d1, int d2, char operator){
		//testing for errors
		if(d1 == 0 || d2 == 0){
			return "ERROR: cannot divide by zero";
		}
		int whole;
		int nume;
		int denom;
		if(operator == '-'){
			nume = findCommonDenom(topHeavafyFraction(whole1, n1, d1)[0], topHeavafyFraction(whole1, n1, d1)[1], topHeavafyFraction(whole2, n2, d2)[0], topHeavafyFraction(whole2, n2, d2)[1])[0] - findCommonDenom(topHeavafyFraction(whole1, n1, d1)[0], topHeavafyFraction(whole1, n1, d1)[1], topHeavafyFraction(whole2, n2, d2)[0], topHeavafyFraction(whole2, n2, d2)[1])[1];
			denom = findCommonDenom(topHeavafyFraction(whole1, n1, d1)[0], topHeavafyFraction(whole1, n1, d1)[1], topHeavafyFraction(whole2, n2, d2)[0], topHeavafyFraction(whole2, n2, d2)[1])[2];
		} else if(operator == '*'){
			nume = topHeavafyFraction(whole1, n1, d1)[0] * topHeavafyFraction(whole2, n2, d2)[0];
			denom = topHeavafyFraction(whole1, n1, d1)[1] * topHeavafyFraction(whole2, n2, d2)[1];
		}else if(operator == '/'){
			nume = topHeavafyFraction(whole1, n1, d1)[0] * topHeavafyFraction(whole2, n2, d2)[1];
			denom = topHeavafyFraction(whole1, n1, d1)[1] * topHeavafyFraction(whole2, n2, d2)[0];
		} else {
			nume = findCommonDenom(topHeavafyFraction(whole1, n1, d1)[0], topHeavafyFraction(whole1, n1, d1)[1], topHeavafyFraction(whole2, n2, d2)[0], topHeavafyFraction(whole2, n2, d2)[1])[0] + findCommonDenom(topHeavafyFraction(whole1, n1, d1)[0], topHeavafyFraction(whole1, n1, d1)[1], topHeavafyFraction(whole2, n2, d2)[0], topHeavafyFraction(whole2, n2, d2)[1])[1];
			denom = findCommonDenom(topHeavafyFraction(whole1, n1, d1)[0], topHeavafyFraction(whole1, n1, d1)[1], topHeavafyFraction(whole2, n2, d2)[0], topHeavafyFraction(whole2, n2, d2)[1])[2];
		}
		//System.out.println(whole + "_" + nume + "/" + denom);
		int[] preCalcSimpFrac = new int[3];
		for(int element = 0; element < 3; element++){
			preCalcSimpFrac[element] = simplyfyFraction(nume, denom)[element];
		}
		whole = preCalcSimpFrac[0];		
		nume = preCalcSimpFrac[1];
		denom = preCalcSimpFrac[2];	
		if(whole != 0 && nume != 0){
			return whole + "_" + nume + "/" + denom;
		} else if(whole == 0 && nume != 0){
			return nume + "/" + denom;
		}
		return whole + "";
	}
	
	public static int[] topHeavafyFraction(int whole, int numerator, int denomenator) {
		boolean negativity = false;
		if(whole < 0 || numerator < 0 || denomenator < 0){
			negativity = true;
		}
		int nume = Math.abs(numerator) + (Math.abs(denomenator) * Math.abs(whole));
		int[] topHfrac = {nume, Math.abs(denomenator)};
		if(negativity){
			topHfrac[0] = -nume;
		}
		return topHfrac;
	}
	
	public static int[] simplyfyFraction(int nume, int denom){
		boolean negativity = false;
		if(nume < 0 || denom < 0){
			negativity = true;
		}
		int whole = 0;
		int numerator = Math.abs(nume);
		int denomenator = Math.abs(denom);
		if(numerator > denomenator){
			while(numerator > denomenator){
				numerator -= denomenator;
				whole++;
			}
		} 
		if(numerator == denomenator){
			whole++;
			numerator = 0;
			denomenator = 0;
		}
		//loops through checking to see if it is simplifyable and breaks when it no longer becomes simpleir
		boolean canSimp = true;
		int preSimpDenom;
		while(canSimp){
			preSimpDenom = denomenator;
			for(int num = 1; num < denomenator/2 + 1; num++){
				if(denomenator % num == 0 && numerator % num == 0){
					numerator /= num;
					denomenator /= num;
				}
			}
			if(preSimpDenom == denomenator){
				canSimp = false;
			}
		}
		int[] simpFrac = {whole, numerator, denomenator};
		if(negativity && whole != 0){
			simpFrac[0] = -whole;
		} else if(negativity){
			simpFrac[1] = -numerator;
		}
		return simpFrac;
	}
	
	public static int[] findCommonDenom(int n1, int d1, int n2, int d2){
		int nume1 = n1 * d2;
		int nume2 = n2 * d1;
		int commDenom = d1 * d2;
		return new int[]{nume1, nume2, commDenom};
	}
	
	public static String processInput(String str){
		if(str.equals("who lives in a pineapple under the sea?")){
			return "Welcome, this is a fraction calculator";
		}
		int whole1 = 0;
		int whole2 = 0;
		int numerator1 = 0;
		int denomenator1 = 1;
		int numerator2 = 0;
		int denomenator2 = 1;
		char operator = '+'; //default as + so if only 1 num inputed it will add nothing to it
		int operIndex = 0;
		//must go first to get operator so it doesn't break when testing for if it is the first or second number
		for(int ch = 0; ch < str.length(); ch++){
			if(ch > 0 && ch < str.length() - 2 && (str.substring(ch - 1, ch + 2).equals(" + ") || str.substring(ch - 1, ch + 2).equals(" * ") || str.substring(ch - 1, ch + 2).equals(" - ") || str.substring(ch - 1, ch + 2).equals(" / "))){
				operator = str.charAt(ch);
				operIndex = ch - 1;
			}
		}
		//makes it not break if there is no operator
		if(operIndex == 0){
			operIndex = str.length();
		}
		for(int ch = 0; ch < str.length(); ch++){
			if(str.charAt(ch) == '_'){	
				if(operIndex > ch){ //tests if first or second number
					whole1 = Integer.parseInt(str.substring(0,ch));
					numerator1 = Integer.parseInt(str.substring(ch + 1, locateNextChar(str, ch, '/')));
				} else {
					whole2 = Integer.parseInt(str.substring(operIndex + 3, ch));
					numerator2 = Integer.parseInt(str.substring(ch + 1, locateNextChar(str, ch, '/')));
				}
			} else if(str.charAt(ch) == '/' && !str.substring(ch - 1, ch + 2).equals(" / ")){
				if(operIndex > ch){ //tests if first or second number
					denomenator1 = Integer.parseInt(str.substring(ch + 1, operIndex));
				} else {
					denomenator2 = Integer.parseInt(str.substring(ch + 1));
				}
			}
			//testing for fractions or whole numbers by themselves
			if(!str.substring(0, operIndex).contains("_")){
				if(!str.substring(0, operIndex).contains("/")){
					whole1 = Integer.parseInt(str.substring(0, operIndex));
				} else {
					numerator1 = Integer.parseInt(str.substring(0, locateNextChar(str.substring(0, operIndex), 0, '/')));
				}
			}
			if(str.contains(" " + operator + " ")){
				if(!str.substring(operIndex).contains("_")){
					if(!str.substring(operIndex + 3).contains("/")){
						whole2 = Integer.parseInt(str.substring(operIndex + 3));
					} else {
						numerator2 = Integer.parseInt(str.substring(operIndex + 3, locateNextChar(str.substring(operIndex + 3), 0, '/') + operIndex + 3));
					}
				}
			}
		}
		//System.out.println(whole1 + "_" + numerator1 + "/" + denomenator1 + " " + operator + " " + whole2 + "_" + numerator2 + "/" + denomenator2);
		return doMath(whole1, whole2, numerator1, numerator2, denomenator1, denomenator2, operator);
	}
		
}