/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabalhofinal.contato.Contato;
import trabalhofinal.evento.Evento;
import trabalhofinal.evento.TipoEvento;
import trabalhofinal.exception.DataInvalidaException;

/**
 *
 * @author Lucas
 */
public class Storage
{

    private static HashMap<Integer, Evento> eventos = new HashMap<>();
    private static HashMap<Integer, Contato> contatos = new HashMap<>();

    private static File directory = new File("events");

    /**
     * Retorna a lista de eventos
     *
     * @return
     */
    public static HashMap<Integer, Evento> getEventos()
    {
        return eventos;
    }

    /**
     * Retorna a lista de contatos
     *
     * @return
     */
    public static HashMap<Integer, Contato> getContatos()
    {
        return contatos;
    }

    /**
     * Salva o evento em arquivo
     *
     * @param data
     * @throws IOException
     */
    public static void saveToFile(Evento data) throws IOException
    {
        if (!directory.exists())
            directory.mkdir();

        int id = data.getID();

        File file = new File(directory, id + ".txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(file, true));

        br.append(serialize((Evento) data));
        br.newLine();

        eventos.put(data.getID(), data);

        br.close();
    }

    /**
     * Salva o contato para o arquivo
     *
     * @param data
     * @throws IOException
     */
    public static void saveToFile(Contato data) throws IOException
    {
        if (!directory.exists())
            directory.mkdir();

        int id = data.getID();

        File file = new File("contatos.txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(file, true));

        br.append(serialize((Contato) data));
        br.newLine();

        contatos.put(data.getID(), data);

        br.close();
    }

    /**
     * Le os dados dos arquivos correspondentes
     */
    public static void loadFromFile()
    {
        try
        {
            File file_c = new File("contatos.txt");

            if (!file_c.exists())
                file_c.createNewFile();

            BufferedReader in = new BufferedReader(new FileReader(file_c));
            while (in.ready())
            {
                String[] line = in.readLine().split(";");

                Contato contato = new Contato(Integer.valueOf(line[0]));

                for (int i = 1; i < line.length; i++)
                {
                    String[] info = line[i].split("=");

                    if (info[0].equals("null"))
                        continue;

                    switch (info[0])
                    {
                        case "nome":
                            contato.setNome(info[1]);
                            break;

                        case "email":
                            contato.setEmail(info[1]);
                            break;

                        case "endereco":
                            contato.setEndereco(info[1]);
                            break;

                        case "cidade":
                            contato.setCidade(info[1]);
                            break;

                        case "estado":
                            contato.setEstado(info[1]);
                            break;

                        case "numero":
                            contato.setNum_ddd(Integer.valueOf(info[1].split(" ")[0]));
                            contato.setNum_fone(Integer.valueOf(info[1].split(" ")[1]));
                            break;

                        case "aniversario":
                            contato.setData_aniversario(info[1]);
                            break;
                    }
                }
                contatos.put(contato.getID(), contato);
            }
            in.close();

        } catch (IOException ex)
        {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataInvalidaException ex)
        {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        }

        File[] files = directory.listFiles();

        if (!directory.exists())
            return;

        for (File file : files)
        {
            try
            {
                BufferedReader in = new BufferedReader(new FileReader(file));
                while (in.ready())
                {
                    String[] line = in.readLine().split(";");

                    Evento evento = null;
                    for (int i = 0; i < line.length; i++)
                    {
                        String[] info = line[i].split("=");

                        if (info[1].equals("null"))
                            continue;

                        switch (info[0])
                        {
                            case "type":
                                evento = new Evento(TipoEvento.valueOf(info[1]), Integer.valueOf(file.getName().substring(0, 2)));
                                break;

                            case "tite":
                                evento.setTite(info[1]);
                                break;

                            case "description":
                                evento.setDescription(info[1]);
                                break;

                            case "local":
                                evento.setLocal(info[1]);
                                break;

                            case "date":
                                evento.setDate(info[1]);
                                break;

                            case "duration":
                                evento.setDuration(Integer.valueOf(info[1]));
                                break;
                        }
                    }

                    eventos.put(evento.getID(), evento);
                }
                in.close();

            } catch (IOException ex)
            {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DataInvalidaException ex)
            {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Converte o evento para String
     *
     * @param event
     * @return
     */
    private static String serialize(Evento event)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("type=%s;", event.getType()));
        sb.append(String.format("tite=%s;", event.getTite()));

        if (event.getDescription() != null)
            sb.append(String.format("description=%s;", event.getDescription()));
        else
            sb.append("description=null;");

        if (event.getLocal() != null)
            sb.append(String.format("local=%s;", event.getLocal()));
        else
            sb.append("local=null;");

        if (event.getDate() != null)
            sb.append(String.format("date=%s;", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(event.getDate().getTime())));
        else
            sb.append("date=null;");

        sb.append(String.format("duration=%s;", event.getDuration()));

        return sb.toString();
    }

    /**
     * Converte o contato para string
     *
     * @param contato
     * @return
     */
    private static String serialize(Contato contato)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%d;", contato.getID()));
        sb.append(String.format("nome=%s;", contato.getNome()));

        if (contato.getEmail() != null)
            sb.append(String.format("email=%s;", contato.getEmail()));
        else
            sb.append("email=null;");

        if (contato.getEndereco() != null)
            sb.append(String.format("endereco=%s;", contato.getEndereco()));
        else
            sb.append("endereco=null;");

        if (contato.getCidade() != null)
            sb.append(String.format("cidade=%s;", contato.getCidade()));
        else
            sb.append("cidade=null;");

        if (contato.getEstado() != null)
            sb.append(String.format("estado=%s;", contato.getEstado()));
        else
            sb.append("estado=null;");

        if (contato.getNum_ddd() != 0 && contato.getNum_fone() != 0)
            sb.append(String.format("numero=%d %d;", contato.getNum_ddd(), contato.getNum_fone()));
        else
            sb.append("numero=0 0;");

        if (contato.getData_aniversario() != null)
            sb.append(String.format("aniversario=%s;", new SimpleDateFormat("dd/MM/yyyy").format(contato.getData_aniversario())));
        else
            sb.append("aniversario=null;");

        return sb.toString();
    }

    /**
     * Faz a juntcao de string
     *
     * @param <T>
     * @param array
     * @param cement
     * @return
     */
    public static <T> String join(T[] array, String cement)
    {
        StringBuilder builder = new StringBuilder();

        if (array == null || array.length == 0)
        {
            return null;
        }

        for (T t : array)
        {
            builder.append(t).append(cement);
        }

        builder.delete(builder.length() - cement.length(), builder.length());

        return builder.toString();
    }

    static void reloadContact()
    {
        try
        {
            File file = new File("contatos.txt");

            file.delete();
            file.createNewFile();

            for (Map.Entry<Integer, Contato> entry : contatos.entrySet())
            {
                Contato contato = entry.getValue();
                saveToFile(contato);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
