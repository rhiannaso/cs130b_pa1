import java.util.Scanner;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

public class Main_Checker {
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

        int[] rowArr = checkRowParity(board, rows, columns);
        //System.out.println(Arrays.toString(rowArr));
        int[] colArr = checkColumnParity(board, rows, columns);
        //System.out.println(Arrays.toString(colArr));


        // board of all 0s
        int sum = 0;
        if((rowArr[0] == 0 && rowArr[1] == 0) || (colArr[0] == 0 && colArr[1] == 0)) {
            ArrayList<Integer> sorter = new ArrayList<Integer>();
            sum = populateByRow(board, 0, rows, columns);
            int sum2 = populateByRow(board, 1, rows, columns);
            int sum3 = populateByCol(board, 0, rows, columns);
            int sum4 = populateByCol(board, 1, rows, columns);
            sorter.add(sum);
            sorter.add(sum2);
            sorter.add(sum3);
            sorter.add(sum4);
            sorter.remove((Integer)(-1));
            Collections.sort(sorter);
            
            // print smallest sum
            System.out.println(Integer.toString(sorter.get(0)));
        } else if ((rowArr[0] == 0 && rowArr[1] == 1) && (colArr[0] == 0 && colArr[1] == 1)) { // if neither row nor column works
            System.out.println("-1");
        } else if((rowArr[0] == 0 && rowArr[1] == 1) && (colArr[0] == 1)) { // if only column works
            if(colArr[1] == 0) { // if first column is even
                sum = populateByRow(board, 0, rows, columns);
                System.out.println(Integer.toString(sum));
            } else { // if first column is odd
                sum = populateByRow(board, 1, rows, columns);
                System.out.println(Integer.toString(sum));
            }
        } else if((rowArr[0] == 1) && (colArr[0] == 0 && colArr[1] == 1)) { // if only row works
            if(rowArr[1] == 0) { // if first row is even
                sum = populateByCol(board, 0, rows, columns);
                System.out.println(Integer.toString(sum));
            } else { // if first row is odd
                sum = populateByCol(board, 1, rows, columns);
                System.out.println(Integer.toString(sum));
            }
        } else if((rowArr[0] == 1) && (colArr[0] == 1)){ // if either column or row works
            int sum2 = 0;
            if((rowArr[0] == 1) && (rowArr[1] == 0)) {
                sum = populateByCol(board, 0, rows, columns);
            } 
            if ((rowArr[0] == 1) && (rowArr[1] == 1)) {
                sum = populateByCol(board, 1, rows, columns);
                //System.out.println("SUM: "+sum);
            } 
            if ((colArr[0] == 1) && (colArr[1] == 0)) {
                sum2 = populateByRow(board, 0, rows, columns);
            } 
            if ((colArr[0] == 1) && (colArr[1] == 1)) {
                sum2 = populateByRow(board, 1, rows, columns);
                //System.out.println("SUM2: "+sum2);
            }
            
            if(sum < sum2) {
                if(sum != -1) {
                    System.out.println(Integer.toString(sum));
                } else {
                    System.out.println(Integer.toString(sum2));
                }
            } else if(sum2 < sum){
                if(sum2 != -1) {
                    System.out.println(Integer.toString(sum2));
                } else {
                    System.out.println(sum);
                }
            } else {
                System.out.println(sum);
            }
        }

