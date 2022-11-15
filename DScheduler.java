
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import java.util.Arrays;

import java.util.Iterator;
import java.util.ListIterator;


//Disk Scheduler by Sumeet Sandhu

public class DScheduler {	
	public static int k;
	public static int cT;
	public static String d= "";
	public static ArrayList<Integer> tN_values = new ArrayList<Integer>();
	public static int SSTF_total;
	public static int LOOK_total;
	public static int CLOOK_total;
	
	

	public class TrackItems {
		int gap;
		boolean completed;
		int trackNumber;
		
		public TrackItems() {
			gap = 0;
			completed = false;
			trackNumber= 0;
		}
		
		public int getGap() {
			return gap;
			
		}
		public void setGap(int val) {
			this.gap= val;
		}
		
		public int getTrackNumber() {
			return trackNumber;
			
		}
		public void setTrackNumber(int val) {
			this.trackNumber= val;
		}
		
		public boolean getCompleted() {
			return completed;
			
		}
		public void setCompleted(boolean val) {
			this.completed= val;
		}
	
	}
	
	public static void main(String args[]) {
		Scanner scan = new Scanner(new InputStreamReader(System.in));
		String line = scan.nextLine();
		String[] input = line.split(" ");
		
		for(int i=0; i< input.length; i++) {
			if(i==0) {
				k= Integer.parseInt(input[i]);
			}
			else if(i==1) {
				cT= Integer.parseInt(input[i]);
			}
			else if(i==2) {
				d= input[i];
			}
			else {
				tN_values.add(Integer.parseInt(input[i]));
			}
		}
		
        //FCFS
        //Same Order tN_values 
        //Calculate head movements
        int prev= cT;
        int total=0;
        int sum=0;
        
        for(int x=0; x<k; x++) {
        	int curr= tN_values.get(x);
        	if(prev> curr) {
        		sum= prev - tN_values.get(x);
        	}
        	else {
        		sum= tN_values.get(x)-prev;
        	}
        	
        	total= total + sum;
        	prev=  tN_values.get(x);
        	
        }
        //Print Array for FCFS and total head movements
        System.out.print("FCFS: ");
        
        for(int y=0; y<k; y++) {
        	System.out.print(tN_values.get(y));
        	System.out.print(" ");
        	
        }
        System.out.println("Total Head Movement: "+ total);
        
        //SSTF
		System.out.print("SSTF: "); 
		ArrayList<Integer> SSTF_temp= SSTF_function();
        for(int i=0; i<SSTF_temp.size(); i++) {
        	System.out.print(SSTF_temp.get(i)+ " ");
        }

        System.out.print("Total Head Movement: ");
        System.out.println(SSTF_total);

        //LOOK
        System.out.print("LOOK: ");
        ArrayList<Integer> LOOK_temp= LOOK_function();
        
        for(int i=0; i< LOOK_temp.size() ;i++) {
        	System.out.print(LOOK_temp.get(i)+" ");
        }
        System.out.print("Total Head Movement: ");
        System.out.println(LOOK_total);
        
        //CLOOK
        System.out.print("CLOOK: ");
        ArrayList<Integer> CLOOK_temp= CLOOK_function();
        
        for(int i=0; i< CLOOK_temp.size() ;i++) {
        	System.out.print(CLOOK_temp.get(i)+" ");
        }
        System.out.print("Total Head Movement: ");
        System.out.println(CLOOK_total);
        
        LOOK_total=0;
        SSTF_total=0;
        CLOOK_total=0;
        
	
	}
	
