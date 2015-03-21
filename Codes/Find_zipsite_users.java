import java.io.*;
import java.util.*;
class Find_zipsite_users
{
	public static void main(String args[])
	{
		try
		{
			Scanner in0 = new Scanner(new FileReader("zip_cen.dat"));
			int zip_count=in0.nextInt();
			String given_zipcen[]=new String[zip_count]; 
			int i1=0;
			int user_count;
			while(in0.hasNext())
			{
				given_zipcen[i1]=in0.next();
				user_count=in0.nextInt();
				i1++;
			}
			
			//Making a new Directory "zipsite_users_N" inside current working directory.
			 String newDir = "zipsite_users_N"; 
			  File f = new File(newDir);
				f.mkdir();
			 // Getting path of current working directory	
			String currentdir = System.getProperty("user.dir");
			System.out.println("Current Working Directory : "+ currentdir);
				
			for(int k=0;k<zip_count;k++)
			{	
					Scanner in = new Scanner(new FileReader("voronoi_zip_coordinates.dat"));
					String zips;
					String zipcen;
					int count=0;
					while(in.hasNext())
					{
						zips=in.next();
						zipcen=in.next();
						if(given_zipcen[k].compareTo(zipcen)==0)
						{
							count++;
						}
					}
					in.close();
				
					String ziparr[] = new String[count];
					Scanner in1 = new Scanner(new FileReader("voronoi_zip_coordinates.dat"));
					int i=0;
					while(in1.hasNext())
					{
						zips=in1.next();
						zipcen=in1.next();
						if(given_zipcen[k].compareTo(zipcen)==0)
						{
							ziparr[i]=zips;
							i++;
						}
					}

					in1.close();
					String s=null;
					String zipread=null;
					int id,age,deg;
					Scanner in2 = new Scanner(new FileReader("users.dat"));
					
					// Appending the paths,resulting into the absolute path for output files..i.e. inside zipsite_users_N directory.
					String outpath=currentdir+"\\zipsite_users_N\\zipsite"+Integer.toString(k)+".dat";
					PrintWriter out = new PrintWriter(outpath);
					out.println(given_zipcen[k]);

					while(in2.hasNext())
					{
						
						id=in2.nextInt();
						s=in2.next();
						age=in2.nextInt();
						deg=in2.nextInt();
						zipread=in2.next();
						for(int j=0;j<ziparr.length;j++)
						{
							String st=ziparr[j];
							if(st.compareTo(zipread)==0)
							{
								out.println(zipread+" "+id);
							}
						}
						
					}
					in2.close();
					out.close();
			}
		}
		catch(FileNotFoundException fileNotFoundException)
		{
			System.err.println("Error Opening File.");
			System.exit(1);
		}
	}
	
}
		