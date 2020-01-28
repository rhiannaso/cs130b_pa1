import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collections;

public class Main {
    public static void checkWeekend(TreeMap<Integer, ArrayList<Integer>> days, TreeMap<Integer, Integer> tracker, int numWorkers, int pianosLeft) {
        int movablePianos = numWorkers/2;
        int pianosToMove = pianosLeft;

        for(Integer startDay : days.keySet()) {
            ArrayList<Integer> startDayArr = days.get(startDay);
            for(int i = 0; i < startDayArr.size(); i++) {
                int range = startDayArr.get(i)-startDay;
                // if start day is weekend and is not maximized yet
                if((startDay%6 == 0) && (startDay%7 == 0) && (tracker.get(startDay) < movablePianos)) {
                    tracker.put(startDay, tracker.get(startDay)+1);
                    pianosToMove -= 1;
                    continue;
                }
                // go through possible days (non-start) for each individual piano
                for(int j = 1; j < range+1 && (pianosToMove > 0); j++) {
                    int currDay = startDay+j;
                    if(tracker.containsKey(currDay) == false) {
                        tracker.put(currDay, 0);
                    } 
                    // if weekend and day is not maxed out yet
                    if((((currDay%6) == 0) || ((currDay%7) == 0)) && (tracker.get(currDay) < movablePianos)) {
                        tracker.put(currDay, tracker.get(currDay)+1);
                        pianosToMove -= 1;
                    } 
                }
            }
        }
        if(pianosToMove == 0) {
            System.out.println("weekend work");
        } else {
            System.out.println("serious trouble");
        }
    }

    public static void checkWeekday(TreeMap<Integer, ArrayList<Integer>> days, TreeMap<Integer, Integer> tracker, int numWorkers, int numPianos) {
        int movablePianos = numWorkers/2;
        int pianosToMove = numPianos;

        for(Integer startDay : days.keySet()) {
            ArrayList<Integer> startDayArr = days.get(startDay);
            for(int i = 0; i < startDayArr.size(); i++) {
                int range = startDayArr.get(i)-startDay;
                // if start day is a weekday and is not maximized yet
                if((startDay%6 != 0) && (startDay%7 != 0) && (tracker.get(startDay) < movablePianos)) {
                    tracker.put(startDay, tracker.get(startDay)+1);
                    pianosToMove -= 1;
                    continue;
                } 
                // go through possible days (non-start) for each individual piano
                for(int j = 1; j < range+1 && (pianosToMove > 0); j++) {
                    int currDay = startDay+j;
                    if(tracker.containsKey(currDay) == false) {
                        tracker.put(currDay, 0);
                    } 
                    // if weekday and day is not maxed out yet
                    if(((currDay%6) != 0) && ((currDay%7) != 0) && (tracker.get(currDay) < movablePianos)) {
                        tracker.put(currDay, tracker.get(currDay)+1);
                        pianosToMove -= 1;
                    } 
                }
            }
        }
        if(pianosToMove == 0) {
            System.out.println("fine");
        } else {
            checkWeekend(days, tracker, numWorkers, pianosToMove);
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

        // sort start days for each case
        for(int i = 0; i < numCases; i++) {
            TreeMap<Integer, ArrayList<Integer>> days = new TreeMap<Integer, ArrayList<Integer>>(); 
            TreeMap<Integer, Integer> tracker = new TreeMap<Integer, Integer>();
            ArrayList<Integer[]> moveDay = moveInfo.get(i);
            for(int j = 0; j < moveDay.size(); j++) {
                Integer startDay = moveDay.get(j)[0];
                Integer endDay = moveDay.get(j)[1];
                if(days.containsKey(startDay)) {
                    days.get(startDay).add(endDay);
                    Collections.sort(days.get(startDay));
                } else {
                    ArrayList<Integer> dayArr = new ArrayList<Integer>();
                    dayArr.add(endDay);
                    days.put(startDay, dayArr);
                    tracker.put(startDay, 0);
                }
            }

            checkWeekday(days, tracker, numWorkers[i], numPianos[i]);
        }
        s.close();
    }
}