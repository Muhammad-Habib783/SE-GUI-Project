/**************************************************************************************************\
 * Project Title : Student Management System (Java GUI Based)                                      *
 * Course/Domain : Software Engineering                                                            *
 * Description   : This project implements a Student Management System in Java using a graphical   *
 *                 user interface (GUI). It integrates core software engineering principles with   *
 *                 file handling for persistent storage of student records. The system provides    *
 *                 functionalities for student registration, record management, fee tracking, and  *
 *                 reporting, all through an interactive GUI.                                      *
 *                                                                                                 *
 * Developed By  : Muhammad Habib (SAP ID: 62669)                                                  *
 *                                                                                                 *
 * Purpose       : Created as an academic project to demonstrate practical applications of         *
 *                 software engineering concepts in building reliable, user-friendly management    *
 *                 systems. This header serves as a professional record of authorship for future   *
 *                 reference.                                                                      *
 *                                                                                                 *
 * Instructor    : Mr. Muhammad Shabir Hassan                                                      *
 \**************************************************************************************************/

// Main.java
public class Main {
    public static void main(String[] args){
        // Load data
        DataStore.loadData();
        // Open login
        javax.swing.SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}
