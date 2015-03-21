import java.io.*;
import java.util.*;
class Find_recommendation
{
	static int   userid;
	static double thresh_val;
	static String mov_catg[] = {"Animation","Children's","Comedy","Adventure","Fantasy","Romance","Drama","Action","Crime","Thriller","Horror","Sci-Fi","Documentary","War","Musical","Mystery","Film-Noir","Western"};
	static int catg_count[] = new int[18];
	public static int chk_in_array(int arr[],int val)
	{
		int index,flag=0;
		for(index=0;index<arr.length;index++)
		{
			if(val==arr[index])
			{
				flag=1;
				break;
			}
		}
		return flag;
	}
	public static int find_index(int arr[],int val)
	{
		int index,ret_index=0;
		for(index=0;index<arr.length;index++)
		{
			if(val==arr[index])
			{
				ret_index=index;
				break;
			}
		}
		return ret_index;
	}
	public static String find_movie_details(int movieid)
	{
		int movid,i,j;
		String movie_name=null;
		String categ=null;
		String movie_detail=null;
		try
		{
			Scanner in1 = new Scanner(new FileReader("movies.dat"));
			in1.useDelimiter("[:\n]+");
			
			while (in1.hasNext())
			{
				movid=in1.nextInt();
				if(movid==movieid)
				{
					movie_name=in1.next();
					categ=in1.next();
				}
				else
					in1.nextLine();
			}
			movie_detail=movie_name+" :: "+categ;
			in1.close();
			
		}catch(FileNotFoundException fileNotFoundException)
			{
				System.err.println("Error Opening File.");
				System.exit(1);
			}
		return movie_detail;
	}
	public static void main(String args[])
	{
		// Taking Userid as input.
		
		try
		{
			BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter your userid (1-6040):");
			System.out.flush();
			userid=Integer.parseInt(in.readLine());
			System.out.flush();
			System.out.print("Enter the Threshold Value:");
			thresh_val=Double.parseDouble(in.readLine());
			System.out.println("userdid= "+userid+"  "+"Threshold Value :"+thresh_val);
		}catch(IOException e) {
			System.out.println("Input Output Error");
			System.exit(1);
			}
		try
		{
			// Searching to which zipcell, that user belongs.
			String currentdir = System.getProperty("user.dir");
			String inpath = currentdir+"\\zipsite_users_N";
			File InpFolder = new File(inpath); // Getting into zip_users_N directory.
			File Inpfiles_users[];
			Inpfiles_users = InpFolder.listFiles(); // Listing all files inside zip_users_N directory.
			int k,flag=0,user;
			String zipcen;
			String zips,found_file="Not EXIST",correl_file="Not EXIST",rating_file="Not Exist";
			for(k = 0;k<Inpfiles_users.length; k++)
			{
				Scanner in_user0 = new Scanner(new FileReader(Inpfiles_users[k]));
				zipcen=in_user0.next();	
				flag=0;
				while(in_user0.hasNext())
				{
					zips=in_user0.next();
					user=in_user0.nextInt();			
					if(user==userid)
					{
						flag=1;
						found_file="zipsite"+Integer.toString(k)+".dat";
						correl_file="correlation"+Integer.toString(k)+".dat";
						rating_file="zipsite_rating"+Integer.toString(k)+".dat";
						break;
					}
				}
				in_user0.close();
				if(flag==1)
				{
					break;
				}
			}
			System.out.println("The file is: "+found_file+" and "+correl_file);
			if(flag==1)
			{
				// Making an array of users highly coorelated with active user.
				String inpath2=currentdir+"\\zipsite_users_Correlation_N\\"+correl_file;
				Scanner in_corr = new Scanner(new FileReader(inpath2));
				zipcen=in_corr.next();
				int user_count=in_corr.nextInt();
				int uid1,uid2;
				double coeff;
				int userid_arr[]=new int[user_count];
				Double user_coeff[]=new Double[user_count];
				int rest_user_count=0;
				while(in_corr.hasNext())
				{	
					uid1=in_corr.nextInt();
					uid2=in_corr.nextInt();
					coeff=in_corr.nextDouble();
					if(Double.isNaN(coeff))
					{
						continue;
					}
					else
					{
						if((uid1==userid || uid2==userid) && (coeff>=thresh_val))
						{
							if(uid2!=userid)
								userid_arr[rest_user_count]=uid2;
							else
								userid_arr[rest_user_count]=uid1;
							user_coeff[rest_user_count]=coeff;
							rest_user_count++;
						}
					}
				}
				in_corr.close();
			
			// Making two array of movie's ids which are higly rated by active user and rest of users respectively.. 
				String inpath3=currentdir+"\\zipsite_users_ratings_N\\"+rating_file;
				Scanner in_rating = new Scanner(new FileReader(inpath3));
				zipcen=in_rating.next();	
				int read_uid,read_mid,read_rating;
				ArrayList al1 = new ArrayList(20);
				ArrayList al2 = new ArrayList(60);

				while(in_rating.hasNext())
				{
					read_uid=in_rating.nextInt();
					read_mid=in_rating.nextInt();
					read_rating=in_rating.nextInt();
					if(read_rating==4 || read_rating==5)
					{
						if(read_uid==userid)
						{
							al1.add(read_mid);
						}
						else 
						{
							if(chk_in_array(userid_arr,read_uid)==1)
								{
									al2.add(read_mid);
								}
						}
					}	
				}
				al1.trimToSize();
				al2.trimToSize();
				//Converting arraylist to array of exact size
				Object active_fav_movie_arr1[]=al1.toArray(); 
				Object rest_fav_movie_arr1[]=al2.toArray(); 
				// CONVERTING Object TYPE ARRAY INTO int type array.
				int active_fav_movie_arr[]=new int[active_fav_movie_arr1.length];
				int rest_fav_movie_arr[]=new int[rest_fav_movie_arr1.length];
				for(int i=0; i<active_fav_movie_arr1.length; i++) 
				{
					active_fav_movie_arr[i]=((Integer) active_fav_movie_arr1[i]).intValue();
				} 
				for(int i=0; i<rest_fav_movie_arr1.length; i++) 
				{
					rest_fav_movie_arr[i]=((Integer) rest_fav_movie_arr1[i]).intValue();
				}
				System.out.println();
			    //Finding the top categories of user's choice by updating the count of each values of array catg_count[], and then taking 2-3 top count values, which gives corresponding categories.
			    // AND Forming 2D array for finding categories of movies of rest users.
			    int rest_mov_catg[][]=new int[rest_fav_movie_arr.length][18];
				Scanner in0 = new Scanner(new FileReader("movies.dat"));
				in0.useDelimiter("[:\n]+");
				int movid,i,j;
				String movie_name;
				String categ;
				String delim = "[|\r]";
				String types[];
				while (in0.hasNext())
				{
					movid=in0.nextInt();
						if(chk_in_array(active_fav_movie_arr,movid)==1 || chk_in_array(rest_fav_movie_arr,movid)==1)
						{
							movie_name=in0.next();
							categ=in0.next();
							types=categ.split(delim);
							if(chk_in_array(active_fav_movie_arr,movid)==1)
							{
								for(i=0;i<types.length;i++)
									for(j=0;j<mov_catg.length;j++)
									{
										if(mov_catg[j].compareTo(types[i])==0)
										{
											catg_count[j] +=1;
											break;
										}
									}
							}
							if(chk_in_array(rest_fav_movie_arr,movid)==1)
							{
								int mov_index=find_index(rest_fav_movie_arr,movid);
								for(i=0;i<types.length;i++)
									for(j=0;j<mov_catg.length;j++)
									{
										if(mov_catg[j].compareTo(types[i])==0)
										{
											rest_mov_catg[mov_index][j]=1;											
										}
									}
							}
						}						
						else 
						{
							in0.nextLine();
						}
				}
				in0.close();
				for(k=0;k<catg_count.length;k++)
				{
					System.out.println(mov_catg[k]+"  "+catg_count[k]);
				}	

				//Finding first two top categories(i.e. index in catg_count[] and eventually mov_catg[])
				int max1,max2,catg_max1_index=0,catg_max2_index=0;
				max1=catg_count[0];
				for(i=1;i<catg_count.length;i++)
				{
					if(catg_count[i]>max1)
					{
						max1=catg_count[i];
						catg_max1_index=i;
					}
				}
				if(max1==catg_count[0])
					max2=catg_count[1];
				else
					max2=catg_count[0];
				for(i=0;i<catg_count.length;i++)
				{
						if(i!=catg_max1_index && catg_count[i]>max2)
						{
						max2=catg_count[i];
						catg_max2_index=i;
						}
					
				}
				System.out.println("Top two categories: "+mov_catg[catg_max1_index]+" "+mov_catg[catg_max2_index]);
				//RECOMMENDING MOVIES..
				System.out.println("***********RECOMMENDED MOVIES***********");
				for(i=0;i<rest_fav_movie_arr.length;i++)
				{
					if(rest_mov_catg[i][catg_max1_index]==1 && rest_mov_catg[i][catg_max2_index]==1)
					{

						if(chk_in_array(active_fav_movie_arr,rest_fav_movie_arr[i])==0)
							System.out.println(rest_fav_movie_arr[i]+" "+find_movie_details(rest_fav_movie_arr[i]));
					}
					
				}					
			}
			else
			{
				System.out.println("SORRY!! INVALID USER.!!");
				System.exit(1);
			}		
			
		}catch(FileNotFoundException fileNotFoundException)
			{
				System.err.println("Error Opening File.");
				System.exit(1);
			}
	}
}