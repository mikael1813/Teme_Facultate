import os
import random

import numpy as np
from sklearn import linear_model


def transposeMatrix(m):
    mapi = map(list,m)
    return mapi

def getMatrixMinor(m,i,j):
    return [row[:j] + row[j+1:] for row in (m[:i]+m[i+1:])]

def getMatrixDeternminant(m):
    #base case for 2x2 matrix
    if len(m) == 2:
        return m[0][0]*m[1][1]-m[0][1]*m[1][0]

    determinant = 0
    for c in range(len(m)):
        determinant += ((-1)**c)*m[0][c]*getMatrixDeternminant(getMatrixMinor(m,0,c))
    return determinant

def getMatrixInverse(m):
    determinant = getMatrixDeternminant(m)
    #special case for 2x2 matrix:
    if len(m) == 2:
        return [[m[1][1]/determinant, -1*m[0][1]/determinant],
                [-1*m[1][0]/determinant, m[0][0]/determinant]]

    #find matrix of cofactors
    cofactors = []
    for r in range(len(m)):
        cofactorRow = []
        for c in range(len(m)):
            minor = getMatrixMinor(m,r,c)
            cofactorRow.append(((-1)**(r+c)) * getMatrixDeternminant(minor))
        cofactors.append(cofactorRow)
    #cofactors = transposeMatrix(cofactors)
    #print(list(cofactors))
    #a = len(list(cofactors))
    for r in range(len(list(cofactors))):
        for c in range(len(list(cofactors))):
            cofactors[r][c] = cofactors[r][c]/determinant
    return cofactors


class MyLinearUnivariateRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = 0.0

    # learn a linear univariate regression model by using training inputs (x) and outputs (y)
    def fit(self, x, y):
        #matrix1 = np.array([[len(x[0]), sum(x[0]), sum(x[1])],
        #           [sum(x[0]), sum(i * i for i in x[0]), sum(x[0][i] * x[1][i] for i in range(len(x[0])))],
        #           [sum(x[1]), sum(x[0][i] * x[1][i] for i in range(len(x[0]))), sum(i * i for i in x[1])]])

        matrix11 = [[len(x[0]), sum(x[0]), sum(x[1])],
                   [sum(x[0]), sum(i * i for i in x[0]), sum(x[0][i] * x[1][i] for i in range(len(x[0])))],
                   [sum(x[1]), sum(x[0][i] * x[1][i] for i in range(len(x[0]))), sum(i * i for i in x[1])]]

        matrix2 = [[sum(y)],
                   [sum(x[0][i] * y[i] for i in range(len(x[0])))],
                   [sum(x[1][i] * y[i] for i in range(len(x[0])))]]


        matrix2inversa = list(getMatrixInverse(matrix11))
        print('matrix2: ',matrix2inversa)

        result=[[0],
                [0],
                [0]]

        # iterate through rows of X
        for i in range(len(matrix2inversa)):
            # iterate through columns of Y
            for j in range(len(matrix2[0])):
                # iterate through rows of Y
                for k in range(len(matrix2)):
                    result[i][j] += matrix2inversa[i][k] * matrix2[k][j]

        self.intercept_ =  result[0][0]
        self.coef_ = [result[1][0],result[2][0]]


    # predict the outputs for some new inputs (by using the learnt model)
    def predict(self, x):


        return [self.intercept_ + self.coef_[0] * x[0][val] + self.coef_[1] * x[1][val] for val in range(len(x[0]))]


