import numpy as np
import cv2 as cv
import math


def get_border(array, mask, pozx, pozy, pixel, dimesion=1):
    border_array = np.empty((dimesion * 2 + 1, dimesion * 2 + 1), int)

    for i in range(-dimesion, dimesion + 1):
        for j in range(-dimesion, dimesion + 1):
            x = pozx + i
            y = pozy + j
            while x < 0:
                x += 1
            while y < 0:
                y += 1
            while x > len(array) - 1:
                x -= 1
            while y > len(array[0]) - 1:
                y -= 1
            if mask[x][y] == 0:
                border_array[i + dimesion][j + dimesion] = array[x][y][pixel]
            else:
                border_array[i + dimesion][j + dimesion] = 0
    return border_array


def get_boundary(mask):
    b = np.where(mask != 0)
    boundary = [[], []]

    for i in range(len(b[0])):
        x = b[0][i]
        y = b[1][i]

        if mask[x][y - 1] == 0 or mask[x + 1][y] == 0 or mask[x - 1][y] == 0 or mask[x][y + 1] == 0:
            boundary[0].append(x)
            boundary[1].append(y)

    for i in range(len(boundary[0])):
        mask[boundary[0][i]][boundary[1][i]] = 0
    return boundary, mask


def get_average_color(array):
    sum = 0
    nr = 0
    for line in array:
        for pixel in line:
            if pixel != 0:
                nr += 1
                sum += pixel
    if nr == 0:
        return 0
    return sum / nr


def inpaint(old_array, mask, true_mask):
    new_array = np.copy(old_array)
    for i in range(len(mask[0])):
        for pixel in range(3):
            x = mask[0][i]
            y = mask[1][i]

            arr = get_border(old_array, true_mask, x, y, pixel)

            new_array[x][y][pixel] += get_average_color(arr)
    return new_array


def algoritm_mean_values(img, mask):
    # img = cv.resize(img,(1980,1080))

    lines = img.shape[0]
    cols = img.shape[1]
    mask = cv.resize(mask, (cols, lines))

    b = np.where(mask != 0)
    for i in range(len(b[0])):
        for pixel in range(3):
            x = b[0][i]
            y = b[1][i]
            img[x][y][pixel] = 0

    A = 15

    mask1 = np.copy(mask)
    # boundary, mask1 = get_boundary(mask1)
    boundary = [[1]]
    while len(boundary[0]) > 0:
        boundary, mask1 = get_boundary(mask1)
        print(len(boundary[0]), len(boundary[1]))
        img = inpaint(img, boundary, mask1)
        # cv.imshow('dst', img)
        # cv.waitKey(0)
        # cv.destroyAllWindows()
    cv.imshow('dst', img)
    cv.waitKey(0)
    cv.destroyAllWindows()


img = cv.imread('messi_2.jpg')
mask = cv.imread('mask2.png', 0)
algoritm_mean_values(img, mask)
