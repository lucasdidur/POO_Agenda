/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinal.exception;

/**
 *
 * @author Lucas
 */
public class DataInvalidaException extends Exception
{

    public DataInvalidaException(String message)
    {
        super(message);
    }

    public DataInvalidaException()
    {
        super("Data digitada é invalida");
    }

}