def computed():
    import csv

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
        inputs = [inputs1, inputs2]
        selectedOutput = dataNames.index(outputVariabName)
        outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

        return inputs, outputs

    import os

    inputs, outputs = loadData('2015.csv', 'Economy (GDP per Capita)', 'Health (Life Expectancy)', 'Happiness Score')
    print('in:  ', inputs)
    print('out: ', outputs)

    import matplotlib.pyplot as plt

    def plotDataHistogram(x, variableName):
        n, bins, patches = plt.hist(x, 10)
        plt.title('Histogram of ' + variableName)
        plt.show()

    plotDataHistogram(inputs[0], 'capita GDP')
    plotDataHistogram(inputs[1], 'Health')
    plotDataHistogram(outputs, 'Happiness score')

    # check the liniarity (to check that a linear relationship exists between the dependent variable (y = happiness) and the independent variable (x = capita).)

    plt.plot(inputs[0], outputs, 'ro')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.title('GDP capita vs. happiness')
    plt.show()

    plt.plot(inputs[1], outputs, 'ro')
    plt.xlabel('Health')
    plt.ylabel('happiness')
    plt.title('Health vs. happiness')
    plt.show()

    import numpy as np

    # Split the Data Into Training and Test Subsets
    # In this step we will split our dataset into training and testing subsets (in proportion 80/20%).

    # Training data set is used for learning the linear model. Testing dataset is used for validating of the model. All data from testing dataset will be new to model and we may check how accurate are model predictions.

    np.random.seed(5)
    indexes = [i for i in range(len(inputs[0]))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs[0])), replace=False)
    validationSample = [i for i in indexes if not i in trainSample]

    trainInputs = [[inputs[0][i] for i in trainSample], [inputs[1][i] for i in trainSample]]
    trainOutputs = [outputs[i] for i in trainSample]

    validationInputs = [[inputs[0][i] for i in validationSample], [inputs[1][i] for i in validationSample]]
    validationOutputs = [outputs[i] for i in validationSample]

    plt.plot(trainInputs[0], trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(validationInputs[0], validationOutputs, 'g^',
             label='validation data')  # test data are plotted by green and a triangle sign
    plt.title('train and validation data')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    plt.plot(trainInputs[1], trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(validationInputs[1], validationOutputs, 'g^',
             label='validation data')  # test data are plotted by green and a triangle sign
    plt.title('train and validation data')
    plt.xlabel('Health')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    # learning step: init and train a linear regression model y = f(x) = w0 + w1 * x
    # Prediction step: used the trained model to estimate the output for a new input

    # using sklearn
    from sklearn import linear_model

    # training data preparation (the sklearn linear model requires as input training data as noSamples x noFeatures array; in the current case, the input must be a matrix of len(trainInputs) lineas and one columns (a single feature is used in this problem))
    xx = [[trainInputs[0][i], trainInputs[1][i]] for i in range(len(trainInputs[0]))]
    print(xx)

    # model initialisation
    regressor = linear_model.LinearRegression()
    # training the model by using the training inputs and known training outputs
    regressor.fit(xx, trainOutputs)
    # save the model parameters
    w0, w1 = regressor.intercept_, regressor.coef_
    print('the learnt model: f(x) = ', w0, ' + ', w1[0], ' * x1', ' + ', w1[1], ' * x2')

    # # using developed code
    # from lab07_linRegression.myRegression import MyLinearUnivariateRegression

    # # model initialisation
    # regressor = MyLinearUnivariateRegression()
    # # training the model by using the training inputs and known training outputs
    # regressor.fit(trainInputs, trainOutputs)
    # # save the model parameters
    # w0, w1 = regressor.intercept_, regressor.coef_
    # print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')

    # plot the learnt model
    # prepare some synthetic data (inputs are random, while the outputs are computed by the learnt model)
    noOfPoints = 1000
    xref1 = []
    val = min(trainInputs[0])
    step = (max(trainInputs[0]) - min(trainInputs[0])) / noOfPoints
    for i in range(1, noOfPoints):
        xref1.append(val)
        val += step
    xref2 = []
    val = min(trainInputs[1])
    step = (max(trainInputs[1]) - min(trainInputs[1])) / noOfPoints
    for i in range(1, noOfPoints):
        xref2.append(val)
        val += step
    xref = [xref1, xref2]
    yref = [w0 + w1[0] * xref[0][el] + w1[1] * xref[1][el] for el in range(len(xref[0]))]

    plt.plot(trainInputs[0], trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(xref[0], yref, 'b-', label='learnt model')  # model is plotted by a blue line
    plt.title('train data and the learnt model')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    plt.plot(trainInputs[1], trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(xref[1], yref, 'b-', label='learnt model')  # model is plotted by a blue line
    plt.title('train data and the learnt model')
    plt.xlabel('Health')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    # use the trained model to predict new inputs

    # makes predictions for test data (manual)
    # computedTestOutputs = [w0 + w1 * el for el in testInputs]
    # makes predictions for test data (by tool)
    computedValidationOutputs = regressor.predict(
        [[validationInputs[0][x], validationInputs[1][x]] for x in range(len(validationInputs[0]))])
    # plot the computed outputs (see how far they are from the real outputs)
    plt.plot(validationInputs[0], computedValidationOutputs, 'yo',
             label='computed test data')  # computed test data are plotted yellow red and circle sign
    plt.plot(validationInputs[0], validationOutputs, 'g^',
             label='real test data')  # real test data are plotted by green triangles
    plt.title('computed validation and real validation data')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    plt.plot(validationInputs[1], computedValidationOutputs, 'yo',
             label='computed test data')  # computed test data are plotted yellow red and circle sign
    plt.plot(validationInputs[1], validationOutputs, 'g^',
             label='real test data')  # real test data are plotted by green triangles
    plt.title('computed validation and real validation data')
    plt.xlabel('Health')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    # compute the differences between the predictions and real outputs
    # "manual" computation
    error = 0.0
    for t1, t2 in zip(computedValidationOutputs, validationOutputs):
        error += (t1 - t2) ** 2
    error = error / len(validationOutputs)
    print('prediction error (manual): ', error)

    # by using sklearn
    from sklearn.metrics import mean_squared_error

    error = mean_squared_error(validationOutputs, computedValidationOutputs)
    print('prediction error (tool):  ', error)


def manual():
    import csv

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
        inputs = [inputs1, inputs2]
        selectedOutput = dataNames.index(outputVariabName)
        outputs = [float(data[i][selectedOutput]) for i in range(len(data))]

        return inputs, outputs

    import os

    inputs, outputs = loadData('2015.csv', 'Economy (GDP per Capita)', 'Health (Life Expectancy)', 'Happiness Score')
    print('in:  ', inputs)
    print('out: ', outputs)

    import matplotlib.pyplot as plt

    def plotDataHistogram(x, variableName):
        n, bins, patches = plt.hist(x, 10)
        plt.title('Histogram of ' + variableName)
        plt.show()

    plotDataHistogram(inputs[0], 'capita GDP')
    plotDataHistogram(inputs[1], 'Health')
    plotDataHistogram(outputs, 'Happiness score')

    # check the liniarity (to check that a linear relationship exists between the dependent variable (y = happiness) and the independent variable (x = capita).)

    plt.plot(inputs[0], outputs, 'ro')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.title('GDP capita vs. happiness')
    plt.show()

    plt.plot(inputs[1], outputs, 'ro')
    plt.xlabel('Health')
    plt.ylabel('happiness')
    plt.title('Health vs. happiness')
    plt.show()

    import numpy as np

    # Split the Data Into Training and Test Subsets
    # In this step we will split our dataset into training and testing subsets (in proportion 80/20%).

    # Training data set is used for learning the linear model. Testing dataset is used for validating of the model. All data from testing dataset will be new to model and we may check how accurate are model predictions.

    np.random.seed(5)
    indexes = [i for i in range(len(inputs[0]))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs[0])), replace=False)
    validationSample = [i for i in indexes if not i in trainSample]

    trainInputs = [[inputs[0][i] for i in trainSample], [inputs[1][i] for i in trainSample]]
    trainOutputs = [outputs[i] for i in trainSample]

    validationInputs = [[inputs[0][i] for i in validationSample], [inputs[1][i] for i in validationSample]]
    validationOutputs = [outputs[i] for i in validationSample]

    plt.plot(trainInputs[0], trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(validationInputs[0], validationOutputs, 'g^',
             label='validation data')  # test data are plotted by green and a triangle sign
    plt.title('train and validation data')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    plt.plot(trainInputs[1], trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(validationInputs[1], validationOutputs, 'g^',
             label='validation data')  # test data are plotted by green and a triangle sign
    plt.title('train and validation data')
    plt.xlabel('Health')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    # learning step: init and train a linear regression model y = f(x) = w0 + w1 * x
    # Prediction step: used the trained model to estimate the output for a new input

    # using sklearn
    from sklearn import linear_model

    # training data preparation (the sklearn linear model requires as input training data as noSamples x noFeatures array; in the current case, the input must be a matrix of len(trainInputs) lineas and one columns (a single feature is used in this problem))
    xx = [[trainInputs[0][i], trainInputs[1][i]] for i in range(len(trainInputs[0]))]
    # print(xx)

    # model initialisation
    regressor = MyLinearUnivariateRegression()
    # training the model by using the training inputs and known training outputs
    regressor.fit(trainInputs, trainOutputs)
    # save the model parameters
    w0, w1 = regressor.intercept_, regressor.coef_
    print('the learnt model: f(x) = ', w0, ' + ', w1[0], ' * x1', ' + ', w1[1], ' * x2')

    # # using developed code
    # from lab07_linRegression.myRegression import MyLinearUnivariateRegression

    # # model initialisation
    # regressor = MyLinearUnivariateRegression()
    # # training the model by using the training inputs and known training outputs
    # regressor.fit(trainInputs, trainOutputs)
    # # save the model parameters
    # w0, w1 = regressor.intercept_, regressor.coef_
    # print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')

    # plot the learnt model
    # prepare some synthetic data (inputs are random, while the outputs are computed by the learnt model)
    noOfPoints = 1000
    xref1 = []
    val = min(trainInputs[0])
    step = (max(trainInputs[0]) - min(trainInputs[0])) / noOfPoints
    for i in range(1, noOfPoints):
        xref1.append(val)
        val += step
    xref2 = []
    val = min(trainInputs[1])
    step = (max(trainInputs[1]) - min(trainInputs[1])) / noOfPoints
    for i in range(1, noOfPoints):
        xref2.append(val)
        val += step
    xref = [xref1, xref2]
    yref = [w0 + w1[0] * xref[0][el] + w1[1] * xref[1][el] for el in range(len(xref[0]))]

    plt.plot(trainInputs[0], trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(xref[0], yref, 'b-', label='learnt model')  # model is plotted by a blue line
    plt.title('train data and the learnt model')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    plt.plot(trainInputs[1], trainOutputs, 'ro', label='training data')  # train data are plotted by red and circle sign
    plt.plot(xref[1], yref, 'b-', label='learnt model')  # model is plotted by a blue line
    plt.title('train data and the learnt model')
    plt.xlabel('Health')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    # use the trained model to predict new inputs

    # makes predictions for test data (manual)
    # computedTestOutputs = [w0 + w1 * el for el in testInputs]
    # makes predictions for test data (by tool)
    computedValidationOutputs = regressor.predict(
        validationInputs)
    print(computedValidationOutputs)
    # plot the computed outputs (see how far they are from the real outputs)
    plt.plot(validationInputs[0], computedValidationOutputs, 'yo',
             label='computed test data')  # computed test data are plotted yellow red and circle sign
    plt.plot(validationInputs[0], validationOutputs, 'g^',
             label='real test data')  # real test data are plotted by green triangles
    plt.title('computed validation and real validation data')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    plt.plot(validationInputs[1], computedValidationOutputs, 'yo',
             label='computed test data')  # computed test data are plotted yellow red and circle sign
    plt.plot(validationInputs[1], validationOutputs, 'g^',
             label='real test data')  # real test data are plotted by green triangles
    plt.title('computed validation and real validation data')
    plt.xlabel('Health')
    plt.ylabel('happiness')
    plt.legend()
    plt.show()

    # compute the differences between the predictions and real outputs
    # "manual" computation
    error = 0.0
    for t1, t2 in zip(computedValidationOutputs, validationOutputs):
        error += (t1 - t2) ** 2
    error = error / len(validationOutputs)
    print('prediction error (manual): ', error)

    # by using sklearn
    #from sklearn.metrics import mean_squared_error

    #error = mean_squared_error(validationOutputs, computedValidationOutputs)
    #print('prediction error (tool):  ', error)


#computed()
manual()
