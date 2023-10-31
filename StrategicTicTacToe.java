import java.util.*;

public class StrategicTicTacToe {
    //Data Structures
    //Queues to check and replace

    private final Queue<Double> OCollection, XCollection;

    //TreeMap
    private final HashMap<Double, String> board; //String for the sign
    private final int allowedAmount;
    private final int winRequirement;
    private final double capacityX, capacityY;
    private final double increment;
    private String winner;

    //private int[] xRow, oRow, xCol, oCol, xDiag, oDiag, xRevDiag, oRevDiag;

    private int col, row, diag, rdiag, Xcol, Xrow, Xdiag, Xrdiag;
    private double oldXCol, oldXRow, oldXdiag, oldXrdiag, oldOCol, oldORow, oldOdiag, oldOrdiag;
    public StrategicTicTacToe(int amount, int requirement, int row, int col) {
        OCollection = new LinkedList<Double>();
        XCollection = new LinkedList<Double>();
        board = new HashMap<Double, String>();
        allowedAmount = amount;
        winRequirement = requirement;
        capacityX = row;
        capacityY = col;
        increment = (1 / Math.pow(10, ("" + col).length()));
        winner = "noWinner";
        /*xRow = new int[xByY];
        oRow = new int[xByY];
        xCol = new int[xByY];
        oCol = new int[xByY];
        xDiag = new int[xByY];
        oDiag = new int[xByY];
        xRevDiag = new int[xByY];
        oRevDiag = new int[xByY];*/
    }

    public void start() {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to strategic tic tac toe! \nLet me explain the rules to you all, this game will be a win by " + winRequirement + ", but you can only place " + allowedAmount + " parts.\nThis means after the allowed amount, you will get rid of the oldest part that you placed.\n" +
                "You will be entering your coordinates for placing down X or O, it will be enter like this as a decimal number: xCord.yCord. \nIt starts at 1.1 as in (row:1,col:1).");
        printBoard();
        double cord;
        char turn;
        int Oamount=1, Xamount=1;
        int x = (int) Math.round(Math.random());
        if (x == 1) {
            turn = 'X';
        } else {
            turn = 'O';
        }
        do {
            System.out.println(turn + " turn!");
            do {
                System.out.println("Please enter your coordinate...");
                cord = console.nextDouble();
            } while (board.get(cord) != null || cord < 1.1 || cord > capacityX + (capacityY * increment));
            if (turn == 'X') {
                if (XCollection.size() >= allowedAmount) {
                    board.remove(XCollection.remove());
                }
                XCollection.add(cord);
                addToBoard(cord, turn, Xamount);
                if (Xamount<allowedAmount)
                {
                    Xamount++;
                } else {
                    int size = XCollection.size();
                    int count = 1;
                    for (int i =0; i<XCollection.size(); i++)
                    {
                        double temp = XCollection.remove();
                        board.put(temp, "X"+count);
                        count++;
                        XCollection.add(temp);
                    }
                }

            } else {
                if (OCollection.size() >= allowedAmount) {
                    board.remove(OCollection.remove());
                }
                OCollection.add(cord);
                addToBoard(cord, turn, Oamount);
                if (Oamount<allowedAmount)
                {
                    Oamount++;
                } else {
                    int size = OCollection.size();
                    int count = 1;
                    for (int i =0; i<OCollection.size(); i++)
                    {
                        double temp = OCollection.remove();
                        board.put(temp, "O"+count);
                        count++;
                        OCollection.add(temp);
                    }
                }

            }
            printBoard();
            if (winner.equalsIgnoreCase("noWinner")) {
                if (turn == 'X') {
                    turn = 'O';
                } else {
                    turn = 'X';
                }
            }
        } while (winner.equalsIgnoreCase("noWinner"));

