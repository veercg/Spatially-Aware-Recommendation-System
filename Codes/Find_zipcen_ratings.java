import java.io.*;
import java.util.*;
class Find_zipcen_ratings
{
	public static void main(String args[])
	{
		try
		{
			String currentdir = System.getProperty("user.dir"); // Getting path of current working directory.
			String inpath = currentdir+"\\zipsite_users_N";
			File InpFolder = new File(inpath); // Getting into zip_users_N directory.
			File Inpfiles[];
			Inpfiles = InpFolder.listFiles(); // Listing all files inside zip_users_N directory.
			
			// Retrieving all rating.dat file datas into memory(i.e. array) for faster computation
			int rating_arr[][]=new int[1000300][3]; 
			Scanner in2 = new Scanner(new FileReader("ratings.dat"));
			int i=0;
			String timestamp;
				while(in2.hasNext())
				{
					rating_arr[i][0]=in2.nextInt();
					rating_arr[i][1]=in2.nextInt();
					rating_arr[i][2]=in2.nextInt();
					timestamp=in2.next();
					i++;
				}
			in2.close();
			
			//Making a new Directory "zipsite_users_ratings_N" inside current working directory.
			String newDir = "zipsite_users_ratings_N"; 
			File f = new File(newDir);
			f.mkdir();
			System.out.println("Current Working Directory : "+ currentdir);
			
			// Naming all the required output files to be made in "zipsite_users_ratings_N" directory.
		//	String outfiles[] = { "zipsite_ratings1.dat","zipsite_ratings2.dat","zipsite_ratings3.dat","zipsite_ratings4.dat","zipsite_ratings5.dat","zipsite_ratings6.dat","zipsite_ratings7.dat","zipsite_ratings8.dat","zipsite_ratings9.dat","zipsite_ratings10.dat"};
		
			for(int k = 0;k<Inpfiles.length; k++)
			{
				Scanner in1 = new Scanner(new FileReader(Inpfiles[k]));
				String zipcen=in1.next();
				String outpath=currentdir+"\\zipsite_users_ratings_N\\zipsite_rating"+Integer.toString(k)+".dat";
			//	String outpath=currentdir+"\\zipsite_users_ratings_N\\"+outfiles[k];
				PrintWriter out = new PrintWriter(outpath);
				out.println(zipcen);
				String zip;
				int userid,r_uid,r_mid,rating;
				
				while(in1.hasNext())
				{
					zip=in1.next();
					userid=in1.nextInt();
					int j=0;
					while(j!=i)
					{
						if(userid==rating_arr[j][0])
						{
							out.println(rating_arr[j][0]+" "+rating_arr[j][1]+" "+rating_arr[j][2]);
						}
						j++;
					}				
				}
				in1.close();
				out.close();
			}
		}catch(FileNotFoundException fileNotFoundException)
			{
				System.err.println("Error Opening File.");
				System.exit(1);
			}
	}
	
}