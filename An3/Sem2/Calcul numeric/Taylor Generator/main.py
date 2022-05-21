n = 99999
k = 1
a = 1
s = 0
for i in range(0, n):
    s = s + (1 / a) * k
    a += 1
    k *= -1
print(s)
