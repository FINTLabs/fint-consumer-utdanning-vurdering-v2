package no.fintlabs.consumer.exception;

public class WrongOrgIdException extends Exception{
    public WrongOrgIdException(String wrongOrgId){
        super("The " + wrongOrgId + " is not valid.");
    }
}
