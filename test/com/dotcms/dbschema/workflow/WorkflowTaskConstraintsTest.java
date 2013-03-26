package com.dotcms.dbschema.workflow;

import java.sql.SQLException;
import java.util.*;
import com.dotmarketing.common.db.*;
import com.dotmarketing.db.HibernateUtil;
import com.dotmarketing.exception.*;
import com.dotcms.TestBase;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

/**
 * @author brent griffin
 *
 */
public class WorkflowTaskConstraintsTest extends TestBase{
	private static String pkInsertTaskString = "insert into workflow_task values('11111111-1111-1111-1111-111111111111', null, null, null, null, null, null, 'Title', 'Desc', null, null);";
	private static String fkToCMSRoleInsertTaskString = "insert into workflow_task values('22222222-2222-2222-2222-222222222222', null, null, null, null, '11111111-1111-1111-1111-111111111111', null, 'Title', 'Desc', null, null);";
	private static String fkToWorkflowStepInsertTaskString = "insert into workflow_task values('33333333-3333-3333-3333-333333333333', null, null, null, null, null, null, 'Title', 'Desc', '11111111-1111-1111-1111-111111111111', null);";
	private static String fkToIdentifierInsertTaskString = "insert into workflow_task values('44444444-4444-4444-4444-444444444444', null, null, null, null, null, null, 'Title', 'Desc', null, '11111111-1111-1111-1111-111111111111');";
	
	@BeforeClass
	public static void init() {
	}
	
	@AfterClass
	public static void cleanup() {
	}
	
	@Test
	public void testPrimaryKey() throws DotDataException, SQLException {
		// id column
		try {
			HibernateUtil.startTransaction();
			DotConnect dc = new DotConnect();
			dc.executeStatement(pkInsertTaskString);
			try {
				dc.executeStatement(pkInsertTaskString);			
				assertTrue(false); // should not reach here - duplicate key exception should have fired
			}
			catch(Exception e) {
				// Do nothing - expecting this exception
				e.printStackTrace();
			}
		}
		finally {
			// do not persist any of the changes - rollback
			HibernateUtil.rollbackTransaction();
		}
	}

	@Test
	public void testFKtoCMSRole() throws DotDataException {
		// assigned_to column
		try {
			HibernateUtil.startTransaction();
			DotConnect dc = new DotConnect();
			try {
				dc.executeStatement(fkToCMSRoleInsertTaskString);			
				assertTrue(false); // should not reach here - foreign key violation exception should have fired
			}
			catch(Exception e) {
				// Do nothing - expecting this exception
				e.printStackTrace();
			}
		}
		finally {
			// do not persist any of the changes - rollback
			HibernateUtil.rollbackTransaction();
		}
	}

	@Test
	public void testFKtoWorkflowStep() throws DotDataException {
		// status column
		try {
			HibernateUtil.startTransaction();
			DotConnect dc = new DotConnect();
			try {
				dc.executeStatement(fkToWorkflowStepInsertTaskString);			
				assertTrue(false); // should not reach here - foreign key violation exception should have fired
			}
			catch(Exception e) {
				// Do nothing - expecting this exception
				e.printStackTrace();
			}
		}
		finally {
			// do not persist any of the changes - rollback
			HibernateUtil.rollbackTransaction();
		}
	}

	@Test
	public void testFKtoIdentifier() throws DotDataException {
		// webasset column
		try {
			HibernateUtil.startTransaction();
			DotConnect dc = new DotConnect();
			try {
				dc.executeStatement(fkToIdentifierInsertTaskString);			
				assertTrue(false); // should not reach here - foreign key violation exception should have fired
			}
			catch(Exception e) {
				// Do nothing - expecting this exception
				e.printStackTrace();
			}
		}
		finally {
			// do not persist any of the changes - rollback
			HibernateUtil.rollbackTransaction();
		}
	}
}
