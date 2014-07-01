/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhofinal;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import trabalhofinal.contato.Contato;
import trabalhofinal.evento.*;
import trabalhofinal.exception.DataInvalidaException;

/**
 *
 * @author Lucas
 */
public class TrabalhoFinal
{

    private static Scanner entrada = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Storage.loadFromFile();

        int choice;

        while (true)
        {
            System.out.println("********* Menu *********\n");

            System.out.println("  1 - Criar um Evento");
            System.out.println("  2 - Criar um Contato\n");

            System.out.println("  3 - Listar Eventos");
            System.out.println("  4 - Listar Contato\n");

            System.out.println("  5 - Apagar Contato\n");

            System.out.println("  9 - Sair");

            System.out.print("\nDigite a opção desejada: ");
            choice = entrada.nextInt();
            entrada.nextLine();

            switch (choice)
            {
                case 1:
                    createEvent();

                    break;
                case 2:
                    createContact();
                    break;

                case 3:
                    listEvents();
                    break;

                case 4:
                    listContact();
                    break;

                case 5:
                    deleteContact();
                    break;

                case 9:
                    entrada.close();
                    return;
            }
        }
    }
    

    public static void createEvent()
    {
        boolean valid = false;

        System.out.println("\n");
        System.out.println("Escolha o tipo do evento:");

        for (int i = 0; i < TipoEvento.values().length; i++)
        {
            String typeE = TipoEvento.values()[i].toString();
            System.out.println(String.format("\t%d - %s", i + 1, typeE));
        }

        TipoEvento type = TipoEvento.values()[entrada.nextInt() - 1];
        entrada.nextLine();

        Evento evento = new Evento(type);

        while (!valid)
        {
            try
            {
                System.out.print("Digite o titulo do evento: ");
                String tite = entrada.nextLine();
                evento.setTite(tite);

                valid = true;
            } catch (NullPointerException ex)
            {
                System.out.println("Titulo é obrigatorio\n");
            }
        }

        System.out.print("Digite uma descricao do evento: ");
        String description = entrada.nextLine();
        evento.setDescription(description);

        if (type == TipoEvento.LAZER || type == TipoEvento.TAREFA || type == TipoEvento.LEMBRETE || type == TipoEvento.REUNIAO)
        {
            valid = false;

            while (!valid)
            {
                try
                {
                    System.out.print("Digite a data do evento: ");
                    String date = entrada.nextLine();
                    evento.setDate(date);
                    valid = true;
                } catch (DataInvalidaException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }

        if (type == TipoEvento.LAZER || type == TipoEvento.TAREFA || type == TipoEvento.REUNIAO)
        {
            System.out.print("Digite o local: ");
            String local = entrada.nextLine();
            evento.setLocal(local);
        }

        if (type == TipoEvento.REUNIAO)
        {
            System.out.print("Digite a duracao do evento (em minutos): ");
            int duracao = entrada.nextInt();
            entrada.nextLine();
            evento.setDuration(duracao);
        }

        try
        {
            Storage.saveToFile(evento);
            System.out.println("Salvo!!\n\n");
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private static void createContact()
    {
        boolean valid = false;

        System.out.println("*****  Criar Contato *****");

        Contato contato = new Contato();

        System.out.print("Digite o Nome Compelto: ");
        contato.setNome(entrada.nextLine());

        System.out.print("Digite o Email: ");
        contato.setEmail(entrada.nextLine());

        System.out.print("Digite o Endereço: ");
        contato.setEndereco(entrada.nextLine());

        System.out.print("Digite a Cidade: ");
        contato.setCidade(entrada.nextLine());

        System.out.print("Digite o Estado: ");
        contato.setEstado(entrada.nextLine());

        System.out.print("Digite o Digite o Telefone (ddd numero): ");
        String[] numero = entrada.nextLine().split(" ");
        contato.setNum_ddd(Integer.valueOf(numero[0]));
        contato.setNum_fone(Integer.valueOf(numero[1]));

        while (!valid)
        {
            System.out.print("Digite a data de Aniversario: ");
            try
            {
                contato.setData_aniversario(entrada.nextLine());
                valid = true;
            } catch (DataInvalidaException ex)
            {
                System.out.println(ex.getMessage());
            }
        }

        try
        {
            Storage.saveToFile(contato);
            System.out.println("Salvo!!\n\n");
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private static void listEvents()
    {
        HashMap<Integer, Evento> eventos = Storage.getEventos();

        System.out.println("");
        for (Map.Entry<Integer, Evento> entry : eventos.entrySet())
        {
            Evento evento = entry.getValue();

            System.out.println(String.format("\t %d - %s", evento.getID(), evento.getType()));
            System.out.println(String.format("\t %s", evento.getTite()));
            System.out.println(String.format("\t %s\n", evento.getDescription()));

            if (evento.getType() == TipoEvento.LAZER || evento.getType() == TipoEvento.TAREFA || evento.getType() == TipoEvento.LEMBRETE || evento.getType() == TipoEvento.REUNIAO)
            {
                System.out.println(String.format("\t\t Data \t\t %s", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(evento.getDate().getTime())));
            }

            if (evento.getType() == TipoEvento.REUNIAO)
            {
                Calendar c = Calendar.getInstance();
                c.setTime(evento.getDate().getTime());
                c.add(Calendar.MINUTE, evento.getDuration());

                System.out.println(String.format("\t\t Duracao \t %d Minutos", evento.getDuration()));

                DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
                System.out.println(String.format("\t\t Data Final \t %s", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(c.getTime())));
            }

            if (evento.getType() == TipoEvento.LAZER || evento.getType() == TipoEvento.TAREFA || evento.getType() == TipoEvento.REUNIAO)
            {
                System.out.println(String.format("\t\t Local\t \t %s", evento.getLocal()));

            }

            System.out.println("");
            System.out.println("********************************************************************");
            System.out.println("");
        }
    }

    public static void listContact()
    {
        HashMap<Integer, Contato> contatos = Storage.getContatos();

        System.out.println("");
        for (Map.Entry<Integer, Contato> entry : contatos.entrySet())
        {
            Contato contato = entry.getValue();

            System.out.println(String.format("\t %d - %s", contato.getID(), contato.getNome()));
            System.out.println(String.format("\t Email: \t%s", contato.getEmail()));
            System.out.println(String.format("\t Endereco: \t%s", contato.getEndereco()));
            System.out.println(String.format("\t Cidade: \t%s", contato.getCidade()));
            System.out.println(String.format("\t Estado: \t%s", contato.getEstado()));
            System.out.println(String.format("\t Numero: \t%d %d", contato.getNum_ddd(), contato.getNum_fone()));
            System.out.println(String.format("\t Aniversario: \t%s", new SimpleDateFormat("dd/MM/yyyy").format(contato.getData_aniversario())));

            System.out.println("");
            System.out.println("********************************************************************");
            System.out.println("");
        }
    }

    private static void deleteContact()
    {
        System.out.println("Digite o ID do contato que deseja remover: ");
        
        int id = entrada.nextInt();
        
        HashMap<Integer, Contato> contatos = Storage.getContatos();
        
        contatos.remove(id);
        
        Storage.reloadContact();
        
        System.out.println("Contato apagado!!\n");
    }
}
