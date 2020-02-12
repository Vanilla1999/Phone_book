package com.example.crime.database

class CrimeDbSchema {
    companion object {

        class CrimeTable {
            companion object {
                val NAME = "crimes"
            }

            class Cols {
                companion object {
                    public val TITLE = "title"
                    public val DATE = "date"
                    public val SOLVED = "solved"
                }
            }
        }
    }
}