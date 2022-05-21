import 'dart:convert';
import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:flutter_application_1/data_base_repo.dart';
import 'package:flutter_application_1/file_repo.dart';
import 'package:flutter_application_1/pinguin.dart';
import 'package:flutter_application_1/repository.dart';
import 'package:flutter_application_1/service.dart';
import 'package:path_provider/path_provider.dart';
import 'dart:io';
import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

import 'dart:async';

import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';

late String stringPath = "";
late var database;

void main() async {
  Pinguin p =
      new Pinguin(1, "nono", 14, 14, Stare.ADOPTAT, 14, "specie", "12/18/2000");
  Pinguin p2 = new Pinguin(
      2, "nono", 13, 13, Stare.NEADOPTAT, 13, "specie", "12/18/2000");

  // Avoid errors caused by flutter upgrade.
// Importing 'package:flutter/widgets.dart' is required.
  WidgetsFlutterBinding.ensureInitialized();
// Open the database and store the reference.
  Future<bool> a = databaseFactory.databaseExists('pinguin.db');
  final db = openDatabase(
    // Set the path to the database. Note: Using the `join` function from the
    // `path` package is best practice to ensure the path is correctly
    // constructed for each platform.
    join(await getDatabasesPath(), 'pinguin.db'),
    onCreate: (db1, version) {
      // Run the CREATE TABLE statement on the database.
      return db1.execute(
        'CREATE TABLE Pinguini(id INTEGER PRIMARY KEY, nume TEXT, inaltime INTEGER,greutate INTEGER,stare TEXT,pret INTEGER,specie TEXT,dataNasterii TEXT)',
      );
    },
    // Set the version. This executes the onCreate function and provides a
    // path to perform database upgrades and downgrades.
    version: 1,
  );
  database = await db;

  // await db.execute(
  //   'CREATE TABLE Pinguini(id INTEGER PRIMARY KEY, nume TEXT, inaltime INTEGER,greutate INTEGER,stare TEXT,pret INTEGER,specie TEXT,dataNasterii TEXT)',
  // );

  // await db.insert(
  //   'Pinguini',
  //   p.toMap(),
  //   conflictAlgorithm: ConflictAlgorithm.replace,
  // );

  // List<Pinguin>? list = <Pinguin>[];

  // Repository repo = Repository(await getFilePath());
  // repo.save(p);
  // repo.save(p2);

  // list = repo.read();
  // print(list);

  // list.add(p);
  // list.add(p);
  // list.add(p);
  // print(list);

  // String jsonP = jsonEncode(list);
  // print(jsonP);

  // //Pinguin p2 = Pinguin.fromJson(jsonDecode(jsonP));
  // var pJson = jsonDecode(jsonP) as List;
  // List<Pinguin> PinguinObjs =
  //     pJson.map((Json) => Pinguin.fromJson(Json)).toList();

  // print(PinguinObjs);
  // //print(p2);

  //runApp(MyApp());

  // getFilePath().then((String value) {
  //   stringPath = value;
  // });

  //saveFile();
  //String x = stringPath;
  stringPath =
      "/data/user/0/com.example.flutter_application_1/app_flutter/pinguini.json";
  FileRepo fileRepo = FileRepo(stringPath);
  DataBaseRepo dataBaseRepo = DataBaseRepo(database);
  //Service service = Service(fileRepo);
  Service service = Service(dataBaseRepo);
  //service.add(p);
  //service.add(p2);

  runApp(MaterialApp(home: Home()));
}

//listview

class ListPinguini extends StatefulWidget {
  ListState listState = ListState();

  @override
  State<StatefulWidget> createState() {
    return listState;
  }

  Pinguin getPinguinSelectat() {
    return listState.getSelectedPinguin();
  }

  void _delete(int id) {
    listState._deleteItem(id);
  }

  void _add(Pinguin pinguin) {
    listState._addItem(pinguin);
  }

  void _update(int id, Pinguin pinguinUpdated) {
    listState._updateItem(id, pinguinUpdated);
  }
}

class ListState extends State<ListPinguini> {
  var _suggestions = <Pinguin>[];

  final _biggerFont = const TextStyle(fontSize: 18.0);

  //final Service service = Service(FileRepo(stringPath));
  final Service service = Service(DataBaseRepo(database));

  Pinguin pinguin = Pinguin(-1, "", 0, 0, Stare.ADOPTAT, 0, "", "");

