import numpy as np
import cv2 as cv
import math


def apply_mask(array, mask, weight=1.0):
    sum = 0
    for i in range(3):
        for j in range(3):
            sum += array[i][j] * mask[2 - i][2 - j]
    return (weight * sum) / 255


def get_border(array, pozx, pozy, pixel, dimesion=1):
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
            border_array[i + dimesion][j + dimesion] = array[x][y][pixel]
    return border_array


laplacian_mask = np.array([[0, -1, 0], [-1, 4, -1], [0, -1, 0]])
derivative_x_mask = np.array([[-1, 0, 1], [-1, 0, 1], [-1, 0, 1]])
derivative_y_mask = np.array([[1, 1, 1], [0, 0, 0], [-1, -1, -1]])

img = cv.imread('messi_2.jpg')
mask = cv.imread('mask2.png', 0)

# img = cv.resize(img,(1980,1080))

lines = img.shape[0]
cols = img.shape[1]
mask = cv.resize(mask, (cols, lines))


# img2 = np.zeros([lines,cols,3],dtype=np.uint8)
# img2.fill(0) # or img[:] = 255
# cv.imwrite("mask_car.jpg",img2)

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


def inpaint(old_array, mask):
    new_array = np.copy(old_array)
    for i in range(len(mask[0])):
        for pixel in range(3):
            x = mask[0][i]
            y = mask[1][i]

            arr = get_border(old_array, x, y, pixel)

            L = apply_mask(arr, laplacian_mask)

            Ix = apply_mask(arr, derivative_x_mask, 1 / 3)
            Iy = apply_mask(arr, derivative_y_mask, 1 / 3)

            N = [-Iy / math.sqrt(Iy ** 2 + Ix ** 2 + 0.0001), Ix / math.sqrt(Iy ** 2 + Ix ** 2 + 0.0001)]
            # if math.isnan(N[0]):
            #     if Iy < 0:
            #         N[0] = 1
            #     else:
            #         N[0] = -1
            # if math.isnan(N[1]):
            #     if Ix < 0:
            #         N[1] = -1
            #     else:
            #         N[1] = 1
            # N = [-Iy , Ix ]

            vector_L = [
                apply_mask(get_border(old_array, x + 1, y, pixel), laplacian_mask) - apply_mask(
                    get_border(old_array, x - 1, y, pixel),
                    laplacian_mask),
                apply_mask(get_border(old_array, x, y + 1, pixel), laplacian_mask) - apply_mask(
                    get_border(old_array, x, y - 1, pixel),
                    laplacian_mask)]

            fi = (N[0] * vector_L[0] + N[1] * vector_L[1]) / (
                    math.sqrt(N[0] ** 2 + N[1] ** 2 + 0.0001) * math.sqrt(vector_L[0] ** 2 + vector_L[1] ** 2 + 0.0001))

            # if math.isnan(fi):
            #     fi = 1

            new_array[x][y][pixel] += 0.1 * fi * 255
    return new_array


A = 15
# b = np.where(mask != 0)
# for i in range(len(b[0])):
#     for pixel in range(3):
#         x = b[0][i]
#         y = b[1][i]
#         img[x][y][pixel] = 255
cv.imshow('dst', img)
cv.waitKey(0)
cv.destroyAllWindows()

mask1 = np.copy(mask)

for x in range(15):
    mask1 = np.copy(mask)
    #boundary, mask1 = get_boundary(mask1)
    print("DADADA")
    for i in range(A):
        boundary, mask1 = get_boundary(mask1)
        print(len(boundary[0]), len(boundary[1]))
        img = inpaint(img, boundary)
        # cv.imshow('dst', img)
        # cv.waitKey(0)
        # cv.destroyAllWindows()
    cv.imshow('dst', img)
    cv.waitKey(0)
    cv.destroyAllWindows()

cv.imshow('dst', img)
cv.waitKey(0)
cv.destroyAllWindows()

cv.imshow('dst', img)
cv.waitKey(0)
cv.destroyAllWindows()
