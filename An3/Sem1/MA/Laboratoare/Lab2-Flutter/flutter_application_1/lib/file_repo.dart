import 'dart:convert';
import 'dart:math';
//import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:flutter_application_1/pinguin.dart';
import 'package:flutter_application_1/repository.dart';
import 'package:path_provider/path_provider.dart';
import 'dart:io';

class FileRepo implements Repository {
  late File file;

  FileRepo(String filePath) {
    this.file = File(filePath);
  }
  @override
  void write(List<Pinguin> list) {
    String jsonPinguini = jsonEncode(list);
    file.writeAsStringSync(jsonPinguini);
  }

  @override
  List<Pinguin>? read() {
    if (file.readAsStringSync() == "") {
      return null;
    }
    var pJson = jsonDecode(file.readAsStringSync().toString()) as List;
    List<Pinguin> PinguinObjs =
        pJson.map((Json) => Pinguin.fromJson(Json)).toList();

    return PinguinObjs;
  }

  @override
  void save(Pinguin p) {
    List<Pinguin>? list = read();
    if (list == null) {
      list = <Pinguin>[];
      list.add(p);
    } else {
      list.add(p);
    }
    write(list);
  }

  @override
  Pinguin? getPoz(int index) {
    int poz = 0;
    List<Pinguin>? list = read();
    if (list != null) {
      for (var pinguin in list) {
        if (poz == index) {
          return pinguin;
        }
        poz++;
      }
    }
    return null;
  }

  @override
  bool delete(int id) {
    List<Pinguin>? list = read();
    if (list != null) {
      for (var pinguin in list) {
        if (pinguin.id == id) {
          list.remove(pinguin);
          write(list);
          return true;
        }
      }
    }
    return false;
  }

  @override
  Pinguin? getPinguinByID(int id) {
    List<Pinguin>? list = read();
    if (list != null) {
      for (var pinguin in list) {
        if (pinguin.id == id) {
          return pinguin;
        }
      }
    }
    return null;
  }

  @override
  bool update(int id, Pinguin p) {
    List<Pinguin>? list = read();
    if (list != null) {
      for (var pinguin in list) {
        if (pinguin.id == id) {
          pinguin.dataNasterii = p.dataNasterii;
          pinguin.id = p.id;
          pinguin.nume = p.nume;
          pinguin.inaltime = p.inaltime;
          pinguin.greutate = p.greutate;
          pinguin.pret = p.pret;
          pinguin.stare = p.stare;
          pinguin.specie = p.specie;
          write(list);
          return true;
        }
      }
    }
    return false;
  }
}
