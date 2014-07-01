/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinal.contato;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import trabalhofinal.exception.DataInvalidaException;

/**
 *
 * @author Lucas
 */
public class Contato
{

    public static List<Integer> list_id = new ArrayList<>();

    private int id;
    private String nome;
    private String email;
    private String endereco;
    private String cidade;
    private String estado;
    private int num_ddd;
    private int num_fone;
    private Date data_aniversario = new Date();

    public Contato()
    {
        this.id = gerateID();
        list_id.add(id);
    }

    public Contato(int id)
    {
        this.id = id;
        list_id.add(id);
    }

    /**
     *
     * @return
     */
    private int gerateID()
    {
        Random gerador = new Random();

        int numero = gerador.nextInt(100);
        while (list_id.contains(numero))
        {
            numero = gerador.nextInt(100);
        }

        return numero;
    }

    public int getID()
    {
        return id;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the endereco
     */
    public String getEndereco()
    {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco)
    {
        this.endereco = endereco;
    }

    /**
     * @return the cidade
     */
    public String getCidade()
    {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado()
    {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    /**
     * @return the num_fone
     */
    public int getNum_fone()
    {
        return num_fone;
    }

    /**
     * @param num_fone the num_fone to set
     */
    public void setNum_fone(int num_fone)
    {
        this.num_fone = num_fone;
    }

    /**
     * @return the num_ddd
     */
    public int getNum_ddd()
    {
        return num_ddd;
    }

    /**
     * @param num_ddd the num_ddd to set
     */
    public void setNum_ddd(int num_ddd)
    {
        this.num_ddd = num_ddd;
    }

    /**
     * @return the data_aniversario
     */
    public Date getData_aniversario()
    {
        return data_aniversario;
    }

    /**
     * @param data_aniversario the data_aniversario to set
     * @throws trabalhofinal.exception.DataInvalidaException
     */
    public void setData_aniversario(String data_aniversario) throws DataInvalidaException
    {
        try
        {
            this.data_aniversario = new SimpleDateFormat("dd/MM/yyyy").parse(data_aniversario);

        } catch (ParseException ex)
        {
            throw new DataInvalidaException("A data informada não é valida");
        }
    }
}
