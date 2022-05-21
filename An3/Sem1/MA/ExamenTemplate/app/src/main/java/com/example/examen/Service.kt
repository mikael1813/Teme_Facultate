package com.example.project_kotlin_ma

import android.view.View
import android.widget.Toast
import com.example.examen.CustomProgressBar
import com.example.examen.Operation
import com.example.examen.Produs
import java.io.File
import java.io.Serializable

class Service : Serializable {
    var repo: Repository
    var server: ServerRepository
    var fileRepo: FileRepository
    var fileOperationsBackup: File
    var pinguinviewmodel: ProdusViewModel
    var progressBar: CustomProgressBar

    constructor(
        repo: Repository,
        server: ServerRepository,
        fileRepo: FileRepository,
        fileOpBackup: File,
        pinguinviewmodel: ProdusViewModel,
        progressBar: CustomProgressBar
    ) {
        this.repo = repo
        this.server = server
        this.fileRepo = fileRepo
        this.fileOperationsBackup = fileOpBackup
        this.pinguinviewmodel = pinguinviewmodel
        this.progressBar = progressBar
    }

    fun writeOperation(operation: Operation) {
        fileOperationsBackup.appendText(operation.toString() + "\n")
    }

    fun add(pinguin: Produs): Produs {
        var id = 1
        var ok = true
        progressBar.progressBar.visibility = View.VISIBLE
        progressBar.add()
        if (getAll() != null) {
            while (ok) {
                ok = false
                var x = getAll()
                for (p in getAll()!!) {
                    if (p.id == id)
                        ok = true
                }
                if (ok) {
                    id++
                }
            }
        }
        pinguin.id = id
        var pp = pinguin
        server.add(pinguin) {
            if (it == null) {
                // server not online
                var ok = true
                for (ppp in getAll()!!) {
                    if (ppp.nume == pinguin.nume) {
                        ok = false
                    }
                }
                if (ok) {
                    fileRepo.add(pinguin)
                    writeOperation(Operation.ADD)
                    repo.add(pinguin)
                    pinguinviewmodel.produse.value = getAll()
                }


            } else {
                // server online
                pinguin.id = it
                repo.add(pinguin)
                pinguinviewmodel.produse.value = getAll()


            }
            //progressBar.visibility = View.INVISIBLE
            progressBar.substract()

        }

        return pp

    }

    fun update(id: Int, pinguin: Produs) {
        progressBar.progressBar.visibility = View.VISIBLE
        progressBar.add()
        server.update(id, pinguin) {
            if (it == null) {
                // server not online
                pinguin.id = id
                fileRepo.add(pinguin)
                writeOperation(Operation.UPDATE)

            } else {
                // server online

            }
            //progressBar.visibility = View.INVISIBLE
            progressBar.substract()
        }
        repo.update(id, pinguin)
        pinguinviewmodel.produse.value = getAll()
    }

    fun delete(id: Int) {
        progressBar.progressBar.visibility = View.VISIBLE
        progressBar.add()
        var deletedPenguin: Produs? = findByID(id)
        server.delete(id) {
            if (it == null) {
                // server not online
                fileRepo.add(deletedPenguin!!)
                writeOperation(Operation.DELETE)

            } else {
                // server online

            }
            //progressBar.visibility = View.INVISIBLE
            progressBar.substract()
        }
        repo.delete(id)
    }

    fun findByPosition(position: Int): Produs? {
        var count = -1
        for (p in getAll()!!) {
            count++
            if (count == position)
                return p
        }
        return null
    }

    fun findByID(id: Int): Produs? {
        var list: List<Produs>? = null

//        server.getAll() {
//            list = it;
//        }

        if (list == null) {
            list = repo.getAll()
        }

        for (p in list!!) {
            if (p.id == id) {
                return p
            }
        }
        return null
    }

    fun ifExistsID(id: Int): Boolean {
        for (p in getAll()!!) {
            if (p.id == id) {
                return true
            }
        }
        return false
    }

    fun getPinguiniFromServer() {
        //var pinguins: ArrayList<Pinguin>? = null
        //progressBar.visibility = View.VISIBLE
        server.getAll() {
            if (it != null) {
                for (p in it) {
                    if (!ifExistsID(p.id)) {
                        repo.add(p)
                    }

                }
            }
            //progressBar.visibility = View.INVISIBLE
        }
    }

    fun getAll(): ArrayList<Produs>? {
        //var aux = repo.getAll()

        return repo.getAll()
    }

//    fun getTipuri(): ArrayList<String> {
//        var list = ArrayList<String>()
//        if (getAll() != null) {
//            for (p in getAll()!!) {
//                var ok = true
//                for (t in list) {
//                    if (t == p.tip) {
//                        ok = false
//                    }
//                }
//                if (ok) {
//                    list.add(p.tip)
//                }
//            }
//        }
//        return list
//    }
//
//    fun getListOfCheapest(tipuri: ArrayList<String>): ArrayList<Produs> {
//        var list = ArrayList<Produs>()
//        if (getAll() != null) {
//            for (t in tipuri) {
//                var cheapest = Produs("", "", 0, 999999999, 0, false)
//                for (p in getAll()!!) {
//                    if (p.tip == t) {
//                        var price = p.pret
//                        if (p.status)
//                            price -= p.discount
//                        if (price < cheapest.pret) {
//                            cheapest = p
//                        }
//                    }
//                }
//                list.add(cheapest)
//            }
//        }
//        return list
//    }
}