        System.out.println(turn + " " + winner + " the game!");
    }


    public void printBoard() {
            int c = 1;
            for (double j = c+increment; j <= capacityX+ (capacityY * increment); j += increment) {
                j = Math.round(j * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length());
                System.out.print("[ ");
                if (board.get(j) == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(board.get(j));
                }
                System.out.print(" ]");
                if (j==c+capacityY*increment)
                {
                    j++;
                    c++;
                    j-=capacityY*increment;
                    System.out.println();
                }
        }
    }

    public void addToBoard(double cord, Character name, int turnAmount) {
        board.putIfAbsent(cord, name+""+turnAmount);
        /*if ((name=='X' && CheckX(cord)) || (name=='O' && CheckO(cord)))
        {
            winner = "won";
        }*/
        if (OCollection.size()>=winRequirement||XCollection.size()>=winRequirement) {
            winner = checkBoard(cord);
        }
    }

    //3.0
    /*public boolean CheckX(double cord)
    {
        int x=(int)Math.round(cord);
        int y= (int)Math.round((cord-x)*Math.pow(10, ((int)capacity + "").length()));
        System.out.println(y);
        xRow[x-1]+=1;
        xCol[y-1]+=1;
        if(x==y) {
            xDiag[x - 1]+=1;
        }
        if(x+y==winRequirement) {
            xRevDiag[x - 1]+=1;
        }
        System.out.println(xRow[x-1] + " " + xCol[y-1] + " " + xDiag[x-1] + " " + xRevDiag[x-1]);
        return xRow[x - 1] == winRequirement || xCol[y - 1] == winRequirement || xDiag[x - 1] == winRequirement || xRevDiag[x - 1] == winRequirement;
    }

    public boolean CheckO(double cord)
    {
        int x=(int)Math.round(cord);
        int y= (int)Math.round((cord-x)*Math.pow(10, ((int)capacity + "").length()));
        //System.out.println(x + " " + ((""+capacity).length()) + " " + (""+capacity));
        oRow[x-1]+=1;
        oCol[y-1]+=1;
        if(x==y) {
            oDiag[x - 1]+=1;
        }
        if(x+y==winRequirement) {
            oRevDiag[x - 1]+=1;
        }
        System.out.println(oRow[x-1] + " " + oCol[y-1] + " " + oDiag[x-1] + " " + oRevDiag[x-1]);
        return oRow[x - 1] == winRequirement || oCol[y - 1] == winRequirement || oDiag[x - 1] == winRequirement || oRevDiag[x - 1] == winRequirement;
    }*/

    public String checkBoard(double cord) {
        //5.0 Must check all directions
        int col = 1, row = 1, diag = 1, rdiag = 1;
        char temp = board.get(cord).charAt(0);
        for (double j = cord-increment; j >= cord - (winRequirement * increment); j -= increment) {
            j = Math.round(j*Math.pow(10,(capacityY+"").length()))/Math.pow(10,(capacityY+"").length());
            if (board.get(j)!=null && temp == board.get(j).charAt(0)) {
                col++;
            } else
            {
                break;
            }
        }
        for (double j = cord+increment; j <= cord + (winRequirement * increment); j += increment) {
            j = Math.round(j*Math.pow(10,(capacityY+"").length()))/Math.pow(10,(capacityY+"").length());
            if (board.get(j)!=null && temp == board.get(j).charAt(0)) {
                col++;
            } else
            {
                break;
            }
        }
        if (col>=winRequirement)
        {
            return "Won";
        }
        //row
        for (double j = cord-1; j >= (cord - winRequirement); j--) {
            j = Math.round(j*Math.pow(10,(capacityY+"").length()))/Math.pow(10,(capacityY+"").length());
            if (board.get(j)!=null && temp == board.get(j).charAt(0)) {
                row++;
            } else
            {
                break;
            }
        }
        for (double j = cord+1; j <= (cord + winRequirement); j++) {
            j = Math.round(j*Math.pow(10,(capacityY+"").length()))/Math.pow(10,(capacityY+"").length());
            if (board.get(j)!=null && temp == board.get(j).charAt(0)) {
                row++;
            } else
            {
                break;
            }
        }
        if (row>=winRequirement)
        {
            return "Won";
        }
        //diagonal
        for (double j = cord-(1+increment); j >= cord - (winRequirement + (winRequirement * increment)); j -= (1 + increment)) {
            j = Math.round(j*Math.pow(10,(capacityY+"").length()))/Math.pow(10,(capacityY+"").length());
            if (board.get(j)!=null && temp == board.get(j).charAt(0)) {
                diag++;
            } else
            {
                break;
            }
        } //Works
        for (double j = cord+(1+increment); j <= (cord + (winRequirement + (winRequirement * increment))); j += (1 + increment)) {
            j = Math.round(j*Math.pow(10,(capacityY+"").length()))/Math.pow(10,(capacityY+"").length());
            if (board.get(j)!=null && temp == board.get(j).charAt(0)) {
                diag++;
            } else
            {
                break;
            }
        }
        if (diag>=winRequirement)
        {
            return "Won";
        }
        //rDiagonal
        for (double j = cord-(1-increment); j >= cord - (winRequirement - (winRequirement * increment)); j -= (1 - increment)) {
            j = Math.round(j*Math.pow(10,(capacityY+"").length()))/Math.pow(10,(capacityY+"").length());
            if (board.get(j)!=null && temp == board.get(j).charAt(0)) {
                rdiag++;
            } else
            {
                break;
            }
        }
        for (double j = cord+(1-increment); j <= cord + (winRequirement - (winRequirement * increment)); j += (1 - increment)) {
            j = Math.round(j*Math.pow(10,(capacityY+"").length()))/Math.pow(10,(capacityY+"").length());
            if (board.get(j)!=null && temp == board.get(j).charAt(0)) {
                rdiag++;
            } else
            {
                break;
            }
        }
        if (rdiag>=winRequirement)
        {
            return "Won";
        }
        /*if (turn == 'X') {
            if (oldXCol != 0 || oldXRow != 0 || oldXdiag != 0 || oldXrdiag != 0)
            {
                //System.out.println("yes");
                //System.out.println(Math.round((oldX+increment)*Math.pow(10,(capacity+"").length()))/Math.pow(10,(capacity+"").length()));
                if (board.get(Math.round((oldXCol + increment) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldXCol + increment) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == cord) {
                    Xcol++;
                    oldXCol = cord;
                }
                //System.out.println(oldX+" new:"+oldX+1 + " x:" + x);
                //System.out.println(Xrow);
                if (board.get(oldXRow + 1) != null && oldXRow + 1 == cord) {
                    Xrow++;
                    oldXRow = cord;
                }
                if (board.get(Math.round((oldXdiag + (1 + increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldXdiag + (1 + increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == cord) {
                    Xdiag++;
                    oldXdiag = cord;
                }
                if (board.get(Math.round((oldXrdiag + (1 - increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldXrdiag + (1 - increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == cord) {
                    Xrdiag++;
                    oldXrdiag = cord;
                }
            } else
            {
                oldXdiag = oldXCol = oldXrdiag = oldXRow = cord;
                Xcol = Xrow = Xdiag = Xrdiag = 1;

            }
        } else if (turn == 'O') {
            //System.out.println("no!");
            if (oldOCol != 0 || oldORow != 0 || oldOdiag != 0 || oldOrdiag != 0) {
                if (board.get(Math.round((oldOCol + increment) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldOCol + increment) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == cord) {
                    col++;
                    oldOCol = cord;
                }
                if (board.get(oldORow + 1) != null && oldORow + 1 == cord) {
                    row++;
                    oldORow = cord;
                }
                if (board.get(Math.round((oldOdiag + (1 + increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldOdiag + (1 + increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == cord) {
                    diag++;
                    oldOdiag = cord;
                }//Works
                if (board.get(Math.round((oldOrdiag + (1 - increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldOrdiag + (1 - increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == cord) {
                    rdiag++;
                    oldOrdiag = cord;
                }
            } else {
                oldOdiag = oldOCol = oldOrdiag = oldORow = cord;
                col = row = diag = rdiag = 1;
            }
        }*/
        //4.0

        /*int index = 0;
        int col = 1, row = 1, diag = 1, rdiag = 1;
        int Xcol = 1, Xrow = 1, Xdiag = 1, Xrdiag = 1;
        //double[] helper = new double[board.keySet().size()];
        double oldXCol = 0, oldXRow = 0, oldXdiag = 0, oldXrdiag = 0, oldX = 0, oldO = 0, oldOCol = 0, oldORow = 0, oldOdiag = 0, oldOrdiag = 0;
        for (double x : board.keySet()) {
            if (board.get(x).charAt(0) == 'X') {
                if (oldXCol != 0 || oldXRow != 0 || oldXdiag != 0 || oldXrdiag != 0)
                {
                    //System.out.println("yes");
                    //System.out.println(Math.round((oldX+increment)*Math.pow(10,(capacity+"").length()))/Math.pow(10,(capacity+"").length()));
                    if (board.get(Math.round((oldXCol + increment) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length())) != null && Math.round((oldXCol + increment) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length()) == x) {
                        Xcol++;
                        oldXCol = x;
                    }
                    //System.out.println(oldX+" new:"+oldX+1 + " x:" + x);
                    //System.out.println(Xrow);
                    if (board.get(oldXRow + 1) != null && oldXRow + 1 == x) {
                        Xrow++;
                        oldXRow = x;
                    }
                    if (board.get(Math.round((oldXdiag + (1 + increment)) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length())) != null && Math.round((oldXdiag + (1 + increment)) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length()) == x) {
                        Xdiag++;
                        oldXdiag = x;
                    }
                    if (board.get(Math.round((oldXrdiag + (1 - increment)) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length())) != null && Math.round((oldXrdiag + (1 - increment)) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length()) == x) {
                        Xrdiag++;
                        oldXrdiag = x;
                    }
                } else
                {
                    oldXdiag = oldXCol = oldXrdiag = oldXRow = x;
                }
            } else if (board.get(x).charAt(0)  == 'O') {
                //System.out.println("no!");
                if (oldOCol != 0 || oldORow != 0 || oldOdiag != 0 || oldOrdiag != 0) {
                    if (board.get(Math.round((oldOCol + increment) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length())) != null && Math.round((oldOCol + increment) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length()) == x) {
                        col++;
                        oldOCol = x;
                    }
                    if (board.get(oldORow + 1) != null && oldORow + 1 == x) {
                        row++;
                        oldORow = x;
                    }
                    if (board.get(Math.round((oldOdiag + (1 + increment)) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length())) != null && Math.round((oldOdiag + (1 + increment)) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length()) == x) {
                        diag++;
                        oldOdiag = x;
                    }//Works
                    if (board.get(Math.round((oldOrdiag + (1 - increment)) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length())) != null && Math.round((oldOrdiag + (1 - increment)) * Math.pow(10, (capacityY + "").length())) / Math.pow(10, (capacityY + "").length()) == x) {
                        rdiag++;
                        oldOrdiag = x;
                    }
                } else {
                    oldOdiag = oldOCol = oldOrdiag = oldORow = x;
                }
            }
        }*/
        //2.0
        /*int index = 0;
        int col = 1, row = 1, diag = 1, rdiag = 1;
        int Xcol = 1, Xrow = 1, Xdiag = 1, Xrdiag = 1;
        //double[] helper = new double[board.keySet().size()];
        double oldX=0, oldO=0;
        for (double x: board.keySet())
        {
            //helper[index]=x;
            //index++;
            if (board.get(x)=='X')
            {
                if (oldX!=0) {
                    //System.out.println("yes");
                    //System.out.println(Math.round((oldX+increment)*Math.pow(10,(capacity+"").length()))/Math.pow(10,(capacity+"").length()));
                    if (board.get(Math.round((oldX + increment) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldX + increment) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == x) {
                        Xcol++;
                    }
                    //System.out.println(oldX+" new:"+oldX+1 + " x:" + x);
                    //System.out.println(Xrow);
                    if (board.get(oldX + 1) != null && oldX + 1 == x) {
                        Xrow++;
                    }
                    if (board.get(Math.round((oldX + (1 + increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldX + (1 + increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == x) {
                        Xdiag++;
                    }
                    if (board.get(Math.round((oldX + (1 - increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldX + (1 - increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == x) {
                        Xrdiag++;
                    }
                }
                oldX=x;
            } else if (board.get(x)=='O') {
                //System.out.println("no!");
                if (oldO!=0) {
                    if (board.get(Math.round((oldO + increment) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldO + increment) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == x) {
                        col++;
                    }
                    if (board.get(oldO + 1) != null && oldO + 1 == x) {
                        row++;
                    }
                    if (board.get(Math.round((oldO + (1 + increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldO + (1 + increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == x) {
                        diag++;
                    }//Works
                    if (board.get(Math.round((oldO + (1 - increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length())) != null && Math.round((oldO + (1 - increment)) * Math.pow(10, (capacity + "").length())) / Math.pow(10, (capacity + "").length()) == x) {
                        rdiag++;
                    }
                }
                oldO = x;
            }
        }*/
        /*for (int i = 1; i<helper.length; i++)
        {
            if (board.get(helper[i])==prev)
            {
                System.out.println("yes");
                if (board.get(old+increment)!=null && old+increment==helper[i])
                {
                    col++;
                }
                if (board.get(old+1)!=null && old+1==helper[i])
                {
                    row++;
                }
                if (board.get(old+(1+increment))!=null && old+(1+increment)==helper[i])
                {
                    diag++;
                }
                if (board.get(old+(1-increment))!=null && old+(1-increment)==helper[i])
                {
                    rdiag++;
                }
            }
            old = helper[i];
            prev = board.get(old);
        }*/


        /*double limit = capacity + (capacity * increment);
        int col = 0, row = 0, diag = 0, rdiag = 0;
        char temp;
        for (double i = 1.0; i <= limit; i += increment) {
            i = Math.round(i*Math.pow(10,(capacity+"").length()))/Math.pow(10,(capacity+"").length());
            if (board.get(i) != null) {
                temp = board.get(i);
                //int x = (int) i % 10, y = (int) ((i * 10) % 10);
                //col
                for (double j = i; j <= (i + (winRequirement * increment)); j += increment) {
                    j = Math.round(j*Math.pow(10,(capacity+"").length()))/Math.pow(10,(capacity+"").length());
                    if (board.get(j)!=null && temp == board.get(j)) {
                        col++;
                    } else
                    {
                        break;//Works
                    }
                }
                //row
                for (double j = i; j <= (i + winRequirement); j++) {
                    if (board.get(j)!=null && temp == board.get(j)) {
                        row++;
                    } else
                    {
                        break;
                    }
                }
                //diagonal
                for (double j = i; j <= (i + winRequirement + (winRequirement * increment)); j += (1 + increment)) {
                    j = Math.round(j*Math.pow(10,(capacity+"").length()))/Math.pow(10,(capacity+"").length());
                    if (board.get(j)!=null && temp == board.get(j)) {
                        diag++;
                        //System.out.println(diag);
                    } else
                    {
                        break;
                    }
                } //Works
                //rDiagonal
                for (double j = i; j <= i + (winRequirement - (winRequirement * increment)); j += (1 - increment)) {
                    j = Math.round(j*Math.pow(10,(capacity+"").length()))/Math.pow(10,(capacity+"").length());
                    if (board.get(j)!=null && temp == board.get(j)) {
                        rdiag++;
                    } else
                    {
                        break;
                    }
                }
                if (rdiag == winRequirement || diag == winRequirement || col == winRequirement || row == winRequirement) {
                    return temp + " Won";
                }
            }
            col =row =diag =rdiag=0;
        }*/

        //Check the Queue
        /*int xSize = XCollection.size(), oSize = OCollection.size();
        for (int i = 0; i<xSize; i++)
        {
            double i1 = XCollection.remove(), i2=XCollection.remove(), i3= XCollection.remove();
            if ((i1+2==i3 && i2+1==i3) || (i1+(2*increment)==i3 && i2+increment==i3) || (i1+2+(2*increment)==i3 && i2+1+increment==i3))
            {
                return "XWon";
            }
            XCollection.add(i1);
            XCollection.add(i2);
            XCollection.add(i3);
        } //Check X
        for (int i = 0; i<oSize; i++)
        {
            double i1 = OCollection.remove(), i2=OCollection.remove(), i3= OCollection.remove();
            if ((i1+2==i3 && i2+1==i3) || (i1+(2*increment)==i3 && i2+increment==i3) || (i1+2+(2*increment)==i3 && i2+1+increment==i3))
            {
                return "OWon";
            }
            OCollection.add(i1);
            OCollection.add(i2);
            OCollection.add(i3);
        } //Check X */
        return "noWinner";
    }
}
