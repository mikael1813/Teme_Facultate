import math
import random
import time


def modulo(x, d):
    c = x // d
    r = x - d * c
    if r > 0:
        return False
    return True


def get_prime_factors(x):
    factors = []
    n = x
    while modulo(x, 2):
        x = x // 2
        factors.append(2)
    for i in range(3, int(math.sqrt(n)), 2):
        while modulo(x, i):
            x = x // i
            factors.append(i)
    if x > 1:
        factors.append(x)
    return factors


def gcd1(x, y):
    gcd = 1
    if x > y:
        x, y = y, x
    if modulo(y, x):
        gcd = x
        return gcd
    d = int(x / 2)
    for i in range(d, 1, -1):
        if modulo(x, i) and modulo(y, i):
            gcd = i
            break
    return gcd


def gcd2(x, y):
    #Fie A,B numere naturale
    #Fie p,q a. i. A = p*gcd(A,B); B = q*gcd(A,B)
    # A-B = C; A-B = gcd(A,B)*(p-q) => gcd(A,B) | C (3)
    # A = B + C
    # Fie m,n a. i. B = m*gcd(B,C); C = n*gcd(B,C)
    # A = (m+n)*gcd(B,C) => gcd(B,C) | A (1)
    # gcd(A,B) | A (2)
    # Din (1) si (2) => gcd(A,B) >= gcd(B,C) (*)
    # gcd(B,C) | C (4)
    # Din (3) si (4) => gcd(B,C) >= gcd(A,B) (**)
    # Din (*) si (**) => gcd(A,B) = gcd(B,C) => gcd(A,B) = gcd(A,B-A)
    # Se opreste cand A = B deoarece gcd(A,A) = A
    while x != y:
        if x > y:
            x = x - y
        else:
            y = y - x
    return x


def gcd3(x, y):
    factorsx = get_prime_factors(x)
    factorsy = get_prime_factors(y)
    factors = []
    gcd = 1
    for i in factorsx:
        if i in factorsy:
            factors.append(i)
            factorsy.remove(i)
    for i in factors:
        gcd = gcd * i
    return gcd


def gcd4(x, y):
    max = 1
    for i in range(x + 1):
        for j in range(x + 1):
            for k in range(y + 1):
                if i * j == x and i * k == y and max < i:
                    max = i

    return max


def main():
    x = random.randint(1, 2 ** 1020)
    y = random.randint(1, 2 ** 1020)


    start = round(time.time() * 1000)
    gcd = gcd2(x, y)
    end = round(time.time() * 1000)
    print("For gcd2 the result was: "+str(gcd) +" and it took: "+str(end-start)+" ms")

    start = round(time.time() * 1000)
    gcd = gcd3(x, y)
    end = round(time.time() * 1000)
    print("For gcd3 the result was: " + str(gcd) + " and it took: " + str(end - start) + " ms")

    start = round(time.time() * 1000)
    gcd = gcd1(x, y)
    end = round(time.time() * 1000)
    print("For gcd1 the result was: " + str(gcd) + " and it took: " + str(end - start) + " ms")


if __name__ == '__main__':
    main()
