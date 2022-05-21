import matplotlib.pyplot as plt
import skimage

from skimage import data, filters
import numpy as np

import os

from skimage.exposure import exposure

from skimage import io
import cv2 as cv



#image = io.imread(filename)
#image_with_edges = cv.Canny(image , 100, 200)
#
#images = [image, image_with_edges]
#
#location = [121, 122]
#
#for loc, img in zip(location, images):
#    plt.subplot(loc)
#    plt.imshow(img, cmap='gray')

#plt.savefig('vis0-rs19.jpg')
plt.show()

img = cv.imread('five.jpg',0)
edges = cv.Canny(img,100,200)

plt.subplot(121),plt.imshow(img,cmap = 'gray')
plt.title('Original Image'), plt.xticks([]), plt.yticks([])
plt.subplot(122),plt.imshow(edges,cmap = 'gray')
plt.title('Edge Image'), plt.xticks([]), plt.yticks([])
plt.show()

#image = cv2.imread('road.jpg', 0)
#detect_edge(image)
#edges = filters.sobel(poza)
#camera = data.camera()
#camera[:10] = 0
#mask = camera < 140
#camera[mask] = 200
#inds_x = np.arange(len(camera))
#inds_y = (4 * inds_x) % len(camera)
#camera[inds_x, inds_y] = 0
#
#l_x, l_y = camera.shape[0], camera.shape[1]
#X, Y = np.ogrid[:l_x, :l_y]
##outer_disk_mask = (X - l_x / 2)**2 + (Y - l_y / 2)**2 > (l_x / 2)**2
##camera[outer_disk_mask] = 0
#
##plt.figure(figsize=(4, 4))
#plt.imshow(camera, cmap='gray')
#plt.axis('off')
#plt.show()


