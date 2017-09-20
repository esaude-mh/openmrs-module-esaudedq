package org.openmrs.module.esaudedq.dataintegrity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataintegrity.rule.RuleDefinition;

import java.text.DateFormat;

public abstract class BasePatientRuleDefinition implements RuleDefinition<Patient> {

    protected Log log = LogFactory.getLog(getClass());

    /**
     * A Session instance used by sub-classes
     * to be used when running the HQL queries
     *
     * @return
     */
    public Session getSession() {
        return Context.getRegisteredComponent("sessionFactory", SessionFactory.class).getCurrentSession();
    }

    /**
     * A formatter for dates
     *
     * @return
     */
    public DateFormat getDateFormatter() {
        return Context.getDateFormat();
    }
}
