#Simple Sudoku Solver

"""define variables to replace range(9) and range(3)"""
nine = [0,1,2,3,4,5,6,7,8]
three = [0,1,2]

"""define the puzzle to solve"""
planePuzzle = [[0,0,3,5,0,2,9,0,0],
               [0,0,0,0,4,0,0,0,0],
               [1,0,6,0,0,0,3,0,5],
               [9,0,0,2,5,1,0,0,8],
               [0,7,0,4,0,8,0,3,0],
               [8,0,0,7,6,3,0,0,1],
               [3,0,8,0,0,0,1,0,4],
               [0,0,0,0,2,0,0,0,0],
               [0,0,5,1,0,4,8,0,0]]

# define the blank puzzle we will complete
completedPuzzle = [[0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0]]

"""define the array of all possibile values for the puzzle"""
possibleValues = [[[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                  [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                  [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                  [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                  [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                  [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                  [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                  [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                  [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]]]

def removeValueFromPossibilities(puzzle,possibilities,row,column,value):
    #remove from other tiles in the row
    for i in nine:
        if(i != column):
            try: possibilities[row][i].remove(value)
            except: pass

    #remove from other tiles in the column
    for i in nine:
        if(i != row):
            try: possibilities[i][column].remove(value)
            except: pass

    #remove from other tiles in the grid
    for x in three:
        for y in three:
            if((3*((3*(int(row/3))+int(column/3))%3))+x != column and (3*(int((3*(int(row/3))+int(column/3))/3)))+y != row):
                try: possibilities[(3*(int((3*(int(row/3))+int(column/3))/3)))+y][(3*((3*(int(row/3))+int(column/3))%3))+x].remove(value)
                except: pass

def reducePossibilities(puzzle,possibilities):
    for row in nine:
        for column in nine:
            if(puzzle[row][column] != 0):
                possibilities[row][column] = [puzzle[row][column]]
                removeValueFromPossibilities(puzzle,possibilities,row,column,puzzle[row][column])

def isComplete(puzzle):
    for row in nine:
        for column in nine:
            if(puzzle[row][column] == 0):
                return False

    return True

def solve(puzzle,possibilities,completedPuzzle):
    reducePossibilities(puzzle,possibilities)

    WHILE_LIMIT = 100
    WHILE_COUNT = 0
    
    while((not isComplete(completedPuzzle)) and WHILE_COUNT<WHILE_LIMIT):
        for row in nine:
            for column in nine:
                if(len(possibilities[row][column]) == 1):
                    completedPuzzle[row][column] = possibilities[row][column][0]
                    removeValueFromPossibilities(puzzle,possibilities,row,column,possibilities[row][column][0])

        WHILE_COUNT += 1

    if(WHILE_COUNT == WHILE_LIMIT):
        print("failed to solve puzzle")
        print(possibilities)
    else:
        print("Solved puzzle in",WHILE_COUNT,"iterations:")
        for row in completedPuzzle:
            print(row)

solve(planePuzzle,possibleValues,completedPuzzle)
