import re
from arbore import Nod, BinarySearchTree
from automat_finit import AutomatFinit


class Error(Exception):
    """Base class for other exceptions"""
    pass


class InvalidIDOrConst(Error):
    def __init__(self, str):
        self.str = str


class IDTooBig(Error):
    def __init__(self, str):
        self.str = str


def get_atomi():
    f = open("program.txt", "r");
    program = f.readlines();
    f.close()
    lines = ""
    separators = ['\+', '-', '\*', '/', '%', '>=', '<=', '<', '>', '==', '=', '!=', '&&', '\|\|', ',', ';', ' ', '\(',
                  '\)']
    sep = '('
    for i in range(0, len(separators) - 1):
        sep += separators[i] + '|'
    sep += separators[-1] + ')'
    for i in program:
        lines = lines + i
    lines = lines.replace('\t', '').replace('\n', '')
    # atomi = re.split('(,| |;)', lines)
    atomi = re.split(sep, lines)
    # atomi = re.split('(\+|-|\*|/|%|>=|<=|<|>|==|=|!=|&&|\|\||,|;| |\(|\))',lines)
    while atomi.__contains__('' or ' '):
        for i in atomi:
            if i == ' ' or i == '':
                atomi.remove(i)
    # print(atomi)
    g = open('atomi.txt', 'w')
    for a in atomi:
        g.write(a + '\n')
    g.close()
    return atomi


def get_cod_atomi():
    f = open("codificare_atomi.txt", "r");
    lines = f.readlines();
    cod_atomi = []
    for line in lines:
        if line[0] == ',':
            row = []
            row.append(',')
            line = line.replace(',', '').replace('\n', '')
            row.append(int(line.split(',')[0]))
            cod_atomi.append(row)
        else:
            line = line.replace('\n', '')
            row = line.split(',')
            row[1] = int(row[1])
            cod_atomi.append(row)
    return cod_atomi


def if_num_const(atom):
    # if atom == "0":
    #     return True
    # str = re.search("[-]?[1-9]+[0-9]*\.[0-9]+", atom)
    # if str != None and str.group(0) == atom:
    #     return True
    # str = re.search("[-]?[1-9]+[0-9]*", atom)
    # if str != None and str.group(0) == atom:
    #     return True
    # str = re.search("[-]?0.[0-9]+", atom)
    # if str != None and str.group(0) == atom:
    #     return True

    automat = AutomatFinit()
    automat.read_from_file("automate_finite/automat_finit_number.txt")

    return automat.check_sequence(atom)


def if_cpp_int_or_float(atom):
    automat = AutomatFinit()
    automat.read_from_file("automate_finite/automat_finit_cpp_int.txt")

    if automat.check_sequence(atom):
        # if int(atom) >= -2 ** 16 and int(atom) < 2 ** 16:
        return True

    automat = AutomatFinit()
    automat.read_from_file("automate_finite/automat_finit_number.txt")

    if automat.check_sequence(atom):
        return True

    return False


def if_ID(atom):
    automat = AutomatFinit()
    automat.read_from_file("automate_finite/automat_finit_id.txt")

    return automat.check_sequence(atom)


def get_cod_cpp_int(cod_atomi, atom):
    if atom[0] == "\"" and atom[-1] == "\"":
        return 0
    for i in range(2, len(cod_atomi)):
        if atom == cod_atomi[i][0]:
            return cod_atomi[i][1]

    if if_cpp_int_or_float(atom):
        return 0
    # str = re.search("[a-zA-Z]+[0-9]*", atom)
    # if str != None and str.group(0) == atom:
    #     return 1
    if if_ID(atom):
        return 1
    else:
        raise InvalidIDOrConst(atom)


def get_cod(cod_atomi, atom):
    if atom[0] == "\"" and atom[-1] == "\"":
        return 0
    for i in range(2, len(cod_atomi)):
        if atom == cod_atomi[i][0]:
            return cod_atomi[i][1]

    if if_num_const(atom):
        return 0
    # str = re.search("[a-zA-Z]+[0-9]*", atom)
    # if str != None and str.group(0) == atom:
    #     return 1
    if if_ID(atom):
        return 1
    else:
        raise InvalidIDOrConst(atom)


