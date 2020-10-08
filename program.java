import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//enum Symbol {
//    BEGINSY, ELSESY, ENDSY, FORSY, IFSY, THENSY,IDSY,INTSY,COLONSY,PLUSSY,STARSY,COMMASY,LPARSY,RPARSY,ASSIGNSY
//};//当前所识别的单词的类型

public class program {
    public static String[] inputWords = {"BEGIN", "END", "FOR", "DO", "IF", "THEN", "ELSE"};//Token
    public static String[] outputWords = {"Begin", "End", "For", "Do", "If", "Then", "Else"};//输出格式

    public static StringBuffer token = new StringBuffer();//单词的字符串
    public static int num;//当前存入的整型数值
    public static int symbol;//当前所识别的单词的类型
//    public static List<Character> record = new ArrayList<Character>();//记录读过的字符

    public static void main(String[] args) throws IOException {
//        String name = args[0];
//        BufferedReader text = new BufferedReader(new FileReader(name));
        BufferedReader text = new BufferedReader(new FileReader("./test.txt"));
//        InputStreamReader in = new InputStreamReader(System.in); // 读取
//        BufferedReader text = new BufferedReader(in); // 缓冲

        int i = 0;
        int a = text.read();//当前读入的字符，read读入的类型为数字
        char c = (char) a;//当前读入的字符
        char record = c;//记录

        while (true) {
            token.setLength(0);
            while (isSpace(c) || isNewline(c) || isTab(c) || isEnter(c)) {
//                如果读入的字符是空格、换行、Tab、Enter,继续读入下一个字符
                a = text.read();
                if(a == -1) break;
                c = (char) a;
                record = c;
                continue;
            }
            if(a == -1 ) break;

            if (isLetter(c)) { //如果是字母
                while (isLetter(c) || isDigit(c)) {
                    catToken(c); //追加
                    a = text.read();
                    if(a == -1) break;
                    c = (char) a;
                    record = c;
                }
                if(a == -1 ) break;

                c = record;//回退

                symbol = reserver();

                if (symbol != 7) {
                    System.out.println(outputWords[symbol]);
                }
                else if (symbol == 7) {
                    System.out.println("Ident(" + (token.toString()) + ")");
                }
                continue;
            }
            else if (isDigit(c)) {
                while (isDigit(c)) {
                    catToken(c);
                    a = text.read();
                    if(a == -1) break;
                    c = (char) a;
                    record = c;
                }

                c = record;//回退

                num = transNum();
                symbol = 9;
                System.out.println("Int(" + num + ")");
                continue;
            }
            else if (isColon(c)) {
                a = text.read();
                if(a == -1) break;
                c = (char) a;
                record = c;

                if (isEqu(c)) {
                    symbol = 16;
                    System.out.println("Assign");
                }
                else {
                    c = record;//回退
                    symbol = 10;
                    System.out.println("Colon");
                    continue;
                }
            }
            else if (isPlus(c)) {
                symbol = 11;
                System.out.println("Plus");
            }
            else if (isStar(c)) {
                symbol = 12;
                System.out.println("Star");
            }
            else if (isComma(c)) {
                symbol = 13;
                System.out.println("Comma");
            }
            else if (isLpar(c)) {
                symbol = 14;
                System.out.println("LParenthesis");
            }
            else if (isRpar(c)) {
                symbol = 15;
                System.out.println("RParenthesis");
            }
            else {
                System.out.println("Unknown");
                break;
            }
            a = text.read();
            if(a == -1) break;
            c = (char) a;
            record = c;
        }

        text.close();
    }

    private static boolean isPlus(char c) {
        return c == 43;
    }

    private static boolean isStar(char c) {
        return c == 42;
    }

    private static boolean isLpar(char c) {
        return c == 40;
    }

    private static boolean isRpar(char c) {
        return c == 41;
    }

    private static boolean isComma(char c) {
        return c == 44;
    }

    private static boolean isEqu(char c) {
        return c == 61;
    }

    private static boolean isColon(char c) {
        return c == 58;
    }

    private static int transNum() {
        int len = token.length();
        int i;
        for (i = 0; i < len; i++) {
            if (token.charAt(i) != '0') {
                token.delete(0, i);//去掉前置0
                break;
            }
        }
        if (i == len) {
            return 0;
        }
        return Integer.parseInt(token.toString());
    }

    private static int reserver() {//是否保留字
        for (int i = 0; i < 7; i++) {
            if (token.toString().equals(inputWords[i])) {
                return i;
            }
        }
        return 7;
    }

    private static void catToken(char c) {
        token.append(c);
    }

    public static boolean isSpace(char c) {
        return c == 32;
    }

    public static boolean isNewline(char c) {
        return c == 10;
    }

    public static boolean isEnter(char c) {
        return c == 13;
    }

    public static boolean isTab(char c) {

        return c == 9;
    }

    public static boolean isLetter(char c) {
        if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122))
            return true;
        else return false;
    }

    public static boolean isDigit(char c) {
        if (c >= 48 && c <= 57)
            return true;
        else return false;
    }
}