  Pinguin getSelectedPinguin() {
    return pinguin;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Lista Pinguinilor'),
        centerTitle: true,
      ),
      body: _buildSuggestions(),
    );
  }

  void _deleteItem(int id) {
    setState(() {
      for (int i = 0; i < _suggestions.length; i++) {
        if (_suggestions.elementAt(i).id == id) {
          _suggestions.removeAt(i);
          break;
        }
      }
    });
  }

  void _addItem(Pinguin pinguin) {
    setState(() {
      _suggestions.add(pinguin);
    });
  }

  void _updateItem(int id, Pinguin nou) {
    setState(() {
      for (int i = 0; i < _suggestions.length; i++) {
        if (_suggestions.elementAt(i).id == id) {
          _suggestions.elementAt(i).id = nou.id;
          _suggestions.elementAt(i).nume = nou.nume;
          _suggestions.elementAt(i).inaltime = nou.inaltime;
          _suggestions.elementAt(i).greutate = nou.greutate;
          _suggestions.elementAt(i).stare = nou.stare;
          _suggestions.elementAt(i).pret = nou.pret;
          _suggestions.elementAt(i).specie = nou.specie;
          _suggestions.elementAt(i).dataNasterii = nou.dataNasterii;
          break;
        }
      }
    });
  }

  Widget? _buildSuggestions() {
    return ListView.builder(
      padding: const EdgeInsets.all(10.0),
      itemCount: service.getPinguini()!.length,
      itemBuilder: (context, i) {
        if (service.getPinguini() != null) {
          if (_suggestions.isEmpty) {
            _suggestions.addAll(service.getPinguini()!);
          }
          return _buildRow(_suggestions[i]);
        } else {
          return Card(
            child: Text("Empty"),
          );
        }
        // if (i.isOdd) return Divider();

        // final index = i ~/ 2;
        // // If you've reached the end of the available word pairings...
        // if (index >= _suggestions.length && service.getPinguini() != null) {
        //   // ...then generate 10 more and add them to the suggestions list.
        //   _suggestions.addAll(service.getPinguini()!);
        // }
        // return _buildRow(_suggestions[index]);
      },
    );
  }

  Widget _buildRow(Pinguin pin) {
    return Card(
      child: ListTile(
        onTap: () {
          pinguin = pin;
          //showAlertDialog(context, "Selected", "Pinguin " + pinguin.nume);
        },
        onLongPress: () {
          Navigator.push(
            this.context,
            MaterialPageRoute(builder: (context) => ReadPage(pin)),
          );
        },
        title: new Text(
            "id: " +
                pin.id.toString() +
                " nume: " +
                pin.nume +
                " stare: " +
                pin.stare.toString().replaceAll("Stare.", ""),
            style: _biggerFont),
      ),
    );
  }
}

//endlistview

//home
class Home extends StatelessWidget {
  Home({Key? key}) : super(key: key);

  final ListPinguini listPinguini = ListPinguini();

  //final Service service = Service(FileRepo(stringPath));
  final Service service = Service(DataBaseRepo(database));

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text("Pinguin"),
          centerTitle: true,
        ),
        body: listPinguini,
        // Center(
        //   child: Text("HEEEEEELOO"),
        // ),
        floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
        floatingActionButton: Stack(
          fit: StackFit.expand,
          children: [
            Positioned(
              left: 30,
              bottom: 20,
              child: FloatingActionButton(
                heroTag: 'add',
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => AddPage(listPinguini)),
                  );
                },
                child: Text("Add"),
                // child: Icon(
                //   Icons.arrow_left,
                //   size: 40,
                // ),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
              ),
            ),
            Positioned(
              bottom: 20,
              right: 30,
              child: FloatingActionButton(
                heroTag: 'delete',
                onPressed: () {
                  if (listPinguini.getPinguinSelectat().id == -1) {
                    showAlertDialog(
                        context, "Error", "Nici un pinguin nu a fost selectat");
                  } else {
                    showAlertDialogDelete(
                        context,
                        listPinguini.getPinguinSelectat().id,
                        service,
                        listPinguini);
                  }

                  //service.delete(listPinguini.getPinguinSelectat().id);
                },
                child: Text("Delete"),
                // child: Icon(
                //   Icons.arrow_right,
                //   size: 40,
                // ),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
              ),
            ),
            Positioned(
              bottom: 20,
              left: 170,
              child: FloatingActionButton(
                heroTag: 'update',
                onPressed: () {
                  if (listPinguini.getPinguinSelectat().id == -1) {
                    showAlertDialog(
                        context, "Error", "Nici un pinguin nu a fost selectat");
                  } else {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => UpdatePage(
                              listPinguini.getPinguinSelectat(), listPinguini)),
                    );
                  }
                },
                child: Text("Update"),
                // child: Icon(
                //   Icons.arrow_right,
                //   size: 40,
                // ),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
              ),
            ),
            // Add more floating buttons if you want
            // There is no limit
          ],
        ),
      ),
    );
  }
}

