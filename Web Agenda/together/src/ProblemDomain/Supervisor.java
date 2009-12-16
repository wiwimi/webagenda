/* Generated by Together */

package ProblemDomain;

/**
 * Supervisors by default have full employee credentials, and are able to access all features that are available to employees.  In addition, supervisors may access special features, allowing them to add new employees in the system and create new workgroups, shifts and schedules. 
 */
public class Supervisor extends Employee {
    /**
     * A collection of all employees that the supervisor is in charge of, stored as a list of the ID's of those employees. 
     */
    private int[] employees;

    /**
     * A list of employee workgroups that the supervisor is in charge of.  This will include the super workgroup that all of the employees for that supervisor are a part of, and all workgroups that those employees have been divided into. 
     */
    private Workgroup[] workgroupList;

    /**
     * A supervisor may be in charge of multiple workgroups, which they have created for their employees. 
     * @clientCardinality 1
     * @supplierCardinality 1..*
     * @clientQualifier has
     * @supplierQualifier supervises
     */
    private Workgroup lnkWorkgroup;

    /**
     * Supervisors may view notifications created by the system, as well as manually create notifications to send to their employees. 
     * @clientCardinality 0..1
     * @supplierCardinality 0..*
     * @clientQualifier is sent to
     * @supplierQualifier receives
     */
    private Notification lnkNotification;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1..*
     * @clientQualifier has
     * @supplierQualifier is in charge of 
     */
    private Employee lnkEmployee;
}
