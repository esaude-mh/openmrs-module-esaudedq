package org.openmrs.module.esaudedq.dataintegrity;

import org.hibernate.Query;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataintegrity.DataIntegrityRule;
import org.openmrs.module.dataintegrity.rule.RuleResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InvalidDemographicsRuleDefinition extends BasePatientRuleDefinition{

    @Override
    public List<RuleResult<Patient>> evaluate() {
        List<RuleResult<Patient>> ruleResults = new ArrayList<RuleResult<Patient>>();
        ruleResults.addAll(missingGender());
        return ruleResults;
    }

    public List<RuleResult<Patient>> missingGender() {
        List<RuleResult<Patient>> ruleResults = new ArrayList<RuleResult<Patient>>();
        Set<Patient> uniquePatientList = new HashSet<Patient>();
        log.info("Checking for patients who have missing gender");
        String queryString = "SELECT p FORM Patient p WHERE p.person.gender=null";
        Query query = getSession().createQuery(queryString);
        List<Patient> patientList = query.list();
        for(Patient patient : patientList) {
            RuleResult<Patient> ruleResult = new RuleResult<Patient>();
            if(!uniquePatientList.contains(patient)) {
                ruleResult.setActionUrl("htmlformentryui/htmlform/editHtmlFormWithStandardUi.page?patientId=" + patient.getUuid());
                ruleResult.setNotes("Missing gender");
                ruleResult.setEntity(patient);
                ruleResults.add(ruleResult);
            }
            uniquePatientList.add(patient);
        }
        return ruleResults;
    }

    @Override
    public DataIntegrityRule getRule() {
        DataIntegrityRule rule = new DataIntegrityRule();
        rule.setRuleCategory("patient");
        rule.setHandlerConfig("java");
        rule.setHandlerClassname(getClass().getName());
        rule.setRuleName("Invalid Patient Demographics");
        rule.setUuid("debedbe2-994e-11e7-aa54-13777bc58006");
        return rule;
    }
}
