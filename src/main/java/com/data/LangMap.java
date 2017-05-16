package com.data;

/**
 * Created by Kieran on 5/3/2017.
 */
public class LangMap {
    public static String toLanguageName(int id) {
        if (id == 1) {
            return "C";
        } else if (id == 2) {
            return "C++";
        } else if (id == 3) {
            return "Java 7";
        } else if (id == 5) {
            return "Python 2";
        } else if (id == 6) {
            return "Perl";
        } else if (id == 7) {
            return "PHP";
        } else if (id == 8) {
            return "Ruby";
        } else if (id == 9) {
            return "C#";
        } else if (id == 10) {
            return "MySQL";
        } else if (id == 11) {
            return "Oracle";
        } else if (id == 12) {
            return "Haskell";
        } else if (id == 13) {
            return "Clojure";
        } else if (id == 14) {
            return "Bash";
        } else if (id == 15) {
            return "Scala";
        } else if (id == 16) {
            return "Erlang";
        } else if (id == 18) {
            return "Lua";
        } else if (id == 20) {
            return "Javascript";
        } else if (id == 21) {
            return "Go";
        } else if (id == 22) {
            return "D";
        } else if (id == 23) {
            return "OCaml";
        } else if (id == 24) {
            return "R";
        } else if (id == 25) {
            return "Pascal";
        } else if (id == 26) {
            return "Common Lisp (SBCL)";
        } else if (id == 30) {
            return "Python 3";
        } else if (id == 31) {
            return "Groovy";
        } else if (id == 32) {
            return "Objective-C";
        } else if (id == 33) {
            return "F#";
        } else if (id == 36) {
            return "COBOL";
        } else if (id == 37) {
            return "VB.NET";
        } else if (id == 38) {
            return "LOLCODE";
        } else if (id == 39) {
            return "Smalltalk";
        } else if (id == 40) {
            return "Tcl";
        } else if (id == 41) {
            return "Whitespace";
        } else if (id == 42) {
            return "T-SQL";
        } else if (id == 43) {
            return "Java 8";
        } else if (id == 44) {
            return "DB2";
        } else if (id == 46) {
            return "Octave";
        } else if (id == 48) {
            return "XQuery";
        } else if (id == 49) {
            return "Racket";
        } else if (id == 50) {
            return "Rust";
        } else if (id == 51) {
            return "Swift";
        } else if (id == 54) {
            return "Fortran";
        }

        return null;
    }

    public static int toLanguageId(String name) {
        if (name.equals("C")) {
            return 1;
        } else if (name.equals("C++")) {
            return 2;
        } else if (name.equals("Java 7")) {
            return 3;
        } else if (name.equals("Python 2")) {
            return 5;
        } else if (name.equals("Perl")) {
            return 6;
        } else if (name.equals("PHP")) {
            return 7;
        } else if (name.equals("Ruby")) {
            return 8;
        } else if (name.equals("C#")) {
            return 9;
        } else if (name.equals("MySQL")) {
            return 10;
        } else if (name.equals("Oracle")) {
            return 11;
        } else if (name.equals("Haskell")) {
            return 12;
        } else if (name.equals("Clojure")) {
            return 13;
        } else if (name.equals("Bash")) {
            return 14;
        } else if (name.equals("Scala")) {
            return 15;
        } else if (name.equals("Erlang")) {
            return 16;
        } else if (name.equals("Lua")) {
            return 18;
        } else if (name.equals("Javascript")) {
            return 20;
        } else if (name.equals("Go")) {
            return 21;
        } else if (name.equals("D")) {
            return 22;
        } else if (name.equals("OCaml")) {
            return 23;
        } else if (name.equals("R")) {
            return 24;
        } else if (name.equals("Pascal")) {
            return 25;
        } else if (name.equals("Common Lisp (SBCL)")) {
            return 26;
        } else if (name.equals("Python 3")) {
            return 30;
        } else if (name.equals("Groovy")) {
            return 31;
        } else if (name.equals("Objective-C")) {
            return 32;
        } else if (name.equals("F#")) {
            return 33;
        } else if (name.equals("COBOL")) {
            return 36;
        } else if (name.equals("VB.NET")) {
            return 37;
        } else if (name.equals("LOLCODE")) {
            return 38;
        } else if (name.equals("Smalltalk")) {
            return 39;
        } else if (name.equals("Tcl")) {
            return 40;
        } else if (name.equals("Whitespace")) {
            return 41;
        } else if (name.equals("T-SQL")) {
            return 42;
        } else if (name.equals("Java 8")) {
            return 43;
        } else if (name.equals("DB2")) {
            return 44;
        } else if (name.equals("Octave")) {
            return 46;
        } else if (name.equals("XQuery")) {
            return 48;
        } else if (name.equals("Racket")) {
            return 49;
        } else if (name.equals("Rust")) {
            return 50;
        } else if (name.equals("Swift")) {
            return 51;
        } else if (name.equals("Fortran")) {
            return 54;
        }

        return 0;
    }
}
