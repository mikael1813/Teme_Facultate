import csv

import matplotlib.pyplot as plt
import numpy as np

import random


class MySGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    # simple stochastic GD
    def fit(self, x, y, learningRate=0.001, noEpochs=1000):
        self.coef_ = [0.0 for _ in range(len(x[0]) + 1)]  # beta or w coefficients y = w0 + w1 * x1 + w2 * x2 + ...
        # self.coef_ = [random.random() for _ in range(len(x[0]) + 1)]    #beta or w coefficients
        for epoch in range(noEpochs):
            # TBA: shuffle the trainind examples in order to prevent cycles
            for i in range(len(x)):  # for each sample from the training data
                ycomputed = self.eval(x[i])  # estimate the output
                crtError = ycomputed - y[i]  # compute the error for the current sample
                for j in range(0, len(x[0])):  # update the coefficients
                    self.coef_[j] = self.coef_[j] - learningRate * crtError * x[i][j]
                self.coef_[len(x[0])] = self.coef_[len(x[0])] - learningRate * crtError * 1

        self.intercept_ = self.coef_[-1]
        self.coef_ = self.coef_[:-1]

    def fit_uni(self, x, y, learningRate=0.001, noEpochs=1000):
        self.coef_ = 0.0  # beta or w coefficients y = w0 + w1 * x1 + w2 * x2 + ...
        # self.coef_ = random.random()    #beta or w coefficients
        for epoch in range(noEpochs):
            # TBA: shuffle the trainind examples in order to prevent cycles
            for i in range(len(x)):  # for each sample from the training data
                ycomputed = self.eval_uni(x[i])  # estimate the output
                crtError = ycomputed - y[i]  # compute the error for the current sample
                # update the coefficients
                self.coef_ = self.coef_ - learningRate * crtError * x[i]
                self.coef_ = self.coef_ - learningRate * crtError * 1

        self.intercept_ = self.coef_
        self.coef_ = self.coef_

    def eval(self, xi):
        yi = self.coef_[-1]
        for j in range(len(xi)):
            yi += self.coef_[j] * xi[j]
        return yi

    def eval_uni(self, xi):
        yi = self.coef_
        yi += self.coef_ * xi
        return yi

    def predict(self, x):
        yComputed = [self.eval(xi) for xi in x]
        return yComputed

    def predict_uni(self, x):
        yComputed = [self.eval_uni(xi) for xi in x]
        return yComputed

def normalisation_manual(features):
    result=[]
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

def loadData(fileName, inputVariabName1, inputVariabName2, outputVariabName):
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
    selectedVariable = dataNames.index(inputVariabName1)
    inputs1 = [float(data[i][selectedVariable]) for i in range(len(data))]
    selectedVariable = dataNames.index(inputVariabName2)
    inputs2 = [float(data[i][selectedVariable]) for i in range(len(data))]
    inputs = []
    for i in range(0, len(inputs1)):
        inputs.append([inputs1[i], inputs2[i]])
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

    return inputs, outputs


def plot3Ddata(x1Train, x2Train, yTrain, x1Model=None, x2Model=None, yModel=None, x1Test=None, x2Test=None, yTest=None,
               title=None):
    from mpl_toolkits import mplot3d
    ax = plt.axes(projection='3d')
    if (x1Train):
        plt.scatter(x1Train, x2Train, yTrain, c='r', marker='o', label='train data')
    if (x1Model):
        plt.scatter(x1Model, x2Model, yModel, c='b', marker='_', label='learnt model')
    if (x1Test):
        plt.scatter(x1Test, x2Test, yTest, c='g', marker='^', label='test data')
    plt.title(title)
    ax.set_xlabel("capita")
    ax.set_ylabel("freedom")
    ax.set_zlabel("happiness")
    plt.legend()
    plt.show()