//home

//addpage

class AddPage extends StatefulWidget {
  late ListPinguini listPinguini;
  AddPage(ListPinguini listPinguini) {
    this.listPinguini = listPinguini;
  }

  AddPageState createState() => new AddPageState(listPinguini);
}

class AddPageState extends State {
  TextEditingController _controllerNume = new TextEditingController();
  TextEditingController _controllerInaltime = new TextEditingController();
  TextEditingController _controllerGreutate = new TextEditingController();
  TextEditingController _controllerStare = new TextEditingController();
  TextEditingController _controllerPret = new TextEditingController();
  TextEditingController _controllerSpecie = new TextEditingController();
  TextEditingController _controllerdataNasterii = new TextEditingController();
  //final Service service = Service(FileRepo(stringPath));
  final Service service = Service(DataBaseRepo(database));

  late ListPinguini listPinguini;

  bool _enabled = false;

  AddPageState(ListPinguini listPinguini) {
    this.listPinguini = listPinguini;
  }

  @override
  Widget build(BuildContext context) {
    ThemeData theme = Theme.of(context);
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('Adauga Pinguin'),
      ),
      floatingActionButton: new FloatingActionButton(
          //child: new Icon(Icons.free_breakfast),
          child: Text("Add"),
          onPressed: () {
            // setState(() {
            //   _enabled = !_enabled;
            // });
            //_controllerNume.text = "hahaha";
            String error = "";
            if (_controllerNume.text == "") {
              error = error + " Numele nu poate fi gol\n";
            }
            if (_controllerSpecie.text == "") {
              error = error + " Specia nu poate fi goala\n";
            }
            if (!isNumeric(_controllerInaltime.text)) {
              error = error + " Inaltimea trebuie sa fie un numar\n";
            }
            if (!isNumeric(_controllerGreutate.text)) {
              error = error + " Greutatea trebuie sa fie un numar\n";
            }
            if (!isNumeric(_controllerPret.text)) {
              error = error + " Pretul trebuie sa fie un numar\n";
            }
            bool okStare = false;
            Stare stare = Stare.ADOPTAT;
            Stare.values.forEach((element) {
              if (element.toString() ==
                  "Stare." + _controllerStare.text.toString()) {
                okStare = true;
                stare = element;
              }
            });
            if (!okStare) {
              error = error + " Starea nu este valida\n";
            }
            if (DateTime.tryParse(_controllerdataNasterii.text) != null ||
                _controllerdataNasterii.text == "") {
              error = error + " Data nu este valida\n";
            }
            if (error != "") {
              showAlertDialog(context, "Error", error);
            } else {
              Pinguin pinguinToAdd = service.add(Pinguin(
                  0,
                  _controllerNume.text,
                  double.tryParse(_controllerInaltime.text)!,
                  double.tryParse(_controllerGreutate.text)!,
                  stare,
                  double.tryParse(_controllerPret.text)!,
                  _controllerSpecie.text,
                  _controllerdataNasterii.text));
              listPinguini._add(pinguinToAdd);
              Navigator.pop(context);
            }
          }),
      body: new Center(
        child: Column(
          children: <Widget>[
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerNume)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerNume,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'nume',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerInaltime)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerInaltime,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'inaltime',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerGreutate)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerGreutate,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'greutate',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerStare)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerStare,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'stare',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerPret)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerPret,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'pret',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerSpecie)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerSpecie,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'specie',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerdataNasterii)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerdataNasterii,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText:
                                _enabled ? 'Type something' : 'data nasterii',
                          ),
                        ),
                      ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

//addpage

//updatepage

class UpdatePage extends StatefulWidget {
  late Pinguin pinguinSelectat;
  late ListPinguini listPinguini;
  UpdatePage(Pinguin pinguin, ListPinguini listPinguini) {
    pinguinSelectat = pinguin;
    this.listPinguini = listPinguini;
  }

  UpdatePageState createState() =>
      new UpdatePageState(pinguinSelectat, listPinguini);
}

