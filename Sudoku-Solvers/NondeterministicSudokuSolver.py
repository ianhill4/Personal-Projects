#Nondeterministically solve sodoku puzzles (for advanced puzzles where guessing is required)

import heapq

"""define variables to replace range(9) and range(3)"""
nine = [0,1,2,3,4,5,6,7,8]
three = [0,1,2]

"""define some example puzzle boards"""
planePuzzle = [[0,0,3,5,0,2,9,0,0],
               [0,0,0,0,4,0,0,0,0],
               [1,0,6,0,0,0,3,0,5],
               [9,0,0,2,5,1,0,0,8],
               [0,7,0,4,0,8,0,3,0],
               [8,0,0,7,6,3,0,0,1],
               [3,0,8,0,0,0,1,0,4],
               [0,0,0,0,2,0,0,0,0],
               [0,0,5,1,0,4,8,0,0]]

hardPuzzle = [[3,7,9,0,0,0,0,0,0],
                [8,0,0,4,2,0,0,0,0],
                [2,0,0,0,1,0,0,0,0],
                [0,5,0,0,0,0,0,2,0],
                [0,0,7,0,0,0,0,0,8],
                [0,0,0,0,6,5,0,0,4],
                [0,0,0,9,0,0,0,0,0],
                [0,8,0,3,0,0,7,0,0],
                [0,0,0,0,0,2,3,9,0]]

extremePuzzle = [[0,0,0,0,0,0,0,9,0],
                   [0,9,7,0,0,0,4,0,0],
                   [0,0,8,0,6,0,0,7,0],
                   [0,0,0,9,8,7,0,0,0],
                   [0,0,0,0,0,4,0,0,1],
                   [0,0,0,0,0,6,0,2,4],
                   [2,0,0,0,0,0,5,0,3],
                   [0,4,0,0,5,0,0,0,0],
                   [6,0,0,8,0,0,0,0,0]]

dailyPuzzle = [[0,0,6,3,4,0,0,0,1],
                [0,0,9,0,0,0,0,7,2],
                [4,5,0,1,0,0,0,0,0],
                [0,0,0,0,8,0,0,0,3],
                [0,0,1,7,0,0,0,0,0],
                [8,3,5,2,0,0,0,4,6],
                [1,0,8,4,0,3,0,0,0],
                [6,2,0,0,0,1,0,0,0],
                [0,0,0,9,0,0,8,0,7]]

emptyPuzzle = [[0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0],
                   [0,0,0,0,0,0,0,0,0]]

"""define helper functions"""

def updatePossibilitiesGivenValue(puzzle,possibilities,row,column,value):
    #remove a given possibility from the other tiles in the same row
    for i in nine:
        if(i != column):
            try: possibilities[row][i].remove(value)
            except: pass

    #remove a given possibility from the other tiles in the same column
    for i in nine:
        if(i != row):
            try: possibilities[i][column].remove(value)
            except: pass

    #remove a given possibility from the other tiles in the same grid
    for x in three:
        for y in three:
            if((3*((3*(int(row/3))+int(column/3))%3))+x != column and (3*(int((3*(int(row/3))+int(column/3))/3)))+y != row):
                try: possibilities[(3*(int((3*(int(row/3))+int(column/3))/3)))+y][(3*((3*(int(row/3))+int(column/3))%3))+x].remove(value)
                except: pass

#reduce initial possibilities using the rules of sodoku
def reducePossibilities(puzzle,possibilities):
    for row in nine:
        for column in nine:
            if(puzzle[row][column] != 0):
                possibilities[row][column] = [puzzle[row][column]]
                updatePossibilitiesGivenValue(puzzle,possibilities,row,column,puzzle[row][column])

#function to quickly check if the puzzle is completed
def isComplete(puzzle):
    for row in nine:
        if(0 in puzzle[row]):
            return False

    return True

#function to quickly check if the puzzle is solved
def isSolved(puzzle):
    for i in nine:
        if(sum(puzzle[i]) != 45):
            #print("Row",i+1,"is incorrect")
            return False

    for i in nine:
        colSum = 0
        
        for x in nine:
            colSum += puzzle[x][i]

        if(colSum != 45):
            #print("Column",i+1,"is incorrect")
            return False

    for row in three:
        for column in three:
            squareSum = 0;
            for x in three:
                for y in three:
                    squareSum += puzzle[(row*3)+x][(column*3)+y]

            if(squareSum != 45):
                #print("Square",(row*3)+column+1,"is incorrect")
                return False

    return True

#function to determine the next best tile to try filling in the value for
def findBestTile(puzzle,possibilities):
        fewestRemainingPossibilities = 9
        locationOfBestChoice = [0,0]
        
        for row in nine:
            for column in nine:
                p = len(possibilities[row][column])
                if(p == 1):
                    if(puzzle[row][column] == 0):
                        return [1,[row,column]]
                elif(p<fewestRemainingPossibilities):
                    fewestRemainingPossibilities = p
                    locationOfBestChoice = [row,column]

        return [fewestRemainingPossibilities,locationOfBestChoice]

#define the intial possible values array
possibleValues = [[[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                      [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                      [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                      [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                      [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                      [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                      [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                      [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]],
                      [[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9],[1,2,3,4,5,6,7,8,9]]]            

#define a recursive function to copy lists of lists
def copy(array):
    if(type(array) != list):
        return array
    
    copiedArray = []

    for element in array:
        copiedArray.append(copy(element))

    return copiedArray

#define the recursive function to solve the puzzels
def solvePuzzle(puzzle,possibleValues,depth):
    if(depth == 100):
        print("depth reached")
        print(puzzle)
        return puzzle

    if(isSolved(puzzle)):
        return [True,puzzle]

    reducePossibilities(puzzle,possibleValues)

    if(not isComplete(puzzle)):
        #pick a move
        bestMove = findBestTile(puzzle,possibleValues)

        if(bestMove[0] == 0):
            return [False]

        for i in range(bestMove[0]):
            newPuzzle = copy(puzzle)
            newPossibilities = copy(possibleValues)

            newPuzzle[bestMove[1][0]][bestMove[1][1]] = possibleValues[bestMove[1][0]][bestMove[1][1]][i]

            result = solvePuzzle(newPuzzle,newPossibilities,depth+1)

            if(result[0]):
                return result
    else:
        return [False]

    return [False]

userPuzzle = []
for i in nine:
    row = []
    for i in nine:
        row.append(int(input()))

    userPuzzle.append(row)
    
solved = solvePuzzle(userPuzzle,possibleValues,0)

if(solved[0]):
    print("Puzzle solved, solution:"
    for row in solved[1]:
        print(row)
else:
    print("Couldn't solve given puzzle")
