import math
import random
import timeit

from cffi.backend_ctypes import xrange


def if_prime(n):
    for i in range(2, int(math.sqrt(n))):
        if n % i == 0:
            return False
    return True


def gcd_euclid(x, y):
    while x != y:
        if x > y:
            x = x - y
        else:
            y = y - x
    return x


def miller_rabin(n, k=100):
    if n == 2:
        return True

    if n % 2 == 0:
        return False

    r, s = 0, n - 1
    while s % 2 == 0:
        r += 1
        s //= 2
    for _ in xrange(k):
        a = random.randrange(2, n - 1)
        x = pow(a, s, n)
        if x == 1 or x == n - 1:
            continue
        for _ in xrange(r - 1):
            x = pow(x, 2, n)
            if x == n - 1:
                break
        else:
            return False
    return True


def is_pseudoprime(n, b):
    value_of_powers = []
    a = 1
    # n = n - 1
    x = math.log2(n - 1)
    while a < x + 1:
        value_of_powers.append(b % n)
        b = (b % n) ** 2
        a += 1
        # if (a >= b): break
    powers_used = []
    a = int(math.log2(n - 1))
    y = n - 1
    while y != 0:
        while (2 ** a) <= y:
            powers_used.append(a)
            y = y - (2 ** a)
        a -= 1
    produs = 1
    for i in powers_used:
        produs = (produs * value_of_powers[i])
        produs = produs % n
    if produs == 1:
        return True
    return False


# is_pseudoprime(91, 3)

if __name__ == '__main__':
    n = 561
    count = 0
    max_base = n - 1
    if ((n) == True):
        print("Number is prime")
    elif (n % 2 == 0):
        print("Number is even")
    else:
        f = open("output.txt", "w")
        print("Bases for which " + str(n) + " is pseudoprim:")
        f.write("Bases for which " + str(n) + " is pseudoprim:\n")
        for i in range(2, max_base + 1):
            if gcd_euclid(i, n) == 1:
                if (is_pseudoprime(n, i)):
                    count += 1
                    print("Base " + str(i))
                    f.write("Base " + str(i) + "\n")
                # else:
                #     print("Baza " + str(i) + " nu este pseudoprim pentru " + str(n))

        f.close()
        print(count)

# start = timeit.default_timer()
#
# print(miller_rabin(19111111311777713))
#
# end = timeit.default_timer()
#
# print(f"Time taken is {end - start}s")
# start = timeit.default_timer()
# print(if_prime(19111111311777713))
# end = timeit.default_timer()
#
# print(f"Time taken is {end - start}s")
