import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static String sign;
    private static boolean isRoman;
    private static final String ROMAN = "^(X|IX|IV|V?I{0,3})\\s?[\\-+/*]\\s?(X|IX|IV|V?I{0,3})$";
    private static final String ARABIC = "^([1-9]|10)\\s?([\\-+/*])\\s?([1-9]|10)$";
    private static final String WRONG_TEXT = "\nI was born with the wrong sign\nIn the wrong house\n" +
            "With the wrong ascendancy\nI took the wrong road\nThat led to\nThe wrong tendencies\n" +
            "I was in the wrong place\nAt the wrong time\nFor the wrong reason\nAnd the wrong rhyme\n" +
            "On the wrong day\nOf the wrong week\nUsed the wrong method\nWith the wrong technique\n" +
            "Wrong\nWrong";
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println(calc(br.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean isValid(String str) {
        if(str.matches(ARABIC)){
            return true;
        }else if(str.matches(ROMAN)){
            isRoman = true;
            return true;
        }
        return false;
    }
    private static String[] parse(String numExp)  {
        return numExp.replace(" ","").split("[\\-+/*]");
    }
    
    public static String calc(String input) throws Exception {
        if(!isValid(input)){
            throw new Exception(WRONG_TEXT);
        }

        searchSign(input);
        String[]mass = parse(input);
        int first;
        int second;
        if(isRoman){
            first = RomanToInt(mass[0]);
            second = RomanToInt(mass[1]);
        }else {
            first = Integer.parseInt(mass[0]);
            second = Integer.parseInt(mass[1]);
        }
        int result = 0;

        switch (sign){
            case "-" : result = first-second;break;
            case "+" : result = first+second;break;
            case "*" : result = first*second;break;
            case "/" : result = first/second;break;
        }
        if(isRoman){
            if(result < 0)throw new Exception(WRONG_TEXT);
            return IntToRoman(result);
        }

        return String.valueOf(result);
    }
    private static void searchSign(String expression){
        if(expression.contains("-"))sign = "-";
        if(expression.contains("/"))sign = "/";
        if(expression.contains("*"))sign = "*";
        if(expression.contains("+"))sign = "+";
    }
    private static int RomanToInt(String romanDigit){
        Map<Character,Integer> map = new HashMap<>(){{
            put('I',1);
            put('V',5);
            put('X',10);
            put('L',50);
            put('C',100);
        }};
        char[]chars = romanDigit.toCharArray();
        int result = map.get(chars[chars.length-1]);
        int tmp;
        for (int i = romanDigit.length()-1-1; i>=0; i--){
            tmp = map.get(chars[i]);
            if(tmp < map.get(chars[i+1])){
                result-=tmp;
            }else {
                result+=tmp;}
        }
        return result;
    }
    public static String IntToRoman(int num)
    {
        var keysRoman = new String[] { "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        var valuesArabic = new int[] { 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder result = new StringBuilder();
        int tmp = 0;

        while(tmp < keysRoman.length)
        {
            while(num >= valuesArabic[tmp])
            {
                var d = num / valuesArabic[tmp];
                num = num % valuesArabic[tmp];
                for(int i=0; i<d; i++)
                    result.append(keysRoman[tmp]);
            }
            tmp++;
        }
        return result.toString();
    }
}
