package com.gl4.tp2

class Student {
    constructor(nom:String,prenom:String,genre: Genre, matiere : Matiere){
        this.nom=nom
        this.prenom=prenom
        this.genre=genre
        this.matiere=matiere
    }
    var  nom: String=""
    var  prenom: String=""
    var genre: Genre=Genre.MASCULIN
    var matiere: Matiere = Matiere.COURS

}

enum class Genre{
    FEMININ, MASCULIN
}
enum class Matiere{
    COURS, TP
}

enum class Etat{
    PRESENT, ABSENT
}