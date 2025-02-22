package tests.wrapper;

import java.io.IOException;

import odra.db.Database;
import odra.db.DatabaseException;
import odra.dbinstance.DBInstance;
import odra.store.DefaultStore;
import odra.store.memorymanagement.RevSeqFitMemManager;
import odra.store.persistence.DataFileHeap;
import odra.store.sbastore.ObjectManager;
import odra.wrapper.Wrapper;
import odra.wrapper.WrapperException;

/**
 * Relational database wrapper tests.
 * 
 * @author jacenty
 * @version 2007-11-02
 * @since 2007-06-25
 */
public class SwardWrapperTest
{
	private String dbFilePath = "test.dbf";
	private int size = 1 * 1024 * 1024;
	
	private ObjectManager manager;
	private DefaultStore store;

	private DBInstance instance;
	
	private String moduleName = "wrapper";
	
	private int mode = Wrapper.MODE_SWARD;
	
	public static void main(String[] args)
	{
		SwardWrapperTest test = new SwardWrapperTest();
		test.startup();
		try
		{
			test.initialize();
		}
		catch (DatabaseException exc)
		{
			exc.printStackTrace();
		}
		catch (WrapperException exc)
		{
			exc.printStackTrace();
		}
		catch (IOException exc)
		{
			exc.printStackTrace();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		test.shutdown();
		
		System.exit(0);
	}
	
	private void startup()
	{
		try
		{
			DataFileHeap fileHeap = new DataFileHeap(dbFilePath);
			fileHeap.format(size);
			fileHeap.open();
			
			RevSeqFitMemManager allocator;
			allocator = new RevSeqFitMemManager(fileHeap);
			allocator.initialize();

			manager = new ObjectManager(allocator);
			manager.initialize(100);
			
			store = new DefaultStore(manager);
			store.initialize();

			Database.initialize(store);
			Database.open(store);
			
			instance = new DBInstance();
			instance.startup();
			
			System.out.println("database started...");
		}
		catch (DatabaseException exc)
		{
			exc.printStackTrace();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
	}
	
	private void shutdown()
	{
		instance.shutdown();
		store.close();
		
		System.out.println("database stopped...");
	}
	
	private void initialize() throws Exception
	{
		SwardCLI cli = new SwardCLI();
		cli.execAddModuleAsWrapper(new String[] {moduleName, "localhost", Integer.toString(2002), Integer.toString(mode)}, "admin");
		cli.execCm(new String[] {moduleName});
		cli.begin();
	}
}
