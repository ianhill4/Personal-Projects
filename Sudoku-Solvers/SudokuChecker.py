# an example board to check the program with
board = [[7,4,3,5,1,2,9,8,6],
         [5,8,9,3,4,6,2,1,7],
         [1,2,6,9,8,7,3,4,5],
         [9,3,4,2,5,1,7,6,8],
         [6,7,1,4,9,8,5,3,2],
         [8,5,2,7,6,3,4,9,1],
         [3,9,8,6,7,5,1,2,4],
         [4,1,7,8,2,9,6,5,3],
         [2,6,5,1,3,4,8,7,9]]

solved = True

for i in range(len(board)):
    if(sum(board[i]) != 45):
        print("Row",i+1,"is incorrect")
        solved = False

for i in range(len(board[0])):
    colSum = 0
    
    for x in range(len(board)):
        colSum += board[x][i]

    if(colSum != 45):
        print("Column",i+1,"is incorrect")
        solved = False

for row in range(3):
    for column in range(3):
        squareSum = 0;
        for x in range(3):
            for y in range(3):
                squareSum += board[(row*3)+x][(column*3)+y]

        if(squareSum != 45):
            print("Square",(row*3)+column+1,"is incorrect")
            solved = False

if(solved):
    print("This puzzle is solved")
