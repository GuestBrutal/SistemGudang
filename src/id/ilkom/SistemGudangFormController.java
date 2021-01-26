package id.ilkom;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SistemGudangFormController implements Initializable {

    @FXML
    private TextField tfGudangID;

    @FXML
    private TextField tfNama;

    @FXML
    private TextField tfAlamat;

    @FXML
    private ComboBox<String> cbNik;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private TextField tfNoMember;

    @FXML
    private TextField tfAkBarang;

    @FXML
    private Button btnAddSistemGudang;

    @FXML
    private Button btnReload;

    @FXML
    private Button btnClear;

    @FXML
    private Label lblSaveStatus;

    @FXML
    private TableView<IndividualHolder> tblSisGudang;

    @FXML
    private TableColumn<IndividualHolder, Integer> colGudangID;

    @FXML
    private TableColumn<IndividualHolder, String> colNama;

    @FXML
    private TableColumn<IndividualHolder, String> colAlamat;

    @FXML
    private TableColumn<IndividualHolder, String> colNik;

    @FXML
    private TableColumn<IndividualHolder, String> colBirthdate;

    @FXML
    private TableColumn<IndividualHolder, Integer> colNoAkunn;

    @FXML
    private TableView<Akun> tblAkun;

    @FXML
    private TableColumn<Akun, Integer> colNoMember;

    @FXML
    private TableColumn<Akun, String> colBarang;

    @FXML
    private TextField tfNewGudangID;

    @FXML
    private TextField tfNewNoMember;

    @FXML
    private TextField tfNewAkBarang;

    @FXML
    private Button btnAddAkun;
    
    @FXML
    private Button btnAddGudang;

    @FXML
    private Label lblDBStatus;
    
    private SistemGudangDataModel sgdm;

    @FXML
    void handleAddAkunButton(ActionEvent event) {
        try {
            Akun acc =  new Akun(Integer.parseInt(tfNewNoMember.getText()),
                            tfNewAkBarang.getText());
            
            sgdm.addAkun(Integer.parseInt(tfNewGudangID.getText()),acc);          
            viewDataAkun(Integer.parseInt(tfNewGudangID.getText()));
            btnReload.fire();
            tfNewAkBarang.setText("");
            
        } catch (SQLException ex) {
            Logger.getLogger(SistemGudangFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @FXML
    void handleAddGudangButton(ActionEvent event) {
        LocalDate ld = dpBirthdate.getValue();
        String birthdate = String.format("%d-%02d-%02d", ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
        IndividualHolder gudang = new IndividualHolder(Integer.parseInt(tfGudangID.getText()), 
                tfNama.getText(),
                tfAlamat.getText(),
                cbNik.getSelectionModel().getSelectedItem() , 
                birthdate,
                new Akun(Integer.parseInt(tfNoMember.getText()),tfAkBarang.getText()));
        try {
            sgdm.addSistemGudang(gudang);
            lblSaveStatus.setText("Akun berhasil dibuat");
            btnReload.fire();
            btnClear.fire();
            
        } catch (SQLException ex) {
            lblSaveStatus.setText("Akun gagal dibuat");
            Logger.getLogger(SistemGudangFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleClearButton(ActionEvent event) {

    }

    @FXML
    void handleReloadButton(ActionEvent event) {

    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> nik = FXCollections.observableArrayList("KTP", "SIM");
        cbNik.setItems(nik);
        try {
            SistemGudangDataModel sgdm = new SistemGudangDataModel ("MYSQL");
      
            lblDBStatus.setText(sgdm.conn==null?"Not Connected" : "Connected");
            tfGudangID.setText(""+sgdm.nextSistemGudangID());
            tfNoMember.setText(tfGudangID.getText()+"01");
            dpBirthdate.setValue(LocalDate.of(LocalDate.now().getYear()-17, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
                    
        } catch (SQLException ex) {
            Logger.getLogger(SistemGudangFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tblSisGudang.getSelectionModel().selectedIndexProperty().addListener(listener->{
            if (tblSisGudang.getSelectionModel().getSelectedItem()!=null){
                IndividualHolder gudang =  tblSisGudang.getSelectionModel().getSelectedItem();
                viewDataAkun(gudang.getGudangID());
                btnAddAkun.setDisable(false);
                tfNewGudangID.setText(""+gudang.getGudangID());
                try {
                    tfNewNoMember.setText(""+sgdm.nextNomorMember(gudang.getGudangID()));
                } catch (SQLException ex) {
                    Logger.getLogger(SistemGudangFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }  
    public void viewDataAkun(int gudangID){
        ObservableList<Akun> data = sgdm.getAkunn(gudangID);
        colNoMember.setCellValueFactory(new PropertyValueFactory<>("noMember"));
        colBarang.setCellValueFactory(new PropertyValueFactory<>("barang"));
        tblAkun.setItems(null);
        tblAkun.setItems(data);
    }
}