	public static ArrayList<Integer> SSTF_function() {
        
        ArrayList<Integer> SSTF = new ArrayList<Integer>();
        DScheduler DS= new DScheduler();
        ArrayList<TrackItems> temp_1 = new ArrayList<TrackItems>();
        
        for(int i=0;i<k;i++) {
        	DScheduler.TrackItems t1= DS.new TrackItems();
        	t1.setTrackNumber(tN_values.get(i));
        	t1.setCompleted(false);
        	temp_1.add(i, t1);	
        }
        

        int new_cT = 0;
        int new_cT_slot=0;
        int smallest=0;
        int gap = 0;
        int curr=0;

        //Finding cT smallest gap from track numbers in input 
		for(int w=0; w<k; w++) {
			curr= tN_values.get(w);
			if(w==0) {
				if(cT > curr) {
					gap = cT - curr;
				}
				else {
					gap= curr - cT;
				}
				smallest= gap;
			}
			
			if(cT > curr) {
				gap = cT - curr;
			}
			
			else {
				gap= curr - cT;
			}
			
			if(smallest > gap) {
				new_cT_slot=w;
				smallest= gap;
				new_cT= tN_values.get(w);
				
			}
		}

		temp_1.get(new_cT_slot).setGap(smallest);
		int total=  temp_1.get(new_cT_slot).getGap();
		
		
		//Check if all values are true in tracker
        gap = 0;
        smallest=0;
        new_cT_slot=0;
        curr=0;

        //We're going through input array tN_values until it is completed starting with the found smallest gap value from cT
        ArrayList<Integer> temp = new ArrayList<Integer>(tN_values);
        
		boolean AllDone= false;
		int count=0;
		int special_case=0;

		while(!AllDone) {
			for (int m = 0; m < temp_1.size();m++) {
				if(temp_1.get(m).getCompleted()!=true) {
					
		            special_case=0;
		            //Check for special case
		            for(int i=0; i<k; i++) {
		            	if(temp_1.get(i).getCompleted()==false) {
		            		special_case++;
		            	}
		            }
		            int prev_gap=0;
		            if(special_case==2) {
		            	prev_gap= gap;
		            	
		            }

	            	curr= temp_1.get(m).getTrackNumber();
	            	

	            	if(m==0) {
	                	if(new_cT > curr) 
	                		smallest = new_cT-curr;
	                	else 
	                		smallest= curr-new_cT;
	            	}
	            	

		            if(new_cT > curr)
		            	gap= new_cT- curr;
		            else 
		            	gap= curr-new_cT;
		            
		            
		            if(special_case==1) {
		            	smallest=gap;
		            	new_cT_slot=m;
		            }

		            if(special_case==2) {
		            	if(prev_gap > gap) {
		            		smallest=gap;
		            		new_cT_slot= m;
		            	}
		            }
		            
		            if (smallest > gap) {
		            	new_cT_slot = m;
		                smallest = gap; 
		                
   
		            }
		            
		            
				}
			}
			if(special_case==0) {
				break;
			}
			
			temp_1.get(new_cT_slot).setGap(smallest);
			temp_1.get(new_cT_slot).setCompleted(true);
			new_cT = temp_1.get(new_cT_slot).getTrackNumber();
			SSTF.add(temp.get(new_cT_slot));
            count++;

            
            if(count==k) {
            	AllDone= true;
            }
            

		}
		
        //Calculate SSTF total head movement
		if(k>1) {
	        for(int x=0; x<k; x++) {
	        	curr= temp_1.get(x).getGap();
	        	total= curr+total;
	        	
	        }
		}
        SSTF_total= total;

		return SSTF;
	}

