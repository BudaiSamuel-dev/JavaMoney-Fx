package application;

import classes.Penzmozgas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class PenzmozgasController {

    private static final File FILE_IN = new File("C:\\Dev\\FeladatForrasok\\BudaiS\\money-data.txt");
    private static final File FILE_OUT = new File("C:\\Dev\\FeladatForrasok\\BudaiS\\money-kiadasok(fx).txt");

    private ArrayList<Penzmozgas> penzmozgasok = new ArrayList<>();

    @FXML
    private Label labEgyenleg;

    @FXML
    private ListView<Penzmozgas> lvKiadasok;

    @FXML
    void fajlbaIrasKlikk(ActionEvent event) {
        try {
            FileWriter fileWriter = new FileWriter(FILE_OUT);

            ArrayList<Penzmozgas> kiadasok = new ArrayList<Penzmozgas>();
            for (Penzmozgas penzmozgas : penzmozgasok){
                if (penzmozgas.getTipus() == 'K'){
                    kiadasok.add(penzmozgas);
                }
            }

            //Kiadások sortolása
            kiadasok.sort(Comparator.comparingInt(Penzmozgas::getOsszeg).reversed());

            for(Penzmozgas penzmozgas : kiadasok){
                String sor = String.format("%s -> %s (%d Ft)\n", penzmozgas.getDatum(), penzmozgas.getMegnevezes(), penzmozgas.getOsszeg());
                fileWriter.write(sor);
            }
            fileWriter.flush();
            fileWriter.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Siker!");
            alert.setHeaderText("A fájl kiírása sikeres volt!");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba!");
            alert.setHeaderText("A fájl kiírása sikertelen volt!");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {

        assert labEgyenleg != null : "fx:id=\"labEgyenleg\" was not injected: check your FXML file 'penzmozgas-ablak.fxml'.";
        assert lvKiadasok != null : "fx:id=\"lvKiadasok\" was not injected: check your FXML file 'penzmozgas-ablak.fxml'.";


        try {
            Scanner fileScanner = new Scanner(FILE_IN);

            while (fileScanner.hasNextLine()){
                String record = fileScanner.nextLine();

                if (!record.trim().isEmpty()){
                    String[] data = record.split("\\|");
                    String datum = data[0].trim().strip();
                    char tipus = data[1].trim().strip().charAt(0);
                    String megnevezes = data[2].trim().strip();
                    int osszeg = Integer.parseInt(data[3].trim().strip());

                    String fizetesMod = null;
                    if (data.length > 4 && tipus == 'K'){
                        fizetesMod = data[4].trim().strip();
                    }

                    Penzmozgas penzmozgas = new Penzmozgas(datum, tipus, megnevezes, osszeg);
                    if (fizetesMod != null){
                        penzmozgas.setFizetesiMod(fizetesMod);
                    }

                    penzmozgasok.add(penzmozgas);
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba!");
            alert.setHeaderText("A fájl nem található");
            alert.showAndWait();

        }

        for (Penzmozgas penzmozgas : penzmozgasok){
            lvKiadasok.getItems().add(penzmozgas);
        }

        int osszegASzamlan = 75000;
        for (Penzmozgas penzmozgas : penzmozgasok){
            int ev = Integer.parseInt(penzmozgas.getDatum().split("\\.")[0]);
            int honap = Integer.parseInt(penzmozgas.getDatum().split("\\.")[1]);

            if (ev == 2024 && (honap < 6 && honap > 1)){
                if (penzmozgas.getTipus() == 'B'){
                    osszegASzamlan += penzmozgas.getOsszeg();
                }else if(penzmozgas.getTipus() == 'K' && penzmozgas.getFizetesiMod().equalsIgnoreCase("BK")){
                    osszegASzamlan -= penzmozgas.getOsszeg();
                }
            }
        }

        labEgyenleg.setText(osszegASzamlan + "Ft");

    }

}
