# -*- coding: utf-8 -*-
import struct

import matplotlib.pyplot as plt
import numpy as np


class MNIST_HELPER(object):
    def __init__(self, filename=None, outpath=None):
        self._filename = filename
        self._outpath = outpath
        self._tag = '>'
        self._twoBytes = 'II'
        self._fourBytes = 'IIII'  # ‘>IIII’指的是使用大端法读取4个unsinged int 32 bit integer
        self._pictureBytes = '784B'  # ‘>784B’指的是使用大端法读取784个unsigned byte (每张图28*28=784Byte)
        self._labelByte = '1B'
        self._twoBytes2 = self._tag + self._twoBytes
        self._fourBytes2 = self._tag + self._fourBytes
        self._pictureBytes2 = self._tag + self._pictureBytes
        self._labelByte2 = self._tag + self._labelByte

    def getImage(self):
        binfile = open(self._filename, 'rb')  # 以二进制方式打开文件
        buf = binfile.read()
        binfile.close()
        index = 0
        numMagic, numImgs, numRows, numCols = struct.unpack_from(self._fourBytes2, buf, index)
        index += struct.calcsize(self._fourBytes)  # 计算size作为索引，这里为16
        images = []
        for i in range(numImgs):
            imgVal = struct.unpack_from(self._pictureBytes2, buf, index)
            index += struct.calcsize(self._pictureBytes2)
            imgVal = list(imgVal)
            for j in range(len(imgVal)):
                if imgVal[j] > 1:
                    imgVal[j] = 1
            images.append(imgVal)
        return np.array(images)

    def getLabel(self):
        binFile = open(self._filename, 'rb')
        buf = binFile.read()
        binFile.close()
        index = 0
        magic, numItems = struct.unpack_from(self._twoBytes2, buf, index)
        index += struct.calcsize(self._twoBytes2)
        labels = [];
        for x in range(numItems):
            im = struct.unpack_from(self._labelByte2, buf, index)
            index += struct.calcsize(self._labelByte2)
            labels.append(im[0])
        return np.array(labels)

    def saveImage(self, fileName):
        binfile = open(self._filename, 'rb')  # 以二进制方式打开文件
        buf = binfile.read()
        index = 0
        numMagic, numImgs, numRows, numCols = struct.unpack_from(self._fourBytes2, buf, index)
        index += struct.calcsize(self._fourBytes)  # 计算size作为索引，这里为16
        for i in range(numImgs):  # 前10张
            imgVal = struct.unpack_from(self._pictureBytes2, buf, index)
            index += struct.calcsize(self._pictureBytes2)
            imgVal = np.array(imgVal)
            imgVal = imgVal.reshape(28, 28)
            fig = plt.figure()
            plotwindow = fig.add_subplot(111)
            plt.axis('off')
            plt.imshow(imgVal, cmap='gray')
            plt.savefig(fileName + str(i) + ".png")
            plt.close()
        binfile.close()

    # 根据生成的特征和数字标号，输出png的图像
    def outImg(self, arrX, arrY):
        m, n = np.shape(arrX)
        for i in range(1):
            img = np.array(arrX[i])
            img = img.reshape(28, 28)
            outfile = str(i) + "_" + str(arrY[i]) + ".png"
            plt.figure()
            plt.imshow(img, cmap='binary')  # 黑白显示图像
            plt.savefig(self._outpath + "/" + outfile)