	public static ArrayList<Integer> LOOK_function() {
        ArrayList<Integer> LOOK = new ArrayList<Integer>();
        ArrayList<Integer> SortedInput = new ArrayList<Integer>(tN_values);
        if(d.equals("up")) {
        	Collections.sort(SortedInput);
        }
        if(d.equals("down")) {
        	Collections.sort(SortedInput, Collections.reverseOrder());
        }
        
        
        DScheduler DS= new DScheduler();
        ArrayList<TrackItems> temp_1 = new ArrayList<TrackItems>();
        
        for(int i=0;i<k;i++) {
        	DScheduler.TrackItems t1= DS.new TrackItems();
        	t1.setTrackNumber(SortedInput.get(i));
        	t1.setCompleted(false);
        	temp_1.add(i, t1);	
        }
        
        //Find Track Number in input closest to cT in the correct direction
        
        if(d.equals("up")) {
        	int max= cT;
            int curr=0;
        	int new_cT_slot=0;
        	int new_cT=0;
        	int prev=cT;
        	int total=0;
        	
        	//Go forward and find values greater than cT
            for(int i=0; i<k; i++) {
            	curr= SortedInput.get(i);
            	if(max<curr) {
            		
            		max= SortedInput.get(i);
            		total= total+ (max- prev);
            		prev= max;
            		
            		LOOK.add(SortedInput.get(i));
            		temp_1.get(i).setCompleted(true);
            		new_cT_slot=i;
    				new_cT= SortedInput.get(i);
    				

            	}	
            }
            
            boolean AllDone= false;
    		int count=0;
            int gap = 0;
            int smallest=0;
            new_cT_slot=0;
            curr=0;
            int special_case=0;
            
            while(!AllDone) {
    			for (int m = 0; m < temp_1.size();m++) {
    				if(temp_1.get(m).getCompleted()!=true) {
    					
    		            special_case=0;
    		            //Check for special case
    		            for(int i=0; i<k; i++) {
    		            	if(temp_1.get(i).getCompleted()==false) {
    		            		special_case++;
    		            	}
    		            }
    		            
    		            int prev_gap=0;
    		            if(special_case==2) {
    		            	prev_gap= gap;
    		            	
    		            }

    	            	curr= temp_1.get(m).getTrackNumber();
    	            	
    	            	if(m==0) {
    	                	if(new_cT > curr) 
    	                		smallest = new_cT-curr;
    	                	else 
    	                		smallest= curr-new_cT;
    	            	}
    	            	
    		            if(new_cT > curr)
    		            	gap= new_cT- curr;
    		            else 
    		            	gap= curr-new_cT;
    		            
    		            
    		            if(special_case==1) {
    		            	smallest=gap;
    		            	new_cT_slot=m;
    		            	AllDone=true;
    		            }

    		            if(special_case==2) {
    		            	if(prev_gap > gap) {
    		            		smallest=gap;
    		            		new_cT_slot= m;
    		            	}
    		            }
    		            

    		            if (smallest > gap) {
    		            	new_cT_slot = m;
    		                smallest = gap; 
    		                
       
    		            }
    		            
    		            
    				}
    			}
    			
    			if(special_case==0) {
    				break;
    			}
    			
    			temp_1.get(new_cT_slot).setGap(smallest);
    			temp_1.get(new_cT_slot).setCompleted(true);
    			new_cT = temp_1.get(new_cT_slot).getTrackNumber();
    			LOOK.add(temp_1.get(new_cT_slot).getTrackNumber());
                count++;

                
                if(count==k) {
                	AllDone= true;
                }
                

    		}
            
            curr=0;
            //Calculate LOOK total head movement
            if(k>1) {
	            for(int x=0; x<k; x++) {
	            	curr= temp_1.get(x).getGap();
	            	total= curr+total;
	            	
	            }
            }
            LOOK_total= total;
        		
          
            
        }
        
        if(d.equals("down")) {
        	
        	int smallest= cT;
            int curr=0;
        	int new_cT_slot=0;
        	int new_cT=0;
        	int prev=cT;
        	int total=0;
        	
        	//Go forward and find values less than cT
        	
        	
            for(int i=0; i<k; i++) {
            	
            	curr= SortedInput.get(i);
            	if(smallest > curr) {
            		LOOK.add(SortedInput.get(i));
            		
            		smallest= SortedInput.get(i);
            		int sum=0;
            		if(smallest>prev) {
            			sum= smallest-prev;
            		}
            		else {
            			sum= prev-smallest;
            		}
            		total= total + sum;
            		prev= smallest;
            		
            		
            		temp_1.get(i).setCompleted(true);
            		new_cT_slot=i;
    				new_cT= SortedInput.get(i);
    				

            	}	
            }
            
            
            boolean AllDone= false;
    		int count=0;
            int gap = 0;
            smallest=0;
            new_cT_slot=0;
            curr=0;
            
            int special_case=0;
            
            while(!AllDone) {
    			for (int m = 0; m < temp_1.size();m++) {
    				if(temp_1.get(m).getCompleted()!=true) {
    					
    		            special_case=0;
    		            //Check for special case
    		            for(int i=0; i<k; i++) {
    		            	if(temp_1.get(i).getCompleted()==false) {
    		            		special_case++;
    		            	}
    		            }
    		            int prev_smallest=0;
    		            int prev_gap=0;
    		            
    		            if(special_case==2) {
    		            	prev_smallest= smallest;
    		            	prev_gap= gap;
    		            	
    		            }

    	            	curr= temp_1.get(m).getTrackNumber();
    	            	

    	            	if(m==0) {
    	                	if(new_cT > curr) 
    	                		smallest = new_cT-curr;
    	                	else 
    	                		smallest= curr-new_cT;
    	            	}
    	            	

    		            if(new_cT > curr)
    		            	gap= new_cT- curr;
    		            else 
    		            	gap= curr-new_cT;
    		            
    		            
    		            if(special_case==1) {
    		            	smallest=gap;
    		            	new_cT_slot=m;
    		            	temp_1.get(new_cT_slot).setCompleted(true);
    		            	AllDone=true;
    		            }

    		            if(special_case==2) {
    		            	if(prev_gap > gap) {
    		            		smallest=gap;
    		            		new_cT_slot= m;
    		            	}
    		            }
    		            
    		            
    		            
    		            if (smallest > gap) {
    		            	new_cT_slot = m;
    		                smallest = gap; 
    		                
       
    		            }
    		            
    		            
    				}
    			}
    			
    			if(special_case==0) {
    				break;
    			}
    			
    			temp_1.get(new_cT_slot).setGap(smallest);
    			temp_1.get(new_cT_slot).setCompleted(true);
    			new_cT = temp_1.get(new_cT_slot).getTrackNumber();
    			LOOK.add(temp_1.get(new_cT_slot).getTrackNumber());
                count++;

                
                if(count==k) {
                	AllDone= true;
                }
                

    		}
            
            curr=0;
            //Calculate LOOK total head movement
            if(k==1) {
            	if(cT>temp_1.get(0).getTrackNumber())
            		total= cT-temp_1.get(0).getTrackNumber();
            	else
            		total= temp_1.get(0).getTrackNumber()- cT;
            }
            
            else {
	            for(int x=0; x< k ; x++) {
	            	curr= temp_1.get(x).getGap();
	            	total= curr+total;
	            	
	            }
            }
            
            LOOK_total= total;
            
         
        }

        return LOOK;

	}
	
