f = open("date.txt", "r")
lines = f.readlines()

neterminal = input("Introdu caracterul cautat: ")

verificator = False

for line in lines:
    stieTerminale = line.split("->")
    if stieTerminale[0].__contains__(neterminal):
        #print(line)
        verificator = True

for line in lines:
    if line.__contains__(neterminal) and verificator:
        print(line)

if not verificator:
    print("Nu e neterminal")
