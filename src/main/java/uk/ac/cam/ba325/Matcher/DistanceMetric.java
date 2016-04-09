package uk.ac.cam.ba325.Matcher;

import uk.ac.cam.ba325.Tab.Translation.Sequence;
import uk.ac.cam.ba325.Tab.Translation.Strike;

import java.util.List;

/**
 * Created by root on 09/04/16.
 */
public class DistanceMetric {

    /**
     * computes the edit distance using the Wagner-Fischer algorithm
     * @param sequence1
     * @param sequence2
     * @return
     */
    public static int twoDimensionalEditDistance(Sequence sequence1, Sequence sequence2, int offset){
        int returnValue = 0;
        returnValue += editDistance(sequence1.getBassDrums(),sequence2.getBassDrums(),offset);
        returnValue += editDistance(sequence1.getCrashCymbals(),sequence2.getCrashCymbals(),offset);
        returnValue += editDistance(sequence1.getFloorToms(),sequence2.getFloorToms(),offset);
        returnValue += editDistance(sequence1.getHighHats(),sequence2.getHighHats(),offset);
        returnValue += editDistance(sequence1.getHighToms(),sequence2.getHighToms(),offset);
        returnValue += editDistance(sequence1.getLowToms(),sequence2.getLowToms(),offset);
        returnValue += editDistance(sequence1.getRideCymbals(),sequence2.getRideCymbals(),offset);
        returnValue += editDistance(sequence1.getSnareDrums(),sequence2.getSnareDrums(),offset);

        return returnValue;
    }

    public static int editDistance(List<Strike> sequence1,List<Strike> sequence2, int offset){
        int m = sequence1.size();
        int n = sequence2.size();
        int[][] distances = new int[m][n];

        for(int i=0; i<m; i++){
            distances[i][0] = i;
        }
        for(int i=0; i<n; i++){
            distances[0][i] = i;
        }

        for(int j=1; j<n; j++){
            for(int i=1; i<m; i++){
                if(sequence1.get(i+offset %16).getValue() == sequence2.get(j+offset %16).getValue()){
                    distances[i][j] = distances[i-1][j-1];
                }else{
                    distances[i][j] = min(distances[i-1][j] +1,distances[i][j-1] +1,distances[i-1][j-1] +1);

                }
            }
        }

        return distances[m][n];
    }

    public static int twoDimensionalCyclicEditDistance(Sequence sequence1, Sequence sequence2){
        int bestMatch = Integer.MAX_VALUE;
        int currentDistance =0;
        for(int i=0; i<sequence1.getResolution(); i++){
            currentDistance = twoDimensionalEditDistance(sequence1,sequence2,i);
            if(currentDistance<bestMatch){
                bestMatch = currentDistance;
            }
        }
        return bestMatch;
    }

    public static int twoDimensionalCyclicHammingDistance(Sequence sequence1, Sequence sequence2){
        int bestMatch =Integer.MAX_VALUE;
        int currentDistance = 0;
        for(int i=0; i<sequence1.getResolution(); i++){
            currentDistance = twoDimensionalHammingDistance(sequence1,sequence2,i);
            if(currentDistance< bestMatch){
                bestMatch = currentDistance;
            }
        }
        return bestMatch;
    }

    public static int twoDimensionalHammingDistance(Sequence sequence1, Sequence sequence2,int offset){
        int returnValue =0;
        returnValue += hammingDistance(sequence1.getBassDrums(),sequence2.getBassDrums(),offset);
        returnValue += hammingDistance(sequence1.getCrashCymbals(),sequence2.getCrashCymbals(),offset);
        returnValue += hammingDistance(sequence1.getFloorToms(),sequence2.getFloorToms(),offset);
        returnValue += hammingDistance(sequence1.getHighHats(),sequence2.getHighHats(),offset);
        returnValue += hammingDistance(sequence1.getHighToms(),sequence2.getHighToms(),offset);
        returnValue += hammingDistance(sequence1.getLowToms(),sequence2.getLowToms(),offset);
        returnValue += hammingDistance(sequence1.getRideCymbals(),sequence2.getRideCymbals(),offset);
        returnValue += hammingDistance(sequence1.getSnareDrums(),sequence2.getSnareDrums(),offset);
        return returnValue;
    }

    public static int hammingDistance(List<Strike> sequence1, List<Strike> sequence2,int offset){
        int shorter = Math.min(sequence1.size(),sequence2.size());
        int longer = Math.max(sequence1.size(),sequence2.size());

        int returnValue = 0;


        int pointInList;
        for(int i=offset; i<shorter; i++){
            pointInList = i % 16;
            if(sequence1.get(pointInList) != sequence2.get(pointInList)){
                returnValue++;
            }
        }

        returnValue += longer-shorter;

        return returnValue;
    }

    private static int min(int deletion, int insertion, int substitution){
        return Math.min(deletion,Math.min(insertion,substitution));

    }
}