def plotDataHistogram(x, variableName):
    n, bins, patches = plt.hist(x, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()


from sklearn.preprocessing import StandardScaler
from math import sqrt


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


def rezolvare_cu_tool():
    inputs, outputs = loadData('2015.csv', 'Economy (GDP per Capita)', 'Freedom', 'Happiness Score')

    feature1 = [ex[0] for ex in inputs]
    feature2 = [ex[1] for ex in inputs]

    # plot the data histograms
    plotDataHistogram(feature1, 'capita GDP')
    plotDataHistogram(feature2, 'freedom')
    plotDataHistogram(outputs, 'Happiness score')

    # check the liniarity (to check that a linear relationship exists between the dependent variable (y = happiness) and the independent variables (x1 = capita, x2 = freedom).)
    plot3Ddata(feature1, feature2, outputs, [], [], [], [], [], [], 'capita vs freedom vs happiness')

    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    trainInputs, testInputs = normalisation(trainInputs, testInputs)
    trainOutputs, testOutputs = normalisation(trainOutputs, testOutputs)

    feature1train = [ex[0] for ex in trainInputs]
    feature2train = [ex[1] for ex in trainInputs]

    feature1test = [ex[0] for ex in testInputs]
    feature2test = [ex[1] for ex in testInputs]

    # plot3Ddata(feature1train, feature2train, trainOutputs, [], [], [], feature1test, feature2test, testOutputs,
    #           "train and test data (after normalisation)")

    # identify (by training) the regressor

    # use sklearn regressor
    from sklearn import linear_model
    regressor = linear_model.SGDRegressor()

    regressor.fit(trainInputs, trainOutputs)
    # print(regressor.coef_)
    # print(regressor.intercept_)

    # parameters of the liniar regressor
    w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
    print('w0: ', w0[0], 'w1: ', w1, 'w2: ', w2)
    # numerical representation of the regressor model
    noOfPoints = 50
    xref1 = []
    val = min(feature1)
    step1 = (max(feature1) - min(feature1)) / noOfPoints
    for _ in range(1, noOfPoints):
        for _ in range(1, noOfPoints):
            xref1.append(val)
        val += step1

    xref2 = []
    val = min(feature2)
    step2 = (max(feature2) - min(feature2)) / noOfPoints
    for _ in range(1, noOfPoints):
        aux = val
        for _ in range(1, noOfPoints):
            xref2.append(aux)
            aux += step2
    yref = [w0 + w1 * el1 + w2 * el2 for el1, el2 in zip(xref1, xref2)]
    # plot3Ddata(feature1train, feature2train, trainOutputs, xref1, xref2, yref, [], [], [],
    #           'train data and the learnt model')

    # makes predictions for test data
    # computedTestOutputs = [w0 + w1 * el[0] + w2 * el[1] for el in testInputs]
    # makes predictions for test data (by tool)
    computedTestOutputs = regressor.predict(testInputs)

    plot3Ddata([], [], [], feature1test, feature2test, computedTestOutputs, feature1test, feature2test, testOutputs,
               'predictions vs real test data')

    # compute the differences between the predictions and real outputs
    error = 0.0
    for t1, t2 in zip(computedTestOutputs, testOutputs):
        error += (t1 - t2) ** 2
    error = error / len(testOutputs)
    print('prediction error (manual): ', error)

    from sklearn.metrics import mean_squared_error

    error = mean_squared_error(testOutputs, computedTestOutputs)
    print('prediction error (tool):   ', error)


def rezolvare_cu_tool_uni():
    inputs, outputs = loadData('2015.csv', 'Economy (GDP per Capita)', 'Freedom', 'Happiness Score')

    feature1 = [ex[0] for ex in inputs]
    # feature2 = [ex[1] for ex in inputs]

    # plot the data histograms
    plotDataHistogram(feature1, 'capita GDP')
    # plotDataHistogram(feature2, 'freedom')
    plotDataHistogram(outputs, 'Happiness score')

    # check the liniarity (to check that a linear relationship exists between the dependent variable (y = happiness) and the independent variables (x1 = capita, x2 = freedom).)
    # plot3Ddata(feature1, outputs, [], [], [], [], [], 'capita vs freedom vs happiness')

    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i][0] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i][0] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    trainInputs, testInputs = normalisation(trainInputs, testInputs)
    trainOutputs, testOutputs = normalisation(trainOutputs, testOutputs)

    feature1train = [ex for ex in trainInputs]
    # feature2train = [ex[1] for ex in trainInputs]

    feature1test = [ex for ex in testInputs]
    # feature2test = [ex[1] for ex in testInputs]

    # plot3Ddata(feature1train, feature2train, trainOutputs, [], [], [], feature1test, feature2test, testOutputs,
    #           "train and test data (after normalisation)")

    # identify (by training) the regressor

    # use sklearn regressor
    from sklearn import linear_model
    regressor = linear_model.SGDRegressor()

    xx = [[el] for el in feature1train]

    regressor.fit(xx, trainOutputs)
    # print(regressor.coef_)
    # print(regressor.intercept_)

    # parameters of the liniar regressor
    w0, w1 = regressor.intercept_, regressor.coef_
    print('w0: ', w0, 'w1: ', w1)
    # numerical representation of the regressor model
    noOfPoints = 50
    xref1 = []
    val = min(feature1)
    step1 = (max(feature1) - min(feature1)) / noOfPoints
    for _ in range(1, noOfPoints):
        for _ in range(1, noOfPoints):
            xref1.append(val)
        val += step1

    # yref = [w0 + w1 * el1 for el1, el2 in zip(xref1)]
    # plot3Ddata(feature1train, feature2train, trainOutputs, xref1, xref2, yref, [], [], [],
    #           'train data and the learnt model')

    # makes predictions for test data
    # computedTestOutputs = [w0 + w1 * el[0] + w2 * el[1] for el in testInputs]
    # makes predictions for test data (by tool)


    computedTestOutputs = regressor.predict(xx)

    # plot3Ddata([], [], [], feature1test, computedTestOutputs, feature1test, testOutputs,
    #          'predictions vs real test data')

    # compute the differences between the predictions and real outputs
    error = 0.0
    for t1, t2 in zip(computedTestOutputs, testOutputs):
        error += (t1 - t2) ** 2
    error = error / len(testOutputs)
    print('prediction error (manual): ', error)


