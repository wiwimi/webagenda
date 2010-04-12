/**
 * persistence - SkillBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.DBConnection;
import business.Skill;
import exception.DBChangeException;
import exception.DBDownException;
import exception.DBException;
import business.Employee;

/**
 * Provides functionality for adding, updating and deleting skills in the
 * database.
 * 
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class SkillBroker extends Broker<Skill>
    {
    
    /**
     * SkillBroker static object that is initialized when broker is returned.
     */
    private static volatile SkillBroker sbrok = null;
    
    /**
     * Constructor that creates a Broker Connection Manager
     */
    private SkillBroker()
        {
        super.initConnectionThread();
        }
    
    /**
     * Method to return an initialized instance of this broker class
     * 
     * @return SkillBroker
     */
    public static SkillBroker getBroker()
        {
        if (sbrok == null)
            sbrok = new SkillBroker();
        return sbrok;
        }
    
    /*
     * (non-Javadoc)
     * @see persistence.Broker#create(business.BusinessObject,
     * business.Employee)
     */
    @Override
    public boolean create(Skill createObj, Employee caller) throws DBException, DBDownException
        {
        if (createObj == null)
            throw new NullPointerException("Can not create null Skill.");
        
        if (createObj.getName() == null)
            throw new DBException("Missing Required Fields: Name");
        
        /*
         * Create insert string.
         */
        String insert = String.format(
                "INSERT INTO `WebAgenda`.`SKILL` " +
                        "(`skillName`, `skillDescription`)" +
                        " VALUES (%s,%s);",
                "'" + createObj.getName() + "'",
                (createObj.getDesc() == null ? "NULL" : "'" + createObj.getDesc() + "'"));
        
        /*
         * Send insert to database. SQL errors such as primary key already in
         * use will be caught, and turned into our own DBException, so this
         * method will only have one type of exception that needs to be caught.
         * If the insert is successful, return true.
         */
        try
            {
            System.out.println(insert);
            DBConnection conn = this.getConnection();
            Statement stmt = conn.getConnection().createStatement();
            int result = stmt.executeUpdate(insert);
            conn.setAvailable(true);
            
            if (result != 1)
                throw new DBException(
                        "Failed to create skill, result count incorrect: " +
                                result);
            }
        catch (SQLException e)
            {
            throw new DBException("Failed to create skill.", e);
            }
        
        return true;
        }
    
    /*
     * (non-Javadoc)
     * @see persistence.Broker#delete(business.BusinessObject,
     * business.Employee)
     */
    @Override
    public boolean delete(Skill deleteObj, Employee caller) throws DBChangeException, DBException,
            DBDownException
        {
        if (deleteObj == null)
            throw new NullPointerException("Can not delete null skill.");
        
        if (deleteObj.getName() == null)
            throw new DBException("Missing Required Field: Name");
        
        String delete = String.format(
                "DELETE FROM `WebAgenda`.`SKILL` WHERE skillName = '%s' AND skillDescription %s;",
                deleteObj.getName(),
                (deleteObj.getDesc() == null ? "IS NULL" : "= '" + deleteObj.getDesc() + "'"));
        
        try
            {
            DBConnection conn = this.getConnection();
            Statement stmt = conn.getConnection().createStatement();
            int result = stmt.executeUpdate(delete);
            
            // Ensure row was updated.
            if (result != 1)
                throw new DBChangeException(
                        "Skill not found, may have been changed or deleted by another user.");
            }
        catch (SQLException e)
            {
            throw new DBException("Failed to delete skill.", e);
            }
        
        return true;
        }
    
    /*
     * (non-Javadoc)
     * @see persistence.Broker#get(business.BusinessObject, business.Employee)
     */
    @Override
    public Skill[] get(Skill searchTemplate, Employee caller)
            throws DBException, DBDownException
        {
        if (searchTemplate == null)
            throw new NullPointerException("Can not search with null template.");
        
        String select = "SELECT * FROM `WebAgenda`.`SKILL` WHERE ";
        String comp = "";
        
        if (searchTemplate.getName() != null)
            comp = "skillName LIKE '%" + searchTemplate.getName() + "%'";
        if (searchTemplate.getDesc() != null)
            comp = comp + (searchTemplate.getDesc() != null ? (comp.equals("") ? "" : " AND ") +
                    "skillDescription LIKE '%" + searchTemplate.getDesc() + "%'" : "");
        
        if (comp.equals(""))
            {
            // Nothing being searched for, return null.
            return null;
            }
        
        // Add comparisons and close select statement.
        select = select + comp + ";";
        
        // Get DB connection, send query, and reopen connection for other users.
        // Parse returned ResultSet into array of skills.
        Skill[] foundSkills;
        try
            {
            DBConnection conn = this.getConnection();
            Statement stmt = conn.getConnection().createStatement();
            ResultSet searchResults = stmt.executeQuery(select);
            conn.setAvailable(true);
            
            foundSkills = parseResults(searchResults);
            }
        catch (SQLException e)
            {
            throw new DBException("Failed to complete skill search.", e);
            }
        
        // Return employees that matched search.
        return foundSkills;
        }
    
    /*
     * (non-Javadoc)
     * @see persistence.Broker#update(business.BusinessObject,
     * business.BusinessObject, business.Employee)
     */
    @Override
    public boolean update(Skill oldSkill, Skill updateSkill, Employee caller)
            throws DBChangeException, DBException, DBDownException
        {
        if (updateSkill == null)
            throw new NullPointerException("Update skill must not be null.");
        if (oldSkill == null)
            throw new NullPointerException("Old skill must not be null.");
        
        if (updateSkill.getName() == null)
            throw new NullPointerException("Update skill missing required field: Name");
        if (oldSkill.getName() == null)
            throw new NullPointerException("Old skill missing required field: Name");
        
        // Create sql update statement from employee object.
        String update = String
                .format(
                        "UPDATE `WebAgenda`.`SKILL` SET skillName = '%s', skillDescription = %s WHERE skillName = '%s' AND skillDescription %s;",
                        updateSkill.getName(),
                        (updateSkill.getDesc() == null ? "NULL" : "'" + updateSkill.getDesc() + "'"),
                        oldSkill.getName(),
                        (oldSkill.getDesc() == null ? "IS NULL" : "= '" + oldSkill.getDesc() + "'"));
        
        // Get DB connection, send update, and reopen connection for other
        // users.
        try
            {
            DBConnection conn = this.getConnection();
            Statement stmt = conn.getConnection().createStatement();
            int updateRowCount = stmt.executeUpdate(update);
            conn.setAvailable(true);
            
            // Ensure row as updated.
            if (updateRowCount != 1)
                throw new DBChangeException(
                        "Skill not found, may have been changed or deleted by another user.");
            }
        catch (SQLException e)
            {
            throw new DBException("Failed to update skill.", e);
            }
        
        return true;
        }
    
    /*
     * (non-Javadoc)
     * @see persistence.Broker#parseResults(java.sql.ResultSet)
     */
    @Override
    protected Skill[] parseResults(ResultSet rs) throws SQLException
        {
        // List will be returned as null if no results are found.
        Skill[] skillList = null;
        
        if (rs.last())
            {
            // Results exist, get total number of rows to create array of same
            // size.
            int resultCount = rs.getRow();
            skillList = new Skill[resultCount];
            
            // Return ResultSet to beginning to start retrieving skill.
            rs.beforeFirst();
            for (int i = 0; i < resultCount && rs.next(); i++)
                {
                Skill skill = new Skill(rs.getString("skillName"), rs.getString("skillDescription"));
                skillList[i] = skill;
                }
            }
        
        return skillList;
        }
    
    }
