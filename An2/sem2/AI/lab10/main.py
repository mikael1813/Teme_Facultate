from random import random

from sklearn.preprocessing import StandardScaler

#from ANN import ANN


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


# step1: load the data

def loadIrisData():
    from sklearn.datasets import load_iris

    data = load_iris()
    inputs = data['data']
    outputs = data['target']
    outputNames = data['target_names']
    featureNames = list(data['feature_names'])
    feature1 = [feat[featureNames.index('sepal length (cm)')] for feat in inputs]
    feature2 = [feat[featureNames.index('petal length (cm)')] for feat in inputs]
    inputs = [[feat[featureNames.index('sepal length (cm)')], feat[featureNames.index('petal length (cm)')]] for feat in
              inputs]
    return inputs, outputs, outputNames


def loadDigitData():
    from sklearn.datasets import load_digits

    data = load_digits()
    inputs = data.images
    outputs = data['target']
    outputNames = data['target_names']

    # shuffle the original data
    noData = len(inputs)
    permutation = np.random.permutation(noData)
    inputs = inputs[permutation]
    outputs = outputs[permutation]

    return inputs, outputs, outputNames


# step2: split data into train and test
import numpy as np


def splitData(inputs, outputs):
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    return trainInputs, trainOutputs, testInputs, testOutputs


from sklearn import neural_network

classifier = neural_network.MLPClassifier()


def training(classifier, trainInputs, trainOutputs):
    # step4: training the classifier
    # identify (by training) the classification model
    classifier.fit(trainInputs, trainOutputs)


def classification(classifier, testInputs):
    # step5: testing (predict the labels for new inputs)
    # makes predictions for test data
    computedTestOutputs = classifier.predict(testInputs)

    return computedTestOutputs


import matplotlib.pyplot as plt


def plotConfusionMatrix(cm, classNames, title):
    from sklearn.metrics import confusion_matrix
    import itertools

    classes = classNames
    plt.figure()
    # plt.imshow(cm, interpolation = 'nearest', cmap = 'Blues')
    plt.title('Confusion Matrix ' + title)
    # plt.colorbar()
    tick_marks = np.arange(len(classNames))
    plt.xticks(tick_marks, classNames, rotation=45)
    plt.yticks(tick_marks, classNames)

    text_format = 'd'
    thresh = cm.max() / 2.
    for row, column in itertools.product(range(cm.shape[0]), range(cm.shape[1])):
        plt.text(column, row, format(cm[row, column], text_format),
                 horizontalalignment='center',
                 color='white' if cm[row, column] > thresh else 'black')

    plt.ylabel('True label')
    plt.xlabel('Predicted label')
    plt.tight_layout()

    plt.show()


def evalMultiClass(realLabels, computedLabels, labelNames):
    from sklearn.metrics import confusion_matrix

    confMatrix = confusion_matrix(realLabels, computedLabels)
    acc = sum([confMatrix[i][i] for i in range(len(labelNames))]) / len(realLabels)
    precision = {}
    recall = {}
    for i in range(len(labelNames)):
        precision[labelNames[i]] = confMatrix[i][i] / sum([confMatrix[j][i] for j in range(len(labelNames))])
        recall[labelNames[i]] = confMatrix[i][i] / sum([confMatrix[i][j] for j in range(len(labelNames))])
    return acc, precision, recall, confMatrix


def data2FeaturesMoreClasses(inputs, outputs):
    labels = set(outputs)
    noData = len(inputs)
    for crtLabel in labels:
        x = [inputs[i][0] for i in range(noData) if outputs[i] == crtLabel]
        y = [inputs[i][1] for i in range(noData) if outputs[i] == crtLabel]
        plt.scatter(x, y, label=outputNames[crtLabel])
    plt.xlabel('feat1')
    plt.ylabel('feat2')
    plt.legend()
    plt.show()


def iris():
    # main flow
    from sklearn import neural_network
    from sklearn import linear_model
    inputs, outputs, outputNames = loadIrisData()
    trainInputs, trainOutputs, testInputs, testOutputs = splitData(inputs, outputs)

    # plot the training data distribution on classes
    plt.hist(trainOutputs, 3, rwidth=0.8)
    plt.xticks(np.arange(len(outputNames)), outputNames)
    plt.show()

    # plot the data in order to observe the shape of the classifier required in this problem
    # data2FeaturesMoreClasses(trainInputs, trainOutputs)

    # normalise the data
    trainInputs, testInputs = normalisation(trainInputs, testInputs)

    # liniar classifier and one-vs-all approach for multi-class
    # classifier = linear_model.LogisticRegression()

    # non-liniar classifier and softmax approach for multi-class
    classifier = neural_network.MLPClassifier(hidden_layer_sizes=(5,), activation='relu', max_iter=100, solver='sgd',
                                              verbose=10, random_state=1, learning_rate_init=.1)

#    ann = ANN(0.1,100)
#    ann.train_network(trainInputs,trainOutputs)

    training(classifier, trainInputs, trainOutputs)
    predictedLabels = classification(classifier, testInputs)
    acc, prec, recall, cm = evalMultiClass(np.array(testOutputs), predictedLabels, outputNames)
    plotConfusionMatrix(cm, outputNames, "iris classification")

    print('acc: ', acc)
    print('precision: ', prec)
    print('recall: ', recall)


