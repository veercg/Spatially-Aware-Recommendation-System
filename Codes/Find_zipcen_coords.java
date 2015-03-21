import java.io.*;
import java.util.*;
class Find_zipcen_coords
{
	public static void main(String args[])
	{
		
		try
		{
			
			// CREATING "zip_cen_coordinates.dat" file..
			int freq;
			Scanner in1 = new Scanner(new FileReader("zip_cen.dat")); 
			int size=in1.nextInt();
			String zipcen[]=new String [size];
			
			for (int i=0;i<size;i++)
			{
				zipcen[i]=in1.next();
				freq=in1.nextInt();   
			}
			in1.close();
			
			Scanner in2 = new Scanner(new FileReader("zips_sm.txt"));
			PrintWriter out = new PrintWriter("zip_cen_coordinates.dat");  
			int a;
			String zips=null;
			String b=null;
			String c=null;
			double longt;
			double latt;
			String d=null;
			String e=null;
			out.println(size);
			while(in2.hasNext())
			{
				a=in2.nextInt();
				zips=in2.next();
				b=in2.next();				
				c=in2.next();
				longt=in2.nextDouble();
				latt=in2.nextDouble();
				d=in2.next();
				e=in2.next();
				for(int i=0;i<size;i++)
				{
					if(zips.compareTo(zipcen[i])==0)
					{
						out.println(zips+" "+longt+" "+latt);
					}
				}
			}
			
			in2.close();
			out.close();
			
			
			// CREATING "zip_coordinates.dat" file..
			Scanner in3 = new Scanner(new FileReader("all_zips.dat")); 
			int size1=in3.nextInt();
			String zip1[]=new String [size1];
			
			for (int i=0;i<size1;i++)
			{
				zip1[i]=in3.next();
			}
			in3.close();
			
			Scanner in4 = new Scanner(new FileReader("zips_sm.txt"));
			PrintWriter out1 = new PrintWriter("zip_coordinates.dat"); 
			int a1;
			String zips1=null;
			String b1=null;
			String c1=null;
			double longt1;
			double latt1;
			String d1=null;
			String e1=null;
			while(in4.hasNext())
			{
				a1=in4.nextInt();
				zips1=in4.next();
				b1=in4.next();				
				c1=in4.next();
				longt1=in4.nextDouble();
				latt1=in4.nextDouble();
				d1=in4.next();
				e1=in4.next();
				for(int i=0;i<size1;i++)
				{
					if(zips1.compareTo(zip1[i])==0)
					{
						out1.println(zips1+" "+longt1+" "+latt1);
					}
				}
			}
			
			in4.close();
			out1.close();
			
		}
		catch(FileNotFoundException fileNotFoundException)
		{
			System.err.println("Error Opening File.");
			System.exit(1);
		}
	}
}