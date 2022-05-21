package com.example.project_kotlin_ma

import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.awaitAll
import java.io.File
import java.io.Serializable
import java.sql.Time

class Service : Serializable {
    var repo: Repository
    var server: ServerRepository
    var fileRepo: FileRepository
    var fileOperationsBackup: File
    var pinguinviewmodel: PinguinViewModel
    var progressBar: ProgressBar

    constructor(
        repo: Repository,
        server: ServerRepository,
        fileRepo: FileRepository,
        fileOpBackup: File,
        pinguinviewmodel: PinguinViewModel,
        progressBar: ProgressBar
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

    fun add(pinguin: Pinguin): Pinguin {
        var id = 0
        var ok = true
        progressBar.visibility = View.VISIBLE
        if (getPinguini() != null) {
            while (ok) {
                ok = false
                for (p in getPinguini()!!) {
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


                fileRepo.add(pinguin)
                writeOperation(Operation.ADD)

            } else {
                // server online
//                repo.add(it)
//                pp = it

            }
            progressBar.visibility = View.INVISIBLE

        }
        repo.add(pinguin)
        pinguinviewmodel.pinguin.value = getPinguini()
        return pp
    }

    fun update(id: Int, pinguin: Pinguin) {
        progressBar.visibility = View.VISIBLE
        server.update(id, pinguin) {
            if (it == null) {
                // server not online
                pinguin.id = id
                fileRepo.add(pinguin)
                writeOperation(Operation.UPDATE)

            } else {
                // server online

            }
            progressBar.visibility = View.INVISIBLE
        }
        repo.update(id, pinguin)
        pinguinviewmodel.pinguin.value = getPinguini()
    }

    fun delete(id: Int) {
        progressBar.visibility = View.VISIBLE
        var deletedPenguin: Pinguin? = findByID(id)
        server.delete(id) {
            if (it == null) {
                // server not online
                fileRepo.add(deletedPenguin!!)
                writeOperation(Operation.DELETE)

            } else {
                // server online

            }
            progressBar.visibility = View.INVISIBLE
        }
        repo.delete(id)
    }

    fun findByPosition(position: Int): Pinguin? {
        var count = -1
        for (p in getPinguini()!!) {
            count++
            if (count == position)
                return p
        }
        return null
    }

    fun findByID(id: Int): Pinguin? {
        var list: List<Pinguin>? = null

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
        for (p in getPinguini()!!) {
            if (p.id == id) {
                return true
            }
        }
        return false
    }

    fun getPinguiniFromServer() {
        //var pinguins: ArrayList<Pinguin>? = null
        progressBar.visibility = View.VISIBLE
        server.getAll() {
            if (it != null) {
                for (p in it) {
                    if (!ifExistsID(p.id)) {
                        repo.add(p)
                    }

                }
            }
            progressBar.visibility = View.INVISIBLE
        }
    }

    fun getPinguini(): ArrayList<Pinguin>? {
        //var aux = repo.getAll()

        return repo.getAll()
    }
}