def cifre():
    # main flow
    inputs, outputs, outputNames = loadDigitData()
    trainInputs, trainOutputs, testInputs, testOutputs = splitData(inputs, outputs)
    # check if the data is uniform distributed over classes
    plt.hist(trainOutputs, rwidth=0.8)
    plt.xticks(np.arange(len(outputNames)), outputNames)
    plt.show()

    def flatten(mat):
        x = []
        for line in mat:
            for el in line:
                x.append(el)
        return x

    trainInputsFlatten = [flatten(el) for el in trainInputs]
    testInputsFlatten = [flatten(el) for el in testInputs]
    trainInputsNormalised, testInputsNormalised = normalisation(trainInputsFlatten, testInputsFlatten)

    # try to play by MLP parameters (e.g. change the HL size from 10 to 20 and see how this modification impacts the accuracy)
    classifier = neural_network.MLPClassifier(hidden_layer_sizes=(10,), activation='relu', max_iter=1000, solver='sgd',
                                              verbose=10, random_state=1, learning_rate_init=.1)

    training(classifier, trainInputsNormalised, trainOutputs)
    predictedLabels = classification(classifier, testInputsNormalised)
    acc, prec, recall, cm = evalMultiClass(np.array(testOutputs), predictedLabels, outputNames)

    plotConfusionMatrix(cm, outputNames, "digit classification")
    print('acc: ', acc)
    print('precision: ', prec)
    print('recall: ', recall)

    # plot first 50 test images and their real and computed labels
    n = 10
    m = 5
    fig, axes = plt.subplots(n, m, figsize=(7, 7))
    fig.tight_layout()
    for i in range(0, n):
        for j in range(0, m):
            axes[i][j].imshow(testInputs[m * i + j])
            if (testOutputs[m * i + j] == predictedLabels[m * i + j]):
                font = 'normal'
            else:
                font = 'bold'
            axes[i][j].set_title(
                'real ' + str(testOutputs[m * i + j]) + '\npredicted ' + str(predictedLabels[m * i + j]),
                fontweight=font)
            axes[i][j].set_axis_off()

    plt.show()


import glob
import cv2
import random


def load_data():
    images1 = [cv2.imread(file) for file in glob.glob("C:/Info An 2/sem 2/ai/lab10/data/*.jpg")]
    images2 = [cv2.imread(file) for file in glob.glob("C:/Info An 2/sem 2/ai/lab10/tricicleta/*.jpg")]
    input = images1 + images2
    output = []
    for i in images1:
        output.append(0)
    for i in images2:
        output.append(1)

    return input, output, ["Bicicleta", "Tricicleta"]


def transform_to_pixel(input):
    input_pixels = []
    for i in input:
        list = []
        rows, cols, z = i.shape
        for j in range(1024):
            x = random.randint(0, rows - 1)
            y = random.randint(0, cols - 1)
            b, g, r = i[x][y]
            list.append(b)
            list.append(g)
            list.append(r)
        input_pixels.append(list)
    return input_pixels


def custom():
    # main flow
    inputs, outputs, outputNames = load_data()
    trainInputs, trainOutputs, testInputs, testOutputs = splitData(inputs, outputs)
    # check if the data is uniform distributed over classes
    plt.hist(trainOutputs, rwidth=0.8)
    plt.xticks(np.arange(len(outputNames)), outputNames)
    plt.show()

    trainInputsPixels = transform_to_pixel(trainInputs)
    testInputsPixels = transform_to_pixel(testInputs)

    # trainInputsFlatten = [flatten(el) for el in trainInputs]
    # testInputsFlatten = [flatten(el) for el in testInputs]
    trainInputsNormalised, testInputsNormalised = normalisation(trainInputsPixels, testInputsPixels)

    # try to play by MLP parameters (e.g. change the HL size from 10 to 20 and see how this modification impacts the accuracy)
    classifier = neural_network.MLPClassifier(hidden_layer_sizes=(10,), activation='relu', max_iter=1000, solver='sgd',
                                              verbose=10, random_state=1, learning_rate_init=.1)

    training(classifier, trainInputsNormalised, trainOutputs)
    predictedLabels = classification(classifier, testInputsNormalised)
    print(predictedLabels)
    acc, prec, recall, cm = evalMultiClass(np.array(testOutputs), predictedLabels, outputNames)

    plotConfusionMatrix(cm, outputNames, "digit classification")
    print('acc: ', acc)
    print('precision: ', prec)
    print('recall: ', recall)

    # plot first 50 test images and their real and computed labels

    n = 10
    m = 5
    fig, axes = plt.subplots(n, m, figsize=(7, 7))
    fig.tight_layout()
    for i in range(0, len(testInputs)):
        axes[0][i].imshow(testInputs[i])
        if (testOutputs[i] == predictedLabels[i]):
            font = 'normal'
        else:
            font = 'bold'
        axes[0][i].set_title(
            'real ' + str(testOutputs[i]) + '\npredicted ' + str(predictedLabels[i]),
            fontweight=font)
        axes[0][i].set_axis_off()

    plt.show()


#iris()
cifre()
#custom()
