/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinal.evento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import trabalhofinal.exception.DataInvalidaException;

/**
 *
 * @author Lucas
 */
public class Evento
{

    public static List<Integer> list_id = new ArrayList<>();

    private int id;
    private final TipoEvento type;
    private String tite;
    private String description;
    private String local;
    private Calendar date = Calendar.getInstance();
    private int duration;

    public Evento(String type)
    {
        this.type = TipoEvento.valueOf(type.toUpperCase());
        this.id = gerateID();
        list_id.add(id);
    }

    public Evento(String type, int id)
    {
        this.type = TipoEvento.valueOf(type.toUpperCase());
        this.id = id;
        list_id.add(id);
    }

    public Evento(TipoEvento type)
    {
        this(type.toString());
    }

    public Evento(TipoEvento type, int id)
    {
        this(type.toString(), id);
    }

    /**
     *
     * @return
     */
    public int getID()
    {
        return id;
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

    /**
     *
     * @param tite
     */
    public void setTite(String tite)
    {
        if (tite == null || tite.equals(""))
            throw new NullPointerException();

        this.tite = tite;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     *
     * @param date
     * @throws DataInvalidaException
     */
    public void setDate(String date) throws DataInvalidaException
    {
        try
        {
            this.date.setTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date));

            if (this.date.equals(Calendar.getInstance()))
                throw new DataInvalidaException("A data informada não é valida 1");

        } catch (ParseException ex)
        {
            throw new DataInvalidaException("A data informada não é valida");
        }
    }

    /**
     *
     * @param local
     */
    public void setLocal(String local)
    {
        this.local = local;
    }

    /**
     *
     * @param duration
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    /**
     *
     * @return
     */
    public TipoEvento getType()
    {
        return type;
    }

    /**
     *
     * @return
     */
    public String getTite()
    {
        return tite;
    }

    /**
     *
     * @return
     */
    public String getDescription()
    {
        return description;
    }

    /**
     *
     * @return
     */
    public String getLocal()
    {
        return local;
    }

    /**
     *
     * @return
     */
    public Calendar getDate()
    {
        return date;
    }

    /**
     *
     * @return
     */
    public int getDuration()
    {
        return duration;
    }

}
