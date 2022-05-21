filename = "polblogs."


def readNet(fileName):
    f = open(fileName, "r")
    net = {}
    lung = 0
    ok = True
    file = f.readlines()
    mat = []
    for line in file:
        source = 0
        target = 0
        substr = line.split()
        if (substr.__contains__("id")):
            for x in substr:
                if x.isnumeric():
                    lung = int(x)
        if (ok and substr.__contains__("edge")):
            ok = False
            for i in range(lung):
                list = []
                for j in range(lung):
                    list.append(0)
                mat.append(list)
        if (substr.__contains__("source")):
            for x in substr:
                if x.isnumeric():
                    source = int(x)
        if (substr.__contains__("target")):
            for x in substr:
                if x.isnumeric():
                    target = int(x)
            mat[source][target] = 1

    net['noNodes'] = lung
    net["mat"] = mat
    degrees = []
    noEdges = 0
    for i in range(lung):
        d = 0
        for j in range(lung):
            if (mat[i][j] == 1):
                d += 1
            if (j > i):
                noEdges += mat[i][j]
        degrees.append(d)
    net["noEdges"] = noEdges
    net["degrees"] = degrees
    f.close()
    return net


network = readNet(filename + "gml")


def modularity(communities):
    noNodes = network['noNodes']
    mat = network['mat']
    degrees = network['degrees']
    noEdges = network['noEdges']
    M = 2 * noEdges
    Q = 0.0
    for i in range(0, noNodes):
        for j in range(0, noNodes):
            if (communities[i] == communities[j]):
                Q += (mat[i][j] - degrees[i] * degrees[j] / M)
    return Q * 1 / M


from random import uniform, random


def generateNewValue(lim1, lim2):
    return randint(lim1, lim2)


from random import randint
from random import seed
from collections import Counter

seed(1)

from random import uniform


# from fcOptimisGA.utils import generateNewValue
class Chromosome:
    def __init__(self, problParam=None):
        self.__problParam = problParam
        self.__repres = [generateNewValue(problParam['min'], problParam['max']) for _ in range(problParam['noDim'])]
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
        r = randint(0, len(self.__repres) - 1)
        newrepres = []
        for i in range(r):
            newrepres.append(self.__repres[i])
        for i in range(r, len(self.__repres)):
            newrepres.append(c.__repres[i])
        offspring = Chromosome(c.__problParam)
        offspring.repres = newrepres
        return offspring

    def mutation(self, pop):
        # pos = randint(0, len(self.__repres) - 1)
        # self.__repres[pos] = generateNewValue(self.__problParam['min'], self.__problParam['max'])
        pos1 = randint(0, len(self.__repres) - 1)
        pos2 = randint(0, len(self.__repres) - 1)
        pos3 = randint(0, len(self.__repres) - 1)
        u = randint(0, len(self.__repres) - 1)
        self.__repres[u] = int(self.repres[pos1] + uniform(0, 1) * (self.repres[pos2] - self.repres[pos3]))

    def __str__(self):
        return '\nChromo: ' + str(self.__repres) + ' has fit: ' + str(self.__fitness)

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__repres == c.__repres and self.__fitness == c.__fitness


MIN = 1
MAX = network['noNodes']
# MAX=2

from random import randint


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
            if (c.fitness > best.fitness):
                best = c
        return best

    def worstChromosome(self):
        best = self.__population[0]
        for c in self.__population:
            if (c.fitness < best.fitness):
                best = c
        return best

    def selection(self):
        pos1 = randint(0, self.__param['popSize'] - 1)
        pos2 = randint(0, self.__param['popSize'] - 1)
        if (self.__population[pos1].fitness > self.__population[pos2].fitness):
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
            off.mutation(self.population)
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


# from fcOptimisGA.RealChromosome import Chromosome
# from fcOptimisGA.BinChromosome import Chromosome

# plot the function to be optimised
noDim = network['noNodes']

# initialise de GA parameters
gaParam = {'popSize': 100, 'noGen': 100, 'pc': 0.8, 'pm': 0.1}
# problem parameters
problParam = {'min': MIN, 'max': MAX, 'function': modularity, 'noDim': noDim, 'noBits': 8}

# store the best/average solution of each iteration (for a final plot used to anlyse the GA's convergence)
allBestFitnesses = []
allAvgFitnesses = []
generations = []

ga = GA(gaParam, problParam)
ga.initialisation()
ga.evaluation()

bestChromosone = Chromosome
bestChromosone.fitness = -1


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

    if bestChromo.fitness > bestChromosone.fitness:
        bestChromosone = bestChromo

    print('Best solution in generation ' + str(g) + ' is: x = ' + str(bestChromo.repres) + ' Modularity = ' + str(
        bestChromo.fitness))
    print('Number of comunities in this generation is: ' + str(len(Counter(bestChromo.repres).keys())))

print('\nNumber of comunities: ' + str(len(Counter(bestChromosone.repres).keys())))
print('Best solution:' + '\n')
print(bestChromosone.repres)
#print(bestChromosone.fitness)

# plot a particular division in communities
communities = ga.bestChromosome().repres

print("Fitnessul cel mai bun este: " + str(ga.bestChromosome().fitness))