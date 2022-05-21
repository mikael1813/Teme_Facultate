import math
import random
import timeit

from cffi.backend_ctypes import xrange


def miller_rabin(n, k=10000):
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


def generate_primes():
    x = 10
    while not miller_rabin(x):
        x = random.randrange(pow(10, 20), pow(10, 40))
    return x


def generate_public_key():
    p = generate_primes()
    g = random.randint(2, p - 1)
    a = random.randint(1, p - 2)
    # a = 2
    public_key = [p, g, pow(g, a, p)]
    private_key = a

    return public_key, private_key


def compute_alphabet():
    alphabet = {' ': 10}
    val = ord('a')
    for idx in range(1, 27):
        alphabet[chr(val + idx - 1)] = idx + 10
    val = ord('A')
    count = 27
    for idx in range(1, 27):
        alphabet[chr(val + idx - 1)] = count + 10
        count += 1
    return alphabet


def compute_inv_alphabet(alphabet):
    inv_alphabet = {}
    for char, num in alphabet.items():
        inv_alphabet[num] = char
    return inv_alphabet


def translate_plaintext_into_numerical(plaintext, alphabet):
    numerical = []
    for char in plaintext:
        numerical.append(alphabet[char])
    return numerical


def translate_numerical_into_plaintext(numerical, inv_alphabet):
    plaintext = ''
    for num in numerical:
        plaintext += inv_alphabet[num]
    return plaintext


def encrypt(public_key, private_key, m):
    p = public_key[0]
    g = public_key[1]
    k = random.randint(1, p - 2)
    alpha = pow(g, k, p)
    beta = (m * pow(pow(g, private_key, p), k, p)) % p
    return alpha, beta


def decrypt(public_key, private_key, alpha, beta):
    p = public_key[0]
    return (pow(alpha, p - 1 - private_key, p) * beta) % p


def convert_message_into_number(message, public_key):
    print(message)
    p = public_key[0]
    if message <= p - 1:
        return [message]
    else:
        slice = math.ceil(len(str(message)) / len(str(p - 1)))
        xxx = len(str(p - 1))
        m = str(message)
        parts = []
        for i in range(0, slice):
            if i == slice - 1:
                part = m[i * xxx:len(m) + 1]
            else:
                part = m[i * xxx:(i + 1) * xxx]
            parts.append(part)
        print(parts)
        return parts


if __name__ == '__main__':
    public_key, private_key = generate_public_key()

    input = "abcdef  ghqaslklksdhfoia faaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ASKFLAIW ASLFIAISFA afajaw ASFASFdgsegs"
    alphabet = compute_alphabet()
    msg = translate_plaintext_into_numerical(input, alphabet)
    print("Input = " + input)
    m = ''
    for el in msg:
        m += str(el)
    m = int(m)

    p = public_key[0]

    m_num = convert_message_into_number(m, public_key)

    list_alpha = []
    list_beta = []
    for el in m_num:
        alpha, beta = encrypt(public_key, private_key, int(el))
        list_alpha.append(alpha)
        list_beta.append(beta)

    print(list_alpha)
    print(list_beta)

    decrypted_text = ""
    decrypted_number = ""
    for i in range(len(list_alpha)):
        output = decrypt(public_key, private_key, list_alpha[i], list_beta[i])
        decrypted_number += str(output)

    list_numbers = []
    for i in range(0, len(decrypted_number), 2):
        number = decrypted_number[i] + decrypted_number[i + 1]
        list_numbers.append(int(number))
    decrypted_text = translate_numerical_into_plaintext(list_numbers, compute_inv_alphabet(alphabet))
    print("Output = " + decrypted_text)