class UpdatePageState extends State {
  TextEditingController _controllerID = new TextEditingController();
  TextEditingController _controllerNume = new TextEditingController();
  TextEditingController _controllerInaltime = new TextEditingController();
  TextEditingController _controllerGreutate = new TextEditingController();
  TextEditingController _controllerStare = new TextEditingController();
  TextEditingController _controllerPret = new TextEditingController();
  TextEditingController _controllerSpecie = new TextEditingController();
  TextEditingController _controllerdataNasterii = new TextEditingController();
  //final Service service = Service(FileRepo(stringPath));
  final Service service = Service(DataBaseRepo(database));
  bool _enabled = false;

  late ListPinguini listPinguini;

  late Pinguin pinguinSelectat;

  UpdatePageState(Pinguin pin, ListPinguini listPinguini) {
    this.listPinguini = listPinguini;
    pinguinSelectat = pin;
    _controllerID.text = pin.id.toString();
    _controllerNume.text = pin.nume.toString();
    _controllerInaltime.text = pin.inaltime.toString();
    _controllerGreutate.text = pin.greutate.toString();
    _controllerStare.text = pin.stare.toString().replaceAll("Stare.", "");
    _controllerPret.text = pin.pret.toString();
    _controllerSpecie.text = pin.specie.toString();
    _controllerdataNasterii.text = pin.dataNasterii.toString();
  }

  @override
  Widget build(BuildContext context) {
    ThemeData theme = Theme.of(context);
    return Scaffold(
      appBar: AppBar(
        title: Text('Modifica Pinguin'),
      ),
      floatingActionButton: FloatingActionButton(
          //child: new Icon(Icons.free_breakfast),
          child: Text("Update"),
          onPressed: () {
            // setState(() {
            //   _enabled = !_enabled;
            // });
            //_controllerNume.text = "hahaha";
            String error = "";
            if (!isNumeric(_controllerID.text)) {
              error = error + " ID-ul trebuie sa fie un numar\n";
            }
            if (_controllerNume.text == "") {
              error = error + " Numele nu poate fi gol\n";
            }
            if (_controllerSpecie.text == "") {
              error = error + " Specia nu poate fi goala\n";
            }
            if (!isNumeric(_controllerInaltime.text)) {
              error = error + " Inaltimea trebuie sa fie un numar\n";
            }
            if (!isNumeric(_controllerGreutate.text)) {
              error = error + " Greutatea trebuie sa fie un numar\n";
            }
            if (!isNumeric(_controllerPret.text)) {
              error = error + " Pretul trebuie sa fie un numar\n";
            }
            bool okStare = false;
            Stare stare = Stare.ADOPTAT;
            Stare.values.forEach((element) {
              if (element.toString() ==
                  "Stare." + _controllerStare.text.toString()) {
                okStare = true;
                stare = element;
              }
            });
            if (!okStare) {
              error = error + " Starea nu este valida\n";
            }
            if (DateTime.tryParse(_controllerdataNasterii.text) != null ||
                _controllerdataNasterii.text == "") {
              error = error + " Data nu este valida\n";
            }
            if (error != "") {
              showAlertDialog(context, "Error", error);
            } else {
              Pinguin pinguinToUpdate = Pinguin(
                  int.tryParse(_controllerID.text)!,
                  _controllerNume.text,
                  double.tryParse(_controllerInaltime.text)!,
                  double.tryParse(_controllerGreutate.text)!,
                  stare,
                  double.tryParse(_controllerPret.text)!,
                  _controllerSpecie.text,
                  _controllerdataNasterii.text);
              int oldID = pinguinSelectat.id;
              listPinguini._update(oldID, pinguinToUpdate);
              service.update(oldID, pinguinToUpdate);
              Navigator.pop(context);
            }
          }),
      body: new Center(
        child: Column(
          children: <Widget>[
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerID)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerID,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'id',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerNume)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerNume,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'nume',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerInaltime)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerInaltime,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'inaltime',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerGreutate)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerGreutate,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'greutate',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerStare)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerStare,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'stare',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerPret)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerPret,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'pret',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerSpecie)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerSpecie,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'specie',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerdataNasterii)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerdataNasterii,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText:
                                _enabled ? 'Type something' : 'data nasterii',
                          ),
                        ),
                      ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

//updatepage

//readpage

class ReadPage extends StatefulWidget {
  late Pinguin pinguinSelectat;
  ReadPage(Pinguin pinguin) {
    pinguinSelectat = pinguin;
  }

  ReadPageState createState() => new ReadPageState(pinguinSelectat);
}

