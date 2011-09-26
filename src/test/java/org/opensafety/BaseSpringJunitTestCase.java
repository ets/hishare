package org.opensafety;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.sql.DataSource;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.opensafety.hishare.dao.interfaces.UserDao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/application-*.xml","classpath:/hishare-*.xml"})
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public abstract class BaseSpringJunitTestCase extends TestCase {
    @Resource
    protected  SessionFactory sessionFactory;
    @Resource
    protected UserDao userDao;
    @Resource
    protected DataSource dataSource;
    
    private IDatabaseTester databaseTester;
        
    
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    /**
     * ResourceBundle loaded from src/test/resources/${package.name}/ClassName.properties (if exists)
     */
    protected ResourceBundle rb;
    
    /**
     * Default constructor - populates "rb" variable if properties file exists for the class in
     * src/test/resources.
     */
    public BaseSpringJunitTestCase() {
        
        // Since a ResourceBundle is not required for each class, just
        // do a simple check to see if one exists
        String className = this.getClass().getName();
        
        try {
            this.rb = ResourceBundle.getBundle(className);
        } catch (MissingResourceException mre) {
            //log.warn("No resource bundle found for: " + className);
        }
    }
    
    @Before
    public void setUpDatabase() throws Exception {
        try {
            IDataSet dataSet = getDataSet();
            if(dataSet != null){
                this.databaseTester = new DataSourceDatabaseTester(this.dataSource);
                this.databaseTester.setSetUpOperation(DatabaseOperation.REFRESH);
                this.databaseTester.setTearDownOperation(DatabaseOperation.DELETE);
                this.databaseTester.setDataSet(dataSet);
                this.databaseTester.onSetup();
            }
        } catch (Exception e) {     e.printStackTrace();     throw e;  }
    }
    
    /** Example:
        IDataSetProducer producer = new FlatXmlProducer(new InputSource("dataset.xml"));
        return new StreamingDataSet(producer);
     */
    protected IDataSet getDataSet() throws DataSetException{
        return null; //Override and return a dataset if you want it loaded
    }
    
    @After
    public void tearDownDatabase() throws Exception {
        if (this.databaseTester != null) {
            this.databaseTester.onTearDown();
        }
    }
    
    /**
     */
    protected void flush() {
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();
    }
    
}
