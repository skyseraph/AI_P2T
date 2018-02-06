# -*- coding: utf-8 -*-

from sklearn import neighbors
from demo.mnist.mnist_helper import MNIST_HELPER
import datetime


def main():
    trainfile_X = '../../dataset/MNIST/train-images.idx3-ubyte'
    trainfile_y = '../../dataset/MNIST/train-labels.idx1-ubyte'
    testfile_X = '../../dataset/MNIST/t10k-images.idx3-ubyte'
    testfile_y = '../../dataset/MNIST/t10k-labels.idx1-ubyte'
    train_X = MNIST_HELPER(filename=trainfile_X).getImage()
    train_y = MNIST_HELPER(filename=trainfile_y).getLabel()
    test_X = MNIST_HELPER(testfile_X).getImage()
    test_y = MNIST_HELPER(testfile_y).getLabel()

    print(train_X)
    print(train_y)
    print(test_X)
    print(test_y)
    return train_X, train_y, test_X, test_y


if __name__ == "__main__":
    main()
