package PPRV.MUSOR;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BasePatient {

    public SimpleIntegerProperty userId = new SimpleIntegerProperty();
    public SimpleStringProperty userName = new SimpleStringProperty();
    public SimpleStringProperty userAge = new SimpleStringProperty();
    public SimpleStringProperty userLO = new SimpleStringProperty();


    public Integer getUserId() {
        return userId.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getUserAge() {
        return userAge.get();
    }

    public String getUserLO() {
        return userLO.get();
    }

}
