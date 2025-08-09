/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package expensetracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDocumentController implements Initializable {

    // FXML UI Components
    @FXML private TableView<Expense> expenseTable;
    @FXML private TableColumn<Expense, String> descriptionColumn;
    @FXML private TableColumn<Expense, Double> amountColumn;
    @FXML private TableColumn<Expense, String> categoryColumn;
    
    @FXML private TextField descriptionField;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> categoryComboBox;
    
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    
    @FXML private Label totalExpensesLabel;
    @FXML private Label errorLabel;

    // ObservableList to store expenses and link to the TableView
    private final ObservableList<Expense> expenseList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Setup TableView columns
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        
        // 2. Load data into the TableView
        expenseTable.setItems(expenseList);
        
        // 3. Populate the category ComboBox
        categoryComboBox.getItems().addAll("Food", "Transport", "Shopping", "Utilities", "Entertainment", "Other");
        
        // 4. Add a listener to handle row selection in the table
        expenseTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showExpenseDetails(newValue));
        
        // 5. Initialize labels
        updateTotalExpenses();
        errorLabel.setText("");
    }    

    /**
     * FEATURE 1: Add a new expense
     */
    @FXML
    private void handleAddButton(ActionEvent event) {
        if (validateInput()) {
            String description = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryComboBox.getValue();
            
            expenseList.add(new Expense(description, amount, category));
            
            updateTotalExpenses();
            clearFields();
        }
    }

    /**
     * FEATURE 2: Update an existing expense
     */
    @FXML
    private void handleUpdateButton(ActionEvent event) {
        Expense selectedExpense = expenseTable.getSelectionModel().getSelectedItem();
        if (selectedExpense != null && validateInput()) {
            selectedExpense.setDescription(descriptionField.getText());
            selectedExpense.setAmount(Double.parseDouble(amountField.getText()));
            selectedExpense.setCategory(categoryComboBox.getValue());
            
            expenseTable.refresh(); // Refresh table to show changes
            updateTotalExpenses();
            clearFields();
        } else {
            errorLabel.setText("Select an expense to update.");
        }
    }

    /**
     * FEATURE 3: Delete a selected expense
     */
    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Expense selectedExpense = expenseTable.getSelectionModel().getSelectedItem();
        if (selectedExpense != null) {
            expenseList.remove(selectedExpense);
            updateTotalExpenses();
            clearFields();
        } else {
            errorLabel.setText("Select an expense to delete.");
        }
    }

    /**
     * FEATURE 4: Clear input fields
     */
    @FXML
    private void handleClearButton(ActionEvent event) {
        clearFields();
    }
    
    /**
     * FEATURE 5: Calculate and display total expenses
     */
    private void updateTotalExpenses() {
        double total = 0.0;
        for (Expense expense : expenseList) {
            total += expense.getAmount();
        }
        totalExpensesLabel.setText(String.format("$%.2f", total));
    }
    
    /**
     * Helper method to populate input fields when a row is selected.
     * @param expense The selected expense, or null if no row is selected.
     */
    private void showExpenseDetails(Expense expense) {
        if (expense != null) {
            descriptionField.setText(expense.getDescription());
            amountField.setText(String.valueOf(expense.getAmount()));
            categoryComboBox.setValue(expense.getCategory());
        } else {
            clearFields();
        }
    }
    
    /**
     * Helper method to clear all input fields.
     */
    private void clearFields() {
        descriptionField.clear();
        amountField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        expenseTable.getSelectionModel().clearSelection();
        errorLabel.setText("");
    }
    
    /**
     * Helper method to validate user input.
     * @return true if input is valid, false otherwise.
     */
    private boolean validateInput() {
        errorLabel.setText("");
        String desc = descriptionField.getText();
        String amountStr = amountField.getText();
        String category = categoryComboBox.getValue();

        if (desc == null || desc.trim().isEmpty()) {
            errorLabel.setText("Description cannot be empty.");
            return false;
        }
        if (amountStr == null || amountStr.trim().isEmpty()) {
            errorLabel.setText("Amount cannot be empty.");
            return false;
        }
        try {
            Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            errorLabel.setText("Amount must be a valid number.");
            return false;
        }
        if (category == null) {
            errorLabel.setText("Please select a category.");
            return false;
        }
        return true;
    }
}
