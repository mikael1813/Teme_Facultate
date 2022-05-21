import csv
import pandas as pd

from math import exp
from numpy.linalg import inv
import numpy as np


def sigmoid(x):
    return 1 / (1 + exp(-x))


def normalisation_manual(features):
    result = []
    if len(features[0]) == 1:
        xx = [el[0] for el in features]
        minFeat = min(xx)
        maxFeat = max(xx)
        scaledFeatures = [(feat - minFeat) / (maxFeat - minFeat) for feat in xx]
        return scaledFeatures
    else:
        print(features[0])
        for i in range(len(features[0])):
            xx = [el[i] for el in features]
            minFeat = min(xx)
            maxFeat = max(xx)
            scaledFeatures = [(feat - minFeat) / (maxFeat - minFeat) for feat in xx]

            if i == 0:
                for j in scaledFeatures:
                    result.append([j])
            else:
                for j in range(len(result)):
                    result[j].append(scaledFeatures[j])

    return result


class MyLogisticRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    # use the gradient descent method
    # simple stochastic GD
    def fit(self, x, y, learningRate=0.001, noEpochs=10000):
        self.coef_ = [0.0 for _ in range(1 + len(x[0]))]  # beta or w coefficients y = w0 + w1 * x1 + w2 * x2 + ...
        # self.coef_ = [random.random() for _ in range(len(x[0]) + 1)]    #beta or w coefficients
        for epoch in range(noEpochs):
            # TBA: shuffle the trainind examples in order to prevent cycles
            for i in range(len(x)):  # for each sample from the training data
                ycomputed = sigmoid(self.eval(x[i], self.coef_))  # estimate the output
                crtError = ycomputed - y[i]  # compute the error for the current sample
                for j in range(0, len(x[0])):  # update the coefficients
                    self.coef_[j + 1] = self.coef_[j + 1] - learningRate * crtError * x[i][j]
                self.coef_[0] = self.coef_[0] - learningRate * crtError * 1

        self.intercept_ = self.coef_[0]
        self.coef_ = self.coef_[1:]

    def eval(self, xi, coef):
        yi = coef[0]
        for j in range(len(xi)):
            yi += coef[j + 1] * xi[j]
        return yi

    def predictOneSample(self, sampleFeatures):
        threshold = 0.5
        coefficients = [self.intercept_] + [c for c in self.coef_]
        computedFloatValue = self.eval(sampleFeatures, coefficients)
        computed01Value = sigmoid(computedFloatValue)
        computedLabel = 0 if computed01Value < threshold else 1
        return computed01Value

    def predict(self, inTest):
        computedLabels = [self.predictOneSample(sample) for sample in inTest]
        return computedLabels


def loadData(fileName):
    data = []
    dataNames = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                dataNames = row
            else:
                data.append(row)
            line_count += 1
    inputs = [[data[i][0], data[i][1], data[i][2], data[i][3]] for i in range(len(data))]
    output = [data[i][4] for i in range(len(data))]
    return inputs, output


from sklearn.preprocessing import StandardScaler


def normalisation(trainData, testData):
    scaler = StandardScaler()
    if not isinstance(trainData[0], list):
        # encode each sample into a list
        trainData = [[d] for d in trainData]
        testData = [[d] for d in testData]

        scaler.fit(trainData)  # fit only on training data
        normalisedTrainData = scaler.transform(trainData)  # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data

        # decode from list to raw values
        normalisedTrainData = [el[0] for el in normalisedTrainData]
        normalisedTestData = [el[0] for el in normalisedTestData]
    else:
        scaler.fit(trainData)  # fit only on training data
        normalisedTrainData = scaler.transform(trainData)  # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data
    return normalisedTrainData, normalisedTestData


import numpy as np


def tool():
    inputs, outputs = loadData('bezdekIris.data')

    # split data into train and test subsets
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    # normalise the features
    trainInputs, testInputs = normalisation(trainInputs, testInputs)

    from sklearn.linear_model import LogisticRegression

    regressor = LogisticRegression()
    regressor.fit(trainInputs, trainOutputs)

    w1 = regressor.coef_
    w0 = regressor.intercept_
    print(w1)
    print(w0)

    computedTestOutputs = regressor.predict(testInputs)

    error = 0.0
    for t1, t2 in zip(computedTestOutputs, testOutputs):
        if (t1 != t2):
            error += 1
    error = error / len(testOutputs)
    print("classification error (manual): ", error)

    from sklearn.metrics import accuracy_score
    error = 1 - accuracy_score(testOutputs, computedTestOutputs)
    print("classification error (tool): ", error)


def manual():
    inputs, outputs = loadData('bezdekIris.data')
    for i in range(len(inputs)):
        for j in range(len(inputs[i])):
            inputs[i][j] = float(inputs[i][j])
    names = list(set(outputs))
    print(names)
    # split data into train and test subsets
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    # normalise the features
    # trainInputs, testInputs = normalisation(trainInputs, testInputs)
    trainInputs = normalisation_manual(trainInputs)
    testInputs = normalisation_manual(testInputs)
    computedTrueOutput = []
    for i in names:
        regressor = MyLogisticRegression()
        new_output = []
        for j in trainOutputs:
            if j == i:
                new_output.append(1)
            else:
                new_output.append(0)
        regressor.fit(trainInputs, new_output)
        computedTestOutputs = regressor.predict(testInputs)

        computedTrueOutput.append(computedTestOutputs)

    computedOutput = []
    for i in range(len(computedTrueOutput[0])):
        max = 0
        index = -1
        for j in range(len(computedTrueOutput)):
            if computedTrueOutput[j][i] > max:
                max = computedTrueOutput[j][i]
                index = j
        computedOutput.append(names[index])
    print(computedOutput)

    from sklearn.linear_model import LogisticRegression

    error = 0.0
    for t1, t2 in zip(computedOutput, testOutputs):
        if (t1 != t2):
            error += 1
    error = error / len(testOutputs)
    print("classification error (manual): ", error)

    from sklearn.metrics import accuracy_score
    error = 1 - accuracy_score(testOutputs, computedOutput)
    print("classification error (tool): ", error)


tool()
manual()