def rezolvare_univariata_fara_tool():
    inputs, outputs = loadData('2015.csv', 'Economy (GDP per Capita)', 'Freedom', 'Happiness Score')

    feature1 = [ex[0] for ex in inputs]
    # feature2 = [ex[1] for ex in inputs]

    # plot the data histograms
    plotDataHistogram(feature1, 'capita GDP')
    # plotDataHistogram(feature2, 'freedom')
    plotDataHistogram(outputs, 'Happiness score')

    # check the liniarity (to check that a linear relationship exists between the dependent variable (y = happiness) and the independent variables (x1 = capita, x2 = freedom).)
    # plot3Ddata(feature1, outputs, [], [], [], [], [], 'capita vs freedom vs happiness')

    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [[inputs[i][0]] for i in trainSample]
    trainOutputs = [[outputs[i]] for i in trainSample]
    testInputs = [[inputs[i][0]] for i in testSample]
    testOutputs = [[outputs[i]] for i in testSample]

    #trainInputs, testInputs = normalisation(trainInputs, testInputs)
    trainInputs = normalisation_manual(trainInputs)
    testInputs = normalisation_manual(testInputs)
    #trainOutputs, testOutputs = normalisation(trainOutputs, testOutputs)
    trainOutputs = normalisation_manual(trainOutputs)
    testOutputs = normalisation_manual(testOutputs)

    feature1train = [ex for ex in trainInputs]
    # feature2train = [ex[1] for ex in trainInputs]

    feature1test = [ex for ex in testInputs]
    # feature2test = [ex[1] for ex in testInputs]

    # plot3Ddata(feature1train, feature2train, trainOutputs, [], [], [], feature1test, feature2test, testOutputs,
    #           "train and test data (after normalisation)")

    # identify (by training) the regressor

    # use sklearn regressor
    from sklearn import linear_model
    regressor = MySGDRegression()

    regressor.fit_uni(feature1train, trainOutputs)
    # print(regressor.coef_)
    # print(regressor.intercept_)

    # parameters of the liniar regressor
    w0, w1 = regressor.intercept_, regressor.coef_
    print('w0: ', w0, 'w1: ', w1)
    # numerical representation of the regressor model
    noOfPoints = 50
    xref1 = []
    val = min(feature1)
    step1 = (max(feature1) - min(feature1)) / noOfPoints
    for _ in range(1, noOfPoints):
        for _ in range(1, noOfPoints):
            xref1.append(val)
        val += step1

    # yref = [w0 + w1 * el1 for el1, el2 in zip(xref1)]
    # plot3Ddata(feature1train, feature2train, trainOutputs, xref1, xref2, yref, [], [], [],
    #           'train data and the learnt model')

    # makes predictions for test data
    # computedTestOutputs = [w0 + w1 * el[0] + w2 * el[1] for el in testInputs]
    # makes predictions for test data (by tool)
    computedTestOutputs = regressor.predict_uni(testInputs)

    # plot3Ddata([], [], [], feature1test, computedTestOutputs, feature1test, testOutputs,
    #          'predictions vs real test data')

    # compute the differences between the predictions and real outputs
    error = 0.0
    for t1, t2 in zip(computedTestOutputs, testOutputs):
        error += (t1 - t2) ** 2
    error = error / len(testOutputs)
    print('prediction error (manual): ', error)


