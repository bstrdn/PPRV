package PPRV;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Study {

    public SimpleIntegerProperty studyId = new SimpleIntegerProperty();
    public SimpleIntegerProperty studyIdPatient = new SimpleIntegerProperty();
    public SimpleStringProperty studyDate = new SimpleStringProperty();
    public SimpleStringProperty studyA1 = new SimpleStringProperty();
    public SimpleStringProperty studyA2 = new SimpleStringProperty();
    public SimpleStringProperty studyA3 = new SimpleStringProperty();
    public SimpleStringProperty studyB1 = new SimpleStringProperty();
    public SimpleStringProperty studyC1 = new SimpleStringProperty();
    public SimpleStringProperty studyC2 = new SimpleStringProperty();
    public SimpleStringProperty studyD1 = new SimpleStringProperty();


    public Integer getStudyId() {
        return studyId.get();
    }
    public Integer getStudyIdPatient() {
        return studyIdPatient.get();
    }

    public String getStudyDate() {
        return studyDate.get();
    }

    public String getStudyA1() {
        return studyA1.get();
    }
    public String getStudyA2() {
        return studyA2.get();
    }
    public String getStudyA3() {
        return studyA3.get();
    }
    public String getStudyB1() {
        return studyB1.get();
    }
    public String getStudyC1() {
        return studyC1.get();
    }
    public String getStudyC2() {
        return studyC2.get();
    }
    public String getStudyD1() {
        return studyD1.get();
    }



}
