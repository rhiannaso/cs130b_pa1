import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;

public class Main_Updated {
    public static void checkWeek(ArrayList<Integer[]> days, int numWorkers) {
        int movablePianos = numWorkers/2;
        int currDay = 1;

        outerloop:
        while(currDay < 101) {
            ArrayList<Integer> potential = new ArrayList<Integer>();
            for(int i = 0; i < days.size(); i++) {
                // if end date is before current day
                if(days.get(i)[1] < currDay) {
                    System.out.println("serious trouble");
                    break outerloop;
                }
                // if start day is some time before or on current day
                if(days.get(i)[0] <= currDay) {
                    potential.add(i);
                }
            }
            if(potential.size() > 0) {
                int numRemoved = 0;
                for(int j = 0; j < movablePianos; j++) {
                    if(potential.size() <= j) {
                        break;
                    }
                    int removeIndex = potential.get(j);
                    days.remove(removeIndex-numRemoved);
                    numRemoved++;
                }
            }
            if(days.size() == 0) {
                break;
            } 
            currDay++;
        }

        if(days.size() == 0) {
            System.out.println("weekend work");
        }
    }

    public static void checkWeekday(ArrayList<Integer[]> days, ArrayList<Integer[]> weekCheck, int numWorkers) {
        int movablePianos = numWorkers/2;
        int currDay = 1;

        while(currDay < 101) {
            ArrayList<Integer> potential = new ArrayList<Integer>();
            // ignore weekends for now
            if(((currDay+1)%7 == 0) || (currDay%7 == 0)) {
                currDay++;
                continue;
            }
            for(int i = 0; i < days.size(); i++) {
                // if end date is before current day
                if((days.get(i)[1] < currDay)) {
                    continue;
                }
                // if start day is some time before or on current day
                if(days.get(i)[0] <= currDay) {
                    potential.add(i);
                }
            }
            if(potential.size() > 0) {
                int numRemoved = 0;
                for(int j = 0; j < movablePianos; j++) {
                    if(potential.size() <= j) {
                        break;
                    }
                    int removeIndex = potential.get(j);
                    days.remove(removeIndex-numRemoved);
                    numRemoved++;
                }
            }
            if(days.size() == 0) {
                break;
            } 
            currDay++;
        }

        if(days.size() == 0) {
            System.out.println("fine");
        } else {
            checkWeek(weekCheck, numWorkers);
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int numCases = Integer.parseInt(s.nextLine());
        int[] numPianos = new int[numCases];
        int[] numWorkers = new int[numCases];
        TreeMap<Integer, ArrayList<Integer[]>> moveInfo = new TreeMap<Integer, ArrayList<Integer[]>>(); 

        // take in input and store
        for (int i = 0; i < numCases; i++) {
            String pInfo = s.nextLine();
            String[] arr = pInfo.split(" ");
            numPianos[i] = Integer.parseInt(arr[0]);
            numWorkers[i] = Integer.parseInt(arr[1]);
            ArrayList<Integer[]> tmp = new ArrayList<Integer[]>();
            for (int j = 0; j < numPianos[i]; j++) {
                String tmpInfo = s.nextLine();
                String[] temp = tmpInfo.split(" ");
                Integer[] range = new Integer[temp.length];
                for (int k = 0; k < range.length; k++) {
                    range[k] = Integer.parseInt(temp[k]);
                }
                tmp.add(range);
            }
            moveInfo.put(i, tmp);
        }

        // sort end days for each case
        for(int i = 0; i < numCases; i++) {
            ArrayList<Integer[]> days = moveInfo.get(i);
            ArrayList<Integer[]> weekCheck = new ArrayList<Integer[]>();

            Collections.sort(days, new Comparator<Integer[]>() {
                public int compare(Integer[] end1, Integer[] end2) {
                    if((end1[1]).compareTo(end2[1]) == 0) {
                        return (Integer)(end1[0]).compareTo(end2[0]);
                    } else {
                        return (Integer)(end1[1]).compareTo(end2[1]);
                    }
                }
            });

            for(int j = 0; j < days.size(); j++) {
                Integer[] temp = new Integer[2];
                temp[0] = days.get(j)[0];
                temp[1] = days.get(j)[1];
                weekCheck.add(temp);
            }

            checkWeekday(days, weekCheck, numWorkers[i]);
        }
        s.close();
    }
}