# Invoices Manager 
##### Version
3.0.2

My first commercial project.

Process of Invoice recipt to goods recipt in logistic department was tracked in excel file. Due to larg nr of incomming invoices this procces need to be mapped in DB.

Application will be made of 3 components:

1. Web app - for DB view and editing records

2. Outlook addin - most of the process is tracked based on recived e-mails

Folder structure of git repository:

    InvoiceManager-3.0/

    |--documentation/       - both user and technical documentation     
  
    |--outlook/             - VBA addin for MS OutLook
  
    |--InvoivcesManager/     - Java source code

### Tech
To create this application I have used:
* Database engine: [SQLITE3]
* Web Framework: [Spark Web]
* Template engine: [Freemarker]
* CSS: [Materialize], [W3.CSS]
* JS: [jQuery]
* Fonts/Icons: [Font Awsome] 

[SQLITE3]: <https://www.sqlite.org/>
[Spark Web]: <http://sparkjava.com/>
[Freemarker]: <http://freemarker.org/>
[Materialize]: <http://materializecss.com/>
[W3.CSS]: <http://www.w3schools.com/w3css/default.asp>
[jQuery]: <http://jquery.com>
[Font Awsome]: <http://fontawesome.io/icons/>

### Chenglog
3.0.2
* Add Config class with IM constants
* Add logger class, now you can see logs @ http://localhost:8082/error
* All exceptions in main class constructor are in try-catch clause. Now you can always display /settings and /error page
3.0.1
* Combo list for suppliers, Authorization contact, Genpact contac and currencies are now aviable for both web app and outlook plugin
