/* Generated by Together */

package ProblemDomain;

/**
 * A workgroup is a collection of employees, grouped together by a supervisor.  It can be used to view workgroup specific schedules, or to print reports on a specific set of employees rather than all that the supervisor is in charge of. 
 */
public class Workgroup {
    /**
     * Retrieves a specific workgroup. 
     */
    public Workgroup getWorkgroup(){}

    public Shift[] getShifts(Workgroup wg){}

    public Schedule getSched(){}

    /**
     * A list of the employees that are in the workgroup. 
     */
    private Employee[] employees;

    /**
     * The supervisor that is in charge of this employee workgroup. 
     */
    private Supervisor supervisor;

    /**
     * The name of the workgroup, assigned by the supervisor who created it. 
     */
    private String name;

    /**
     * An employee may be part of multiple workgroups, which must be assigned by that employee's supervisor.
     * @link aggregation 
     * @clientCardinality 1..*
     * @supplierCardinality 0..*
     * @clientQualifier is part of
     * @supplierQualifier contains
     */
    private Employee lnkEmployee;
}