	public static ArrayList<Integer> CLOOK_function() {
		
        ArrayList<Integer> CLOOK = new ArrayList<Integer>();
        ArrayList<Integer> SortedInput = new ArrayList<Integer>(tN_values);

        Collections.sort(SortedInput);

        
        
        DScheduler DS= new DScheduler();
        ArrayList<TrackItems> temp_1 = new ArrayList<TrackItems>();
        
        for(int i=0;i<k;i++) {
        	DScheduler.TrackItems t1= DS.new TrackItems();
        	t1.setTrackNumber(SortedInput.get(i));
        	t1.setCompleted(false);
        	temp_1.add(i, t1);	
        }
        
        if(d.equals("up")) {
        	int max= cT;
            int curr=0;
        	int prev=cT;
        	int total=0;
        	
        	//Go forward and find values greater than cT
            for(int i=0; i<k; i++) {
            	curr= SortedInput.get(i);
            	if(max<curr) {
            		
            		max= SortedInput.get(i);
            		total= total+ (max- prev);
            		prev= max;
            		
            		CLOOK.add(SortedInput.get(i));
            		temp_1.get(i).setCompleted(true);


            	}	
            }
            boolean AllDone= false;
            CLOOK_total= total;
            curr=0;
            
            while(!AllDone) {
    			for (int m = 0; m < temp_1.size();m++) {
    				if(temp_1.get(m).getCompleted()!=true) {
    					curr= temp_1.get(m).getTrackNumber();
    					int sum=0;
    					CLOOK.add(SortedInput.get(m));
    					temp_1.get(m).setCompleted(true);
    					
    					if(prev< curr) {
    						sum= curr-prev;
    					}
    					else {
    						sum= prev-curr;
    					}
    					prev= curr;
    					
    					total= total + sum;
   		            
    				}
    			}



                AllDone= true;
                
                

    		}
            
            curr=0;
            //Calculate CLOOK total head movement
            for(int x=0; x<k; x++) {
            	curr= temp_1.get(x).getGap();
            	total= curr+total;
            	
            }
            CLOOK_total= total; 
        }	
        
        if(d.equals("down")) {
        	
        	int prev=cT;
        	int total=0;
        	
        	//Go forward and find values less than cT
        	
            for(int i=0; i<k; i++) {
            	CLOOK.add(SortedInput.get(i));
            	int curr= SortedInput.get(i);
            	int sum=0;
            	if(prev< curr) {
            		
					sum= curr-prev;
				}
				else {
					sum= prev-curr;
				}
				prev= curr;
				
				total= total + sum;
            }
            
            CLOOK_total= total;
            
	
        }
        return CLOOK;
		
		
	}
	
	
	
	

}