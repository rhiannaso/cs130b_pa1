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

        long sum1 = checkOptions(board, rows, columns, "row");
        long sum2 = checkOptions(board, rows, columns, "column");
        //System.out.println("SUM 1 "+sum1);
        //System.out.println("SUM 2 "+sum2);

        if(sum1 != -1 && sum2 != -1) {
            System.out.println(String.valueOf(Math.min(sum1, sum2)));
        } else if(sum1 != -1 && sum2 == -1) {
            System.out.println(String.valueOf(sum1));
        } else if(sum1 == -1 && sum2 != -1) {
            System.out.println(String.valueOf(sum2));
        } else {
            System.out.println(String.valueOf(sum1));
        }
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

    public static long populateBoard(int[][] origBoard, int rows, int columns, int firstCell) {
        int parityCheck = firstCell%2;
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
                    if((i-1) < 0 && (j-1) > -1) { // if top row and not leftmost
                        board[i][j] = board[i][j-1]+2;
                    }
                    if((i-1) > -1 && (j-1) < 0) { // when not top row but leftmost
                        board[i][j] = board[i-1][j]+1;
                    } 
                    if((i-1) > -1 && (j-1) > -1) { // when not leftmost or top row
                        board[i][j] = Math.max(board[i][j-1]+2, board[i-1][j]+1);
                    }
                    if(rowParity == 0) { // if an odd row
                        if(board[i][j]%2 != parityCheck) {
                            return -1;
                        } 
                    } else { // if an even row
                        if(board[i][j]%2 == parityCheck) {
                            return -1;
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
        //System.out.println("ROWS");
        //System.out.println(Arrays.deepToString(board));
        long sum = calculateSum(board, rows, columns);
        return sum;
    }

    public static long populateBoardCol(int[][] origBoard, int rows, int columns, int firstCell) {
        //System.out.println("IN HERE");
        //System.out.println("FIRST CELL "+Integer.toString(firstCell));
        int parityCheck = firstCell%2;
        //System.out.println(Integer.toString(parityCheck));
        int[][] board = new int[rows][columns];
        for(int k = 0; k < rows; k++) {
            for(int l = 0; l < columns; l++) {
                board[k][l] = origBoard[k][l];
            }
        }
        for(int j = 0; j < columns; j++) {
            int rowParity = j%2;
            //System.out.println("COLUMN: "+Integer.toString(j));
            //System.out.println(Integer.toString(rowParity));
            for(int i = 0; i < rows; i++) {
                //System.out.println("ROW: "+Integer.toString(i));
                if(board[i][j] == 0) { // when needs to be populated
                    if((i-1) < 0 && (j-1) > -1) { // if top row and not leftmost
                        board[i][j] = board[i][j-1]+1;
                    }
                    if((i-1) > -1 && (j-1) < 0) { // when not top row but leftmost
                        //System.out.println("leftmost");
                        board[i][j] = board[i-1][j]+2;
                    } 
                    if((i-1) > -1 && (j-1) > -1) { // when not leftmost or top row
                        board[i][j] = Math.max(board[i][j-1]+1, board[i-1][j]+2);
                    }
                    //System.out.println("NEW VAL: "+board[i][j]);
                    if(rowParity == 0) { // if an odd column
                        if(board[i][j]%2 != parityCheck) {
                            //System.out.println("odd not match");
                            return -1;
                        } 
                    } else { // if an even column
                        if(board[i][j]%2 == parityCheck) {
                            //System.out.println("even not match");
                            return -1;
                        } 
                    }
                } else {
                    if((i-1) > -1) {
                        if(board[i][j] <= board[i-1][j]) {
                            //System.out.println("fixed increasing row problem");
                            return -1;
                        }
                    }
                    if((j-1) > -1) {
                        if(board[i][j] <= board[i][j-1]) {
                            //System.out.println("fixed increasing column problem");
                            return -1;
                        }
                    }
                    if(rowParity == 0) { // if an odd column
                        if(board[i][j]%2 != parityCheck) {
                            //System.out.println("fixed odd parity prob");
                            return -1;
                        } 
                    } else { // if an even column
                        if(board[i][j]%2 == parityCheck) {
                            //System.out.println("fixed even parity prob");
                            return -1;
                        } 
                    }
                }
            }
        }
        //System.out.println("COLS");
        //System.out.println(Arrays.deepToString(board));
        long sum = calculateSum(board, rows, columns);
        return sum;
    }

    public static long checkOptions(int[][] origBoard, int rows, int columns, String caseType) {
        long oddCase = 0;
        long evenCase = 0;
        int[][] board = new int[rows][columns];
        for(int k = 0; k < rows; k++) {
            for(int l = 0; l < columns; l++) {
                board[k][l] = origBoard[k][l];
            }
        }
        if(board[0][0] != 0) {
            if(board[0][0]%2 == 0) {
                if(caseType == "row") {
                    evenCase = populateBoard(board, rows, columns, board[0][0]);
                } else {
                    evenCase = populateBoardCol(board, rows, columns, board[0][0]);
                }
                oddCase = evenCase+1;
            } else {
                oddCase = populateBoard(board, rows, columns, board[0][0]);
                evenCase = oddCase+1;
            }
        }

        for(int i = 0; i < 2; i++) {
            if(i == 0) { // even odd even
                board[0][0] = 2;
                if(caseType == "row") {
                    evenCase = populateBoard(board, rows, columns, board[0][0]);
                } else {
                    evenCase = populateBoardCol(board, rows, columns, board[0][0]);
                }     
            } else { // odd even odd
                board[0][0] = 1;
                if(caseType == "row") {
                    oddCase = populateBoard(board, rows, columns, board[0][0]);
                } else {
                    oddCase = populateBoardCol(board, rows, columns, board[0][0]);
                }     
            }
        }

        if(oddCase < evenCase) {
            if(oddCase != -1) {
                return oddCase;
            } else {
                if(evenCase == 0) {
                    return oddCase;
                }
                return evenCase;
            }
        } else if(evenCase < oddCase) {
            if(evenCase != -1) {
                return evenCase;
            } else {
                if(oddCase == 0) {
                    return evenCase;
                }
                return oddCase;
            }
        } else {
            return oddCase;
        }
    }

    // public static long columnWise(int[][] origBoard, int rows, int columns) {
    //     long oddCase = 0;
    //     long evenCase = 0;
    //     int[][] board = new int[rows][columns];
    //     for(int k = 0; k < rows; k++) {
    //         for(int l = 0; l < columns; l++) {
    //             board[k][l] = origBoard[k][l];
    //         }
    //     }
    //     if(board[0][0] != 0) {
    //         if(board[0][0]%2 == 0) {
    //             evenCase = populateBoardCol(board, rows, columns, board[0][0]);
    //             oddCase = evenCase+1;
    //         } else {
    //             oddCase = populateBoardCol(board, rows, columns, board[0][0]);
    //             evenCase = oddCase+1;
    //         }
    //     }

    //     for(int i = 0; i < 2; i++) {
    //         if(i == 0) { // even odd even
    //             board[0][0] = 2;
    //             evenCase = populateBoardCol(board, rows, columns, board[0][0]);
    //         } else { // odd even odd
    //             board[0][0] = 1;
    //             oddCase = populateBoardCol(board, rows, columns, board[0][0]);
    //         }
    //     }

    //     if(oddCase < evenCase) {
    //         if(oddCase != -1) {
    //             return oddCase;
    //         } else {
    //             if(evenCase == 0) {
    //                 return oddCase;
    //             }
    //             return evenCase;
    //         }
    //     } else if(evenCase < oddCase) {
    //         if(evenCase != -1) {
    //             return evenCase;
    //         } else {
    //             if(oddCase == 0) {
    //                 return evenCase;
    //             }
    //             return oddCase;
    //         }
    //     } else {
    //         return oddCase;
    //     }
    // }
}