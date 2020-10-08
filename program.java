import java.io.BufferedReader;
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
    public static List<Character> record = new ArrayList<Character>();//记录读过的字符

    public static void main(String[] args) throws IOException {
        getSym();
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
        if (i == len - 1) {
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

    public static int getSym() throws IOException {
        //        String name = args[0];
//        BufferedReader text = new BufferedReader(new FileReader(name));
        InputStreamReader in = new InputStreamReader(System.in); // 读取
        // 创建字符流缓冲区
        BufferedReader text = new BufferedReader(in); // 缓冲

        int i = 0;
        int a;//当前读入的字符，read读入的类型为数字
        char c;//当前读入的字符
        while ((a = text.read()) != -1) {
            c = (char) a;

            if (!isSpace(c) && !isNewline(c) && !isTab(c) && !isEnter(c)) {
//                读入的字符不是空格、换行、Tab
                record.add(c);
            }


            if (!record.isEmpty()) {
                c = record.get(0);
                record.remove(0);//清空
            }

            if (!isSpace(c) && !isNewline(c) && !isTab(c) && !isEnter(c)) {
                if (isLetter(c)) { //如果是字母
                    while (isLetter(c) || isDigit(c)) {
                        catToken(c); //追加
                        if (!record.isEmpty()) {
                            c = record.get(0);
                            record.remove(0);
                            if (!isSpace(c) && !isNewline(c) && !isTab(c) && !isEnter(c))
                                catToken(c);
                        }
                        a = text.read();
                        c = (char) a;
                    }
                    if (!isSpace(c) && !isNewline(c) && !isTab(c) && !isEnter(c)) {
                        record.add(c); //最后一个多读的放进list
                    }

                    symbol = reserver();

                    if (symbol != 7) {
                        System.out.println(outputWords[symbol]);
                    } else if (symbol == 7) {
                        System.out.println("Ident(" + (token.toString()) + ")");
                    }

                    token.setLength(0);
                } else if (isDigit(c)) {
                    while (isDigit(c)) {
                        catToken(c);
                        if (!record.isEmpty()) {
                            c = record.get(0);
                            record.remove(0);
                            if (isDigit(c))
                                catToken(c);
                        } else {
                            a = text.read();

                            c = (char) a;
                        }
                    }
                    if (!isSpace(c) && !isNewline(c) && !isTab(c) && !isEnter(c) && !isDigit(c)) {
                        record.add(c);
                    }

                    num = transNum();
                    symbol = 9;
                    System.out.println("Int(" + num + ")");
                    token.setLength(0);
                } else if (isColon(c)) {
                    if (!record.isEmpty()) {
                        c = record.get(0);
                        record.remove(0);
                    } else {
                        a = text.read();
                        c = (char) a;
                    }
                    if (isEqu(c)) {
                        symbol = 16;
                        System.out.println("Assign");
                    } else {
                        if (!isSpace(c) && !isNewline(c) && !isTab(c) && !isEnter(c)) {
                            record.add(c);
                        }
                        symbol = 10;
                        System.out.println("Colon");
                    }
                } else if (isPlus(c)) {
                    symbol = 11;
                    System.out.println("Plus");
                } else if (isStar(c)) {
                    symbol = 12;
                    System.out.println("Star");
                } else if (isComma(c)) {
                    symbol = 13;
                    System.out.println("Comma");
                } else if (isLpar(c)) {
                    symbol = 14;
                    System.out.println("LParenthesis");
                } else if (isRpar(c)) {
                    symbol = 15;
                    System.out.println("RParenthesis");
                } else {
                    System.out.println("Unknown");
                    break;
                }
            }
        }
        text.close();
        return 0;
    }
}

