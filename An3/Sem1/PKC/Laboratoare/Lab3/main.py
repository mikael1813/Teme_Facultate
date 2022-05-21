# def gcd_euclid(x, y):
#     while x != y:
#         if x > y:
#             x = x - y
#         else:
#             y = y - x
#     return x

def gcd_euclid2(x, y):
    if y == 0:
        return x
    else:
        return gcd_euclid2(y, x % y)


def pollard_algorithm(n, function="x^2 + 1", xo=2):
    # default f(x) = x^2 + 1
    # xo = 3

    while xo < n:

        x = f(xo, function, n)
        y = f(x, function, n)

        gcd = gcd_euclid2(abs(y - x), n)

        list = [x, y]
        count = 2

        while gcd == 1:
            x = f(y, function, n)
            y = f(x, function, n)
            list.append(x)
            list.append(y)
            count += 2

            gcd = gcd_euclid2(abs(y - list[int(count / 2) - 1]), n)

        if (gcd != n):
            print(str(gcd) + " " + str(int(n / gcd)))
            break

        else:
            print("FAILURE  x0 = " + str(xo))
            xo += 1


def f(x, function, n):
    function = function.replace("^", "**")
    # print(function)
    x = eval(function)
    return x % n


if __name__ == '__main__':
    #function = "x^3 + x^2 + x + 1"
    #function = "x^2 + 1"
    function = "x"
    pollard_algorithm(15, function)
    str = "aaa"
    strnou = str[:1]+"."+str[1:]
    print(strnou)