        s.close();
    }

    public static boolean checkIncreasing(int[][] board, int rows, int columns) {
        //System.out.println("HERE");
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if((i+1 < rows)) {
                    if((board[i][j] >= board[i+1][j])) {
                        return false;
                    }
                } 
                if((j+1) < columns) {
                    if((board[i][j] >= board[i][j+1])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int calculateSum(int[][] board, int rows, int columns) {
        int sum = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                sum += board[i][j];
            }
        }
        return sum;
    }

    public static int populateByRow(int[][] origBoard, int parity, int rows, int columns) {
        //System.out.println("BY ROW");
        int sum = 0;
        int[][] board = new int[rows][columns];
        for(int k = 0; k < rows; k++) {
            for(int l = 0; l < columns; l++) {
                board[k][l] = origBoard[k][l];
            }
        }
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                // if doesn't need to be populated, skip
                if(board[i][j] != 0) {
                    continue;
                }

                if(j-1 > -1) {
                    board[i][j] = board[i][j-1]+1; // increment from value to left
                    if(i-1 > -1) { 
                        if(board[i][j] == board[i-1][j]) { // if value above is equivalent
                            board[i][j] = board[i][j]+2;
                        }
                    }
                    //System.out.println("FROM LEFT POPULATING ["+i+"]["+j+"] "+board[i][j]);
                } else { // if furthest to the left
                    if(i-1 < 0) { // if top corner
                        if(parity == 0) {
                            board[i][j] = 2; // put smallest even
                        } else {
                            board[i][j] = 1; // put smallest odd
                        }
                    } else {
                        board[i][j] = board[i-1][j]+2; // increment from value to top
                        //System.out.println("FROM TOP POPULATING ["+i+"]["+j+"] "+board[i][j]);
                    }
                }
            }
        }
        //System.out.println(Arrays.deepToString(board));
        boolean isIncreasing = checkIncreasing(board, rows, columns);
        if(isIncreasing == false) {
            return -1;
        }
        sum = calculateSum(board, rows, columns);
        return sum;
    }

    public static int populateByCol(int[][] origBoard, int parity, int rows, int columns) {
        //System.out.println("BY COL");
        int sum = 0;
        int[][] board = new int[rows][columns];
        for(int k = 0; k < rows; k++) {
            for(int l = 0; l < columns; l++) {
                board[k][l] = origBoard[k][l];
            }
        }
        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {
                // if doesn't need to be populated, skip
                if(board[j][i] != 0) {
                    continue;
                }

                if(j-1 > -1) {
                    board[j][i] = board[j-1][i]+1; // increment from value to top
                    if(i-1 > -1) {
                        if(board[j][i] == board[j][i-1]) { // if value to left is equivalent
                            board[j][i] = board[j][i]+2;
                        }
                    }
                } else { // if furthest to the top
                    if(i-1 < 0) { // if top corner
                        if(parity == 0) {
                            board[j][i] = 2; // put smallest even
                        } else {
                            board[j][i] = 1; // put smallest odd
                        }
                    } else {
                        board[j][i] = board[j][i-1]+2; // increment from value to left
                    }
                }
            }
        }
        //System.out.println(Arrays.deepToString(board));
        boolean isIncreasing = checkIncreasing(board, rows, columns);
        if(isIncreasing == false) {
            return -1;
        }
        sum = calculateSum(board, rows, columns);
        return sum;
    }

    public static int[] checkRowParity(int[][] board, int rows, int columns) {
        // return [x, y] where x = 1 if it is row parity 
            // and y = 0 if first row is even and y = 1 if first row is odd
        int initRow = -1; // 0 if even, 1 if odd
        int rowNo = -1;
        int[] returnArr = new int[2];
        for(int i = 0; i < rows; i++) {
            int checkVal = 0;
            for(int j = 0; j < columns; j++) {
                // ignore 0s
                if(board[i][j] == 0) {
                    continue;
                }
                // set checkVal for the given row
                if((board[i][j] != 0) && (checkVal == 0)) {
                    checkVal = board[i][j];
                    if(checkVal%2 == 0) {
                        initRow = 0;
                    } else {
                        initRow = 1;
                    }
                    rowNo = i;
                }
                if((board[i][j]%2 != checkVal%2) && (checkVal != 0) && (board[i][j] != 0)) {
                    returnArr[0] = 0;
                    returnArr[1] = 1;
                    return returnArr;
                }
            }
            // check adjacent row
            for(int k = 0; k < columns; k++) {
                if((i+1) < rows) {
                    // if adjacent rows have same parity
                    if((initRow == board[i+1][k]%2) && (checkVal != 0) && (board[i+1][k] != 0)) {
                        returnArr[0] = 0;
                        returnArr[1] = 1;
                        return returnArr;
                    }
                }
            }
            checkVal = 0;
        }
        // return 0,0 if all 0s and anything works
        // 0,1 if just doesn't work
        if (initRow == -1) {
            returnArr[0] = 0;
            returnArr[1] = 0;
            return returnArr;
        } else {
            returnArr[0] = 1;
            // if an even row has even numbers or an odd row has odd numbers, then first row is odd
            if(((rowNo%2 == 1) && (initRow == 0)) || ((rowNo%2 == 0) && (initRow == 1))) {
                returnArr[1] = 1;
            } else {
                returnArr[1] = 0;
            }
            return returnArr;
        } 
    }

    public static int[] checkColumnParity(int[][] board, int rows, int columns) {
        // return [x, y] where x = 1 if it is column parity 
            // and y = 0 if first column is even and y = 1 if first column is odd
        int initCol = -1; // 0 if even, 1 if odd
        int colNo = -1;
        int[] returnArr = new int[2];
        for(int i = 0; i < columns; i++) {
            int checkVal = 0;
            for(int j = 0; j < rows; j++) {
                // ignore 0s
                if(board[j][i] == 0) {
                    continue;
                }
                // set checkVal for the given column
                if((board[j][i] != 0) && (checkVal == 0)) {
                    checkVal = board[j][i];
                    if(checkVal%2 == 0) {
                        initCol = 0;
                    } else {
                        initCol = 1;
                    }
                    colNo = i;
                }
                if((board[j][i]%2 != checkVal%2) && (checkVal != 0) && (board[j][i] != 0)) {
                    returnArr[0] = 0;
                    returnArr[1] = 1;
                    return returnArr;
                }
            }
            // check adjacent column
            for(int k = 0; k < rows; k++) {
                if((i+1) < columns) {
                    // if adjacent columns have same parity
                    if((initCol == board[k][i+1]%2) && (checkVal != 0) && (board[k][i+1] != 0)) {
                        returnArr[0] = 0;
                        returnArr[1] = 1;
                        return returnArr;
                    }
                }
            }
            checkVal = 0;
        }
        // return 0,0 if all 0s and anything works
        // 0,1 if just doesn't work
        if (initCol == -1) {
            returnArr[0] = 0;
            returnArr[1] = 0;
            return returnArr;
        } else {
            returnArr[0] = 1;
            // if an even column has even numbers or an odd column has odd numbers, then first column is odd
            if(((colNo%2 == 1) && (initCol == 0)) || ((colNo%2 == 0) && (initCol == 1))) {
                returnArr[1] = 1;
            } else {
                returnArr[1] = 0;
            }
            return returnArr;
        } 
    }
}