def lab1():
    try:
        atomi = get_atomi()
        cod_atomi = get_cod_atomi()
        print(cod_atomi)

        FIP = []
        TS = BinarySearchTree()
        for atom in atomi:
            cod = get_cod_cpp_int(cod_atomi, atom)
            if cod > 1:
                FIP.append([cod, -1])
            else:
                if cod == 1 and len(atom) > 8:
                    raise IDTooBig(atom)
                FIP.append([cod, 0])
                TS.add_node(Nod(atom))
        TS.display()
        TS.write_to_file()
        for i in range(len(atomi)):
            if FIP[i][1] == 0:
                FIP[i][1] = TS.get_index_by_value(atomi[i])

        print(FIP)
        g = open('FIP.txt', 'w')
        for i in FIP:
            g.write(str(i[0]) + "\t" + str(i[1]) + '\n')
        g.close()

    except IDTooBig as e:
        print("ID can only have 8 characters: ", str(e.str))
        print()
    except InvalidIDOrConst as e:
        print("ID or Const doesn't respect the syntax rules: ", str(e.str))
        print()


def main():
    try:
        atomi = get_atomi()
        cod_atomi = get_cod_atomi()
        print(cod_atomi)

        FIP = []
        TS = BinarySearchTree()
        for atom in atomi:
            cod = get_cod(cod_atomi, atom)
            if cod > 1:
                FIP.append([cod, -1])
            else:
                if cod == 1 and len(atom) > 8:
                    raise IDTooBig(atom)
                FIP.append([cod, 0])
                TS.add_node(Nod(atom))
        TS.display()
        TS.write_to_file()
        for i in range(len(atomi)):
            if FIP[i][1] == 0:
                FIP[i][1] = TS.get_index_by_value(atomi[i])

        print(FIP)
        g = open('FIP.txt', 'w')
        for i in FIP:
            g.write(str(i[0]) + "\t" + str(i[1]) + '\n')
        g.close()

    except IDTooBig as e:
        print("ID can only have 8 characters: ", str(e.str))
        print()
    except InvalidIDOrConst as e:
        print("ID or Const doesn't respect the syntax rules: ", str(e.str))
        print()


def lab2():
    automat = AutomatFinit()
    # automat.read_from_keyboard()
    automat.read_from_file("automate_finite/automat_finit_number.txt")
    while (True):
        n = input("Adauga comanda: ")
        if n == '1':
            print(automat.get_alfabet())
        elif n == '2':
            print(automat.get_stari())
        elif n == '3':
            print(automat.get_stari_finale())
        elif n == '4':
            for i in automat.get_tranziti():
                print(i)
        elif n == '5':
            if automat.check_determinism():
                sequnce = ''
                while sequnce != 'stop':
                    sequnce = input("Adauga o secventa: ")

                    if automat.check_sequence(sequnce):
                        print("da")
                    else:
                        print("nu")
            else:
                print("Automatul nu este determinist")
        elif n == '6':
            if automat.check_determinism():
                sequnce = ''
                while sequnce != 'stop':

                    sequnce = input("Adauga o secventa: ")
                    max = ''
                    for i in range(len(sequnce)):
                        for j in range(i + 1, len(sequnce) + 1):
                            if j - i > len(max) and automat.check_sequence(sequnce[i:j]):
                                max = sequnce[i:j]
                    print("Cel mai mare prefix este: " + max)
            else:
                print("Automatul nu este determinist")


        elif n == 'stop':
            break


if __name__ == '__main__':
    main()
    # https://docs.microsoft.com/en-us/cpp/cpp/fundamental-types-cpp?view=msvc-160
    # https://en.cppreference.com/w/cpp/language/integer_literal
    #lab1()
    # lab2()
