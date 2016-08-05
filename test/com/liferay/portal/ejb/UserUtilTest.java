package com.liferay.portal.ejb;

import com.dotcms.repackage.com.ibm.icu.util.GregorianCalendar;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.RoleAPI;
import com.dotmarketing.business.UserAPI;
import com.dotmarketing.cms.factories.PublicCompanyFactory;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.util.dao.hibernate.OrderByComparator;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nollymar Longa on 8/5/16.
 */
public class UserUtilTest {

    private static User systemUser;
    private static UserAPI userAPI;
    private static RoleAPI roleAPI;

    @BeforeClass
    public static void prepare() throws DotDataException {

        //Setting the test user
        systemUser = APILocator.getUserAPI().getSystemUser();
        userAPI = APILocator.getUserAPI();
        roleAPI = APILocator.getRoleAPI();
    }

    @Test
    public void testFindByCompanyId() throws DotSecurityException, DotDataException, SystemException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);
        userAPI.save(user, systemUser, false);

        List users = UserUtil.findByCompanyId(companyId);

        assertNotNull(users);
        assertTrue(users.size() > 0);
        assertTrue(!users.contains(user));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testFindByCompanyIdWithLimit() throws DotSecurityException, DotDataException, SystemException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);
        userAPI.save(user, systemUser, false);

        List users = UserUtil.findByCompanyId(companyId, 0, 5, null);

        assertNotNull(users);
        assertTrue(users.size() > 0 && users.size() <= 5);
        assertTrue(!users.contains(user));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testFindByC_U() throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, false);
        userAPI.save(user, systemUser, false);

        User result = UserUtil.findByC_U(companyId, userName);

        assertNotNull(result);
        assertTrue(result.equals(user));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test(expected = NoSuchUserException.class)
    public void testFindByC_UMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);
        userAPI.save(user, systemUser, false);

        UserPool.remove(userName);
        try {
            UserUtil.findByC_U(companyId, userName);
        } finally {
            try {
                userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
            } catch (DotSecurityException e) {
                // no need to validate this
            }
        }
    }

    @Test
    public void testFindByC_P() throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, false);
        user.setPassword(userName);
        userAPI.save(user, systemUser, false);

        List users = UserUtil.findByC_P(companyId, userName);

        assertNotNull(users);
        assertTrue(users.size() == 1);
        assertTrue(users.contains(user));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testFindByC_PMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);
        user.setPassword(userName);
        userAPI.save(user, systemUser, false);

        List users = UserUtil.findByC_P(companyId, userName);

        assertNotNull(users);
        assertTrue(users.size() == 0);

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testFindByC_PWithLimit()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        id = String.valueOf(new Date().getTime());
        companyId = PublicCompanyFactory.getDefaultCompanyId();

        userName = "user" + id;
        user = getUser(userName, true);
        user.setPassword("1:1:EBk/HSdzfiWh52GO9xxbBJhZgsb2jd9Q:i=4e20:LnjrBImIZ2XRA6woT8lSZmGNrDP8LKgE");
        user.setCompanyId(companyId);
        userAPI.save(user, systemUser, false);

        List
            users =
            UserUtil
                .findByC_P(companyId, "1:1:EBk/HSdzfiWh52GO9xxbBJhZgsb2jd9Q:i=4e20:LnjrBImIZ2XRA6woT8lSZmGNrDP8LKgE", 0,
                    5, null);

        assertNotNull(users);
        assertTrue(users.size() > 0 && users.size() <= 5);
        assertTrue(!users.contains(user));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testFindByC_P_PrevAndNext()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user1, user2, user3, user4;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        user1 = getUser("user" + id, false);
        user1.setPassword("password");
        userAPI.save(user1, systemUser, false);

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user2 = getUser(userName, false);
        user2.setPassword("password");
        userAPI.save(user2, systemUser, false);

        id = String.valueOf(new Date().getTime());
        user3 = getUser("user" + id, true);
        user3.setPassword("password");
        userAPI.save(user3, systemUser, false);

        id = String.valueOf(new Date().getTime());
        user4 = getUser("user" + id, false);
        user4.setPassword("password");
        userAPI.save(user4, systemUser, false);

        User[] users = UserUtil.findByC_P_PrevAndNext(userName, companyId, "password", new UserUtilComparatorTest());

        assertNotNull(users);
        assertTrue(users.length == 3);
        assertTrue(!users[0].equals(user3));
        assertTrue(!users[1].equals(user3));
        assertTrue(!users[2].equals(user3));

        try {
            userAPI.delete(user1, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
            userAPI.delete(user2, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
            userAPI.delete(user3, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
            userAPI.delete(user4, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testFindByC_EA() throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, false);
        userAPI.save(user, systemUser, false);

        User result = UserUtil.findByC_EA(companyId, user.getEmailAddress());

        assertNotNull(result);
        assertTrue(result.equals(user));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test(expected = NoSuchUserException.class)
    public void testFindByC_EAMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);
        userAPI.save(user, systemUser, false);

        try {
            UserUtil.findByC_EA(companyId, user.getEmailAddress());
        } finally {
            try {
                userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
            } catch (DotSecurityException e) {
                // no need to validate this
            }
        }
    }

    @Test
    public void testFindAll() throws DotSecurityException, DotDataException, SystemException {
        String id;
        String userName;
        User user;

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);
        userAPI.save(user, systemUser, false);

        List users = UserUtil.findAll();

        assertNotNull(users);
        assertTrue(users.size() > 0);
        assertTrue(!users.contains(user));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test(expected = NoSuchUserException.class)
    public void testRemoveByCompanyId()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String id;
        String userName;
        User user;

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, false);

        Company company = PublicCompanyFactory.create("fake.org");
        company.setHomeURL("localhost");
        company.setPortalURL("localhost");
        company.setMx("fake.org");
        company.setName("fake.org");
        company.setShortName("fake.org");
        PublicCompanyFactory.update(company);
        user.setCompanyId(company.getCompanyId());

        userAPI.save(user, systemUser, false);

        roleAPI.removeRoleFromUser(roleAPI.getUserRole(user), user);

        UserUtil.removeByCompanyId(company.getCompanyId());

        try {
            PublicCompanyFactory.remove(company.getCompanyId());
        } catch (NoSuchCompanyException e) {
            // no need to validate this
        }
        UserUtil.findByC_EA(userName, user.getEmailAddress());

    }

    @Test
    public void testRemoveByCompanyIdMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String id;
        String userName;
        User user;

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);

        Company company = PublicCompanyFactory.create("fake.org");
        company.setHomeURL("localhost");
        company.setPortalURL("localhost");
        company.setMx("fake.org");
        company.setName("fake.org");
        company.setShortName("fake.org");
        PublicCompanyFactory.update(company);
        user.setCompanyId(company.getCompanyId());

        userAPI.save(user, systemUser, false);

        UserUtil.removeByCompanyId(company.getCompanyId());

        UserPool.remove(userName);

        assertNotNull(UserUtil.findByPrimaryKey(userName));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
            PublicCompanyFactory.remove(company.getCompanyId());
        } catch (NoSuchCompanyException | DotSecurityException e) {
            // no need to validate this
        }

    }

    @Test(expected = NoSuchUserException.class)
    public void testRemoveByC_U() throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();
        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, false);

        userAPI.save(user, systemUser, false);

        roleAPI.removeRoleFromUser(roleAPI.getUserRole(user), user);

        UserUtil.removeByC_U(companyId, userName);

        UserUtil.findByC_EA(userName, user.getEmailAddress());

    }

    @Test
    public void testRemoveByC_UMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();
        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);

        userAPI.save(user, systemUser, false);

        UserUtil.removeByC_U(companyId, userName);

        UserPool.remove(userName);

        assertNotNull(UserUtil.findByPrimaryKey(userName));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }

    }

    @Test(expected = NoSuchUserException.class)
    public void testRemoveByC_P() throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        id = String.valueOf(new Date().getTime());
        companyId = PublicCompanyFactory.getDefaultCompanyId();
        userName = "user" + id;
        user = getUser(userName, false);
        user.setPassword(userName);
        userAPI.save(user, systemUser, false);

        roleAPI.removeRoleFromUser(roleAPI.getUserRole(user), user);

        UserUtil.removeByC_P(companyId, userName);

        UserUtil.findByC_EA(userName, user.getEmailAddress());

    }

    @Test
    public void testRemoveByC_PMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        id = String.valueOf(new Date().getTime());
        companyId = PublicCompanyFactory.getDefaultCompanyId();
        userName = "user" + id;
        user = getUser(userName, true);
        user.setPassword(userName);
        userAPI.save(user, systemUser, false);

        UserUtil.removeByC_P(companyId, userName);

        UserPool.remove(userName);

        assertNotNull(UserUtil.findByPrimaryKey(userName));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }

    }

    @Test(expected = NoSuchUserException.class)
    public void testRemoveByC_EA() throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        id = String.valueOf(new Date().getTime());
        companyId = PublicCompanyFactory.getDefaultCompanyId();
        userName = "user" + id;
        user = getUser(userName, false);
        userAPI.save(user, systemUser, false);

        roleAPI.removeRoleFromUser(roleAPI.getUserRole(user), user);

        UserUtil.removeByC_EA(companyId, user.getEmailAddress());

        UserUtil.findByC_EA(userName, user.getEmailAddress());

    }

    @Test
    public void testRemoveByC_EAMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        id = String.valueOf(new Date().getTime());
        companyId = PublicCompanyFactory.getDefaultCompanyId();
        userName = "user" + id;
        user = getUser(userName, true);

        userAPI.save(user, systemUser, false);

        UserUtil.removeByC_EA(companyId, user.getEmailAddress());

        UserPool.remove(userName);

        assertNotNull(UserUtil.findByPrimaryKey(userName));

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }

    }

    @Test
    public void testCountByCompanyId() throws DotSecurityException, DotDataException, SystemException {
        String companyId;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        int total = UserUtil.countByCompanyId(companyId);

        assertTrue(total > 0);
    }

    @Test
    public void testCountByC_U() throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, false);
        userAPI.save(user, systemUser, false);

        int total = UserUtil.countByC_U(companyId, userName);

        assertTrue(total == 1);

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testCountByC_UMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);
        userAPI.save(user, systemUser, false);

        int total = UserUtil.countByC_U(companyId, userName);

        assertTrue(total == 0);

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testCountByC_P() throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, false);
        user.setPassword(userName);
        userAPI.save(user, systemUser, false);

        int total = UserUtil.countByC_P(companyId, userName);

        assertTrue(total == 1);

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testCountByC_PMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);
        user.setPassword(userName);
        userAPI.save(user, systemUser, false);

        int total = UserUtil.countByC_P(companyId, userName);

        assertTrue(total == 0);

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testCountByC_EA() throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, false);
        userAPI.save(user, systemUser, false);

        int total = UserUtil.countByC_EA(companyId, user.getEmailAddress());
        ;
        assertTrue(total == 1);

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    @Test
    public void testCountByC_EAMarkDeleted()
        throws DotSecurityException, DotDataException, SystemException, NoSuchUserException {
        String companyId;
        String id;
        String userName;
        User user;

        companyId = PublicCompanyFactory.getDefaultCompanyId();

        id = String.valueOf(new Date().getTime());
        userName = "user" + id;
        user = getUser(userName, true);
        userAPI.save(user, systemUser, false);

        int total = UserUtil.countByC_EA(companyId, user.getEmailAddress());

        assertTrue(total == 0);

        try {
            userAPI.delete(user, userAPI.getDefaultUser(), userAPI.getSystemUser(), false);
        } catch (DotSecurityException e) {
            // no need to validate this
        }
    }

    /**
     * Create a new user given a user name. Also, consider if the new user must be marked as an user to be deleted
     */
    private User getUser(String userName, boolean toBeDeleted)
        throws DotSecurityException, DotDataException {

        User user;
        user = userAPI.createUser(userName, userName + "@fake.org");

        user.setDeleteInProgress(toBeDeleted);

        if (toBeDeleted) {
            user.setDeleteDate(GregorianCalendar.getInstance().getTime());

        }

        return user;
    }

    private class UserUtilComparatorTest extends OrderByComparator {

        public String getOrderBy() {

            return "firstName ASC";
        }

        @Override
        public int compare(Object obj1, Object obj2) {
            User user1 = (User) obj1;
            User user2 = (User) obj2;

            return ((User) obj1).compareTo(obj2);
        }
    }
}
