package no.fintlabs.consumer.exception;

public class WrongOrgIdException extends Exception {
    public WrongOrgIdException(String wrongOrgId) {
        super(wrongOrgId == null ? "OrgId can not be null." : "The orgId " + wrongOrgId + " is not valid.");
    }
}
