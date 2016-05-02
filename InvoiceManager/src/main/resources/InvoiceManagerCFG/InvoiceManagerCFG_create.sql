-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2016-05-02 16:02:59.836

-- tables
-- Table: CFG
CREATE TABLE CFG (
    ID integer NOT NULL AUTOINCREMENT,
    imExternalFolderPath text NOT NULL,
    imDBPath text NOT NULL,
    rowsPerPage integer NOT NULL,
    backgroundColor varchar(7) NOT NULL DEFAULT #eee,
    OrderByClause varchar(255) NOT NULL DEFAULT ORDER BY ID DESC,
    InvDuplicates boolean NOT NULL DEFAULT 1,
    userNetID varchar(6) NOT NULL DEFAULT DB,
    userEmail varchar(255) NOT NULL,
    userColor varchar(7) NOT NULL,
    CONSTRAINT CFG_pk PRIMARY KEY (ID)
);
-- End of file.

