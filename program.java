import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class program {
    public static String[] words = {"BEGIN", "END", "FOR", "DO", "IF", "THEN", "ELSE"};
    public static String[] wordsOut = {"Begin", "End", "For", "Do", "If", "Then", "Else"};

    public static List<Character> cnow = new ArrayList<Character>();
    public static char now;
    public static int symbol;
    public static int num;
    public static StringBuffer Token = new StringBuffer();

    public static void main(String[] args) throws IOException {
        String name = args[0];

        int c;
        // BufferedReader text = new BufferedReader(new FileReader("/Users/wzy/Desktop/word/src/code.txt"));
        BufferedReader text = new BufferedReader(new FileReader(name));

        int i = 0;
        while((c=text.read()) != -1){
            // System.out.println((char)c);
//            Token.setLength(0);
            //now = (char)c;

            now=(char)c;
            if(!isSpace() && !isNewline() && !isTab() && !isEnter()) {
                cnow.add(now);
//                System.out.println(now + " 这里加了now，现在长度：" + cnow.size());
            }

            // catToken();

            if(!cnow.isEmpty()){
                now=cnow.get(0);
//                System.out.println(now+"这是从cnow取到的");
                cnow.remove(0);
                // System.out.println(now+"现在的now");
            }

            if(!isSpace() && !isNewline() && !isTab() && !isEnter()) {
                if (isLetter()) { //如果是字母

                    while (isLetter() || isDigit()) {
                        // System.out.println(Token+" 现在的token");
                        catToken(); //追加
                        if(!cnow.isEmpty()){
                            now=cnow.get(0);
                            cnow.remove(0);
                            // System.out.println(now+"现在的now");
                            if(!isSpace() && !isNewline() && !isTab() && !isEnter())
                                catToken();
                        }
                        c = text.read();//读下一个直到不再是字符串

                        now = (char) c;
                    }
                    // System.out.println(Token+"这是token");
                    if(!isSpace() && !isNewline() && !isTab() && !isEnter()){
                        cnow.add(now); //最后一个多读的放进list
//                         System.out.println(now+" 这里加了now, 现在长度：" + cnow.size());
                    }

                    symbol = reserver();

                    if(symbol != 7){
                        System.out.println(wordsOut[symbol]);
                    }
                    else if(symbol == 7){
                        System.out.println("Ident("+(Token.toString()) +")");
                    }

                    //System.out.println(Token.toString() + "," + symbol);
                    Token.setLength(0);
                } else if (isDigit()) {
                    while (isDigit()) {
                        catToken();
                        if(!cnow.isEmpty()){
                            now=cnow.get(0);
                            cnow.remove(0);
                            // System.out.println(now+"现在的now");
                            if(isDigit())
                                catToken();
                        }
                        else {
                            c = text.read();

                            now = (char) c;
                        }
                    }
                    if(!isSpace() && !isNewline() && !isTab() && !isEnter() && !isDigit()){
                        cnow.add(now);
//                         System.out.println(now + " 这里加了now, 现在长度：" + cnow.size());
                    }


                    num = transNum();

                    //symbol = 9;
                    System.out.println("Int("+num+")");
                    Token.setLength(0);
                } else if (isColon()) {

                    if(!cnow.isEmpty()){
                        now = cnow.get(0);
                        cnow.remove(0);
                    }

                    else{
                        c = text.read();

                        now = (char)c;
                    }

//                     if(cnow.size()>0)
//                     System.out.println(cnow.get(0));
//                     System.out.println((int)now+"看看你是不是等号");
                    if (isEqu()) {
                        symbol = 16;
                        System.out.println("Assign");
                    } else {
                        if(!isSpace() && !isNewline() && !isTab() && !isEnter()){
                            cnow.add(now);
//                             System.out.println(now + " 这里加了now, 现在长度：" + cnow.size());
                        }

                        symbol = 10;
                        System.out.println("Colon");
                    }
                } else if (isPlus()) {
                    symbol = 11;
                    System.out.println("Plus");
                } else if (isStar()) {
                    symbol = 12;
                    System.out.println("Star");
                } else if (isComma()) {
                    symbol = 13;
                    System.out.println("Comma");
                } else if (isLpar()) {
                    symbol = 14;
                    System.out.println("LParenthesis");
                } else if (isRpar()) {
                    symbol = 15;
                    System.out.println("RParenthesis");
                } else {
                    System.out.println("Unknown");
                    break;
                }
            }
        }
    }

    private static boolean isRpar() {
        return now == 41;
    }

    private static boolean isLpar() {
        return now == 40;
    }

    private static boolean isComma() {
        return now == 44;
    }

    private static boolean isStar() {
        return now == 42;
    }

    private static boolean isPlus() {
        return now == 43;
    }

    private static boolean isEqu() {
        return now == 61;
    }

    private static boolean isColon() {
        return now == 58;
    }

    private static int transNum() {
        int judge = 0;
        int len = Token.length();

        for(int i = 0; i < Token.length(); i ++){
            if(Token.charAt(i) == '0'){
                judge ++;
            }
        }

        if(judge == Token.length()) return 0;

        for(int i = 0; i < Token.length(); i ++){
            if(Token.charAt(i) != '0'){
                Token.delete(0,i);
                break;
            }
        }

        int number = 0;


        number = Integer.valueOf(Integer.parseInt(Token.toString()));
        return number;
    }

    private static int reserver() {
        for(int i = 0; i < 7; i ++){
            if(Token.toString().equals(words[i])){
                return i;
            }
        }
        return 7;
    }

    private static void catToken() {
        Token.append(now);
    }

    public static boolean isSpace() {
        return now==32;
    }
    public static boolean isNewline(){
        return now == 10;
    }
    public static boolean isEnter(){
        return now == 13;
    }
    public static boolean isTab(){
        return now == 9;
    }
    public static boolean isLetter(){
        if((now >= 65 && now <= 90) || (now >= 97 && now <= 122))
            return true;
        else return false;
    }
    public static boolean isDigit(){
        if(now >= 48 && now <= 57)
            return true;
        else return false;
    }
}
