import numpy as np
import cv2 as cv

img = cv.imread('messi_2.jpg')
mask = cv.imread('mask2.png', 0)

# img = cv.resize(img,(1980,1080))

lines = img.shape[0]
cols = img.shape[1]
mask = cv.resize(mask, (cols, lines))

# img2 = np.zeros([lines,cols,3],dtype=np.uint8)
# img2.fill(0) # or img[:] = 255
# cv.imwrite("mask_car.jpg",img2)

b = np.where(mask != 0)

print(len(b[1]))

dst = cv.inpaint(img, mask, 3, cv.INPAINT_TELEA)
cv.imshow('dst', dst)
cv.waitKey(0)
cv.destroyAllWindows()

dst = cv.inpaint(img, mask, 3, cv.INPAINT_NS)
cv.imshow('dst', dst)
cv.waitKey(0)
cv.destroyAllWindows()
