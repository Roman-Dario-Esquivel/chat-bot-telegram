package es.codegym.telegrambot;

import java.util.Random;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.groupadministration.DeleteChatStickerSet;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyFirstTelegramBot extends MultiSessionTelegramBot {

    public static final String NAME = "RomanJavaBot_bot";
    public static final String TOKEN = "6890569864:AAFNbfT-6-69ftzZqmcG2DTV1Iks4t2Av2U";
    private String[] motivarChiste = {"¿Qué hace una abeja en el gimnasio? ¡Zum-ba!",
        "¿Cuál es el colmo de un electricista? ¡No tener corriente para trabajar!",
        "¿Por qué los pájaros no usan Facebook? ¡Porque ya tienen Twitter!",
        "¿Cómo llama Superman a su madre? ¡Supermadre!",
        "¿Cuál es el animal más antiguo? ¡La cebra, porque está en blanco y negro!",
        "¿Qué le dice una iguana a su hermana gemela? ¡Iguanita!",
        "¿Qué hace una vaca en una montaña? ¡Leche condensada!",
        "¿Qué le dice un perro a otro perro? ¡Guau, guau!",
        "¿Qué hace una persona con un sobre de ketchup en la oreja? ¡Escuchando salsa!",
        "¿Qué le dice una uva verde a una roja? ¡¡Respira, respira!!"};
    private String[] motivarFrase = {"'El éxito no es la clave de la felicidad. La felicidad es la clave del éxito. Si amas lo que haces, tendrás éxito.' - Albert Schweitzer",
        "'La vida es 10% lo que me pasa y 90% cómo reacciono a ello.' - Charles R. Swindoll",
        "'No importa lo lento que vayas, siempre y cuando no te detengas.' - Confucio",
        "'Cree en ti mismo y todo será posible.' - Anónimo",
        "'El único modo de hacer un gran trabajo es amar lo que haces.' - Steve Jobs",
        "'No dejes que los sueños sean sueños, haz que se conviertan en realidad.' - Anónimo",
        "'El éxito no es definitivo, el fracaso no es fatal: es el coraje para continuar lo que cuenta.' - Winston Churchill",
        "'La actitud es una pequeña cosa que hace una gran diferencia.' - Anónimo",
        "'El único límite para tus logros es tu imaginación.' - Anónimo",
        "'No esperes a que pase la tormenta, aprende a bailar bajo la lluvia.' - Anónimo"};

    private int ban = 0;
    public MyFirstTelegramBot() {
        super(NAME, TOKEN);
    }

    @Override
    public void onUpdateEventReceived(Update update) {
        // TODO: escribiremos la funcionalidad principal del bot aquí
        
        if (getMessageText().equals("/start")
                || getMessageText().toLowerCase().contains("hola")
                || getMessageText().toLowerCase().contains("me llamo")
                || getMessageText().toLowerCase().contains("mi nombre es")
                || getMessageText().toLowerCase().contains("soy")) {
            ban = chatBot(update, ban);
        }
        if (getMessageText().toLowerCase().contains("reir")
                || getMessageText().toLowerCase().contains("frases")) {
            getTexto(update);
        }
        if (getMessageText().toLowerCase().contains("triste")
                || getMessageText().toLowerCase().contains("bien")) {
             ban=funtionFeelings(update);
        }
       

    }

    public int chatBot(Update update, int ban) {
        if (getMessageText().equals("/start")) {
            sendTextMessageAsync("hola  iniciando el bot de *RomanBot*");
            return 1;
        }
        if (getMessageText().toLowerCase().contains("hola") && ban == 1) {
            sendTextMessageAsync("Cual es tu nombre?\n las opcion para poner tu nombre son 'mi nombre es','me llamo' y 'soy'");
            return  2;
        } else if (getMessageText().toLowerCase().contains("hola") && ban == 0) {
            sendTextMessageAsync("Hola\nCual es tu nombre?\n las opcion para poner tu nombre son 'mi nombre es','me llamo' y 'soy'");
            return  2;
        }
        if (getMessageText().toLowerCase().contains("me llamo")
                || getMessageText().toLowerCase().contains("mi nombre es")
                || getMessageText().toLowerCase().contains("soy")) {
            String nombre = "";
            String frase = getMessageText().toLowerCase();
            nombre = frase.replaceFirst("me llamo", "");
            frase = nombre;
            if (frase.contains("mi nombre es") || frase.contains("soy")) {
                nombre = frase.replaceFirst("mi nombre es", "");
                if (frase.contains("soy")) {
                    nombre = frase.replaceFirst("soy", " ");
                    nombre = nombre.replaceAll("", "");
                    nombre = nombre.toUpperCase();
                    sendTextMessageAsync("Como estas *" + nombre + "*?\nYo me llamo *RomanBot*\n bien o triste");
                } else {
                    nombre = nombre.replaceAll(" ", "");
                    nombre = nombre.toUpperCase();
                    sendTextMessageAsync("Como estas *" + nombre + "*?\nYo me llamo *RomanBot*\n bien o triste");
                }
            } else {
                nombre = nombre.replaceAll(" ", "");
                nombre = nombre.toUpperCase();
                sendTextMessageAsync("Como estas *" + nombre + "*?\nYo me llamo *RomanBot*\n bien o triste");
            }
            
        }
        return 2;
    }
public int funtionFeelings(Update update){
    if (getMessageText().toLowerCase().contains("triste")) {
        Random rand = new Random();
        int chisteAleatorio = rand.nextInt(10);
        int fraseAleatorio = rand.nextInt(10);
        sendTextMessageAsync("aqui tienes un chiste: \n*" + motivarChiste[chisteAleatorio] + "*\n aqui tienes un frase: \n*" + motivarFrase[fraseAleatorio] + "*\n espero que te alla servido alguna \n *QUE INICIE EL JUEGO*");
    }else sendTextMessageAsync("*QUE INICIE EL JUEGO*");
    return 3;
}
    public void getTexto(Update update) {
        if (getMessageText().toLowerCase().contains("frases")) {
            String mensajeCompletoFrases = String.join("\n", motivarFrase);
            sendTextMessageAsync("frases: \n" + mensajeCompletoFrases);
        }
        if (getMessageText().toLowerCase().contains("reir")) {
            String mensajeCompletoChistes = String.join("\n", motivarChiste);
            sendTextMessageAsync("chistes: \n" + mensajeCompletoChistes);

        }
    }

    public static void main(String[] args) throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot());
    }
}
