import java.io.*;
import java.util.*;
class Test_prg
{
	static int   actv_userid;
	static double thresh_val;
	static String mov_catg[] = {"Animation","Children's","Comedy","Adventure","Fantasy","Romance","Drama","Action","Crime","Thriller","Horror","Sci-Fi","Documentary","War","Musical","Mystery","Film-Noir","Western"};
	static int catg_count[] = new int[18];
	static int mr_count[];
	static int test_mr_count[];
	static int test_movieid[];
	
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
	public static void permutate_array(int arr[])
	{
			Random rand = new Random();
			int num,temp; 
			for(int n=arr.length-1;n>0;n--)
			{
				num=rand.nextInt(n);
				if(num!=n)
				{
					temp=arr[n];
					arr[n]=arr[num];
					arr[num]=temp;
				}				
			}
	}
	
	public static void main(String args[])
	{
		// Taking Userid as input.
		
		try
		{
			BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter your userid (1-6040):");
			System.out.flush();
			actv_userid=Integer.parseInt(in.readLine());
			System.out.flush();
			System.out.print("Enter the Threshold Value:");
			thresh_val=Double.parseDouble(in.readLine());
			System.out.println("userdid= "+actv_userid+"  "+"Threshold Value :"+thresh_val);
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
			int i,j,k,flag=0,user;
			String zipcen;
			String zips,found_file="Not EXIST",rating_file="Not Exist";
			for(k = 0;k<Inpfiles_users.length; k++)
			{
				Scanner in_user0 = new Scanner(new FileReader(Inpfiles_users[k]));
				zipcen=in_user0.next();	
				flag=0;
				while(in_user0.hasNext())
				{
					zips=in_user0.next();
					user=in_user0.nextInt();			
					if(user==actv_userid)
					{
						flag=1;
						found_file="zipsite"+Integer.toString(k)+".dat";
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
			System.out.println("The file is: "+found_file+"and"+rating_file);
			if(flag==1)
			{
				// MAKING Training_set.dat and Test_set.dat files.
				//String currentdir = System.getProperty("user.dir"); // Getting path of current working directory.
				String newDir = "TEST_CASE"; 
				File f = new File(newDir);
				f.mkdir();
				
				String outpath1=currentdir+"\\TEST_CASE\\Training_set.dat";
				PrintWriter out1 = new PrintWriter(outpath1);
				String outpath2=currentdir+"\\TEST_CASE\\Test_set.dat";
				PrintWriter out2 = new PrintWriter(outpath2);
				
				
				String inpath1=currentdir+"\\zipsite_users_ratings_N\\"+rating_file;
				Scanner in_rat = new Scanner(new FileReader(inpath1));
				zipcen=in_rat.next();
				out1.println(zipcen);
				int user_id,movie_id,rating;
				ArrayList actv_movieid = new ArrayList(20);
				ArrayList actv_ratings = new ArrayList(20);
				
				while(in_rat.hasNext())
				{	
					user_id=in_rat.nextInt();
					movie_id=in_rat.nextInt();
					rating=in_rat.nextInt();
					if(user_id != actv_userid)
					{
						out1.println(user_id+" "+movie_id+" "+rating);					
					}
					else
					{
						actv_movieid.add(movie_id);
						actv_ratings.add(rating);
					}
				}
				actv_movieid.trimToSize();
				actv_ratings.trimToSize();
				//Converting arraylist to array of exact size
				Object active_movieid1[]=actv_movieid.toArray(); 
				Object active_ratings1[]=actv_ratings.toArray(); 
				// CONVERTING Object TYPE ARRAY INTO int type array.
				int active_movieid[]=new int[active_movieid1.length];
				int active_ratings[]=new int[active_ratings1.length];
				for(i=0; i<active_movieid1.length; i++) 
				{
					active_movieid[i]=((Integer) active_movieid1[i]).intValue();
				} 
				for(i=0; i<active_ratings1.length; i++) 
				{
					active_ratings[i]=((Integer) active_ratings1[i]).intValue();
				}
				
				mr_count = new int[active_movieid1.length];
				for(i=0; i<mr_count.length; i++) 
				{
					mr_count[i]=i;
				}
				
				// SPLITTING TRAINING SET AND TEST SET (80-20% split)
				permutate_array(mr_count);
				int test_size=(int)(mr_count.length*0.2);
				//System.out.println("TEST SIZE = "+test_size);
				test_movieid = new int[test_size];
				for(i=0;i<mr_count.length; i++)
				{
					if(i<test_size)
					{
						out2.println(actv_userid+" "+active_movieid[mr_count[i]]+" "+active_ratings[mr_count[i]]);
						test_movieid[i] = active_movieid[mr_count[i]];
					}
					else
						out1.println(actv_userid+" "+active_movieid[mr_count[i]]+" "+active_ratings[mr_count[i]]);
				}
				in_rat.close();
				out1.close();
				out2.close();
				
				//FINDING CORRELATIONS BASED ON TRAINING SET AND STORING IT INTO "Training_correl_set.dat".
				
				String inpath2=currentdir+"\\zipsite_users_N\\"+found_file;
				Scanner in_user0 = new Scanner(new FileReader(inpath2));
				zipcen=in_user0.next();
				int user_count=0;
				
				while(in_user0.hasNext())
				{
					zips=in_user0.next();
					in_user0.next();			
					user_count++;
				}
				in_user0.close();
				String userid[]= new String[user_count];
				Scanner in_user1 = new Scanner(new FileReader(inpath2));
				zipcen=in_user1.next();
				 i=0;
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
				String inpath3=currentdir+"\\TEST_CASE\\Training_set.dat";
				Scanner in_mov0 = new Scanner(new FileReader(inpath3));
				zipcen = in_mov0.next();
				int movie_count,flag1;
				k=0;
				while(in_mov0.hasNext())
				{	
					in_mov0.next();
					movie=in_mov0.next();
					in_mov0.next();
				
					flag1=0;
					for( j = 0;j <k; j++)  
					{
						if(movie.compareTo(m_id[j])==0) 
						{
							flag1=1;
							break;
						}
					}
					if(flag1==0)
					{		
						m_id[k]=movie;
						k++;
					}	
				}
				movie_count=k;
				in_mov0.close();
				
				// Making Rating Array rating_arr[movie_count][user_count].
				int rating_arr[][]= new int[movie_count][user_count];
				Scanner in_mov1 = new Scanner(new FileReader(inpath3));
				zipcen = in_mov1.next();
				int rating1,user_index,mov_index;
				String user1;
				while(in_mov1.hasNext())
				{	
					user1=in_mov1.next();
					movie=in_mov1.next();
					rating1=in_mov1.nextInt();
					
					user_index=get_index(userid,user1);
					mov_index=get_index(m_id,movie);
					
					rating_arr[mov_index][user_index]=rating1;
				}
				String outpath3=currentdir+"\\TEST_CASE\\Training_correl_set.dat";
				PrintWriter out3 = new PrintWriter(outpath3);
				out3.println(zipcen+" "+user_count);
				for(i=0;i<user_count-1;i++)
					for( j=i+1;j<user_count;j++)
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
						out3.println(userid[i]+" "+userid[j]+" "+corr_coeff);	
					}	
					out3.close();	
					// Finding Recommendation based on Training Set.
				// Making an array of users highly coorelated with active user.
				String inpath4=currentdir+"\\TEST_CASE\\Training_correl_set.dat";
				Scanner in_corr = new Scanner(new FileReader(inpath4));
				zipcen=in_corr.next();
				user_count=in_corr.nextInt();
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
						if((uid1==actv_userid || uid2==actv_userid) && (coeff>=thresh_val))
						{
							if(uid2!=actv_userid)
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
				String inpath5=currentdir+"\\TEST_CASE\\Training_set.dat";
				Scanner in_rating = new Scanner(new FileReader(inpath5));
				zipcen=in_rating.next();	
				int read_uid,read_mid,read_rating;
				ArrayList al1 = new ArrayList(30);
				ArrayList al2 = new ArrayList(60);

				while(in_rating.hasNext())
				{
					read_uid=in_rating.nextInt();
					read_mid=in_rating.nextInt();
					read_rating=in_rating.nextInt();
					if(read_rating==4 || read_rating==5)
					{
						if(read_uid==actv_userid)
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
				for( i=0; i<active_fav_movie_arr1.length; i++) 
				{
					active_fav_movie_arr[i]=((Integer) active_fav_movie_arr1[i]).intValue();
				} 
				for( i=0; i<rest_fav_movie_arr1.length; i++) 
				{
					rest_fav_movie_arr[i]=((Integer) rest_fav_movie_arr1[i]).intValue();
				}
				System.out.println(" ");
			/*	System.out.println("ACTIVE USER FAV MOVIES");
				for( i=0; i<active_fav_movie_arr.length; i++) 
				{
					System.out.print(active_fav_movie_arr[i]+" | "); 
				} 
				System.out.println(" ");*/
				System.out.println("TEST SET");
				for( i=0; i<test_movieid.length; i++) 
				{
					System.out.print(test_movieid[i]+" | "); 
				}
			    //Finding the top categories of user's choice by updating the count of each values of array catg_count[], and then taking 2-3 top count values, which gives corresponding categories.
			    // AND Forming 2D array for finding categories of movies of rest users.
			    int rest_mov_catg[][]=new int[rest_fav_movie_arr.length][18];
				Scanner in0 = new Scanner(new FileReader("movies.dat"));
				in0.useDelimiter("[:\n]+");
				int movid;
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
								//System.out.println();
							}
							if(chk_in_array(rest_fav_movie_arr,movid)==1)
							{
								int mov_index1=find_index(rest_fav_movie_arr,movid);
								for(i=0;i<types.length;i++)
									for(j=0;j<mov_catg.length;j++)
									{
										if(mov_catg[j].compareTo(types[i])==0)
										{
											rest_mov_catg[mov_index1][j]=1;											
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
				System.out.println(" ");
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

				System.out.println("***********RECOMMENDED MOVIES***********");
				int top_N_count=0;
				for(i=0;i<rest_fav_movie_arr.length;i++)
				{
					if(rest_mov_catg[i][catg_max1_index]==1 && rest_mov_catg[i][catg_max2_index]==1)
					{
						if(chk_in_array(active_fav_movie_arr,rest_fav_movie_arr[i])==0)
						{
							System.out.println(rest_fav_movie_arr[i]+" "+find_movie_details(rest_fav_movie_arr[i]));
							top_N_count +=1;
						}
					}
				}
				System.out.println("***********HIT SET***********");
				int hit_count=0;
				for(i=0;i<rest_fav_movie_arr.length;i++)
				{
					if(rest_mov_catg[i][catg_max1_index]==1 && rest_mov_catg[i][catg_max2_index]==1)
					{
						if(chk_in_array(test_movieid,rest_fav_movie_arr[i])==1)
						{
							System.out.println(rest_fav_movie_arr[i]+" "+find_movie_details(rest_fav_movie_arr[i]));
							hit_count +=1;
						}
					}
				}
				System.out.println(" ");
				System.out.println("**********************");
				System.out.println("TOP_N SET COUNT = "+top_N_count);
				System.out.println("TEST SIZE = "+test_size);
				System.out.println("HIT SET COUNT = "+hit_count);
				in_rating.close();
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