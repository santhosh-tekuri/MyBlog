package santhosh.solution;

import santhosh.DNDChallenge;
import santhosh.Person;
import santhosh.Employee;
import santhosh.Manager;

import javax.swing.*;

public class DNDChallengeSolved extends DNDChallenge{
    public DNDChallengeSolved(){
        personList.setTransferHandler(new MyListTransferableHandler(Person.class));
        employeeList.setTransferHandler(new MyListTransferableHandler(Employee.class));
        managerList.setTransferHandler(new MyListTransferableHandler(Manager.class));
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        DNDChallenge challenge = new DNDChallengeSolved();
        challenge.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        challenge.setVisible(true);
    }
}
