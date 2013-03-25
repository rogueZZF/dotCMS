/**
 * 
 */
package com.dotcms.dbschema;

import java.util.*;
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
public class WorkflowSchemaConstraintsTest extends TestBase{

	
	@BeforeClass
	public static void init() {
	}
	
	@AfterClass
	public static void cleanup() {
	}
	
	@Test
	public void test() throws DotDataException {
		try {
			HibernateUtil.startTransaction();
		}
		finally {
			// do not persist any of the changes - rollback
			HibernateUtil.rollbackTransaction();
		}
	}
}