class ReadPageState extends State {
  TextEditingController _controllerID = new TextEditingController();
  TextEditingController _controllerNume = new TextEditingController();
  TextEditingController _controllerInaltime = new TextEditingController();
  TextEditingController _controllerGreutate = new TextEditingController();
  TextEditingController _controllerStare = new TextEditingController();
  TextEditingController _controllerPret = new TextEditingController();
  TextEditingController _controllerSpecie = new TextEditingController();
  TextEditingController _controllerdataNasterii = new TextEditingController();
  //final Service service = Service(FileRepo(stringPath));
  final Service service = Service(DataBaseRepo(database));
  final bool _enabled = false;

  late Pinguin pinguinSelectat;

  ReadPageState(Pinguin pin) {
    pinguinSelectat = pin;
    _controllerID.text = pin.id.toString();
    _controllerNume.text = pin.nume.toString();
    _controllerInaltime.text = pin.inaltime.toString();
    _controllerGreutate.text = pin.greutate.toString();
    _controllerStare.text = pin.stare.toString().replaceAll("Stare.", "");
    _controllerPret.text = pin.pret.toString();
    _controllerSpecie.text = pin.specie.toString();
    _controllerdataNasterii.text = pin.dataNasterii.toString();
  }

  @override
  Widget build(BuildContext context) {
    ThemeData theme = Theme.of(context);
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('Afisare Date Pinguin'),
      ),
      body: new Center(
        child: Column(
          children: <Widget>[
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerID)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerID,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'id',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerNume)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerNume,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'nume',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerInaltime)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerInaltime,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'inaltime',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerGreutate)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerGreutate,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'greutate',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerStare)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerStare,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'stare',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerPret)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerPret,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'pret',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerSpecie)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerSpecie,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText: _enabled ? 'Type something' : 'specie',
                          ),
                        ),
                      ),
              ),
            ),
            Expanded(
              child: Container(
                margin: const EdgeInsets.all(10.0),
                height: 50,
                child: _enabled
                    ? new TextFormField(controller: _controllerdataNasterii)
                    : new FocusScope(
                        node: new FocusScopeNode(),
                        child: new TextFormField(
                          controller: _controllerdataNasterii,
                          // style: theme.textTheme.subhead.copyWith(
                          //   color: theme.disabledColor,
                          // ),
                          decoration: new InputDecoration(
                            hintText:
                                _enabled ? 'Type something' : 'data nasterii',
                          ),
                        ),
                      ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

//readpage

showAlertDialog(BuildContext context, String error, String string) {
  // set up the button
  Widget okButton = TextButton(
    child: Text("OK"),
    onPressed: () {
      Navigator.pop(context);
    },
  );

  // set up the AlertDialog
  AlertDialog alert = AlertDialog(
    title: Text(error),
    content: Text(string),
    actions: [
      okButton,
    ],
  );

  // show the dialog
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
}

// void saveFile() async {
//   File file = File(await getFilePath());
//   //await file.delete();
//   file.writeAsString(
//       "This is my demo text that will be saveddddddddddd to : demoTextFile.txt");
// }

// Future<String> getFilePath() async {
//   Directory appDocumentsDirectory = await getApplicationDocumentsDirectory();
//   String appDocumentsPath = appDocumentsDirectory.path;
//   String filePath = '$appDocumentsPath/pinguini.json';

//   return filePath;
// }

bool isNumeric(String s) {
  if (s == null) {
    return false;
  }
  return double.tryParse(s) != null;
}

showAlertDialogDelete(
    BuildContext context, int id, Service service, ListPinguini listPinguini) {
  // set up the buttons
  Widget cancelButton = TextButton(
    child: Text("Cancel"),
    onPressed: () {
      Navigator.pop(context, false);
    },
  );
  Widget continueButton = TextButton(
    child: Text("Continue"),
    onPressed: () {
      listPinguini._delete(id);
      service.delete(id);
      Navigator.pop(context, true);
    },
  );

  // set up the AlertDialog
  AlertDialog alert = AlertDialog(
    title: Text("Alert"),
    content: Text("Are you sure that you want to delete " +
        service.getPinguinByID(id)!.nume +
        " ?"),
    actions: [
      cancelButton,
      continueButton,
    ],
  );

  // show the dialog
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
}

bool isValidDate(String input) {
  try {
    final date = DateTime.parse(input);
    final originalFormatString = toOriginalFormatString(date);
    return input == originalFormatString;
  } catch (e) {
    return false;
  }
}

String toOriginalFormatString(DateTime dateTime) {
  final y = dateTime.year.toString().padLeft(4, '0');
  final m = dateTime.month.toString().padLeft(2, '0');
  final d = dateTime.day.toString().padLeft(2, '0');
  return "$y$m$d";
}
