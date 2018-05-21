package AutomateRules;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoRuleBuilder
{

    public static void main(String[] args)
    {
        readAndRun();
    }

    //reading

    public static void readAndRun()
    {
        try
        {
            String filePath = new File("").getAbsolutePath().concat("/rules/Pipe Final EM7 Rules.csv");
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            br.readLine();
            List<String> list = new ArrayList<String>();
            int count = 1;
            while ((line = br.readLine()) != null)
            {
                count++;
                if (count == 48) {
                    System.out.println("");
                }
                line = line.replace("%", "");
                line = line.replace("|", "@");
                String[] input = line.split("@");
                String[] args = new String[4];
                System.out.println(count + " : " + input[0]);
                args[0] = input[1] + "-" + input[2];
                args[1] = input[5];
                args[2] = input[3];
                System.out.println(args[0]);
                args[3] = input[8];
                list.add(buildARule(args[0], args[1], args[2]));
            }
            br.close();
            printAllRules(list);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    //printing

    public static void printAllRules(List<String> rule)
    {
        try
        {
            FileWriter fw = new FileWriter("sampleOutput.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(String str: rule)
            {
                bw.write(str);
            }
            bw.close();
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void printARule(String rule)
    {
        try
        {
            FileWriter fw = new FileWriter("sampleOutput.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(rule);
            bw.close();
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static String buildARule(String name, String oid, String formula)
    {
        StringBuilder strBuild = new StringBuilder();
        //rule tag
        strBuild.append("rule \"");
        strBuild.append(name);
        strBuild.append("\"");
        strBuild.append("\n");
        //when
        strBuild.append("when\n");
        strBuild.append("\tmessage:Message()\n");
        strBuild.append("\teval(message.checkOID(\"");
        strBuild.append(oid);
        strBuild.append("\") == true)\n");
        //then
        strBuild.append("then\n");
        strBuild.append("\t");
        strBuild.append(formula);
        strBuild.append("\n");
        strBuild.append("end");

        return strBuild.toString();
    }
}