def rezolvare_bivariata_fara_tool():
    inputs, outputs = loadData('2015.csv', 'Economy (GDP per Capita)', 'Freedom', 'Happiness Score')

    feature1 = [ex[0] for ex in inputs]
    feature2 = [ex[1] for ex in inputs]

    # plot the data histograms
    plotDataHistogram(feature1, 'capita GDP')
    plotDataHistogram(feature2, 'freedom')
    plotDataHistogram(outputs, 'Happiness score')

    # check the liniarity (to check that a linear relationship exists between the dependent variable (y = happiness) and the independent variables (x1 = capita, x2 = freedom).)
    plot3Ddata(feature1, feature2, outputs, [], [], [], [], [], [], 'capita vs freedom vs happiness')

    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [[outputs[i]] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [[outputs[i]] for i in testSample]

    #trainInputs, testInputs = normalisation(trainInputs, testInputs)
    trainInputs = normalisation_manual(trainInputs)
    testInputs = normalisation_manual(testInputs)
    #trainOutputs, testOutputs = normalisation(trainOutputs, testOutputs)
    trainOutputs = normalisation_manual(trainOutputs)
    testOutputs = normalisation_manual(testOutputs)

    feature1train = [ex[0] for ex in trainInputs]
    feature2train = [ex[1] for ex in trainInputs]

    feature1test = [ex[0] for ex in testInputs]
    feature2test = [ex[1] for ex in testInputs]

    # plot3Ddata(feature1train, feature2train, trainOutputs, [], [], [], feature1test, feature2test, testOutputs,
    #           "train and test data (after normalisation)")

    # identify (by training) the regressor

    # use sklearn regressor
    from sklearn import linear_model
    regressor = MySGDRegression()

    regressor.fit(trainInputs, trainOutputs)
    # print(regressor.coef_)
    # print(regressor.intercept_)

    # parameters of the liniar regressor
    w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
    print('w0: ', w0, 'w1: ', w1, 'w2: ', w2)
    # numerical representation of the regressor model
    noOfPoints = 50
    xref1 = []
    val = min(feature1)
    step1 = (max(feature1) - min(feature1)) / noOfPoints
    for _ in range(1, noOfPoints):
        for _ in range(1, noOfPoints):
            xref1.append(val)
        val += step1

    xref2 = []
    val = min(feature2)
    step2 = (max(feature2) - min(feature2)) / noOfPoints
    for _ in range(1, noOfPoints):
        aux = val
        for _ in range(1, noOfPoints):
            xref2.append(aux)
            aux += step2
    yref = [w0 + w1 * el1 + w2 * el2 for el1, el2 in zip(xref1, xref2)]
    # plot3Ddata(feature1train, feature2train, trainOutputs, xref1, xref2, yref, [], [], [],
    #           'train data and the learnt model')

    # makes predictions for test data
    # computedTestOutputs = [w0 + w1 * el[0] + w2 * el[1] for el in testInputs]
    # makes predictions for test data (by tool)
    computedTestOutputs = regressor.predict(testInputs)

    plot3Ddata([], [], [], feature1test, feature2test, computedTestOutputs, feature1test, feature2test, testOutputs,
               'predictions vs real test data')

    # compute the differences between the predictions and real outputs
    error = 0.0
    for t1, t2 in zip(computedTestOutputs, testOutputs):
        error += (t1 - t2) ** 2
    error = error / len(testOutputs)
    print('prediction error (manual): ', error)


#rezolvare_cu_tool()
#rezolvare_cu_tool_uni()
rezolvare_univariata_fara_tool()
#rezolvare_bivariata_fara_tool()
