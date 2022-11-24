package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);


        //TODO add here the entry point to your implementation
        GameInput joc = new GameInput();
        StartGameInput inceputjoc = new StartGameInput();

        DecksInput deck1 = new DecksInput();
        DecksInput deck2 = new DecksInput();

        deck1.setDecks(inputData.getPlayerOneDecks().getDecks());
        deck1.setNrDecks((inputData.getPlayerOneDecks().getNrDecks()));
        deck1.setNrCardsInDeck(inputData.getPlayerOneDecks().getNrCardsInDeck());

        ArrayList<Deck> listadecuri1 = new ArrayList<>();
        for (int deckNumber = 0; deckNumber < deck1.getNrDecks(); deckNumber++) {
            listadecuri1.add(new Deck(deck1.getNrCardsInDeck()));
            for (int cardNumber = 0; cardNumber < deck1.getNrCardsInDeck(); cardNumber++) {
                final CardInput cardInput = deck1.getDecks().get(deckNumber).get(cardNumber);
                if (cardInput.getHealth() > 0) {
                    final MinionCard card = new MinionCard(cardInput.getMana(),
                            cardInput.getAttackDamage(), cardInput.getHealth(), cardInput.getDescription(),
                            cardInput.getColors(), cardInput.getName());
                    listadecuri1.get(deckNumber).addMinionCard(card);
                } else {
                    final Card card = new Card(cardInput.getMana(), cardInput.getDescription(), cardInput.getColors(), cardInput.getName());
                    listadecuri1.get(deckNumber).addCard(card);
                }
            }
        }

        deck2.setDecks(inputData.getPlayerTwoDecks().getDecks());
        deck2.setNrDecks((inputData.getPlayerTwoDecks().getNrDecks()));
        deck2.setNrCardsInDeck(inputData.getPlayerTwoDecks().getNrCardsInDeck());

        ArrayList<Deck> listadecuri2 = new ArrayList<>();
        for (int deckNumber = 0; deckNumber < deck1.getNrDecks(); deckNumber++) {
            listadecuri2.add(new Deck(deck2.getNrCardsInDeck()));
            for (int cardNumber = 0; cardNumber < deck2.getNrCardsInDeck(); cardNumber++) {
                final CardInput cardInput = deck2.getDecks().get(deckNumber).get(cardNumber);
                if (cardInput.getHealth() > 0) {
                    final MinionCard card = new MinionCard(cardInput.getMana(), cardInput.getAttackDamage(),
                            cardInput.getHealth(), cardInput.getDescription(), cardInput.getColors(), cardInput.getName());
                    listadecuri2.get(deckNumber).addCard(card);
                } else {
                    final Card card = new Card(cardInput.getMana(), cardInput.getDescription(), cardInput.getColors(), cardInput.getName());
                    listadecuri2.get(deckNumber).addCard(card);
                }
            }
        }

        joc = inputData.getGames().get(0);
        inceputjoc = joc.getStartGame();
        HeroCard erou1 = new HeroCard(inceputjoc.getPlayerOneHero().getMana(),
                inceputjoc.getPlayerOneHero().getDescription(),
                inceputjoc.getPlayerOneHero().getColors(),
                inceputjoc.getPlayerOneHero().getName(),
                inceputjoc.getPlayerOneHero().getHealth());
        HeroCard erou2 = new HeroCard(inceputjoc.getPlayerTwoHero().getMana(),
                inceputjoc.getPlayerTwoHero().getDescription(),
                inceputjoc.getPlayerTwoHero().getColors(), inceputjoc.getPlayerTwoHero().getName(),
                inceputjoc.getPlayerTwoHero().getHealth());

        ArrayList<Actions> actiuni = new ArrayList<>();
        for (int numberactions = 0; numberactions < joc.getActions().size(); numberactions++) {
            Actions act = new Actions(joc.getActions().get(numberactions).getCommand(), joc.getActions().get(numberactions).getPlayerIdx());
            actiuni.add(act);
        }
        Game game = new Game(inceputjoc.getPlayerOneDeckIdx(),
                inceputjoc.getPlayerTwoDeckIdx(), inceputjoc.getShuffleSeed(), inceputjoc.getStartingPlayer(), erou1, erou2, actiuni);

        Collections.shuffle(listadecuri2.get(game.getPlayerId2()).getCards(), new Random(game.shuffleSeed));
        Collections.shuffle(listadecuri1.get(game.getPlayerId1()).getCards(), new Random(game.shuffleSeed));

        ArrayNode output = objectMapper.createArrayNode();

        output.add(adaugaNodDeck(listadecuri2.get(game.getPlayerId2()).getCards(),
                game.actiuni.get(0).getCommand(), actiuni.get(0).getPlayerId(), objectMapper));
        output.add(adaugaNodDeck(listadecuri1.get(game.playerId1).getCards(),
                game.actiuni.get(1).getCommand(), actiuni.get(1).getPlayerId(), objectMapper));
        output.add(adaugaNodDeckHero(game.hero1, game.actiuni.get(2).getCommand(),
                actiuni.get(1).getPlayerId(), objectMapper));
        output.add(adaugaNodDeckHero(game.hero2, game.actiuni.get(3).getCommand(),
                actiuni.get(0).getPlayerId(), objectMapper));
        output.add(adaugaComandaFinal(game.getActiuni().get(4).getCommand(),
                2, objectMapper));


        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter()
                .withObjectIndenter(new DefaultIndenter())).withRootValueSeparator("\n");
        objectWriter.writeValue(new File(filePath2), output);

    }


    private static ObjectNode adaugaComandaFinal(String command, int playerIdx, ObjectMapper mapper) {
        final ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("output", playerIdx);
        return objectNode;
    }


    private static ObjectNode adaugaNodDeck(ArrayList<Card> listeCarti, String command, int playerIdx, ObjectMapper mapper) {
        final ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("playerIdx", playerIdx);
        final ArrayNode arrayNode = mapper.createArrayNode();
        for (int numarcarte = 1; numarcarte < listeCarti.size(); numarcarte++) {
            arrayNode.add(adadugaNodOutput(listeCarti.get(numarcarte), mapper));
        }
        objectNode.put("output", arrayNode);

        return objectNode;
    }

    private static ObjectNode adadugaNodOutput(Card carte, ObjectMapper mapper) {

        if (carte instanceof MinionCard) {
            return adaugaNodCarteMinion((MinionCard) carte, mapper);

        } else {
            return adaugaNodCarteNormala(carte, mapper);
        }

    }

    private static ObjectNode adaugaNodCarteMinion(MinionCard cartejoc, ObjectMapper mapper) {
        final ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("mana", cartejoc.getMana());
        objectNode.put("attackDamage", cartejoc.getAttackdamge());
        objectNode.put("health", cartejoc.getHealth());
        objectNode.put("description", cartejoc.getDescription());
        ArrayNode culori = mapper.createArrayNode();
        for (int i = 0; i < cartejoc.getColors().size(); i++) {
            culori.add(cartejoc.getColors().get(i));
        }
        objectNode.put("colors", culori);
        objectNode.put("name", cartejoc.getName());

        return objectNode;
    }

    private static ObjectNode adaugaNodCarteNormala(Card cartejoc, ObjectMapper mapper) {
        final ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("mana", cartejoc.getMana());
        objectNode.put("description", cartejoc.getDescription());
        ArrayNode culori = mapper.createArrayNode();
        for (int i = 0; i < cartejoc.getColors().size(); i++) {
            culori.add(cartejoc.getColors().get(i));
        }
        objectNode.put("colors", culori);
        objectNode.put("name", cartejoc.getName());


        return objectNode;
    }


    private static ObjectNode adaugaNodDeckHero(HeroCard cartejoc, String command, int playerIdx, ObjectMapper mapper) {
        final ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("command", command);
        objectNode.put("playerIdx", playerIdx);


        final ObjectNode arrayNode = mapper.createObjectNode();

        arrayNode.put("mana", cartejoc.getMana());
        arrayNode.put("description", cartejoc.getDescription());
        ArrayNode culori = mapper.createArrayNode();
        for (int i = 0; i < cartejoc.getColors().size(); i++) {
            culori.add(cartejoc.getColors().get(i));
        }
        arrayNode.put("colors", culori);
        arrayNode.put("name", cartejoc.getName());
        cartejoc.setHealth(30);
        arrayNode.put("health", cartejoc.getHealth());

        objectNode.put("output", arrayNode);

        return objectNode;
    }


}
