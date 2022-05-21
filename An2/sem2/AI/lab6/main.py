import math


def readNetRegression(fileName):
    f = open(fileName, "r")
    realOutputs = []
    computedOutputs = []

    n = int(f.readline())
    for t in range(n):
        # list = []
        for i in f.readline().split(','):
            realOutputs.append(float(i))
        # realOutputs.append(list)
    n = int(f.readline())
    for t in range(n):
        # list = []
        for i in f.readline().split(','):
            computedOutputs.append(float(i))
        # computedOutputs.append(list)

    return realOutputs, computedOutputs


def mean_square_error(real, predicted):
    sum = 0
    for i in range(len(real)):
        sum = sum + (real[i] - predicted[i]) ** 2
    sum = sum / len(real)
    return sum


def readNetBinary(fileName):
    f = open(fileName, "r")
    realOutputs = []
    computedOutputs = []

    str = f.readline()
    for i in str.split(","):
        if i.__contains__('\n'):
            realOutputs.append(i[:-1])
        else:
            realOutputs.append(i)

    str = f.readline()
    for i in str.split(","):
        computedOutputs.append(i)

    f.close()

    return realOutputs, computedOutputs


def readBinaryProbability(fileName):
    f = open(fileName, "r")
    realOutputs = []
    computedOutputs = []

    str = f.readline()
    for i in str.split(","):
        if i.__contains__('\n'):
            realOutputs.append(i[:-1])
        else:
            realOutputs.append(i)

    n = int(f.readline())
    for t in range(n):
        list = []
        for i in f.readline().split(','):
            list.append(float(i))
        computedOutputs.append(list)

    f.close()

    return realOutputs, computedOutputs


def regression(filename):
    realOutputs, computedOutputs = readNetRegression(filename)
    print(realOutputs)
    print(computedOutputs)

    # compute the prediction error

    from math import sqrt

    # MAE
    errorL1 = sum(abs(r - c) for r, c in zip(realOutputs, computedOutputs)) / len(realOutputs)
    print('Error (L1): ', errorL1)

    # RMSE
    errorL2 = sqrt(sum((r - c) ** 2 for r, c in zip(realOutputs, computedOutputs)) / len(realOutputs))
    print('Error (L2): ', errorL2)

    loss = mean_square_error(realOutputs, computedOutputs)
    print('Loss mean square error: ', loss)


# version 1 - using the sklearn functions
def evalClassificationV1(realLabels, computedLabels, labelNames):
    from sklearn.metrics import confusion_matrix, accuracy_score, precision_score, recall_score

    cm = confusion_matrix(realLabels, computedLabels, labels=labelNames)
    print(cm)
    #acc = (TP+TN)/(TP+TN+FP+FN)
    #precision = TP/(TP+FP)
    #recall =  TP/(TP+FN)
    acc = accuracy_score(realLabels, computedLabels)
    precision = precision_score(realLabels, computedLabels, average=None, labels=labelNames)
    recall = recall_score(realLabels, computedLabels, average=None, labels=labelNames)
    return acc, precision, recall


def binary(filename):
    realLabels, computedLabels = readNetBinary(filename)
    labelNames = list(set(realLabels))
    print(realLabels)
    print(computedLabels)
    print(labelNames)
    # plot the data
    import matplotlib.pyplot as plt

    indexes = [i for i in range(len(realLabels))]
    real, = plt.plot(indexes, realLabels, 'ro', label='real')
    computed, = plt.plot(indexes, computedLabels, 'bo', label='computed')
    plt.xlim(0, 8)
    plt.ylim(0, 10)
    plt.legend([real, (real, computed)], ["Real", "Computed"])
    plt.show()

    acc, prec, recall = evalClassificationV1(realLabels, computedLabels, labelNames)

    print('acc: ', acc, ' precision: ', prec, ' recall: ', recall)


def bynary_cross_entropy_loss(real, predicted):
    sum = 0
    for i in range(len(real)):
        sum = sum + (real[i][0] * math.log(predicted[i][0]) + (1 - real[i][0]) * math.log(1 - predicted[i][0]))
        sum = sum + (real[i][1] * math.log(predicted[i][1]) + (1 - real[i][1]) * math.log(1 - predicted[i][1]))
    sum = sum / len(real)
    sum = sum * (-1)
    return sum

def cross_entropy_multi_label(real,predicted):
    sum = 0
    for i in range(len(real)):
        for j in range(len(real[i])):
            sum = sum - (real[i][j]*math.log(predicted[i][j]) + (1-real[i][j])*math.log(1-predicted[i][j]))
    return sum/len(real)

def cross_entropy_multi_class(real,predicted):
    sum = 0
    for i in range(len(real)):
        for j in range(len(real[i])):
            sum = sum - (real[i][j]*math.log(predicted[i][j]))
    return sum/len(real)


def binaryProbability(filename):
    realLabels, computedLabels = readBinaryProbability(filename)
    print(realLabels)
    print(computedLabels)
    labelNames = list(set(realLabels))
    print(labelNames)
    realLabelsProb = []
    for i in realLabels:
        if i == labelNames[0]:
            realLabelsProb.append([1, 0])
        else:
            realLabelsProb.append([0, 1])
    print(realLabelsProb)
    loss = bynary_cross_entropy_loss(realLabelsProb,computedLabels)
    print('Binary cross entropy :',loss)


def multiclassloss():
    real = [[1,0,0],[0,1,0]]
    predicted = [[0.5,0.2,0.3],[0.6,0.1,0.3]]
    print(cross_entropy_multi_class(real,predicted))

def multilabelloss():
    real = [[1,0,1],[1,1,0]]
    predicted=[[0.6,0.7,0.4],[0.2,0.5,0.6]]
    print(cross_entropy_multi_label(real,predicted))

regression("date.txt")
binary("binary.txt")
binaryProbability("binaryProbability.txt")
multiclassloss()
multilabelloss()
