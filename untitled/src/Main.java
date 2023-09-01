import java.util.Scanner;
import java.util.HashMap;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws Exception{


        System.out.println("Введите математическое выражение:");

        Scanner inputExsp = new Scanner(System.in);
        String mathExspression = inputExsp.nextLine();

        String result = calc(mathExspression);

        System.out.println(result);

    }

    public static String calc(String input) throws Exception {
        String[] scrMathAct = {"\\+", "-", "/","\\*"};

        FindMathAct mathInExsp = new FindMathAct();
        mathInExsp.exspression = input;
        int act = mathInExsp.defFindMathAct();

        if(act == - 1){
            throw new Exception(" строка не является математической операцией");

        }


        String[] data = input.split(scrMathAct[act]);
        if(data.length!=2){
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }


        IsRoman dataA = new IsRoman();
        IsRoman dataB = new IsRoman();
        dataA.data = data[0];
        dataB.data = data[1];
        int IsRomanA = dataA.FindeRoman();
        int IsRomanB = dataB.FindeRoman();


        if(IsRomanA == IsRomanB){}
        else {
            throw new Exception("используются одновременно разные системы счисления");}


            if(IsRomanA == 1){
                int ConvertRomanA = dataA.ConvertRomanToArabian();
                int ConvertRomanB = dataB.ConvertRomanToArabian();
                if (ConvertRomanA <= ConvertRomanB && scrMathAct[act] == "-"){
                    throw new Exception("в римской системе нет отрицательных чисел");
                }
                if (ConvertRomanA > 10 || ConvertRomanB > 10){
                    throw new Exception("Калькулятор принимает на вход числа до 10 включительно");
                }

                CalculateExp arabian = new CalculateExp();
                arabian.a = ConvertRomanA;
                arabian.b = ConvertRomanB;
                arabian.act = act;
                int resulsExp = arabian.calculateResult();


                ConvertArabianToRoman romanResult = new ConvertArabianToRoman();
                romanResult.rowData = resulsExp;
                String result = romanResult.Convertation();
                return result;
            }




                CalculateExp arabian = new CalculateExp();
                arabian.a = Integer.parseInt(data[0]);
                arabian.b = Integer.parseInt(data[1]);
                arabian.act = act;
                if (Integer.parseInt(data[1])>10 || Integer.parseInt(data[0]) >10){
                    throw new Exception("Калькулятор принимает на вход числа от 1 до 10 включительно");
                }
                    else if(Integer.parseInt(data[1])<1 || Integer.parseInt(data[0]) < 1){
                    throw new Exception("Калькулятор принимает на вход числа от 1 до 10 включительно");                }
                int result = arabian.calculateResult();
                String strResult = Integer.toString(result);
                return strResult;

    }
}
    class FindMathAct{
        String exspression;

        int defFindMathAct(){
            String[] mathAct = {"+", "-", "/","*"};

            int mathActIndex = -1;

            for (int i = 0; i < mathAct.length; i++){
                if (exspression.contains(mathAct[i])){
                    mathActIndex = i;
                    break;
                }
            }
            return mathActIndex;
        }

    }
    class CalculateExp {
        int a;
        int b;
        int act;

        int calculateResult(){
            String[] mathAct = {"+", "-", "/", "*"};

            if (mathAct[act] == "+"){
                int result = a + b;
                return result;
            }
            if (mathAct[act] == "-"){
                int result = a - b;
                return result;
            }
            if (mathAct[act] == "/"){
                int result = a / b;
                return result;
            }
            if (mathAct[act] == "*"){
                int result = a * b;
                return result;
            }
            return 0;
        }
    }
    class IsRoman{
    String data;

    HashMap<Character, Integer> romanKeyMap = new HashMap<>();
    int FindeRoman(){
        romanKeyMap.put('I', 1);
        romanKeyMap.put('V', 5);
        romanKeyMap.put('X', 10);
        romanKeyMap.put('L', 50);
        romanKeyMap.put('C', 100);
        romanKeyMap.put('D', 500);
        romanKeyMap.put('M', 1000);

        char[] dataArr = data.toCharArray();
        int findRoman = 1;
        int testRoman = 0;

        for(int i = 0; i < data.length(); i++){
            try{
                testRoman = romanKeyMap.get(dataArr[i]);
            }
            catch(Exception e){
                findRoman = -1;
                return findRoman;
            }
        }
        return findRoman;
    }
    int ConvertRomanToArabian(){
        int endIndex = data.length()-1;
        char[] dataArr = data.toCharArray();
        int arabian;

        int result = romanKeyMap.get(dataArr[endIndex]);

        for (int i = endIndex-1; i >= 0; i--){
            arabian = romanKeyMap.get(dataArr[i]);

            if (arabian < romanKeyMap.get(dataArr[i+1])){
                result -= arabian;
            }
            else {
                result += arabian;
            }
        }
        return result;
    }
}

    class ConvertArabianToRoman{
        int rowData;
        TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

        String Convertation(){
            arabianKeyMap.put(100, "C");
            arabianKeyMap.put(90, "XC");
            arabianKeyMap.put(50, "L");
            arabianKeyMap.put(40, "XL");
            arabianKeyMap.put(10, "X");
            arabianKeyMap.put(5, "V");
            arabianKeyMap.put(4, "IV");
            arabianKeyMap.put(1, "I");

            int arabianKey;
            String roman = "";

            do {
                arabianKey = arabianKeyMap.floorKey(rowData);
                roman += arabianKeyMap.get(arabianKey);
                rowData -= arabianKey;
            }
            while (rowData != 0);

            return roman;
        }
    }

