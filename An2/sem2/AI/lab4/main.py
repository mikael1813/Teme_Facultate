filename = "hard_tsp.txt"


def readNet(fileName):
    f = open(fileName, "r")
    net = {}
    n = int(f.readline())
    table = []

    for i in range(n):
        line = f.readline().split(',')
        nline = []
        for j in line:
            nline.append(int(j))
        table.append(nline)
    # print(table)

    f.close()

    net['noNodes'] = n
    net["tab"] = table
    return net


network = readNet(filename)


def path_length(drum):
    a = network['tab']
    sum = 0
    for i in range(0, len(drum) - 1):
        sum = sum + a[drum[i]][drum[i + 1]]
    return sum

from random import uniform, random
import random
from random import seed, randint
from collections import Counter

seed(1)

from random import uniform


def generateNewValue(n):
    list = []
    for i in range(n):
        list.append(i)
    random.shuffle(list)
    list.append(list[0])
    return list


# from fcOptimisGA.utils import generateNewValue
class Chromosome:
    def __init__(self, problParam=None):
        self.__problParam = problParam
        self.__repres = generateNewValue(problParam['noNodes'])
        self.__fitness = 0.0

    @property
    def repres(self):
        return self.__repres

    @property
    def fitness(self):
        return self.__fitness

    @repres.setter
    def repres(self, l=[]):
        self.__repres = l

    @fitness.setter
    def fitness(self, fit=0.0):
        self.__fitness = fit

    def crossover(self, c):
        r1 = randint(1, len(self.__repres) - 2)
        r2 = randint(1, len(self.__repres) - 2)
        if r1 > r2:
            r1, r2 = r2, r1
        newrepres = []
        for i in self.__repres:
            newrepres.append(i)
        for i in range(r1, r2 + 1):
            newrepres[i] = c.__repres[i]
        lacking = []
        for i in range(len(self.__repres) - 1):
            if not newrepres.__contains__(i):
                lacking.append(i)
        random.shuffle(lacking)
        for i in range(len(self.__repres)):
            if newrepres.count(newrepres[i]) > 1 and i != 0 and i != len(self.__repres) - 1:
                newrepres[i] = lacking[0]
                lacking.remove(lacking[0])

        offspring = Chromosome(c.__problParam)
        offspring.repres = newrepres
        return offspring

    def mutation(self,pm):
        # print(self.__repres)
        if(uniform(0,1) <= pm):
            pos1 = randint(0, len(self.__repres) - 1)
            pos2 = randint(0, len(self.__repres) - 1)
            # print(pos1)
            # print(pos2)
            c = self.repres[pos1]
            self.repres[pos1] = self.repres[pos2]
            self.repres[pos2] = c
            if pos1 == len(self.__repres) - 1 or pos2 == len(self.__repres) - 1:
                self.repres[0] = self.repres[-1]
            else:
                self.repres[-1] = self.__repres[0]
            # print(self.__repres)

    def __str__(self):
        return '\nChromo: ' + str(self.__repres) + ' has fit: ' + str(self.__fitness)

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__repres == c.__repres and self.__fitness == c.__fitness


def pseudoTestXO():
    # seed(5)
    problParam = {'noNodes': 10}
    c1 = Chromosome(problParam)
    c2 = Chromosome(problParam)
    print('parent1: ', c1)
    print('parent2: ', c2)
    off = c1.crossover(c2)
    print('offspring: ', off)


def pseudoTestMutation():
    problParam = {'noNodes' : 10}
    c1 = Chromosome(problParam)
    print('before mutation: ', c1)
    c1.mutation(0.1)
    print('after mutation: ', c1)

pseudoTestXO()
pseudoTestMutation()

class GA:
    def __init__(self, param=None, problParam=None):
        self.__param = param
        self.__problParam = problParam
        self.__population = []

    @property
    def population(self):
        return self.__population

    def initialisation(self):
        for _ in range(0, self.__param['popSize']):
            c = Chromosome(self.__problParam)
            self.__population.append(c)

    def evaluation(self):
        for c in self.__population:
            c.fitness = self.__problParam['function'](c.repres)

    def bestChromosome(self):
        best = self.__population[0]
        for c in self.__population:
            if (c.fitness < best.fitness):
                best = c
        return best

    def worstChromosome(self):
        best = self.__population[0]
        for c in self.__population:
            if (c.fitness > best.fitness):
                best = c
        return best

    def selection(self):
        pos1 = randint(0, self.__param['popSize'] - 1)
        pos2 = randint(0, self.__param['popSize'] - 1)
        if (self.__population[pos1].fitness < self.__population[pos2].fitness):
            return pos1
        else:
            return pos2

    def oneGeneration(self):
        newPop = []
        for _ in range(self.__param['popSize']):
            p1 = self.__population[self.selection()]
            p2 = self.__population[self.selection()]
            off = p1.crossover(p2)
            off.mutation(self.population)
            newPop.append(off)
        self.__population = newPop
        self.evaluation()

    def oneGenerationElitism(self):
        newPop = [self.bestChromosome()]
        for _ in range(self.__param['popSize'] - 1):
            p1 = self.__population[self.selection()]
            p2 = self.__population[self.selection()]
            off = p1.crossover(p2)
            off.mutation(self.__param['pm'])
            newPop.append(off)
        self.__population = newPop
        self.evaluation()

    def oneGenerationSteadyState(self):
        for _ in range(self.__param['popSize']):
            p1 = self.__population[self.selection()]
            p2 = self.__population[self.selection()]
            off = p1.crossover(p2)
            off.mutation()
            off.fitness = self.__problParam['function'](off.repres)
            worst = self.worstChromosome()
            if (off.fitness < worst.fitness):
                worst = off


noDim = network['noNodes']

# initialise de GA parameters
gaParam = {'popSize': 100, 'noGen': 100, 'pc': 0.8, 'pm': 0.1}
# problem parameters
problParam = {'function': path_length, 'noNodes': noDim}

# store the best/average solution of each iteration (for a final plot used to anlyse the GA's convergence)
allBestFitnesses = []
allAvgFitnesses = []
generations = []

ga = GA(gaParam, problParam)
ga.initialisation()
ga.evaluation()

bestChromosone = Chromosome
bestChromosone.fitness = 10000000000000

for g in range(gaParam['noGen']):
    # plotting preparation
    allPotentialSolutionsX = [c.repres for c in ga.population]
    allPotentialSolutionsY = [c.fitness for c in ga.population]
    bestSolX = ga.bestChromosome().repres
    bestSolY = ga.bestChromosome().fitness
    allBestFitnesses.append(bestSolY)
    allAvgFitnesses.append(sum(allPotentialSolutionsY) / len(allPotentialSolutionsY))
    generations.append(g)
    # plotAFunction(xref, yref, allPotentialSolutionsX, allPotentialSolutionsY, bestSolX, [bestSolY],
    # 'generation: ' + str(g))

    # logic alg
    ga.oneGenerationElitism()
    # ga.oneGenerationElitism()
    # ga.oneGenerationSteadyState()

    bestChromo = ga.bestChromosome()

    if bestChromo.fitness < bestChromosone.fitness:
        bestChromosone = bestChromo

    print('Best solution in generation ' + str(g) + ' is: x = ' + str(bestChromo.repres) + ' Distanta = ' + str(
        bestChromo.fitness))

print('Best solution:')
print(bestChromosone.repres)
# print(bestChromosone.fitness)

# plot a particular division in communities
communities = ga.bestChromosome().repres

print("Fitnessul cel mai bun este: " + str(ga.bestChromosome().fitness))
