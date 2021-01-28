# SistemGudang

# KRISNA BENEDICTA PRIMA 1917051069 (Coding, Connection Database, GUI Form)

# AKHMAD ZULFIKAR 1957051013 (Desain ERD, Desain Database, Class Diagram)

# RAESHA SALSABILA 1957051006 (Coding, GUI Form, ERD Diagram)

# CLASS DIAGRAM
```mermaid
classDiagram
    SistemGudang <|-- IndividualHolder
    SistemGudang <|-- CorporateHolder
    SistemGudang "1"--o"*" Akun : has
    
    class SistemGudang{
      <<abstract>>
      #int gudangID
      #String nama
      #String alamat
      +int nextID()
    }
    
    class IndividualHolder{
      -String nik
      -String birthdate
    }
    class CorporateHolder{
      -String contact
    }
    class Akun{
      -int noMember
      -string barang
    }
```

# ER DIAGRAM
```mermaid
erDiagram
          SISTEMGUDANG ||..|| INDIVIDUAL-HOLDER : is
          SISTEMGUDANG ||--|| CORPORATE-HOLDER : is
          SISTEMGUDANG ||--|{ AKUN: "has"
          SISTEMGUDANG {
            int gudangid
            string nama
            string alamat
          }
          INDIVIDUAL-HOLDER{
            string nik
            string birthdate
          }
          CORPORATE-HOLDER{
            string contact
          }
          AKUN{
            int nomember
            string barang
          }
```

# Design Class Diagram for JavaFX and Database

```mermaid
classDiagram
    SitemGudang <|-- IndividualHolder
    SitemGudang <|-- CorporateHolder
    SitemGudang "1"--o"*" Akun : has
    SitemGudang o-- SitemGudangDataModel : Data Modeling
    SitemGudangDataModel <-- AccountHolderController : Data Control
    SitemGudangDataModel --> DBHelper : DB Connection
    SitemGudangController <.. SitemGudangForm : Form Control      

    class SitemGudang{
      <<abstract>>
      #IntegerProperty gudangID
      #StringProperty nama
      #StringProperty alamat
      #IntegerProperty noAkun
      
      +IntegerProperty nextID()
    }
    
    class IndividualHolder{
      -StringProperty nik
      -StringProperty birthdate
    }
    class CorporateHolder{
      -StringProperty contact
    }
    class Akun{
      -StringProperty barang
      +String getBarang()
    }

    class SitemGudangDataModel{
        Connection conn
        addSitemGudang()
        addAkun()
        getIndividualHolders()
        getCorporateHolders()
        nextSitemGudangID()
        nextAkunNumber()
    }

    class SitemGudangController{
        initialize()
        handleButtonAddAkun()
        handleButtonAddSistemGudang()
        loadDataIndividualHolder()
        loadDataCorporateHolder()
        loadDataAkun()
        handleClearForm()
    }
    class DBHelper{
        - String USERNAME
        - String PASSWORD
        - String DB
        getConnection()
        getConnection(String driver)
        createTable();
    }
```
