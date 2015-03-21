import java.io.*;
import java.util.*;
class Find_sites
{
	public static void main(String args[])
	{
		String s;
		String zips= null;
		String zip_sites[]=new String[3600];
		int zipcount[]=new int[3600];
		int id,age,deg,flag,k=0;
		
		// Reading input from file "users.dat" and storing only "zipcode" part into array zip_sites[] 
		//					and their corresponding counts(i.e. no. of users) into array "zipcount[]".	
		try
		{
			Scanner in = new Scanner(new FileReader("users.dat"));
			while(in.hasNext())
			{
				
				id=in.nextInt();
				s=in.next();
				age=in.nextInt();
				deg=in.nextInt();
				zips=in.next();
			
				
				flag=0;
				for(int j = 0;j <k; j++)  // k denotes no. of zipcodes currently stored in the array "zip_sites[]".
				{
					if(zips.compareTo(zip_sites[j])==0)  // Checking If new zip code read from file, exist in array "zip_sites[]" or not.
					{
						zipcount[j]++;    
						flag=1;
					}
				}
				if(flag==0)
				{
						
					zip_sites[k]=zips;
					zipcount[k]++;
					k++;
				}	
			}
			in.close();
		}
		catch(FileNotFoundException fileNotFoundException)
		{
			System.err.println("Error Opening File.");
			System.exit(1);
		}
		
	// Writing All distinct Zip-Codes into file "all_zips.dat" and Vornoi Site Points(centers-having >15 users)	into file "zip_cen.dat"
		try
		{
			PrintWriter out1 = new PrintWriter("zip_cen.dat");
			PrintWriter out2 = new PrintWriter("all_zips.dat");
			int centre_count=0;
			for(int i=0;i<k;i++)
			{
				if(zipcount[i]>=15)
					centre_count++;
			}
			out1.println(centre_count); // for writing no. of zip centers at the top of "zip_cen.dat".
			out2.println(k); // for writing total no. of distinct zip codes at the top of "all_zips.dat".
			for(int i=0;i<k;i++)
			{
				out2.println(zip_sites[i]);
				if(zipcount[i]>=15)
					out1.println(zip_sites[i]+" "+zipcount[i]);
			}
			out1.close();
			out2.close();
		}
		catch(FileNotFoundException fileNotFoundException)
		{
			System.err.println("Error Opening File.");
			System.exit(1);
		}	
	}
}
	