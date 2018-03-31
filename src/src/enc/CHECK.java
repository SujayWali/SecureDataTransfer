/*s
 * DBS.java
 *
 * Created on July 15, 2017, 3:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

 package src.enc;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
class filedialog extends JFrame
{
	 String name;
	 filedialog()
	 {
	 FileDialog fd=new FileDialog(filedialog.this,"Save as",FileDialog.SAVE);
	 fd.show();
	 if (fd.getFile()!=null)
	 {
		 name=fd.getDirectory()+fd.getFile();
		 setTitle(name);
	 }
	}
	public String getfile()
	{
		return name;
	}
};

public class CHECK

{
  static String name;
   public boolean CHECKT(int op,String x,javax.swing.JTextArea jLabel9)
       {
        boolean flag=false;
	String toBeSaved = "";
		String theText;
	   int  choice=op;
	    name=x;
		if(choice == 1)
	    {
		
		try{
		byte[] theFile = getFile(name);
		
	
		
		String key = JOptionPane.showInputDialog("Enter your key (the longer the better):");

		
	     Encryption enc = new Encryption(theFile,key);
		
		
		
  		enc.encrypt();
		
		

		toBeSaved=saveFile(enc.getFileBytes());
		jLabel9.setText(toBeSaved);
		JOptionPane.showMessageDialog(null,"\nYour file has been encrypted and saved\n","message",JOptionPane.INFORMATION_MESSAGE);
                flag=true;}catch(Exception e){flag=false;jLabel9.setText("");e.printStackTrace();}
		}
	
	
	else if(choice == 2)
	    {
		
			
              try{
		byte[] theFile = getFile(name);

		JPasswordField pf = new JPasswordField("Enter the key: ");
		
		String key = JOptionPane.showInputDialog("Enter the key: ");
		
		Encryption enc = new Encryption(theFile,key);
		enc.decrypt();
		
		toBeSaved=saveFile(enc.getFileBytes());
		jLabel9.setText(toBeSaved);
		JOptionPane.showMessageDialog(null,"\nYour file has been decrypted and saved\n","message",JOptionPane.INFORMATION_MESSAGE);
		flag=true;}catch(Exception e){flag=false;jLabel9.setText("");e.printStackTrace();}	
	    }
	
	
	
	return flag;
	} 
		    
  
    
    public static byte[] getFile(String name)
    {
       
 	byte[] readFromFile = null;
	String txt=name;
		try
	    {
		FileInputStream in = new FileInputStream(txt);
		readFromFile = new byte[in.available()];
		in.read(readFromFile);
		in.close();
	    }
	    catch(IOException e)
	    {
		System.out.println("\nSorry  file not found!\n");
			    }
	return readFromFile;
    }
  
    
    public static String saveFile(byte[] toSave)
    {
	
	  String txt ;
	  filedialog fd=new filedialog();
	  txt=fd.getfile();
	  try
		{
	  FileOutputStream out = new FileOutputStream(txt);
	  out.write(toSave);
	  out.close();
	 
	    }
	   catch(IOException e)
	    {
		System.out.println(" INTERNAL ERROR\n" );
			    }
           return txt; 	
    }

    


class Encryption

{
        private String key;
    private char[] keys;
    private byte[] fileBytes;
    private byte[] fileBytez;
    private int middle;
    private int size;
    private long alpha;
    private long beta;
    private long gamma;
    private long delta;
    private long sumA;
    private long sumB;
    private long sumC;
    private long sumD;
    private int forLevel2;
    public Encryption(byte[] fileBytes,String key)
    {
	this.fileBytes = fileBytes;
	this.key = key;
	

	
	keys = new char[key.length()];
	middle = (int)(fileBytes.length/2);
	delta = 0x9e3779b9;///algo
	alpha = 0x7f2637c6;
	beta  = 0x5d656dc8;
	gamma = 0x653654d9;
	sumA = (long)(alpha >> key.charAt(0));
	sumB = (long)(beta << key.charAt(1));
	sumC = (long)(gamma >> key.charAt(2));
	sumD = (long)(delta >> key.charAt(3));	
	
	
	
	if (fileBytes.length%5 > 0)
	    {
		size = (int)((fileBytes.length-1)/5);
	    }
	else size = (int)(fileBytes.length/5);
	
	forLevel2 = key.length();
    }
 
    
    public void setFileBytes(byte[] newBytes)
    {
	fileBytes = newBytes;
    }
    
    public byte[] getFileBytes()
    {
	return fileBytes;
    }
    //check paper
    //java docs check tes
    //
    

    
    public void encrypt()
    {
	
	int f = 0;
	boolean truth = true;
	
	key = keyStream(); 
	
	keys = new char[key.length()];
	
	for(int c = 0;c<key.length();c++)
	    {
		keys[c] = key.charAt(c);
	    }
		
	
	for(int extra = 0;extra<127;extra++)
	    {
		for(int i = 0;i<fileBytes.length;i = i + keys.length)
		    {
			if (truth == false)
			    break;
			f = 0;
			for(int j = i;j<i+keys.length;j++)
			    {
				
				if(j>=fileBytes.length)
				    {
					truth = false;
					break;
				    }
				
				fileBytes[j] = (byte)((fileBytes[j] ^
						       (keys[f] - 'A' << sumD)) ^ (keys[f] + sumD));
				
				sumD -= delta;
				f++;
							    }
			
					    }
		
		fileBytes = splitNSwap(fileBytes);
		
		setFileBytes(fileBytes);
		
	    }
	
	setFileBytes(level2(fileBytes,true));
	
    }
    
    
    public void decrypt()
    {
	
	setFileBytes(level2(fileBytes,false));
	int f = 0;
	boolean truth = true;
	
	key = keyStream();
	
	keys = new char[key.length()];
	
	for(int c = 0;c<key.length();c++)
	    {
		keys[c] = key.charAt(c);
	    }
	
	
	
	
	for(int extra = 0;extra<127;extra++)
	    {
		fileBytes = getFileBytes();
		
		fileBytes = splitNSwap(fileBytes);
		
  		for(int i = 0;i<fileBytes.length;i = i + keys.length)
		    {
			
			if (truth == false)
			    break;
			
			f = 0;
			for(int j = i;j<i+keys.length;j++)
			    {
				
				if(j>=fileBytes.length)
				    {
					truth = false;
					break;
				    }
				
				fileBytes[j] = (byte)((fileBytes[j] ^
						       (keys[f] - 'A' << sumD)) ^ (keys[f] + sumD));
				
				sumD -= delta;
				f++;				
			    }			
		    }
		setFileBytes(fileBytes);		
	    }			
    }
    

    
    public byte[] splitNSwap(byte[] zeBytes)
    {
	if(zeBytes.length%2==0)
	    {
		middle = (int)(zeBytes.length/2);
	    }
	else middle = (int)((zeBytes.length-1)/2);
	fileBytez = new byte[zeBytes.length];
	for(int reverse = 0;reverse<middle;reverse++)
	    {
		fileBytez[reverse] = (byte)(zeBytes[reverse+middle]^fileBytez[reverse]);
	    }
	
	for (int reverseB = middle;reverseB<zeBytes.length;reverseB++)
	    {
		fileBytez[reverseB] = (byte)(zeBytes[reverseB - middle]^fileBytez[reverseB]);
	    }
	setFileBytes(fileBytez);
	return fileBytez;
	
    } 
    public byte[] level2(byte[] oldBytes, boolean state)
    {
	
	
     
	int s = forLevel2;

	int stop = oldBytes.length%s;

	
	byte[] newBytes = new byte[oldBytes.length];
	byte[] tempBytes = new byte[oldBytes.length-stop];
	byte[] resultBytes = new byte[oldBytes.length];
	byte[] remainderBytes = new byte[stop];

	int a = oldBytes.length-stop;



	for (int old = 0;old<oldBytes.length-stop;old++)
	    {
		tempBytes[old] = oldBytes[old];
	    }

	for (int old = 0;old<stop;old++)
	    {
		remainderBytes[old] = oldBytes[(oldBytes.length-stop+old)];
	    }


	
	if (state)
	    {
		for (int outer = 0;outer<s;outer++)
		    {
			for (int c = outer;c<a+outer;c+=s)
			    {
				if(c+s<oldBytes.length)
				 {
					newBytes[c] = (byte)(oldBytes[c+s]-sumA);
					newBytes[c+s] = (byte)(oldBytes[c]+sumB);
					    }
					else break;
				
			    }
		    }
	    }	
	else if (!state)
	    {
		for (int outer = s-1;outer >=0;outer--)
		    {
			for(int c = (a-1-outer);c>=0-outer;c-=s)
			    {
				if(c-s>=0)
				  {
					newBytes[c-s] = (byte)(oldBytes[c]-sumB);
					newBytes[c] = (byte)(oldBytes[c-s]+sumA);
				
					  }
					else break;
			    }
			if (outer <= 0)break;else continue;
		    }
		
		
	    }
	    

	

	
	setFileBytes(newBytes);
	return newBytes;
    }
    
    
    
    
    public String keyStream()
    {
	
	
	
	
	String answer = key;
	String thekey = key;
	
	for(int i = 0;i<(thekey.length()*128);i++)
	    {
		answer = answer + getPart(thekey);
		thekey = getPart(thekey);
	    }
	return answer;
	}
    
  
    
    public String getPart(String thekey)
    {
	char[] keyPart = new char[thekey.length()];
	String result = "";
	
	for(int c = 0;c<thekey.length()-1;c++)
	    {
		keyPart[c] = (char)(thekey.charAt(c+1) - 1);
	    }
	
	keyPart[thekey.length()-1] = thekey.charAt(0);
	for(int put = 0;put<keyPart.length;put++)
	    {
		result = result + keyPart[put];
	    }
	
	return result;
    }
    


	}
}



