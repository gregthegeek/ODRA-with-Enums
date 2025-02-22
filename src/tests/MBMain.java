package tests;
import java.net.UnknownHostException;

import odra.cli.CLI;
import odra.db.Database;
import odra.db.OID;
import odra.db.objects.data.DBModule;
import odra.db.objects.data.ModuleDumper;
import odra.db.objects.meta.MBStruct;
import odra.db.objects.meta.MBTypeDef;
import odra.db.objects.meta.MBVariable;
import odra.dbinstance.DBInstance;
import odra.sbql.builder.BuilderUtils;
import odra.sbql.builder.ModuleLinker;
import odra.sessions.Session;
import odra.store.DefaultStore;
import odra.store.memorymanagement.RevSeqFitMemManager;
import odra.store.persistence.DataFileHeap;
import odra.store.sbastore.ObjectManager;
import odra.system.config.ConfigDebug;

public class MBMain {

	/**
	 * @param args
	 */
	private ObjectManager manager;
	private DefaultStore store;

	private Database db;
	private DBInstance instance;

	private void createDatabase() throws Exception {
		DataFileHeap fileHeap;
		RevSeqFitMemManager allocator;

		fileHeap = new DataFileHeap("c:\\test1.dbf");
		fileHeap.format(1024 * 1024 * 5);
		fileHeap.open();

		allocator = new RevSeqFitMemManager(fileHeap);
		allocator.initialize();		

		
		manager = new ObjectManager(allocator);
		manager.initialize(100); //create the root object, complex object (int) name -1,parent 0, children
		
		store = new DefaultStore(manager);
		store.initialize();// create the complex object nidx (oid) 0,entry,2 children
		//System.out.println(store.getEntry().getObjectNameId());
		
		
		
		// prepare the database
		Database.initialize(store);
		Database.open(store);
 
		
		DBModule sysmod = Database.getSystemModule();
		System.out.println(sysmod.getName()+"  "+sysmod.getOID().getObjectNameId()+"  "+sysmod.getMetabaseEntry().getObjectName());
		OID[] subMods = sysmod.getSubmodules();
		for(int i=0;i<subMods.length;i++){
			//System.out.print(subMods[i].getObjectName()+"  ");System.out.println(subMods[i].getObjectNameId());
			}
		
		//DBModule mod = Database.getModuleByName("admin");
		//Session.create();
		//Session.initialize("admin", "admin");
				
		//DBModule nMod2 = new DBModule(mod.createSubmodule("mod2"));
		
		//OID oid = nMod2.createComplexObject("osoba", store.getEntry(), 2);
		//store.createStringObject(store.addName("imie"), oid, "Antek", 0);
		//store.createIntegerObject(store.addName("wiek"), oid, 12);
		
		//System.out.println(store.dump());
		

		//System.out.println(new ModuleDumper(nMod2).dump());
		//System.out.println(store.dump());
		System.out.println("Database created");
		//Session.close();
	}

	private void startup() {
		try { 
			instance = new DBInstance();
			instance.startup(); 
		}
		catch (UnknownHostException ex) {
			System.out.println("*** Database instance cannot be started");
		}
	}

	private void shutdown() {
		instance.shutdown();		
	}

/*	public void importData(String mod, String doc, String par) throws FilterException {
		System.out.println("Importujemy do " + mod);
		System.out.println("-");
		System.out.println(doc);
		System.out.println("-");
		System.out.println(par);
	}
*/
	public void testCli() throws Exception {		
//<<<<<<< .mine
		//createDatabase();
		//startup();
		//new odra.system.Main().createExpandableDatabase("d:\\testexp", 500000, 500000, 100000);
		new odra.system.Main().startExpandableDatabaseInstance("d:\\testexp");
		
//=======
		//createDatabase();
		//startup();
		
		//new odra.system.Main().createExpandableDatabase("c:\\ODRA_db_files\\testexp", 500000, 500000, 100000);
		//new odra.system.Main().startExpandableDatabaseInstance("c:\\ODRA_db_files\\testexp");
		
		//new odra.system.Main().createExpandableDatabase("c:\\ODRA_db_files\\testexp1", 50000, 50000, 10000);
		//new odra.system.Main().startExpandableDatabaseInstance("c:\\ODRA_db_files\\testexp1");

//>>>>>>> .r2774

		CLI cli = new CLI();
		cli.begin();

		shutdown();
	}

	public static void main(String[] args) throws Exception {
        /*java.lang.System.setProperty("apple.laf.useScreenMenuBar", "true");
		java.lang.System.setProperty("apple.awt.antialiasing", "false");
		java.lang.System.setProperty("apple.awt.textantialiasing", "false");
		java.lang.System.setProperty("com.apple.mrj.application.apple.menu.about.name", "CLI");
		
		System.out.println("Test started (debug mode: " + ConfigDebug.ASSERTS + ")");
*/
		new MBMain().testCli();
		//new MBMain().createDatabase();
	}


}
