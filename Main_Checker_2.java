import java.util.Scanner;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Math;

public class Main_Checker_2 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        
        String input = s.nextLine();
        String[] stringDim = input.split(" ");
        int rows = Integer.parseInt(stringDim[0]);
        int columns = Integer.parseInt(stringDim[1]);

        int[][] board = new int[rows][columns];

        // populate board
        for (int i = 0; i < rows; i++) {
            String tempRow = s.nextLine();
            String[] rowVals = tempRow.split(" ");
            for (int j = 0; j < rowVals.length; j++) {
                board[i][j] = Integer.parseInt(rowVals[j]);
            }
        }

        long sum1 = checkOptions(board, rows, columns, "row", "e");
        long sum2 = checkOptions(board, rows, columns, "row", "o");
        long sum3 = checkOptions(board, rows, columns, "column", "e");
        long sum4 = checkOptions(board, rows, columns, "column", "o");

        if(sum1 == -1 && sum2 == -1 && sum3 == -1 && sum4 == -1) {
            System.out.println("-1");
            s.close();
            return;
        }

        ArrayList<Long> sorter = new ArrayList<Long>();
        sorter.add(sum1);
        sorter.add(sum2);
        sorter.add(sum3);
        sorter.add(sum4);
        sorter.removeAll(Collections.singleton((long)(-1)));
        Collections.sort(sorter);
        System.out.println(String.valueOf(sorter.get(0)));

        s.close();
    }

    public static long calculateSum(int[][] board, int rows, int columns) {
        long sum = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                sum += board[i][j];
            }
        }
        return sum;
    }

    public static long populateBoard(int[][] origBoard, int rows, int columns, String parType) {
        int parityCheck = -1;
        if(parType.equals("e")) {
            parityCheck = 0;
        } else {
            parityCheck = 1;
        }
        int[][] board = new int[rows][columns];
        for(int k = 0; k < rows; k++) {
            for(int l = 0; l < columns; l++) {
                board[k][l] = origBoard[k][l];
            }
        }
        for(int i = 0; i < rows; i++) {
            int rowParity = i%2;
            for(int j = 0; j < columns; j++) {
                if(board[i][j] == 0) { // when needs to be populated
                    if(i == 0 && j == 0) {
                        if(parType.equals("e")) {
                            board[i][j] = 2;
                        } else {
                            board[i][j] = 1;
                        }
                    }
                    if((i-1) < 0 && (j-1) > -1) { // if top row and not leftmost
                        board[i][j] = board[i][j-1]+2;
                    }
                    if((i-1) > -1 && (j-1) < 0) { // when not top row but leftmost
                        board[i][j] = board[i-1][j]+1;
                    } 
                    if((i-1) > -1 && (j-1) > -1) { // when not leftmost or top row
                        board[i][j] = Math.max(board[i][j-1]+2, board[i-1][j]+1);
                    }
                    if(rows != 1 && columns != 1) {
                        if(rowParity == 0) { // if an odd row
                            if(board[i][j]%2 != parityCheck) {
                                return -1;
                            } 
                        } else { // if an even row
                            if(board[i][j]%2 == parityCheck) {
                                return -1;
                            } 
                        }
                    }
                } else {
                    if((i-1) > -1) {
                        if(board[i][j] <= board[i-1][j]) {
                            return -1;
                        }
                    }
                    if((j-1) > -1) {
                        if(board[i][j] <= board[i][j-1]) {
                            return -1;
                        }
                    }
                    if(rows != 1 && columns != 1) {
                        if(rowParity == 0) { // if an odd row
                            if(board[i][j]%2 != parityCheck) {
                                return -1;
                            } 
                        } else { // if an even row
                            if(board[i][j]%2 == parityCheck) {
                                return -1;
                            } 
                        }
                    }
                }
            }
        }
        long sum = calculateSum(board, rows, columns);
        return sum;
    }

    public static long populateBoardCol(int[][] origBoard, int rows, int columns, String parType) {
        int parityCheck = -1;
        if(parType.equals("e")) {
            parityCheck = 0;
        } else {
            parityCheck = 1;
        }
        int[][] board = new int[rows][columns];
        for(int k = 0; k < rows; k++) {
            for(int l = 0; l < columns; l++) {
                board[k][l] = origBoard[k][l];
            }
        }
        for(int j = 0; j < columns; j++) {
            int rowParity = j%2;
            for(int i = 0; i < rows; i++) {
                if(board[i][j] == 0) { // when needs to be populated
                    if(i == 0 && j == 0) {
                        if(parType.equals("e")) {
                            board[i][j] = 2;
                        } else {
                            board[i][j] = 1;
                        }
                    }
                    if((i-1) < 0 && (j-1) > -1) { // if top row and not leftmost
                        board[i][j] = board[i][j-1]+1;
                    }
                    if((i-1) > -1 && (j-1) < 0) { // when not top row but leftmost
                        board[i][j] = board[i-1][j]+2;
                    } 
                    if((i-1) > -1 && (j-1) > -1) { // when not leftmost or top row
                        board[i][j] = Math.max(board[i][j-1]+1, board[i-1][j]+2);
                    }
                    if(rows != 1 && columns != 1) {
                        if(rowParity == 0) { // if an odd column
                            if(board[i][j]%2 != parityCheck) {
                                return -1;
                            } 
                        } else { // if an even column
                            if(board[i][j]%2 == parityCheck) {
                                return -1;
                            } 
                        }
                    }
                } else {
                    if((i-1) > -1) {
                        if(board[i][j] <= board[i-1][j]) {
                            return -1;
                        }
                    }
                    if((j-1) > -1) {
                        if(board[i][j] <= board[i][j-1]) {
                            return -1;
                        }
                    }
                    if(rows != 1 && columns != 1) {
                        if(rowParity == 0) { // if an odd column
                            if(board[i][j]%2 != parityCheck) {
                                return -1;
                            } 
                        } else { // if an even column
                            if(board[i][j]%2 == parityCheck) {
                                return -1;
                            } 
                        }
                    }
                }
            }
        }
        long sum = calculateSum(board, rows, columns);
        return sum;
    }

    public static long checkOptions(int[][] origBoard, int rows, int columns, String caseType, String parType) {
        long sum = 0;
        int[][] board = new int[rows][columns];
        for(int k = 0; k < rows; k++) {
            for(int l = 0; l < columns; l++) {
                board[k][l] = origBoard[k][l];
            }
        }

        if(caseType.equals("row")) {
            sum = populateBoard(board, rows, columns, parType);
        } else {
            sum = populateBoardCol(board, rows, columns, parType);
        }

        return sum;
    }
}