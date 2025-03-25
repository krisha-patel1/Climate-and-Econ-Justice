package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine(); // Skips header
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE


        String[] cat = inputLine.split(",");
        StateNode newState = new StateNode(cat[2],null, null);
       
        if(firstState == null){
            firstState = newState;
        }
        boolean repeat = false;
        StateNode lastState = firstState;
        for(StateNode curState = firstState; curState!=null; curState = curState.next){
            if(curState.getName().equals(newState.getName())){
                repeat = true;
            }
            lastState = curState;
        }

        if (!repeat){
            lastState.setNext(newState);
         }
             

    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] cat = inputLine.split(",");
        CountyNode newCounty = new CountyNode(cat[1], null, null);
        String state = cat[2];
       // StateNode ending = firstState;

        for(StateNode curState = firstState; curState != null; curState = curState.next){
            if(curState.getName().equals(state)){
                if(curState.getDown() == null){
                    curState.setDown(newCounty);
                } else{
                    CountyNode last = curState.getDown();
                    boolean repeat = false;
                    for(CountyNode node = curState.getDown(); node!= null; node = node.getNext()){
                        if(node.getName().equals(newCounty.getName())){
                            repeat = true;
                        }
                        last = node;
                    }
                    if(!repeat){
                        last.setNext(newCounty);

                    }
                }
                
            }
        }

    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        String[] cat = inputLine.split(",");
        String state = cat[2];
        String county = cat[1];
        Data data = new Data (Double.valueOf(cat[3]),Double.valueOf(cat[4]),Double.valueOf(cat[5]), 
                            Double.valueOf(cat[8]),Double.valueOf(cat[9]),cat[19],Double.valueOf(cat[49]),
                            Double.valueOf(cat[37]),Double.valueOf(cat[121]));
        CommunityNode newCommunity = new CommunityNode(cat[0],null, data);


        for(StateNode curState = firstState; curState != null; curState = curState.getNext()){
            if(curState.getName().equals(state)){
                for(CountyNode curCounty = curState.getDown(); curCounty != null; curCounty = curCounty.getNext()){
                    if(curCounty.getName().equals(county)){
                        if(curCounty.getDown() == null){
                            curCounty.setDown(newCommunity);
                        } else {
                            CommunityNode last = new CommunityNode();
                            boolean repeat = false;
                            for(CommunityNode curCommunity = curCounty.getDown(); curCommunity != null; curCommunity = curCommunity.getNext()){
                                if(curCommunity.getName().equals(newCommunity.getName())){
                                    repeat = true;
                                }
                                last = curCommunity;
                            }
        
                            if(!repeat){
                                last.setNext(newCommunity);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        // WRITE YOUR CODE HERE
     
        int count = 0;
        
        for(StateNode curState = firstState; curState != null; curState = curState.getNext()){
            for(CountyNode curCounty = curState.getDown(); curCounty != null; curCounty = curCounty.getNext()){
                for(CommunityNode curCommunity = curCounty.getDown(); curCommunity!= null; curCommunity = curCommunity.getNext()){
                    if(Boolean.valueOf(curCommunity.getInfo().getAdvantageStatus())){
                        double percentage = 0.0;
                        if(race.equals("African American")){ percentage = curCommunity.getInfo().getPrcntAfricanAmerican();
                        } else if(race.equals("Native American")){ percentage = curCommunity.getInfo().getPrcntNative();
                        } else if(race.equals("Asian American")){ percentage = curCommunity.getInfo().getPrcntAsian();
                        } else if(race.equals("White American")){ percentage = curCommunity.getInfo().getPrcntWhite();
                        } else if(race.equals("Hispanic American")){ percentage = curCommunity.getInfo().getPrcntHispanic();
                        }

                        if((percentage * 100 ) >= userPrcntage){ count++;}

                    }
                }
            }
        }

        return count; // update this line
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

	//WRITE YOUR CODE HERE
    int count = 0;
        
    for(StateNode curState = firstState; curState != null; curState = curState.getNext()){
        for(CountyNode curCounty = curState.getDown(); curCounty != null; curCounty = curCounty.getNext()){
            for(CommunityNode curCommunity = curCounty.getDown(); curCommunity!= null; curCommunity = curCommunity.getNext()){
                if(Boolean.valueOf(curCommunity.getInfo().getAdvantageStatus()) == false){
                    double percentage = 0.0;
                    if(race.equals("African American")){ percentage = curCommunity.getInfo().getPrcntAfricanAmerican();
                    } else if(race.equals("Native American")){ percentage = curCommunity.getInfo().getPrcntNative();
                    } else if(race.equals("Asian American")){ percentage = curCommunity.getInfo().getPrcntAsian();
                    } else if(race.equals("White American")){ percentage = curCommunity.getInfo().getPrcntWhite();
                    } else if(race.equals("Hispanic American")){ percentage = curCommunity.getInfo().getPrcntHispanic();
                    }

                    if((percentage * 100 ) >= userPrcntage){ count++;}

                }
            }
        }
    }

    return count;
    
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        // WRITE YOUR METHOD HERE
        ArrayList<StateNode> states = new ArrayList<StateNode>();

        for(StateNode curState = firstState; curState != null; curState = curState.getNext()){
            for(CountyNode curCounty = curState.getDown(); curCounty != null; curCounty = curCounty.getNext()){
                for(CommunityNode curCommunity = curCounty.getDown(); curCommunity!= null; curCommunity = curCommunity.getNext()){
                    if(curCommunity.getInfo().getPMlevel() >= PMlevel){
                        boolean repeat = false;
                        for(StateNode state: states){
                            if(state.getName().equals(curState.getName())){
                                repeat = true;
                            }
                        }
                        if (!repeat) { states.add(curState); }
                    }

                }
            }
        }

	
        return states; // update this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

	// WRITE YOUR METHOD HERE
    int count = 0;
    for(StateNode curState = firstState; curState != null; curState = curState.getNext()){
        for(CountyNode curCounty = curState.getDown(); curCounty != null; curCounty = curCounty.getNext()){
            for(CommunityNode curCommunity = curCounty.getDown(); curCommunity!= null; curCommunity = curCommunity.getNext()){
                if((curCommunity.getInfo().getChanceOfFlood())>= userPercntage){
                    count++;
                }
            }
        }
    }
        
        return count; // update this line
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

	//WRITE YOUR METHOD HERE
    ArrayList<CommunityNode> coms = new ArrayList<CommunityNode>();
    
    for(StateNode curState = firstState; curState != null; curState = curState.getNext()){
        if(curState.getName().equals(stateName)){
            for(CountyNode curCounty = curState.getDown(); curCounty != null; curCounty = curCounty.getNext()){
                for(CommunityNode curCommunity = curCounty.getDown(); curCommunity != null; curCommunity = curCommunity.getNext()){
                    if(coms.size() < 10){
                        coms.add(curCommunity);
                    } else {

                        CommunityNode pov_com = coms.get(0);

                        for(int i = 1 ; i < coms.size(); i++){
                            CommunityNode community = coms.get(i);
                            if(community.getInfo().getPercentPovertyLine() < pov_com.getInfo().getPercentPovertyLine()){
                                pov_com = community;
                            }
                        }

                        if(curCommunity.getInfo().getPercentPovertyLine() > pov_com.getInfo().getPercentPovertyLine()){

                            coms.set(coms.indexOf(pov_com), curCommunity);
                        }
                        
                    }
                }
            }
        }
    }

        return coms; // update this line
    }
}
    
