import java.io.*;
import java.util.*;
class Find_zip_voronoi
{
	public static void main(String args[])
	{
		
		try
		{
			
			Scanner in1 = new Scanner(new FileReader("zip_cen_coordinates.dat")); 
			int size=in1.nextInt();
			String zipid[]= new String [size]; 
			double coord_arr[][]=new double[size][2];  
			int i=0;
			while(in1.hasNext())
			{
				
				zipid[i]=in1.next();
				coord_arr[i][0]=in1.nextFloat();
				coord_arr[i][1]=in1.nextFloat();
				i++;
			}
			
			Scanner in2 = new Scanner(new FileReader("zip_coordinates.dat")); 
			PrintWriter out = new PrintWriter("voronoi_zip_coordinates.dat"); 
			String zip;
			double longtx,latty;
			while(in2.hasNext())
			{
				zip=in2.next();
				longtx=in2.nextDouble();
				latty=in2.nextDouble();
				
				int min_pos=0;
				double min=Math.sqrt(((longtx-coord_arr[0][0])*(longtx-coord_arr[0][0]))+((latty-coord_arr[0][1])*(latty-coord_arr[0][1])));
				for(int j=1;j<size;j++)
				{
					double dist=Math.sqrt(((longtx-coord_arr[j][0])*(longtx-coord_arr[j][0]))+((latty-coord_arr[j][1])*(latty-coord_arr[j][1])));
				
					if(dist<min)
					{
						min=dist;
						min_pos=j;
					}
					
				}
				
				out.println(zip+" "+zipid[min_pos]);
			}
			in1.close();
			in2.close();
			out.close();
		}
		catch(FileNotFoundException fileNotFoundException)
		{
			System.err.println("Error Opening File.");
			System.exit(1);
		}
	}	
}