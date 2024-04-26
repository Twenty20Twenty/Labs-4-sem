package ru.nstu.javafx_labs_lipatov_v2;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nstu.javafx_labs_lipatov_v2.mvc.HabitatModel;

public class UserConsole {
    @FXML
    private TextArea consoleText;
    public HabitatModel parentModel;

    @FXML
    void keyPressed(KeyEvent keyEvent) {
        keyEvent.consume();
        switch (keyEvent.getCode()) {
            case KeyCode.ENTER:
                readCommand();
                break;
            default:
                break;
        }
    }

    private void readCommand() {
        String[] arrayConsole = consoleText.getText().split("\n");
        String currentCommand = arrayConsole[arrayConsole.length - 1];
        currentCommand = currentCommand.replaceAll(">> ", "");
        switch (currentCommand) {
            case "Команды":
                consoleText.setText(consoleText.getText() + "\t\"Показать время\" - отобразить время симуляции.\n\t\"Скрыть время\" - скрыть время симуляции.\n");
                consoleText.setText(consoleText.getText() + ">> ");
                consoleText.end();
                break;
            case "Скрыть время":
                if (parentModel.timeFlag) {
                    parentModel.showTimer();
                    consoleText.setText(consoleText.getText() + "Время симуляции скрыто\n");
                    consoleText.setText(consoleText.getText() + ">> ");
                    consoleText.end();
                } else {
                    consoleText.setText(consoleText.getText() + "Время симуляции уже скрыто\n");
                    consoleText.setText(consoleText.getText() + ">> ");
                    consoleText.end();
                }
                break;
            case "Показать время":
                if (!parentModel.timeFlag) {
                    parentModel.showTimer();
                    consoleText.setText(consoleText.getText() + "Время симуляции показано\n");
                    consoleText.setText(consoleText.getText() + ">> ");
                    consoleText.end();
                } else {
                    consoleText.setText(consoleText.getText() + "Время симуляции уже отображенно\n");
                    consoleText.setText(consoleText.getText() + ">> ");
                    consoleText.end();
                }
                break;
            default:
                consoleText.setText(consoleText.getText() + "Введена неверная команда\n");
                consoleText.setText(consoleText.getText() + ">> ");
                consoleText.end();
                break;
        }
    }

    public TextArea getConsoleText() {
        return consoleText;
    }
}
