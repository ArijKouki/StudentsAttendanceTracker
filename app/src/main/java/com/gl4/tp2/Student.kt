package com.gl4.tp2

class Student {
    constructor(nom:String,prenom:String,genre: Genre, matiere : Matiere, etat:Etat){
        this.nom=nom
        this.prenom=prenom
        this.genre=genre
        this.matiere=matiere
        this.etat=etat
    }
    var  nom: String=""
    var  prenom: String=""
    var genre: Genre=Genre.MASCULIN
    var matiere: Matiere = Matiere.COURS
    var etat: Etat = Etat.ABSENT

}

enum class Genre{
    FEMININ, MASCULIN
}

enum class Matiere{
    COURS {
        override fun toString(): String {
            return "Cours"
        }
    }, TP {
        override fun toString(): String {
            return "TP"
        }
    },
}

enum class Etat{
    PRESENT {
        override fun toString(): String {
            return "Present"
        }
    }, ABSENT {
        override fun toString(): String {
            return "Absent"
        }
    },
}