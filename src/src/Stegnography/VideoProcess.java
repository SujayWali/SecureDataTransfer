package src.Stegnography;


import java.io.File;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author STARK-PC
 */
public class VideoProcess {
    String efilename;
    public String emb(String s, String s1)
    {
        try{
        File file = new File(s);
        File file1 = new File(s1);
        FileInputStream inputfile = new FileInputStream(s);
        FileOutputStream outputfile = new FileOutputStream("temp");
        byte abyte0[] = new byte[8];
        int i;
        int k;
        for(k = 0; (i = inputfile.read(abyte0, 0, 8)) > 0; k = i)
            outputfile.write(abyte0, 0, i);

        inputfile.close();
        for(int l = 1; l <= 8 - k; l++)
            outputfile.write(65);

        outputfile.write("DATAFILE".getBytes(), 0, 8);
        System.out.println("File name==="+file1.getName());
        StringBuffer str = new StringBuffer(file1.getName());
        str.setLength(40);
        outputfile.write(str.toString().getBytes(), 0, 40);
        inputfile = new FileInputStream(s1);
        int j;
        while((j = inputfile.read(abyte0, 0, 8)) > 0) 
            outputfile.write(abyte0, 0, j);
        inputfile.close();
        outputfile.close();
        file.delete();
        File file2 = new File("temp");
        file2.renameTo(file);
        efilename=file.getName();
        }
        catch(Exception e){
            e.printStackTrace();
            efilename="";
        }
        return efilename;
    }
    
    public String demb(String s)
    {
        boolean flag;
        String demfile = "";
        try
        {
            File file = new File(s);
            String outpath=s.substring(0, s.lastIndexOf("\\")+1);
            FileInputStream inputfile = new FileInputStream(s);
            char c = '\b';
            byte abyte0[] = new byte[c];
            String s1 = "";
            int i;
            while((i = inputfile.read(abyte0, 0, c)) > 0) 
            {
                s1 = new String(abyte0);
                if(s1.equals("DATAFILE"))
                    break;
            }
            if(!s1.equals("DATAFILE"))
            {
                flag=false;
                inputfile.close();
                return demfile;
            }
            abyte0 = new byte[50];
            inputfile.read(abyte0, 0, 50);
            s1 = new String(abyte0);
            String s2 = s1.trim();
            String fpath = s2.substring(0, s2.lastIndexOf(".") + 1) + "enc";
            System.out.println("fpath------"+fpath);
            FileOutputStream outputfile = new FileOutputStream(outpath+fpath);
            c = '\u5000';
            abyte0 = new byte[c];
            while((i = inputfile.read(abyte0, 0, c)) > 0) 
                outputfile.write(abyte0, 0, i);
            inputfile.close();
            outputfile.close();
            demfile=fpath;
        }
        catch(Exception exception)
        {
            demfile="";
            exception.printStackTrace();
            System.out.println(exception);
        }
        return demfile;
    }

}
