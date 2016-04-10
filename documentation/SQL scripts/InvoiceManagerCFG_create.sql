-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2016-04-07 06:39:06.092



-- tables
-- Table: CFG
CREATE TABLE CFG (
    ID integer  NOT NULL   PRIMARY KEY,
    imExternalFolderPath text  NOT NULL,
    imDBPath text  NOT NULL,
    rowsPerPage integer  NOT NULL
);

-- Table: columnsAndWidth
CREATE TABLE columnsAndWidth (
    columnID varchar(255)  NOT NULL   PRIMARY KEY,
    columnWidth integer  NOT NULL
);





-- End of file.

