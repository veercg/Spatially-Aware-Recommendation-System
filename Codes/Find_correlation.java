// LINE:63 Initialization of array Explicitly (4000).
import java.io.*;
import java.util.*;
class Find_correlation
{
	static int user_count_in_diff_range[][];
	public static int get_index(String arr[],String val)
	{
		int index;
		for(index=0;index<arr.length;index++)
		{
			if(val.compareTo(arr[index])==0)
				break;
		}
		return index;
	}
	public static void main(String args[])
	{
		try
		{
			String currentdir = System.getProperty("user.dir"); // Getting path of current working directory.
			String newDir = "zipsite_users_Correlation_N"; 
			File f = new File(newDir);
			f.mkdir();
			// Making distinct User's Arrray userid[].	
			
			String inpath = currentdir+"\\zipsite_users_N";
			File InpFolder = new File(inpath); // Getting into zip_users_N directory.
			File Inpfiles_users[];
			Inpfiles_users = InpFolder.listFiles(); // Listing all files inside zip_users_N directory.
			
			String inpath2 = currentdir+"\\zipsite_users_ratings_N";
			File InpFolder2 = new File(inpath2); // Getting into zip_users_ratings_N directory.
			File Inpfiles_ratings[];
			Inpfiles_ratings = InpFolder2.listFiles(); // Listing all files inside zip_users_ratings_N directory.
			user_count_in_diff_range = new int [Inpfiles_users.length][4];
			for(int k1 = 0;k1<Inpfiles_users.length; k1++)
			{
				Scanner in_user0 = new Scanner(new FileReader(Inpfiles_users[k1]));
				String zipcen=in_user0.next();
				int user_count=0;
				String zips,user;
				while(in_user0.hasNext())
				{
					zips=in_user0.next();
					in_user0.next();			
					user_count++;
				}
				in_user0.close();
				String userid[]= new String[user_count];
				Scanner in_user1 = new Scanner(new FileReader(Inpfiles_users[k1]));
				zipcen=in_user1.next();
				int i=0;
				while(in_user1.hasNext())
				{
					zips=in_user1.next();
					userid[i]=in_user1.next();
					i++;
				}
				in_user1.close();
			
				// Making distinct Movies Array m_id[].
				String movie;
				String m_id[] = new String[4000];
				Scanner in_mov0 = new Scanner(new FileReader(Inpfiles_ratings[k1]));
				zipcen = in_mov0.next();
				int movie_count,flag,k=0;
				while(in_mov0.hasNext())
				{	
					in_mov0.next();
					movie=in_mov0.next();
					in_mov0.next();
				
					flag=0;
					for(int j = 0;j <k; j++)  
					{
						if(movie.compareTo(m_id[j])==0) 
						{
							flag=1;
							break;
						}
					}
					if(flag==0)
					{		
						m_id[k]=movie;
						k++;
					}	
				}
				movie_count=k;
				in_mov0.close();
				
				// Making Rating Array rating_arr[movie_count][user_count].
				int rating_arr[][]= new int[movie_count][user_count];
				Scanner in_mov1 = new Scanner(new FileReader(Inpfiles_ratings[k1]));
				zipcen = in_mov1.next();
				int rating,user_index,mov_index;
				while(in_mov1.hasNext())
				{	
					user=in_mov1.next();
					movie=in_mov1.next();
					rating=in_mov1.nextInt();
					
					user_index=get_index(userid,user);
					mov_index=get_index(m_id,movie);
					
					rating_arr[mov_index][user_index]=rating;
				}

				// FINDING CORRELATIONS AMONG USERS
				String outpath=currentdir+"\\zipsite_users_Correlation_N\\correlation"+Integer.toString(k1)+".dat";
				PrintWriter out = new PrintWriter(outpath);
				out.println(zipcen+" "+user_count);
				int a=0,b=0,c=0,d=0;
				for(i=0;i<user_count-1;i++)
				{
					for(int j=i+1;j<user_count;j++)
					{
						int common_movie_index[] = new int[movie_count];
						int m=0,n=0,sum_ratg_a=0,sum_ratg_b=0;
						for(k=0;k<movie_count;k++)
						{
							if(rating_arr[k][i]!=0&&rating_arr[k][j]!=0)
							{
								m++;
								sum_ratg_a += rating_arr[k][i];
								sum_ratg_b += rating_arr[k][j];
								common_movie_index[n]=k;
								n++;
							}
						}
						Double part1,part2,avg_rat_a,avg_rat_b,num=0.0,denom,sum1=0.0,sum2=0.0,corr_coeff,de_part1,de_part2;
						avg_rat_a=sum_ratg_a/(double)m;
						avg_rat_b=sum_ratg_b/(double)m;
						
						for(int p=0;p<n;p++)
						{
							int index1=common_movie_index[p];
							// FOR NUMERATOR PART
							part1=rating_arr[index1][i]-avg_rat_a;
							part2=rating_arr[index1][j]-avg_rat_b;
							num +=(part1*part2);
							
							//FOR DENOMINATOR
							de_part1=Math.pow(part1,2);
							de_part2=Math.pow(part2,2);
							sum1 += de_part1;
							sum2 += de_part2;
						}
						denom=Math.sqrt((sum1*sum2));
						corr_coeff=num/denom;
						out.println(userid[i]+" "+userid[j]+" "+corr_coeff);
						if(Double.isNaN(corr_coeff))
						{
							continue;
						}
						if(corr_coeff<0)
						{
							a++;
						}
						else if(corr_coeff<0.25)
							{
								b++;
							}
							else if(corr_coeff<0.6)
							{
								c++;
							}
							else if(corr_coeff<=1)
							{
								d++;
							}
					}
				}
				
				out.close();
				user_count_in_diff_range[k1][0]=a;
				user_count_in_diff_range[k1][1]=b;
				user_count_in_diff_range[k1][2]=c;
				user_count_in_diff_range[k1][3]=d;				
			}
			String outpath1=currentdir+"\\zipsite_users_Correlation_N\\count_range.dat";
			PrintWriter out1 = new PrintWriter(outpath1);
			for(int c=0;c<Inpfiles_users.length;c++)
			{
				out1.println(user_count_in_diff_range[c][0]+"|"+user_count_in_diff_range[c][1]+"|"+user_count_in_diff_range[c][2]+"|"+user_count_in_diff_range[c][3]);
			}
			out1.close();
		}
		catch(FileNotFoundException fileNotFoundException)
		{
			System.err.println("Error Opening File.");
			System.exit(1);
		}
	}
	
}